package com.ecommerce.repo.CartRepo;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.CartModel.CartEntity;

public interface CartRepo extends JpaRepository<CartEntity, Integer> {


	    Optional<CartEntity> findByUser_Id(int userId);
	}

	
	

