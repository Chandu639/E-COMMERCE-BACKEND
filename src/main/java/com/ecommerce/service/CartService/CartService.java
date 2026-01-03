package com.ecommerce.service.CartService;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.model.UserModel.UserEntity;
import com.ecommerce.repo.UserRepo.UserRepo;
import com.ecommerce.dto.CartDTO.*;
import com.ecommerce.exception.cart.CartItemNotFoundException;
import com.ecommerce.exception.cart.CartNotFoundException;
import com.ecommerce.exception.common.InvalidRequestException;
import com.ecommerce.exception.product.ProductNotFoundException;
import com.ecommerce.exception.user.UserNotFoundException;
import com.ecommerce.model.CartModel.*;
import com.ecommerce.model.ProductModel.ProductEntity;
import com.ecommerce.repo.CartRepo.CartRepo;
import com.ecommerce.repo.ProductRepo.ProductRepo;
import com.ecommerce.security.UserPrincipal;
import com.ecommerce.service.OrderService.OrderService;

@Service
public class CartService {
	
	private static final Logger log =
	        LoggerFactory.getLogger(CartService.class);

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    public CartService(CartRepo cartRepo, ProductRepo productRepo, UserRepo userRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public CartViewDTO addToCart(int productId, AddToCartRequestDTO request) {

        if (request.getQuantity() <= 0) {
        	throw new InvalidRequestException("Quantity must be greater than zero");
        }

        // 🔐 Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        int userId = principal.getUserId();

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        
        log.info("Adding product to cart. userId={}, productId={}, quantity={}",
                userId, productId, request.getQuantity());


        // 🛒 Fetch or create cart
        CartEntity cart = cartRepo.findByUser_Id(userId)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(user);
                    return newCart;
                });

        // 📦 Fetch product
        ProductEntity product = productRepo.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product not found while adding to cart. productId={}", productId);
                    return new ProductNotFoundException();
                });
        
        

        // 🔁 Check if product already in cart
        CartItemEntity existingItem = null;
        for (CartItemEntity item : cart.getCartItems()) {
            if (item.getProductId()==productId) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            existingItem.setQuantity(
                    existingItem.getQuantity() + request.getQuantity()
            );
        } else {
            CartItemEntity newItem = new CartItemEntity();
            newItem.setCart(cart);
            newItem.setProductId(productId);
            newItem.setQuantity(request.getQuantity());
            newItem.setPrice(product.getPrice()); // snapshot
            cart.getCartItems().add(newItem);
        }

        CartEntity savedCart = cartRepo.save(cart);

        return buildCartView(savedCart);
    }

    // 🔄 DTO MAPPER
    private CartViewDTO buildCartView(CartEntity cart) {

        List<CartItemResponseDTO> itemDTOs = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItemEntity item : cart.getCartItems()) {
            CartItemResponseDTO dto = new CartItemResponseDTO();
            dto.setProductId(item.getProductId());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getPrice());

            BigDecimal itemTotal =
                    item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);

            itemDTOs.add(dto);
        }

        CartViewDTO view = new CartViewDTO();
        view.setCartItems(itemDTOs);
        view.setTotalItems(itemDTOs.size());
        view.setTotalPrice(totalPrice);

        return view;
    }
    public CartViewDTO updateCartItemQuantity(
            int productId,
            UpdateCartQuantityRequestDTO request
    ) {

        int quantity = request.getQuantity();

       
       

        // 🔐 Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        int userId = principal.getUserId();
        
        // ❌ quantity < 0 → error
        if (quantity < 0) {
            
    	    log.warn("Invalid quantity update attempt. userId={}, productId={}, quantity={}",
    	             userId, productId, quantity);
    	    

    	throw new InvalidRequestException("Quantity must be greater than zero");
    }

        // 🛒 Fetch cart
        CartEntity cart = cartRepo.findByUser_Id(userId)
                .orElseThrow(() -> new CartNotFoundException());

        // 🔍 Find cart item
        CartItemEntity targetItem = null;
        for (CartItemEntity item : cart.getCartItems()) {
            if (item.getProductId()==productId) {
                targetItem = item;
                break;
            }
        }

        // ❌ Product not in cart → error
        if (targetItem == null) {
            throw new CartItemNotFoundException();
        }

        // ✅ quantity = 0 → remove item
        if (quantity == 0) {
            log.info("Removing product from cart. userId={}, productId={}", userId, productId);

            cart.getCartItems().remove(targetItem);
        }
        // ✅ quantity > 0 → set quantity
        else {
            targetItem.setQuantity(quantity);
        }

        // 💾 Save cart (aggregate root)
        CartEntity savedCart = cartRepo.save(cart);

        // 🔄 Return updated cart view
        return buildCartView(savedCart);
    }
    
    public CartViewDTO viewCart() {

        // 1️⃣ Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        int userId = principal.getUserId();

        // 2️⃣ Fetch cart (DO NOT create if absent)
        CartEntity cart = cartRepo.findByUser_Id(userId).orElse(null);

        // 3️⃣ If no cart exists → return empty cart view
        if (cart == null || cart.getCartItems().isEmpty()) {
            CartViewDTO emptyView = new CartViewDTO();
            emptyView.setCartItems(new ArrayList<>());
            emptyView.setTotalItems(0);
            emptyView.setTotalPrice(BigDecimal.ZERO);
            return emptyView;
        }

        // 4️⃣ Build CartViewDTO
        return buildCartView(cart);
    }
    
    public CartViewDTO removeFromCart(int productId) {

        // 🔐 Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        int userId = principal.getUserId();

        // 🛒 Fetch cart (no cart → empty view)
        CartEntity cart = cartRepo.findByUser_Id(userId).orElse(null);

        if (cart == null || cart.getCartItems().isEmpty()) {
            CartViewDTO emptyView = new CartViewDTO();
            emptyView.setCartItems(new ArrayList<>());
            emptyView.setTotalItems(0);
            emptyView.setTotalPrice(BigDecimal.ZERO);
            return emptyView;
        }

        // 🔍 Find item to remove
        CartItemEntity itemToRemove = null;
        for (CartItemEntity item : cart.getCartItems()) {
            if (item.getProductId()==productId) {
                itemToRemove = item;
                break;
            }
        }

        // ❗ Idempotent behavior: if item exists → remove
        if (itemToRemove != null) {
            cart.getCartItems().remove(itemToRemove);
            cartRepo.save(cart);
        }

        // 🔄 Return updated cart view
        return buildCartView(cart);
    }


    // 🔄 DTO MAPPER (shared logic)
    
}
