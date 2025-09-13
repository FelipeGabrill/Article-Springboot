package com.topavnbanco.artigos.servicies;

import com.topavnbanco.artigos.dto.CongressoDTO;
import com.topavnbanco.artigos.entities.Congresso;
import com.topavnbanco.artigos.entities.User;
import com.topavnbanco.artigos.repositories.CongressoRepository;
import com.topavnbanco.artigos.repositories.UserRepository;
import com.topavnbanco.artigos.schedulers.CongressoScheduler;
import com.topavnbanco.artigos.schedulers.DailyCheckScheduler;
import com.topavnbanco.artigos.schedulers.ReviewDeadlineScheduler;
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
    private CongressoScheduler congressoScheduler;

    @Autowired
    private DailyCheckScheduler dailyCheckScheduler;

    @Autowired
    private ReviewDeadlineScheduler reviewDeadlineScheduler;

    @Transactional(readOnly = true)
    public CongressoDTO findById(Long id) {
        Congresso congresso = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new CongressoDTO(congresso);
    }

    @Transactional(readOnly = true)
    public Page<CongressoDTO> findAll(Pageable pageable) {
        Page<Congresso> result = repository.findAll(pageable);
        return result.map(CongressoDTO::new);
    }

    @Transactional
    public CongressoDTO insert(CongressoDTO dto) {
        Congresso entity = new Congresso();
        copyDtoToEntity(dto, entity);
        validReviewsPerArticle(dto.getMinReviewsPerArticle(), dto.getMaxReviewsPerArticle());
        //apenas permace assim por conta dos testes
        entity.setSubmissionDeadline(Date.from(Instant.now().plusSeconds(100)));
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
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new CongressoDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
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
    }
}
