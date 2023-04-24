package com.henanmu.csye6220ecommerce.controller;

import com.henanmu.csye6220ecommerce.dao.CommodityDAO;
import com.henanmu.csye6220ecommerce.pojo.Commodity;
import com.henanmu.csye6220ecommerce.util.MessageModel;
import com.henanmu.csye6220ecommerce.validator.CommodityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commodity")
public class CommodityController {
    @Autowired
    CommodityDAO commodityDAO;

    @Autowired
    CommodityValidator commodityValidator;

    @PostMapping
    public ResponseEntity createCommodity(@RequestBody Commodity commodity, BindingResult result) {
        commodityValidator.validate(commodity, result);
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(MessageModel.create(errorMessage));
        }
        commodityDAO.createCommodity(commodity);
        return ResponseEntity.status(HttpStatus.OK).body(commodity);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getById(@PathVariable("id") Integer commodityId) {
        Commodity commodity = commodityDAO.readCommodityById(commodityId);
        return ResponseEntity.status(HttpStatus.OK).body(commodity);
    }
}
