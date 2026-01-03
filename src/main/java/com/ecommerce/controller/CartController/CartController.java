package com.ecommerce.controller.CartController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.dto.CartDTO.AddToCartRequestDTO;
import com.ecommerce.dto.CartDTO.CartViewDTO;
import com.ecommerce.model.CartModel.UpdateCartQuantityRequestDTO;
import com.ecommerce.service.CartService.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items/{productId}")
    public ResponseEntity<CartViewDTO> addToCart(
            @PathVariable int productId,
            @RequestBody AddToCartRequestDTO request
    ) {
        return ResponseEntity.ok(cartService.addToCart(productId, request));
    }
    @GetMapping
    public ResponseEntity<CartViewDTO> viewCart() {
        return ResponseEntity.ok(cartService.viewCart());
    }
    
    @PutMapping("/items/{productId}")
    public ResponseEntity<CartViewDTO> updateCartItemQuantity(
            @PathVariable int productId,
            @RequestBody UpdateCartQuantityRequestDTO request
    ) {
        return ResponseEntity.ok(
                cartService.updateCartItemQuantity(productId, request)
        );
}
    
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartViewDTO> removeFromCart(
            @PathVariable int productId
    ) {
        return ResponseEntity.ok(
                cartService.removeFromCart(productId)
        );
    }
}
