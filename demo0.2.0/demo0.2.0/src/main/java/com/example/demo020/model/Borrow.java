package com.example.demo020.model;

import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // ผู้ใช้ที่ยืมสินค้า

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // สินค้าที่ถูกยืม

    private int quantity; // จำนวนสินค้าที่ยืม

    private LocalDate borrowDate; // วันที่ยืมสินค้า

    private LocalDate returnDate; // วันที่คืนสินค้า

    // Constructor, Getter, and Setter
    public Borrow() {}

    public Borrow(User user, Product product, int quantity, LocalDate borrowDate, LocalDate returnDate) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
