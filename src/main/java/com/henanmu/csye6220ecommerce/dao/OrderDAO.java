package com.henanmu.csye6220ecommerce.dao;

import com.henanmu.csye6220ecommerce.pojo.Order;
import com.henanmu.csye6220ecommerce.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDAO extends DAO {
    public void createOrder(Order order) {
        begin();
        getSession().save(order);
        commit();
    }

    public Order getOrderById(Integer id) {
        Order order = getSession().get(Order.class, id);
        return order;
    }

    public void updateOrder(Order order) {
        begin();
        getSession().update(order);
        commit();
    }

    public List<Order> getOrderByUid (User user) {
        List<Order> orders = getSession().createQuery("FROM Order where user = :user")
                                    .setParameter("user", user)
                                    .getResultList();
        System.out.println("size is " + orders.size());
        return orders;
    }
}
