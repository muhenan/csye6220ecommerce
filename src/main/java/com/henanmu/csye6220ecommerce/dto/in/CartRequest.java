package com.henanmu.csye6220ecommerce.dto.in;

public class CartRequest {
    private Integer userId;
    private Integer promotionId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }
}
