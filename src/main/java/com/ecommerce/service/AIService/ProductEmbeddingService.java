package com.ecommerce.service.AIService;

import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import com.ecommerce.model.ProductModel.ProductEntity;

@Service
public class ProductEmbeddingService {

	public Document toDocument(ProductEntity product) {

	    String content = """
	            Product Name: %s

	            Brand: %s

	            Category: %s

	            Description: %s

	            Price: %s

	            Stock: %d
	            """
	            .formatted(
	                    product.getProductName(),
	                    product.getBrand(),
	                    product.getCategory(),
	                    product.getDescription(),
	                    product.getPrice(),
	                    product.getStock()
	            );

	    return new Document(
	            content,
	            Map.of(
	                    "productId",
	                    String.valueOf(product.getProductID())
	            )
	    );
	}
}