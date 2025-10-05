package com.topavnbanco.artigos.application.usecases;

import com.topavnbanco.artigos.adapters.inbound.dtos.evaluation.EvaluationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EvaluationUseCases {

    EvaluationDTO findById(Long id);

    Page<EvaluationDTO> findAll(Pageable pageable);

    void delete(Long id);
}
