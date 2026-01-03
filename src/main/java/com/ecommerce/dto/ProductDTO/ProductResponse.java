package com.ecommerce.dto.ProductDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductResponse {
	
	
	private int productID;
	 
	
	private String productName;
	
	
	private BigDecimal price;
	
	
	private String category;
	
	
	private int stock;
	
	
	private LocalDateTime createdAt;
	
	
	private LocalDateTime updatedAt;

}
