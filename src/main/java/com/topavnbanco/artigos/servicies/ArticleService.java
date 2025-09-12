package com.topavnbanco.artigos.servicies;

import com.topavnbanco.artigos.dto.ArticleDTO;
import com.topavnbanco.artigos.entities.Article;
import com.topavnbanco.artigos.entities.Congresso;
import com.topavnbanco.artigos.entities.Review;
import com.topavnbanco.artigos.entities.User;
import com.topavnbanco.artigos.entities.enuns.ArticleFormat;
import com.topavnbanco.artigos.entities.enuns.ReviewStatus;
import com.topavnbanco.artigos.repositories.ArticleRepository;
import com.topavnbanco.artigos.repositories.CongressoRepository;
import com.topavnbanco.artigos.repositories.ReviewRepository;
import com.topavnbanco.artigos.repositories.UserRepository;
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
import java.util.List;
import java.util.Set;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CongressoRepository congressoRepository;

    @Transactional(readOnly = true)
    public ArticleDTO findById(Long id) {
        Article article = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new ArticleDTO(article);
    }

    @Transactional(readOnly = true)
    public Page<ArticleDTO> findAll(Pageable pageable) {
        Page<Article> result = repository.findAll(pageable);
        return result.map(x -> new ArticleDTO(x));
    }

    @Transactional
    public ArticleDTO insert(ArticleDTO dto) {
        Article entity = new Article();
        copyDtoToEntity(dto, entity);
        entity.setFormat(ArticleFormat.PDF);
        entity.setPublishedAt(Instant.now());
        entity.setStatus(ReviewStatus.PENDING);
        entity = repository.save(entity);
        return new ArticleDTO(entity);
    }

    @Transactional
    public ArticleDTO update(Long id, ArticleDTO dto) {
        try {
            Article entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ArticleDTO(entity);
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

    private void copyDtoToEntity(ArticleDTO dto, Article entity) {

        Congresso congresso = congressoRepository.findById(dto.getCongressoId()).orElseThrow(() -> new ResourceNotFoundException("Congresso não encontrado"));

        entity.setDescription(dto.getDescription());
        entity.setFormat(dto.getFormat());
        entity.setBody(dto.getBody());
        entity.setCongresso(congresso);
        entity.setTitle(dto.getTitle());

        Set<Long> userIds = dto.getArticlesUsersIds();
        if (userIds != null && !userIds.isEmpty()) {
            for (Long userId : userIds) {
                boolean exists = entity.getArticlesUsers() != null &&
                        entity.getArticlesUsers().stream().anyMatch(u -> u.getId().equals(userId));
                if (!exists) {
                    User ref = userRepository.getReferenceById(userId);
                    entity.getArticlesUsers().add(ref);
                }
            }
        }

        List<Long> reviewIds = dto.getReviewsIds();
        if (reviewIds != null && !reviewIds.isEmpty()) {
            for (Long reviewId : reviewIds) {
                boolean exists = entity.getReview() != null &&
                        entity.getReview().stream().anyMatch(r -> r.getId().equals(reviewId));
                if (!exists) {
                    Review ref = reviewRepository.getReferenceById(reviewId);
                    entity.getReview().add(ref);
                }
            }
        }
    }
}
