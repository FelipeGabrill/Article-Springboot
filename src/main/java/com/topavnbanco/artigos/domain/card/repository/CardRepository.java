package com.topavnbanco.artigos.domain.card.repository;

import com.topavnbanco.artigos.domain.card.Card;

import java.util.Optional;

public interface CardRepository {

    Card save(Card card);

    Optional<Card> findById(Long id);

    Card getReferenceById(Long id);

    void deleteById(Long id);
}
