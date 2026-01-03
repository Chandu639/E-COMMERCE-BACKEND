package com.ecommerce.model.CartModel;

import java.util.ArrayList;

import java.util.List;

import com.ecommerce.model.UserModel.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
	@OneToMany(mappedBy="cart",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CartItemEntity> cartItems=new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id",nullable = false,unique = true)
	private UserEntity user;
}
