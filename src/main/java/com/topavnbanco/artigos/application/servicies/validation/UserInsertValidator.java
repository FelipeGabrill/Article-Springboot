package com.topavnbanco.artigos.application.servicies.validation;

import com.topavnbanco.artigos.adapters.inbound.controllers.exception.FieldMessage;
import com.topavnbanco.artigos.adapters.inbound.dtos.user.UserInsertDTO;
import com.topavnbanco.artigos.adapters.outbound.repositories.JpaUserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private JpaUserRepository repository;

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (repository.existsByLogin(dto.getLogin())) {
            list.add(new FieldMessage("login", "Login já existente"));
        }

        for(FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

}
