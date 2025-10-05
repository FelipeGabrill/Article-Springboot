package com.topavnbanco.artigos.application.usecases;

import com.topavnbanco.artigos.adapters.inbound.dtos.congresso.CongressoDTO;
import com.topavnbanco.artigos.adapters.inbound.dtos.congresso.CongressoSimpleDTO;
import com.topavnbanco.artigos.infrastructure.queryfilters.CongressoQueryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CongressoUseCases {

    CongressoSimpleDTO findById(Long id);

    Page<CongressoSimpleDTO> findAll(CongressoQueryFilter filter, Pageable pageable);

    CongressoDTO insert(CongressoDTO dto);

    CongressoDTO update(Long id, CongressoDTO dto);

    void delete(Long id);
}
