package com.henanmu.csye6220ecommerce.pojo;

import jakarta.persistence.*;

@Entity
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    private String commodityName;

    private String description;

    private Integer price;

    private String imageUrl;

    public Commodity() {
    }

    public Commodity(Integer cid, String commodityName, String description, Integer price, String imageUrl) {
        this.cid = cid;
        this.commodityName = commodityName;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
