package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaCardEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaCardRepository;
import com.topavnbanco.artigos.domain.card.Card;
import com.topavnbanco.artigos.domain.card.repository.CardRepository;
import com.topavnbanco.artigos.infrastructure.mappers.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CardRepositoryImpl implements CardRepository {

    @Autowired
    private JpaCardRepository jpaCardRepository;

    @Autowired
    private CardMapper cardMapper;

    @Override
    public Card save(Card card) {
        JpaCardEntity entity = cardMapper.toEntity(card);
        entity = jpaCardRepository.save(entity);
        return cardMapper.toDomain(entity);
    }

    @Override
    public Optional<Card> findById(Long id) {
        return jpaCardRepository.findById(id)
                .map(cardMapper::toDomain);
    }

    @Override
    public Card getReferenceById(Long id) {
        JpaCardEntity entity = jpaCardRepository.getReferenceById(id);
        return cardMapper.toDomain(entity);
    }

    @Override
    public void deleteById(Long id) {
        jpaCardRepository.deleteById(id);
    }
}
