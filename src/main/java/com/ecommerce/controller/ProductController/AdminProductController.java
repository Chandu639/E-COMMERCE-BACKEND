package com.ecommerce.controller.ProductController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ProductDTO.CreateProductRequest;
import com.ecommerce.dto.ProductDTO.ProductResponse;
import com.ecommerce.dto.ProductDTO.UpdateProductRequest;
import com.ecommerce.service.ProductService.ProductService;

@RestController
@RequestMapping("/admin")
public class AdminProductController {
	
	@Autowired
	ProductService service;
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addProduct")
	public ResponseEntity<ProductResponse> addProduct(
	        @RequestBody CreateProductRequest request) {

	    ProductResponse response = service.addProduct(request);

	    return ResponseEntity.status(201).body(response);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<ProductResponse> updateProduct(
	        @PathVariable int id,
	        @RequestBody UpdateProductRequest request) {

	    ProductResponse response = service.updateProduct(id, request);

	    return ResponseEntity.ok(response);
	}


	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable int id) {

	    service.deleteProduct(id);

	    return ResponseEntity.noContent().build();
	}

	
	
	

}
