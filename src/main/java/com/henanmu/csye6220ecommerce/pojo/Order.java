package com.henanmu.csye6220ecommerce.pojo;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer oid;

    /**
     * order status
     * -2: promotion invalid
     * -1: insufficient stock
     * 0: created and awaiting payment
     * 1: paid
     * 2: order expired or invalid
     * */
    private Integer orderStatus;

    @ManyToOne
    @JoinColumn(name = "fkpromotion")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "fkuser")
    private User user;

//    private Integer orderAmount;

    private LocalDateTime createTime;

    private LocalDateTime payTime;

    @Transient
    private Integer userId;

    @Transient
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

    public Order() {
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public Integer getOrderAmount() {
//        return orderAmount;
//    }
//
//    public void setOrderAmount(Integer orderAmount) {
//        this.orderAmount = orderAmount;
//    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }
}
