package com.topavnbanco.artigos.application.usecases;

import com.topavnbanco.artigos.adapters.inbound.dtos.user.UserDTO;
import com.topavnbanco.artigos.adapters.inbound.dtos.user.UserInsertDTO;
import com.topavnbanco.artigos.adapters.inbound.dtos.user.UserSimpleDTO;
import com.topavnbanco.artigos.adapters.inbound.dtos.user.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserUseCases {

    UserDTO findById(Long id);

    Page<UserSimpleDTO> findAll(Pageable pageable);

    UserDTO insert(UserInsertDTO dto);

    UserSimpleDTO update(Long id, UserUpdateDTO dto);

    void delete(Long id);

    UserDTO getMe();

    Page<UserDTO> getByLogin(String login, Pageable pageable);
}
