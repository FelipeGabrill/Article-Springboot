package com.topavnbanco.artigos.servicies.review;

import com.topavnbanco.artigos.dto.ReviewDTO;
import com.topavnbanco.artigos.entities.Review;
import com.topavnbanco.artigos.repositories.ArticleRepository;
import com.topavnbanco.artigos.repositories.EvaluationRepository;
import com.topavnbanco.artigos.repositories.ReviewRepository;
import com.topavnbanco.artigos.servicies.exceptions.DatabaseException;
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
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Transactional(readOnly = true)
    public ReviewDTO findById(Long id) {
        Review review = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new ReviewDTO(review);
    }

    @Transactional(readOnly = true)
    public Page<ReviewDTO> findAll(Pageable pageable) {
        Page<Review> result = repository.findAll(pageable);
        return result.map(x -> new ReviewDTO(x));
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        Review entity = new Review();
        copyDtoToEntity(dto, entity);
        entity.setCreateAt(Date.from(Instant.now()));
        entity = repository.save(entity);
        return new ReviewDTO(entity);
    }

    @Transactional
    public ReviewDTO update(Long id, ReviewDTO dto) {
        try {
            Review entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ReviewDTO(entity);
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

    private void copyDtoToEntity(ReviewDTO dto, Review entity) {

        entity.setScore(dto.getScore());
        entity.setComment(dto.getComment());
    }
}
