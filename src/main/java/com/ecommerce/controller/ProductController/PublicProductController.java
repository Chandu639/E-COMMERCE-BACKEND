package com.ecommerce.controller.ProductController;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ProductDTO.ProductResponse;
import com.ecommerce.service.ProductService.ProductService;

@RestController
@RequestMapping("/product")
public class PublicProductController {
	
	@Autowired
	ProductService service;
	
	@GetMapping("/findProduct/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {

	    ProductResponse response = service.getProductById(id);

	    return ResponseEntity.ok(response);
	}

	
	@GetMapping("/allProducts")
	public ResponseEntity<List<ProductResponse>> getAllProducts(
	        @RequestParam(defaultValue = "0") int pageNo,
	        @RequestParam(defaultValue = "10") int pageSize,
	        @RequestParam(defaultValue = "productName") String sortField,
	        @RequestParam(defaultValue = "ASC") String sortOrder) {

	    List<ProductResponse> products =
	            service.getAllProducts(pageNo, pageSize, sortField, sortOrder);

	    return ResponseEntity.status(HttpStatus.OK).body(products);
	}

	@GetMapping("/products/filter")
	public ResponseEntity<List<ProductResponse>> filterProducts(
	        @RequestParam(required = false) String category,
	        @RequestParam(required = false) Double minPrice,
	        @RequestParam(required = false) Double maxPrice,
	        @RequestParam(defaultValue = "0") int pageNo,
	        @RequestParam(defaultValue = "10") int pageSize,
	        @RequestParam(defaultValue = "productName") String sortField,
	        @RequestParam(defaultValue = "ASC") String sortOrder) {

	    List<ProductResponse> products =
	            service.filterProducts(
	                    category,
	                    minPrice,
	                    maxPrice,
	                    pageNo,
	                    pageSize,
	                    sortField,
	                    sortOrder
	            );

	    return ResponseEntity.ok(products);
	}



	

}
