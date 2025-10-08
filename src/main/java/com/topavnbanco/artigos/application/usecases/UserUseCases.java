package com.topavnbanco.artigos.application.usecases;

import com.topavnbanco.artigos.adapters.inbound.dtos.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserUseCases {

    UserSimpleDTO findById(Long id);

    Page<UserSimpleDTO> findAll(Pageable pageable);

    Page<UserSimpleDTO> findAllByCongressoId(Long congressoId, Pageable pageable);

    UserArticleDTO findByIdWithArticles(Long id, Pageable pageable);

    UserDTO insert(UserInsertDTO dto);

    UserSimpleDTO update(Long id, UserUpdateDTO dto);

    void delete(Long id);

    UserDTO getMe();

    Page<UserDTO> getByLogin(String login, Pageable pageable);
}
