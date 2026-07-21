package com.api.service;

import com.api.model.Product;
import com.api.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

}
