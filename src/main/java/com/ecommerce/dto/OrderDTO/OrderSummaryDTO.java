package com.ecommerce.dto.OrderDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryDTO {


	    private int orderId;
	    private String status;
	    private BigDecimal totalPrice;
	    private int itemCount;
	    private LocalDateTime createdAt;

	    // getters & setters
	}


