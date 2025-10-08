package com.topavnbanco.artigos.application.servicies;

import com.topavnbanco.artigos.adapters.inbound.dtos.article.ArticleSimpleDTO;
import com.topavnbanco.artigos.application.servicies.exceptions.DatabaseException;
import com.topavnbanco.artigos.application.servicies.exceptions.ResourceNotFoundException;
import com.topavnbanco.artigos.application.usecases.ArticleUseCases;
import com.topavnbanco.artigos.domain.article.Article;
import com.topavnbanco.artigos.adapters.inbound.dtos.article.ArticleDTO;
import com.topavnbanco.artigos.domain.article.enuns.ArticleFormat;
import com.topavnbanco.artigos.domain.article.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.domain.article.repository.ArticleRepository;
import com.topavnbanco.artigos.domain.congresso.Congresso;
import com.topavnbanco.artigos.domain.congresso.repository.CongressoRepository;
import com.topavnbanco.artigos.domain.user.repository.UserRepository;
import com.topavnbanco.artigos.infrastructure.queryfilters.ArticleQueryFilter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleUseCases {

    @Autowired
    private ArticleRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private CongressoRepository congressoRepository;

    @Transactional(readOnly = true)
    public ArticleSimpleDTO findById(Long id) {
        Article article = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new ArticleSimpleDTO(article);
    }

    @Transactional(readOnly = true)
    public String findArticleBodyById(Long id) {
        byte[] bodyBytes = repository.findById(id)
                .map(Article::getBody)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

        return Base64.getEncoder().encodeToString(bodyBytes);
    }

    @Transactional(readOnly = true)
    public Page<ArticleSimpleDTO> findByCongressoId(Long id, Pageable pageable) {
        Page<Article> result = repository.findByCongressoId(id, pageable);
        return result.map(ArticleSimpleDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<ArticleSimpleDTO> findAll(ArticleQueryFilter filter, Pageable pageable) {
        Page<Article> result = repository.findAll(filter.toSpecification(), pageable);
        return result.map(ArticleSimpleDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<ArticleSimpleDTO> findTop20(Long congressoId) {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Article> top20 = repository.findByCongresso_IdAndStatusOrderByEvaluation_FinalScoreDesc(
                congressoId,
                ReviewPerArticleStatus.VALID,
                pageable
        );
        return top20.map(ArticleSimpleDTO::new);
    }

    @Transactional
    public ArticleSimpleDTO insert(ArticleDTO dto) {
        validTitle(dto.getTitle());
        Article entity = new Article();
        copyDtoToEntity(dto, entity);
        entity.setFormat(ArticleFormat.PDF);
        entity.setPublishedAt(Instant.now());
        entity.setStatus(ReviewPerArticleStatus.PENDING);
        entity = repository.save(entity);
        return new ArticleSimpleDTO(entity);
    }

    @Transactional
    public ArticleSimpleDTO update(Long id, ArticleDTO dto) {
        try {
            Article entity = repository.getReferenceById(id);

            if (dto.getTitle() == null || dto.getTitle().isBlank()) {
                throw new DatabaseException("O título do artigo é obrigatório.");
            }
            if (!dto.getTitle().equals(entity.getTitle()) &&
                    repository.existsByTitleAndIdNot(dto.getTitle(), id)) {
                throw new DatabaseException("Já existe um artigo com esse título.");
            }

            copyDtoToEntity(dto, entity);
            try {
                entity = repository.save(entity);
            } catch (DataIntegrityViolationException e) {
                throw new DatabaseException("Já existe um artigo com esse título.");
            }

            return new ArticleSimpleDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    public void validTitle(String title) {
        if (repository.existsByTitle(title)) {
            throw new DatabaseException("Já existe um artigo com esse título.");
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

        if (dto.getCongressoId() == null) {
            throw new ResourceNotFoundException("Congresso é obrigatório");
        }
        Congresso congresso = congressoRepository.findById(dto.getCongressoId())
                .orElseThrow(() -> new ResourceNotFoundException("Congresso não encontrado"));

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setBody(dto.getBody());
        entity.setCongresso(congresso);

        if (dto.getArticlesUsersIds() == null || dto.getArticlesUsersIds().isEmpty()) {
            throw new ResourceNotFoundException("Usuários do artigo são obrigatórios");
        }

        Set<Long> existing = userRepository.findExistingIds(dto.getArticlesUsersIds());
        Set<Long> missing = new HashSet<>(dto.getArticlesUsersIds());
        missing.removeAll(existing);
        if (!missing.isEmpty()) throw new ResourceNotFoundException("Usuário(s) não encontrado(s): " + missing);

        entity.getArticlesUsers().removeIf(u -> !existing.contains(u.getId()));

        for (Long id : existing) {
            boolean present = entity.getArticlesUsers().stream().anyMatch(u -> u.getId().equals(id));
            if (!present) entity.getArticlesUsers().add(userRepository.getReferenceById(id));
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
