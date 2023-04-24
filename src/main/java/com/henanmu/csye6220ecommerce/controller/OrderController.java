package com.henanmu.csye6220ecommerce.controller;

import com.henanmu.csye6220ecommerce.dao.OrderDAO;
import com.henanmu.csye6220ecommerce.dao.PromotionDAO;
import com.henanmu.csye6220ecommerce.dao.UserDAO;
import com.henanmu.csye6220ecommerce.pojo.Order;
import com.henanmu.csye6220ecommerce.pojo.Promotion;
import com.henanmu.csye6220ecommerce.pojo.User;
import com.henanmu.csye6220ecommerce.util.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderDAO orderDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    PromotionDAO promotionDAO;

    @PostMapping
    public ResponseEntity createOrder(@RequestBody Order order) {
        User user = userDAO.readUserById(order.getUserId());
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
        Promotion promotion = promotionDAO.getById(order.getPromotionId());
        if (promotion == null) {
            return ResponseEntity.badRequest().body("Invalid promotion ID");
        }
        if (!promotionDAO.lockStock(order.getPromotionId())) {
            return ResponseEntity.badRequest().body("No available stock");
        }
        order.setUser(user);
        order.setPromotion(promotion);
        order.setCreateTime(LocalDateTime.now());
        order.setOrderStatus(0);
        orderDAO.createOrder(order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Integer id) {
        Order order = orderDAO.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(order);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Order> updateOrder(@PathVariable("id") Integer id, @RequestBody Order order) {
//        Order existingOrder = orderDAO.getOrderById(id);
//        if (existingOrder == null) {
//            return ResponseEntity.notFound().build();
//        }
//        orderDAO.updateOrder(order);
//        return ResponseEntity.ok(order);
//    }


    @PutMapping("/pay/{id}")
    public ResponseEntity payOrder(@PathVariable("id") Integer id) {
        Order existingOrder = orderDAO.getOrderById(id);
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }
        if (existingOrder.getOrderStatus() != 0) {
            MessageModel messageModel = MessageModel.create("This order is already paid or canceled");
            return ResponseEntity.badRequest().body(messageModel);
        }
        if (!promotionDAO.deductStock(existingOrder.getPromotion().getPid())) {
            return ResponseEntity.badRequest().body("No available stock");
        }
        existingOrder.setOrderStatus(1);
        existingOrder.setPayTime(LocalDateTime.now());
        orderDAO.updateOrder(existingOrder);
        return ResponseEntity.ok(existingOrder);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity cancelOrder(@PathVariable("id") Integer id) {
        Order existingOrder = orderDAO.getOrderById(id);
        if (existingOrder == null) {
            return ResponseEntity.notFound().build();
        }
        if (existingOrder.getOrderStatus() != 0) {
            MessageModel messageModel = MessageModel.create("This order is already paid or canceled");
            return ResponseEntity.badRequest().body(messageModel);
        }
        if (!promotionDAO.revertStock(existingOrder.getPromotion().getPid())) {
            return ResponseEntity.badRequest().body("Your order already expired");
        }
        existingOrder.setOrderStatus(2);
        orderDAO.updateOrder(existingOrder);
        return ResponseEntity.ok(existingOrder);
    }

    @GetMapping("/uid/{uid}")
    public ResponseEntity getOrderByUId(@PathVariable("uid") Integer uid) {
        User user = userDAO.readUserById(uid);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
        List<Order> orders = orderDAO.getOrderByUid(user);
        if (orders == null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().body(orders);
    }
}
