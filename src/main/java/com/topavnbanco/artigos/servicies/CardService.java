package com.topavnbanco.artigos.servicies;

import com.topavnbanco.artigos.dto.CardDTO;
import com.topavnbanco.artigos.entities.Card;
import com.topavnbanco.artigos.repositories.CardRepository;
import com.topavnbanco.artigos.servicies.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    @Transactional(readOnly = true)
    public CardDTO findById(Long id) {
        Card card = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new CardDTO(card);
    }

    @Transactional
    protected Card insert(CardDTO dto) {
        Card entity = new Card();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return entity;
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

    private void copyDtoToEntity(CardDTO dto, Card entity) {

        entity.setNumber(dto.getNumber());
        entity.setCvv(dto.getCvv());
        entity.setExpired(dto.getExpired());
    }
}
