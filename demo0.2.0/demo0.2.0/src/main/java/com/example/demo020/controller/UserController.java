package com.example.demo020.controller;

import com.example.demo020.model.Loan;
import com.example.demo020.model.Product;
import com.example.demo020.model.User;
import com.example.demo020.repository.ProductRepository;
import com.example.demo020.repository.LoanRepository;
import com.example.demo020.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final LoanRepository loanRepository;

    public UserController(UserRepository userRepository, ProductRepository productRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.loanRepository = loanRepository;
    }

    // ดูรายการสินค้าทั้งหมด
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ยืมสินค้า
    @PostMapping("/borrow/{productId}")
    public String borrowProduct(@RequestParam String username, @RequestParam int quantity, @PathVariable Long productId) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (userOptional.isEmpty() || productOptional.isEmpty()) {
            return "User or Product not found!";
        }

        User user = userOptional.get();
        Product product = productOptional.get();

        if (product.getQuantity() < quantity) {
            return "Not enough stock!";
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setProduct(product);
        loan.setQuantity(quantity);
        loan.setBorrowDate(LocalDate.now());

        product.setQuantity(product.getQuantity() - quantity); // ลดจำนวนสินค้า
        productRepository.save(product);
        loanRepository.save(loan);

        return "Product borrowed successfully!";
    }

    // คืนสินค้า
    @PostMapping("/return/{loanId}")
    public String returnProduct(@PathVariable Long loanId) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);

        if (loanOptional.isEmpty()) {
            return "Loan record not found!";
        }

        Loan loan = loanOptional.get();
        Product product = loan.getProduct();

        product.setQuantity(product.getQuantity() + loan.getQuantity()); // เพิ่มจำนวนสินค้าที่คืน
        productRepository.save(product);

        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);

        return "Product returned successfully!";
    }

    // ดูประวัติการยืมของผู้ใช้
    @GetMapping("/loans/{username}")
    public List<Loan> getUserLoans(@PathVariable String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();
        return loanRepository.findByUserId(user.getId());
    }
}
