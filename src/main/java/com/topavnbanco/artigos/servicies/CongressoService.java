package com.topavnbanco.artigos.servicies;

import com.topavnbanco.artigos.dto.CongressoDTO;
import com.topavnbanco.artigos.entities.Congresso;
import com.topavnbanco.artigos.entities.User;
import com.topavnbanco.artigos.queryfilters.CongressoQueryFilter;
import com.topavnbanco.artigos.repositories.CongressoRepository;
import com.topavnbanco.artigos.repositories.UserRepository;
import com.topavnbanco.artigos.schedulers.CongressoLifecycleScheduler;
import com.topavnbanco.artigos.schedulers.CongressoReviewDeadlineScheduler;
import com.topavnbanco.artigos.schedulers.ReviewDailyCheckScheduler;
import com.topavnbanco.artigos.servicies.exceptions.DatabaseException;
import com.topavnbanco.artigos.servicies.exceptions.InvalidReviewRangeException;
import com.topavnbanco.artigos.servicies.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
public class CongressoService {

    @Autowired
    private CongressoRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CongressoLifecycleScheduler congressoScheduler;

    @Autowired
    private ReviewDailyCheckScheduler dailyCheckScheduler;

    @Autowired
    private CongressoReviewDeadlineScheduler reviewDeadlineScheduler;

    @Transactional(readOnly = true)
    public CongressoDTO findById(Long id) {
        Congresso congresso = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new CongressoDTO(congresso);
    }

    @Transactional(readOnly = true)
    public Page<CongressoDTO> findAll(CongressoQueryFilter filter, Pageable pageable) {
        Page<Congresso> result = repository.findAll(filter.toSpecification(), pageable);
        return result.map(CongressoDTO::new);
    }

    @Transactional
    public CongressoDTO insert(CongressoDTO dto) {
        validName(dto.getName());
        Congresso entity = new Congresso();
        copyDtoToEntity(dto, entity);
        validReviewsPerArticle(dto.getMinReviewsPerArticle(), dto.getMaxReviewsPerArticle());
        //apenas permace assim por conta dos testes
        entity.setSubmissionDeadline(Date.from(Instant.now().plusSeconds(100)));
        entity.setReviewDeadline(Date.from(Instant.now().plusSeconds(300)));
        entity = repository.save(entity);
        congressoScheduler.scheduleOnSubmissionDeadline(entity);
        dailyCheckScheduler.scheduleOnSubmissionDaily(entity);
        reviewDeadlineScheduler.scheduleOnReviewDeadline(entity);
        return new CongressoDTO(entity);
    }

    @Transactional
    public CongressoDTO update(Long id, CongressoDTO dto) {
        try {
            Congresso entity = repository.getReferenceById(id);
            validReviewsPerArticle(dto.getMinReviewsPerArticle(), dto.getMaxReviewsPerArticle());

            if (dto.getName() == null || dto.getName().isBlank()) {
                throw new DatabaseException("O nome do congresso é obrigatório.");
            }
            if (!dto.getName().equals(entity.getName()) &&
                    repository.existsByNameAndIdNot(dto.getName(), id)) {
                throw new DatabaseException("Já existe um congresso com esse nome.");
            }

            copyDtoToEntity(dto, entity);
            try {
                entity = repository.save(entity);
            } catch (DataIntegrityViolationException e) {
                throw new DatabaseException("Já existe um congresso com esse nome.");
            }
            return new CongressoDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    public void validName(String name) {
        if (repository.existsByName(name)) {
            throw new DatabaseException("Já existe um congresso com esse nome.");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void validReviewsPerArticle(Integer min, Integer max) {
        if (max < min) {
            throw new InvalidReviewRangeException("O número mínimo de revisões não pode ser maior que o máximo.");
        }
    }

    private void copyDtoToEntity(CongressoDTO dto, Congresso entity) {
        entity.setName(dto.getName());
        entity.setPlace(dto.getPlace());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setReviewDeadline(dto.getReviewDeadline());
        entity.setDescription(dto.getDescription());
        entity.setDescriptionTitle(dto.getDescriptionTitle());
        entity.setCongressoModality(dto.getCongressoModality());
        entity.setImageThumbnail(dto.getImageThumbnail());
        entity.setMinReviewsPerArticle(dto.getMinReviewsPerArticle());
        entity.setMaxReviewsPerArticle(dto.getMaxReviewsPerArticle());

        if (dto.getUsersIds() != null) {
            for (Long userId : dto.getUsersIds()) {
                User user = userRepository.getReferenceById(userId);
                user.setCongresso(entity);
                entity.getUser().add(user);
            }
        }

        if (dto.getKnowledgeArea() != null) {
            for (String area : dto.getKnowledgeArea()) {
                if (area != null && !area.isBlank()) {
                    entity.getKnowledgeArea().add(area.trim());
                }
            }
        }

    }
}
