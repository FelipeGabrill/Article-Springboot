package com.topavnbanco.artigos.application.usecases;

import com.topavnbanco.artigos.domain.card.Card;
import com.topavnbanco.artigos.adapters.inbound.dtos.card.CardDTO;

public interface CardUseCases {

    CardDTO findById(Long id);

    Card insert(CardDTO dto);

    CardDTO update(Long id, CardDTO dto);

}
