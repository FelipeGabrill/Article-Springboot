package com.topavnbanco.artigos.servicies;

import com.topavnbanco.artigos.dto.ArticleDTO;
import com.topavnbanco.artigos.entities.Article;
import com.topavnbanco.artigos.entities.Congresso;
import com.topavnbanco.artigos.entities.enuns.ArticleFormat;
import com.topavnbanco.artigos.entities.enuns.ReviewPerArticleStatus;
import com.topavnbanco.artigos.repositories.ArticleRepository;
import com.topavnbanco.artigos.repositories.CongressoRepository;
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
import java.util.HashSet;
import java.util.Set;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repository;

    @Autowired
    private UserRepository userRepository;

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
        return result.map(ArticleDTO::new);
    }

    @Transactional
    public ArticleDTO insert(ArticleDTO dto) {
        Article entity = new Article();
        copyDtoToEntity(dto, entity);
        entity.setFormat(ArticleFormat.PDF);
        entity.setPublishedAt(Instant.now());
        entity.setStatus(ReviewPerArticleStatus.PENDING);
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
