package com.ecommerce.dto.CartDTO;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDTO {
	
	private int productId;
	private int quantity;
	private BigDecimal price;

}
