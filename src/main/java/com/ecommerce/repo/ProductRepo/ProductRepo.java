package com.ecommerce.repo.ProductRepo;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.ProductModel.ProductEntity;

public interface ProductRepo extends JpaRepository< ProductEntity,Integer>{

	
	
	Page<ProductEntity> findByCategory(
            String category,
            Pageable pageable
    );

    Page<ProductEntity> findByPriceBetween(
            double minPrice,
            double maxPrice,
            Pageable pageable
    );

    Page<ProductEntity> findByCategoryAndPriceBetween(
            String category,
            double minPrice,
            double maxPrice,
            Pageable pageable
    );
}
