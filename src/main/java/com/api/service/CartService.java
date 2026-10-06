package com.api.service;

import com.api.model.Cart;
import com.api.model.CartItem;
import com.api.model.Product;
import com.api.model.User;
import com.api.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public CartItem addItem(int quantity, User user , Product product) {

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart Not Found"));

        Optional<CartItem> existItems = cart.getItems().stream()
                .filter(item -> item.getProduct()
                        .getId().equals(product.getId())).findFirst();

        if (existItems.isPresent()) {
            CartItem item = existItems.get();
            existItems.get().setQuantity(item.getQuantity()  + quantity);
            cartRepository.save(cart);
            return item;

        }

        CartItem newItem = CartItem
                . builder()
                .product(product)
                .quantity(quantity)
                .cart(cart)
                .build();

        cart.getItems().add(newItem);
        cartRepository.save(cart);

        return newItem ;

    }
}
