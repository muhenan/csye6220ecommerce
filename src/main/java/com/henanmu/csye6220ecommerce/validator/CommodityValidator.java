package com.henanmu.csye6220ecommerce.validator;

import com.henanmu.csye6220ecommerce.pojo.Commodity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CommodityValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Commodity.class.isAssignableFrom(clazz); //Commodity and all its subclasses
    }

    @Override
    public void validate(Object target, Errors errors) {
        Commodity commodity = (Commodity) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "commodityName", "empty-commodityName", "Commodity name cannot be empty");
        Integer price = commodity.getPrice();
        if (price == null || price < 0) {
            errors.rejectValue("price", "invalid-price", "Price must be a non-negative integer");
        }
    }
}
