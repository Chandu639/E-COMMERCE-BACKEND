package com.ecommerce.dto.OrderDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponseDTO {

	
	 private int orderId;
	    private String status;
	    private BigDecimal totalPrice;
	    private LocalDateTime createdAt;
	    private List<OrderItemResponseDTO> items;
}
