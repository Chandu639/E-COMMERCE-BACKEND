package com.ecommerce.dto.CartDTO;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartViewDTO {
	
	private List<CartItemResponseDTO> cartItems;
	private BigDecimal totalPrice;
	private int totalItems;

}
