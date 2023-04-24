package com.henanmu.csye6220ecommerce.validator;

import com.henanmu.csye6220ecommerce.dao.CommodityDAO;
import com.henanmu.csye6220ecommerce.pojo.Commodity;
import com.henanmu.csye6220ecommerce.pojo.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class PromotionValidator implements Validator {

    @Autowired
    private CommodityDAO commodityDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return Promotion.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Promotion promotion = (Promotion) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "promotionName", "required.promotionName", "Promotion name is required.");
        ValidationUtils.rejectIfEmpty(errors, "originalPrice", "required.originalPrice", "Original price is required.");
        ValidationUtils.rejectIfEmpty(errors, "promotionalPrice", "required.promotionalPrice", "Promotional price is required.");
        ValidationUtils.rejectIfEmpty(errors, "startTime", "required.startTime", "Start time is required.");
        ValidationUtils.rejectIfEmpty(errors, "endTime", "required.endTime", "End time is required.");
        ValidationUtils.rejectIfEmpty(errors, "totalStock", "required.totalStock", "Total stock is required.");
        ValidationUtils.rejectIfEmpty(errors, "commodityId", "required.commodityId", "Commodity ID is required.");

        if (promotion.getOriginalPrice() != null && promotion.getOriginalPrice() < 0) {
            errors.rejectValue("originalPrice", "invalid.originalPrice", "Original price must be a non-negative number.");
        }

        if (promotion.getPromotionalPrice() != null && promotion.getPromotionalPrice() < 0) {
            errors.rejectValue("promotionalPrice", "invalid.promotionalPrice", "Promotional price must be a non-negative number.");
        }

        if (promotion.getStartTime() != null && promotion.getEndTime() != null && promotion.getEndTime().isBefore(promotion.getStartTime())) {
            errors.rejectValue("endTime", "invalid.endTime", "End time must be after start time.");
        }

        if (promotion.getCommodityId() != null && promotion.getCommodityId() > 0) {
            Commodity commodity = commodityDAO.readCommodityById(promotion.getCommodityId());
            if (commodity == null) {
                errors.rejectValue("commodityId", "invalid.commodityId", "Commodity not found.");
            }
        }
    }
}
