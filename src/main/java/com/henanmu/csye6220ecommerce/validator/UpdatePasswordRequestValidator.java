package com.henanmu.csye6220ecommerce.validator;

import com.henanmu.csye6220ecommerce.dto.in.UpdatePasswordRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UpdatePasswordRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UpdatePasswordRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "empty-password", "New password cannot be empty");
    }
}

