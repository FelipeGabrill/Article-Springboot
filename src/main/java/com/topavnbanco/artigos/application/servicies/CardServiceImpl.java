package com.topavnbanco.artigos.application.servicies;

import com.topavnbanco.artigos.application.usecases.CardUseCases;
import com.topavnbanco.artigos.adapters.inbound.dtos.card.CardDTO;
import com.topavnbanco.artigos.domain.card.Card;
import com.topavnbanco.artigos.application.servicies.exceptions.ResourceNotFoundException;
import com.topavnbanco.artigos.domain.card.repository.CardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardServiceImpl implements CardUseCases {

    @Autowired
    private CardRepository repository;

    @Transactional(readOnly = true)
    public CardDTO findById(Long id) {
        Card card = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new CardDTO(card);
    }

    @Transactional
    public Card insert(CardDTO dto) {
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
