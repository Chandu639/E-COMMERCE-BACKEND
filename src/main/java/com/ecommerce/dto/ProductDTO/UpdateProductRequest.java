package com.ecommerce.dto.ProductDTO;



import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
	
		
		private String productName;
		
		
		private BigDecimal price;
		
		
		private String category;
		
		
		private int stock;
	}



