package com.api.controller;

import com.api.model.Product;
import com.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class ProductController {
    private final ProductService productService;
    @RequestMapping("")
    public List<Product> getProducts(){
        return productService.getProducts();
    }
}
