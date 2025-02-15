package com.example.demo020.controller;

import com.example.demo020.model.Product;
import com.example.demo020.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    // เพิ่มสินค้า
    @PostMapping("/add-product")
    public String addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return "Product added successfully: " + product.getName();
    }

    // แก้ไขสินค้า
    @PutMapping("/update-product/{id}")
    public String updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return "Product updated successfully: " + updatedProduct.getName();
        } else {
            return "Product not found.";
        }
    }

    // ลบสินค้า
    @DeleteMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return "Product deleted successfully.";
        } else {
            return "Product not found.";
        }
    }
}
