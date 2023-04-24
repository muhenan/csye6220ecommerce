package com.henanmu.csye6220ecommerce.pojo;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;
    private String name;
    private String password;

    private Integer role; // 0 -> customer, 1 -> admin

//    @ManyToMany
//    @JoinTable(
//            name = "Cart",
//            joinColumns = @JoinColumn(name = "userId"),
//            inverseJoinColumns = @JoinColumn(name = "promotionId")
//    )
//    private List<Promotion> cart;

    public User() {
    }

    public User(Integer uid, String name, String password, Integer role) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

//    public List<Promotion> getCart() {
//        return cart;
//    }
//
//    public void setCart(List<Promotion> cart) {
//        this.cart = cart;
//    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
