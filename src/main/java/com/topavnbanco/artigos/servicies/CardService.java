package com.topavnbanco.artigos.servicies;

import com.topavnbanco.artigos.dto.CardDTO;
import com.topavnbanco.artigos.entities.Card;
import com.topavnbanco.artigos.entities.User;
import com.topavnbanco.artigos.repositories.CardRepository;
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

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public CardDTO findById(Long id) {
        Card card = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new CardDTO(card);
    }

    @Transactional(readOnly = true)
    public Page<CardDTO> findAll(Pageable pageable) {
        Page<Card> result = repository.findAll(pageable);
        return result.map(x -> new CardDTO(x));
    }

    @Transactional
    public CardDTO insert(CardDTO dto) {
        Card entity = new Card();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new CardDTO(entity);
    }

    @Transactional
    public CardDTO update(Long id, CardDTO dto) {
        try {
            Card entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new CardDTO(entity);
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

    private void copyDtoToEntity(CardDTO dto, Card entity) {
        User user = userRepository.getReferenceById(dto.getUserId());

        entity.setNumber(dto.getNumber());
        entity.setCvv(dto.getCvv());
        entity.setExpired(dto.getExpired());

        entity.setUser(user);
    }
}
