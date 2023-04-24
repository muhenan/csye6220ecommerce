package com.henanmu.csye6220ecommerce.dao;

import com.henanmu.csye6220ecommerce.pojo.Cart;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CartDAO extends DAO {

    public void saveCartByUserIdAndPromotionId(Integer userId, Integer promotionId) {
        List<Cart> cartList = getCartByUserIdAndPromotionId(userId, promotionId);
        if (cartList.isEmpty()) {
            Cart cart = new Cart(userId, promotionId);
            begin();
            getSession().save(cart);
            commit();
        }
    }

    public void deleteCartByUserIdAndPromotionId(Integer userId, Integer promotionId) {
        begin();
        Query query = getSession().createQuery("DELETE FROM Cart WHERE uid = :userId AND pid = :promotionId");
        query.setParameter("userId", userId);
        query.setParameter("promotionId", promotionId);
        query.executeUpdate();
        commit();
    }

    public List<Cart> getCartByUserIdAndPromotionId(Integer userId, Integer promotionId) {
        String hql = "FROM Cart c WHERE uid = :userId AND pid = :promotionId";
        List<Cart> carts = getSession().createQuery(hql)
                .setParameter("userId", userId)
                .setParameter("promotionId", promotionId)
                .list();
        return carts;
    }

    public List<Integer> readCartsByUserId(Integer userId) {
        List<Integer> pids = new ArrayList<>();
        Query query = getSession().createQuery("SELECT pid FROM Cart c WHERE uid = :userId");
        query.setParameter("userId", userId);
        List<Integer> result = query.getResultList();
        if (result != null && !result.isEmpty()) {
            pids.addAll(result);
        }
        return pids;
    }

}
