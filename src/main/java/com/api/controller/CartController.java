package com.api.controller;

import com.api.model.Cart;
import com.api.model.CartItem;
import com.api.model.Product;
import com.api.model.User;
import com.api.repository.CartRepository;
import com.api.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class CartController {
    private final CartService cartService;


    @PostMapping("cart")
    public CartItem addItem(@PathVariable int quantity, @PathVariable User user , @PathVariable Product product) {
      return cartService.addItem(quantity, user, product);
    }
}
