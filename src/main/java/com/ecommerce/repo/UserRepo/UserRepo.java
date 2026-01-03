package com.ecommerce.repo.UserRepo;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import com.ecommerce.model.UserModel.UserEntity;

import lombok.NonNull;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer>{
	
	UserEntity findByEmail(String email);

	boolean existsByEmail(@NonNull String email);

}
