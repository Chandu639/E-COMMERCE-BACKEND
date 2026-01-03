package com.ecommerce.service.OrderService;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;




import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.model.CartModel.CartEntity;
import com.ecommerce.model.CartModel.CartItemEntity;
import com.ecommerce.model.OrderModel.OrderEntity;
import com.ecommerce.model.OrderModel.OrderItemEntity;
import com.ecommerce.model.OrderModel.OrderStatus;
import com.ecommerce.model.UserModel.UserEntity;
import com.ecommerce.repo.CartRepo.CartRepo;
import com.ecommerce.repo.OrderRepo.OrderRepo;
import com.ecommerce.repo.ProductRepo.ProductRepo;
import com.ecommerce.repo.UserRepo.UserRepo;
import com.ecommerce.dto.OrderDTO.OrderDetailResponseDTO;
import com.ecommerce.dto.OrderDTO.OrderItemResponseDTO;
import com.ecommerce.dto.OrderDTO.OrderResponseDTO;
import com.ecommerce.dto.OrderDTO.OrderSummaryDTO;
import com.ecommerce.exception.cart.CartNotFoundException;
import com.ecommerce.exception.cart.EmptyCartException;
import com.ecommerce.exception.order.InvalidCartItemException;
import com.ecommerce.exception.order.OrderNotFoundException;
import com.ecommerce.exception.user.UserNotFoundException;
import com.ecommerce.security.UserPrincipal;

@Service
public class OrderService {
	
	private static final Logger log =
	        LoggerFactory.getLogger(OrderService.class);

    private final CartRepo cartRepo;
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final ProductRepo repo;

    public OrderService(CartRepo cartRepo, OrderRepo orderRepo, UserRepo userRepo,ProductRepo repo) {
        this.cartRepo = cartRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.repo=repo;
    }

    // ✅ TASK 2 — PLACE ORDER
    @Transactional
    public OrderResponseDTO placeOrder() {

    	

        // 🔐 Get authenticated user
    	
    	
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        int userId = principal.getUserId();

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        
        log.info("Placing order for userId={}", userId);


        // 🛒 Fetch cart
        CartEntity cart = cartRepo.findByUser_Id(userId)
                .orElseThrow(() -> new CartNotFoundException());

        if (cart.getCartItems().isEmpty()) {
        	log.warn("Attempt to place order with empty cart. userId={}", userId);
            throw new EmptyCartException();
        }

        // 📦 Create Order
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);

        List<OrderItemEntity> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        // 🔁 Copy cart items → order items
        for (CartItemEntity cartItem : cart.getCartItems()) {

            if (cartItem.getQuantity() <= 0) {
            	log.warn("Invalid cart item quantity. userId={}, productId={}",
            	         userId, cartItem.getProductId());

                throw new InvalidCartItemException();
            }

            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrder(order);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());

            BigDecimal itemTotal =
                    cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);

        // 💾 Save order
        OrderEntity savedOrder = orderRepo.save(order);
        log.info("Order created successfully. orderId={}, userId={}",
                savedOrder.getId(), userId);


        // 🧹 Clear cart after checkout
        cart.getCartItems().clear();
        cartRepo.save(cart);

        // 🔄 Build response
        return buildOrderResponse(savedOrder);
    }

    // 🔄 DTO Mapper
    private OrderResponseDTO buildOrderResponse(OrderEntity order) {

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setStatus(order.getStatus());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemResponseDTO> items = new ArrayList<>();
        for (OrderItemEntity item : order.getOrderItems()) {
            OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
            itemDTO.setProductId(item.getProductId());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());
            items.add(itemDTO);
        }

        dto.setItems(items);
        return dto;
    }
    
    @Transactional(readOnly = true)
    public Page<OrderSummaryDTO> viewMyOrders(Pageable pageable) {

        int userId = getCurrentUserId();

        Page<OrderEntity> orders =
                orderRepo.findByUser_IdOrderByCreatedAtDesc(userId, pageable);

        return orders.map(this::mapToSummaryDTO);
    }

    private OrderSummaryDTO mapToSummaryDTO(OrderEntity order) {

        OrderSummaryDTO dto = new OrderSummaryDTO();
        dto.setOrderId(order.getId());
        dto.setStatus(order.getStatus().name());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setItemCount(order.getOrderItems().size());

        return dto;
    }

    private int getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        return principal.getUserId();
    }
    @Transactional(readOnly = true)
    public OrderDetailResponseDTO viewOrderDetails(int orderId) {

        int userId = getCurrentUserId();

        OrderEntity order = orderRepo
                .findOrderWithItems(orderId, userId)
                .orElseThrow(() ->
                        new OrderNotFoundException()
                );

        return buildOrderDetailResponse(order);
    }
    private OrderDetailResponseDTO buildOrderDetailResponse(OrderEntity order) {

        OrderDetailResponseDTO dto = new OrderDetailResponseDTO();
        dto.setOrderId(order.getId());
        dto.setStatus(order.getStatus().name());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemResponseDTO> itemDTOs = new ArrayList<>();

        for (OrderItemEntity item : order.getOrderItems()) {

            OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
            itemDTO.setProductId(item.getProductId());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setQuantity(item.getQuantity());

            itemDTO.setItemTotal(
                    item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()))
            );

            itemDTOs.add(itemDTO);
        }

        dto.setItems(itemDTOs);
        return dto;
    }
    
}
    
    

