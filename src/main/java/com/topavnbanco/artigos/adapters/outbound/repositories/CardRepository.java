package com.topavnbanco.artigos.adapters.outbound.repositories;

import com.topavnbanco.artigos.domain.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
