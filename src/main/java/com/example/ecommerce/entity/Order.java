package com.example.ecommerce.entity;

import jakarta.persistence.*;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}