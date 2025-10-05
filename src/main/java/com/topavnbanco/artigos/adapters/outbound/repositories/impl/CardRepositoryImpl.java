package com.topavnbanco.artigos.adapters.outbound.repositories.impl;

import com.topavnbanco.artigos.adapters.outbound.entities.JpaCardEntity;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaCardRepository;
import com.topavnbanco.artigos.domain.card.Card;
import com.topavnbanco.artigos.domain.card.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CardRepositoryImpl implements CardRepository {

    @Autowired
    private JpaCardRepository jpaCardRepository;

    @Override
    public Card save(Card card) {
        JpaCardEntity c = jpaCardRepository.save(new JpaCardEntity(card));
        return new Card(
                c.getId(),
                c.getNumber(),
                c.getExpired(),
                c.getCvv()
        );
    }

    @Override
    public Optional<Card> findById(Long id) {
        return jpaCardRepository.findById(id)
                .map(c -> new Card(
                        c.getId(),
                        c.getNumber(),
                        c.getExpired(),
                        c.getCvv()
                ));
    }

    @Override
    public Card getReferenceById(Long id) {
        JpaCardEntity c = jpaCardRepository.getReferenceById(id);
        return new Card( c.getId(),
                c.getNumber(),
                c.getExpired(),
                c.getCvv());
    }

    @Override
    public void deleteById(Long id) {
        jpaCardRepository.deleteById(id);
    }
}
