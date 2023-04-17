package com.henanmu.csye6220ecommerce.dao;

import com.henanmu.csye6220ecommerce.pojo.Promotion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PromotionDAO extends DAO {
    public void createPromotion(Promotion promotion) {
        begin();
        getSession().save(promotion);
        commit();
    }

    public Promotion getById(Integer pid) {
        Promotion promotion = getSession().get(Promotion.class, pid);
        return promotion;
    }

    public List<Promotion> getByStatus(Integer status) {
        List<Promotion> promotions = getSession().createQuery("FROM Promotion WHERE status = :status")
                .setParameter("status", status)
                .list();
        return promotions;
    }

    public void updatePromotion(Promotion promotion) {
        begin();
        getSession().update(promotion);
        commit();
    }


    public boolean lockStock(Integer id) {
        Promotion promotion = getById(id);
        if (promotion == null || promotion.getAvailableStock() <= 0) {
            return false;
        }
        promotion.setLockStock(promotion.getLockStock() + 1);
        promotion.setAvailableStock(promotion.getAvailableStock() - 1);
        updatePromotion(promotion);
        return true;
    }

    public boolean revertStock(Integer id) {
        Promotion promotion = getById(id);
        if (promotion == null || promotion.getLockStock() <= 0) {
            return false;
        }
        promotion.setLockStock(promotion.getLockStock() - 1);
        promotion.setAvailableStock(promotion.getAvailableStock() + 1);
        updatePromotion(promotion);
        return true;
    }

    public boolean deductStock(Integer id) {
        Promotion promotion = getById(id);
        if (promotion == null || promotion.getLockStock() <= 0) {
            return false;
        }
        promotion.setLockStock(promotion.getLockStock() - 1);
        updatePromotion(promotion);
        return true;
    }
}
