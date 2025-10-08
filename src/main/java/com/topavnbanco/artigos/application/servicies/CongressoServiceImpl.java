package com.topavnbanco.artigos.application.servicies;

import com.topavnbanco.artigos.adapters.inbound.dtos.congresso.CongressoSimpleDTO;
import com.topavnbanco.artigos.application.servicies.exceptions.DatabaseException;
import com.topavnbanco.artigos.application.servicies.exceptions.InvalidReviewRangeException;
import com.topavnbanco.artigos.application.servicies.exceptions.ResourceNotFoundException;
import com.topavnbanco.artigos.application.usecases.CongressoUseCases;
import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.adapters.inbound.dtos.congresso.CongressoDTO;
import com.topavnbanco.artigos.domain.congresso.repository.CongressoRepository;
import com.topavnbanco.artigos.domain.user.User;
import com.topavnbanco.artigos.domain.user.repository.UserRepository;
import com.topavnbanco.artigos.infrastructure.queryfilters.CongressoQueryFilter;
import com.topavnbanco.artigos.infrastructure.schedulers.CongressoLifecycleScheduler;
import com.topavnbanco.artigos.infrastructure.schedulers.CongressoReviewDeadlineScheduler;
import com.topavnbanco.artigos.infrastructure.schedulers.ReviewDailyCheckScheduler;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
public class CongressoServiceImpl implements CongressoUseCases {

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
    public CongressoSimpleDTO findById(Long id) {
        Congresso congresso = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new CongressoSimpleDTO(congresso);
    }

    @Transactional(readOnly = true)
    public Page<CongressoSimpleDTO> findAll(CongressoQueryFilter filter, Pageable pageable) {
        Page<Congresso> result = repository.findAll(filter.toSpecification(), pageable);
        return result.map(CongressoSimpleDTO::new);
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

        byte[] thumb = decodeImageBase64(dto.getImageThumbnail());
        if (thumb != null && thumb.length > 0) {
            entity.setImageThumbnail(thumb);
        }

        entity.setMinReviewsPerArticle(dto.getMinReviewsPerArticle());
        entity.setMaxReviewsPerArticle(dto.getMaxReviewsPerArticle());

        if (dto.getKnowledgeArea() != null) {
            for (String area : dto.getKnowledgeArea()) {
                if (area != null && !area.isBlank()) {
                    entity.getKnowledgeArea().add(area.trim());
                }
            }
        }

    }

    private static byte[] decodeImageBase64(String base64) {
        if (base64 == null || base64.isBlank()) return null;
        int comma = base64.indexOf(',');
        String payload = (comma >= 0) ? base64.substring(comma + 1) : base64;
        try {
            return Base64.getDecoder().decode(payload);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Imagem do congresso inválida (Base64 incorreto).");
        }
    }
}
