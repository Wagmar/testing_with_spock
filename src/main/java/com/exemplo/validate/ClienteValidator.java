package com.exemplo.validate;

import com.exemplo.domain.Cliente;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ClienteValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Cliente.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (target == null || !(target instanceof Cliente)) {
            return ;
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cpf", "erro.numero.cpf.nao.informado");
    }
}
