package com.topavnbanco.artigos.application.usecases;

import com.topavnbanco.artigos.adapters.inbound.dtos.review.ReviewDTO;
import com.topavnbanco.artigos.infrastructure.queryfilters.ReviewQueryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewUseCases {

    ReviewDTO findById(Long id);

    Page<ReviewDTO> findAll(ReviewQueryFilter filter, Pageable pageable);

    ReviewDTO insert(ReviewDTO dto);

    ReviewDTO update(Long id, ReviewDTO dto);

    void delete(Long id);
}
