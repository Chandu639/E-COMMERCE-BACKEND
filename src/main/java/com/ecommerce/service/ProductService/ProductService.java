package com.ecommerce.service.ProductService;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ProductDTO.CreateProductRequest;
import com.ecommerce.dto.ProductDTO.ProductResponse;
import com.ecommerce.dto.ProductDTO.UpdateProductRequest;
import com.ecommerce.exception.product.ProductNotFoundException;
import com.ecommerce.model.ProductModel.ProductEntity;
import com.ecommerce.repo.ProductRepo.ProductRepo;
import com.ecommerce.service.OrderService.OrderService;


@Service
public class ProductService {
	
	private static final Logger log =
	        LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	ProductRepo repo;

	public ProductResponse addProduct(CreateProductRequest request) {
	  ProductEntity product = new ProductEntity();
	  product.setProductName(request.getProductName());
	  product.setPrice(request.getPrice());
	  product.setCategory(request.getCategory());
	  product.setStock(request.getStock());
	  ProductEntity savedProduct = repo.save(product);

	    ProductResponse response = new ProductResponse();
	    response.setProductID(savedProduct.getProductID());
	    response.setProductName(savedProduct.getProductName());
	    response.setPrice(savedProduct.getPrice());
	    response.setCategory(savedProduct.getCategory());
	    response.setStock(savedProduct.getStock());
	    response.setCreatedAt(savedProduct.getCreatedAt());
	    response.setUpdatedAt(savedProduct.getUpdatedAt());
	    log.info("Product created. productId={}", savedProduct.getProductID());

	  return response;
	}

	public ProductResponse updateProduct(
	        int id,
	        UpdateProductRequest request) {

	    ProductEntity product = repo.findById(id)
	            .orElseThrow(() -> new ProductNotFoundException());

	    // update only allowed fields
	    product.setProductName(request.getProductName());
	    product.setPrice(request.getPrice());
	    product.setCategory(request.getCategory());
	    product.setStock(request.getStock());

	    ProductEntity updatedProduct = repo.save(product);

	    ProductResponse response = new ProductResponse();
	    response.setProductID(updatedProduct.getProductID());
	    response.setProductName(updatedProduct.getProductName());
	    response.setPrice(updatedProduct.getPrice());
	    response.setCategory(updatedProduct.getCategory());
	    response.setStock(updatedProduct.getStock());
	    response.setCreatedAt(updatedProduct.getCreatedAt());
	    response.setUpdatedAt(updatedProduct.getUpdatedAt());

	    return response;
	}


	public void deleteProduct(int id) {
		Optional<ProductEntity> product=repo.findById(id);
		if(product.isEmpty()) {
			throw new ProductNotFoundException();
		}
		ProductEntity entity=product.get();
		
		repo.delete(entity);
		log.info("Product deleted. productId={}", id);

		
	}

	public ProductResponse getProductById(int id) {
		Optional<ProductEntity> product=repo.findById(id);
		if(product.isEmpty()) {
		throw new ProductNotFoundException();
		}
		ProductEntity savedProduct=product.get();
		 ProductResponse response = new ProductResponse();
		 response.setProductID(savedProduct.getProductID());
		    response.setProductName(savedProduct.getProductName());
		    response.setPrice(savedProduct.getPrice());
		    response.setCategory(savedProduct.getCategory());
		    response.setStock(savedProduct.getStock());
		    response.setCreatedAt(savedProduct.getCreatedAt());
		    response.setUpdatedAt(savedProduct.getUpdatedAt());
		    
		    return response;
		
	}

	public List<ProductResponse> getAllProducts(
	        int pageNo,
	        int pageSize,
	        String sortField,
	        String sortOrder) {

	    Sort sort = sortOrder.equalsIgnoreCase("DESC")
	            ? Sort.by(sortField).descending()
	            : Sort.by(sortField).ascending();

	    PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

	    Page<ProductEntity> page = repo.findAll(pageRequest);

	    List<ProductResponse> responseList = new ArrayList<>();

	    for (ProductEntity product : page.getContent()) {
	        ProductResponse response = new ProductResponse();
	        response.setProductID(product.getProductID());
	        response.setProductName(product.getProductName());
	        response.setPrice(product.getPrice());
	        response.setCategory(product.getCategory());
	        response.setStock(product.getStock());
	        response.setCreatedAt(product.getCreatedAt());
	        response.setUpdatedAt(product.getUpdatedAt());
	        responseList.add(response);
	    }

	    return responseList;
	}
	
	public List<ProductResponse> filterProducts(
	        String category,
	        Double minPrice,
	        Double maxPrice,
	        int pageNo,
	        int pageSize,
	        String sortField,
	        String sortOrder) {

	    Sort sort = sortOrder.equalsIgnoreCase("DESC")
	            ? Sort.by(sortField).descending()
	            : Sort.by(sortField).ascending();

	    PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

	    Page<ProductEntity> page;

	    if (category != null && minPrice != null && maxPrice != null) {
	        page = repo.findByCategoryAndPriceBetween(category, minPrice, maxPrice, pageRequest);
	    } 
	    else if (category != null) {
	        page = repo.findByCategory(category, pageRequest);
	    } 
	    else if (minPrice != null && maxPrice != null) {
	        page = repo.findByPriceBetween(minPrice, maxPrice, pageRequest);
	    } 
	    else {
	        page = repo.findAll(pageRequest);
	    }

	    List<ProductResponse> responseList = new ArrayList<>();

	    for (ProductEntity product : page.getContent()) {
	        ProductResponse response = new ProductResponse();
	        response.setProductID(product.getProductID());
	        response.setProductName(product.getProductName());
	        response.setPrice(product.getPrice());
	        response.setCategory(product.getCategory());
	        response.setStock(product.getStock());
	        response.setCreatedAt(product.getCreatedAt());
	        response.setUpdatedAt(product.getUpdatedAt());
	        responseList.add(response);
	    }

	    return responseList;
	}
}

