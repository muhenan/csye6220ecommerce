package com.henanmu.csye6220ecommerce.controller;

import com.henanmu.csye6220ecommerce.dao.CartDAO;
import com.henanmu.csye6220ecommerce.dao.PromotionDAO;
import com.henanmu.csye6220ecommerce.dao.UserDAO;
import com.henanmu.csye6220ecommerce.dto.in.CartRequest;
import com.henanmu.csye6220ecommerce.pojo.Cart;
import com.henanmu.csye6220ecommerce.pojo.Promotion;
import com.henanmu.csye6220ecommerce.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    PromotionDAO promotionDAO;

    @Autowired
    CartDAO cartDAO;

    @PostMapping("/add")
    public ResponseEntity addCart (@RequestBody CartRequest cartRequest) {
        User user = userDAO.readUserById(cartRequest.getUserId());
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
        Promotion promotion = promotionDAO.getById(cartRequest.getPromotionId());
        if (promotion == null) {
            return ResponseEntity.badRequest().body("Invalid promotion ID");
        }
//        if (user.getCart() == null) {
//            user.setCart(new ArrayList<>());
//        }
//        if (!user.getCart().contains(promotion)) {
//            user.getCart().add(promotion);
//        }
//        userDAO.updateUser(user);
        cartDAO.saveCartByUserIdAndPromotionId(cartRequest.getUserId(), cartRequest.getPromotionId());
        return ResponseEntity.ok().body("Add succeed");
    }


    @DeleteMapping()
    public ResponseEntity deleteCart (@RequestBody CartRequest cartRequest) {
        User user = userDAO.readUserById(cartRequest.getUserId());
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid user ID");
        }
        Promotion promotion = promotionDAO.getById(cartRequest.getPromotionId());
        if (promotion == null) {
            return ResponseEntity.badRequest().body("Invalid promotion ID");
        }
//        if (user.getCart() == null) {
//            return ResponseEntity.ok().body("Your shopping cart is empty");
//        }
//        if (!user.getCart().contains(promotion)) {
//            return ResponseEntity.ok().body("You don't have this item in your shopping cart");
//        }
//        user.getCart().remove(promotion);
//        userDAO.updateUser(user);
        cartDAO.deleteCartByUserIdAndPromotionId(cartRequest.getUserId(), cartRequest.getPromotionId());
        return ResponseEntity.ok().body("Delete succeed");
    }

    @GetMapping("id/{id}")
    public ResponseEntity getCart (@PathVariable("id") Integer id) {
        User user = userDAO.readUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid user");
        }
        List<Promotion> promotions = new ArrayList<>();
        List<Integer> pids = cartDAO.readCartsByUserId(id);
        for (Integer pid:pids) {
            promotions.add(promotionDAO.getById(pid));
        }
        return ResponseEntity.status(HttpStatus.OK).body(promotions);
    }
}
