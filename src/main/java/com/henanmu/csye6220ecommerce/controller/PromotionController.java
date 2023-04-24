package com.henanmu.csye6220ecommerce.controller;

import com.henanmu.csye6220ecommerce.dao.CommodityDAO;
import com.henanmu.csye6220ecommerce.dao.PromotionDAO;
import com.henanmu.csye6220ecommerce.pojo.Commodity;
import com.henanmu.csye6220ecommerce.pojo.Promotion;
import com.henanmu.csye6220ecommerce.util.MessageModel;
import com.henanmu.csye6220ecommerce.validator.PromotionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotion")
public class PromotionController {
    @Autowired
    PromotionDAO promotionDAO;

    @Autowired
    CommodityDAO commodityDAO;

    @Autowired
    PromotionValidator promotionValidator;

    @PostMapping
    public ResponseEntity createPromotion(@RequestBody Promotion promotion, BindingResult result) {
        promotionValidator.validate(promotion, result);
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(MessageModel.create(errorMessage));
        }
        Commodity commodity = commodityDAO.readCommodityById(promotion.getCommodityId());
        if (commodity == null) {
            return ResponseEntity.badRequest().body(MessageModel.create("Invalid commodity ID"));
        }
        promotion.setCommodity(commodity);
        promotion.setStatus(1);
        promotion.setAvailableStock(promotion.getTotalStock());
        promotion.setLockStock(0);
        promotionDAO.createPromotion(promotion);
        return ResponseEntity.ok(promotion);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Promotion> getById(@PathVariable("id") Integer id) {
        Promotion promotion = promotionDAO.getById(id);
        if (promotion == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(promotion);
        }
    }

    @GetMapping("/status/{status}")
    public List<Promotion> getByStatus(@PathVariable("status") Integer status) {
        List<Promotion> promotions = promotionDAO.getByStatus(status);
        return promotions;
    }


    @PostMapping("/lock/id/{id}")
    public ResponseEntity lockStock(@PathVariable("id") Integer id) {
        Promotion promotion = promotionDAO.getById(id);
        if (promotion == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Promotion");
        }
        boolean isLocked = promotionDAO.lockStock(id);
        if (isLocked) {
            return ResponseEntity.status(HttpStatus.OK).body("Lock succeed");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lock failed");
    }

    @PostMapping("/revert/id/{id}")
    public ResponseEntity revertStock(@PathVariable("id") Integer id) {
        Promotion promotion = promotionDAO.getById(id);
        if (promotion == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Promotion");
        }
        boolean isReverted = promotionDAO.revertStock(id);
        if (isReverted) {
            return ResponseEntity.status(HttpStatus.OK).body("Revert succeed");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Revert failed");
    }

    @PostMapping("/deduct/id/{id}")
    public ResponseEntity deductStock(@PathVariable("id") Integer id) {
        Promotion promotion = promotionDAO.getById(id);
        if (promotion == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Promotion");
        }
        boolean isDeducted = promotionDAO.deductStock(id);
        if (isDeducted) {
            return ResponseEntity.status(HttpStatus.OK).body("Deduct succeed");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deduct failed");
    }


}
