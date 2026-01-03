package com.ecommerce.controller.OrderController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.dto.OrderDTO.OrderResponseDTO;
import com.ecommerce.dto.OrderDTO.OrderSummaryDTO;
import com.ecommerce.service.OrderService.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ✅ PLACE ORDER ENDPOINT
    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder() {
        return ResponseEntity.ok(orderService.placeOrder());
    }
    @GetMapping
    public Page<OrderSummaryDTO> viewMyOrders(
            @PageableDefault(size = 5) Pageable pageable
    ) {
        return orderService.viewMyOrders(pageable);
    }
}
