package com.henanmu.csye6220ecommerce.controller;

import com.henanmu.csye6220ecommerce.dao.CommodityDAO;
import com.henanmu.csye6220ecommerce.pojo.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commodity")
public class CommodityController {
    @Autowired
    CommodityDAO commodityDAO;
    @PostMapping
    public ResponseEntity createCommodity(@RequestBody Commodity commodity) {
        commodityDAO.createCommodity(commodity);
        return ResponseEntity.status(HttpStatus.OK).body(commodity);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getById(@PathVariable("id") Integer commodityId) {
        Commodity commodity = commodityDAO.readCommodityById(commodityId);
        return ResponseEntity.status(HttpStatus.OK).body(commodity);
    }
}
