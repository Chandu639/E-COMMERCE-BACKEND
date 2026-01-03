package com.ecommerce.model.CartModel;

import java.math.BigDecimal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cart_items",
uniqueConstraints = {
		@UniqueConstraint(columnNames = {"cart_id","product_id"})
}
)
public class CartItemEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="cart_id",nullable = false)
	private CartEntity cart;
	
	 @Column(name = "product_id", nullable = false)
	    private int productId;
	@Column(nullable = false)
	private BigDecimal price;
	@Column(nullable = false)
	private int quantity;
	
	
	
	
	
	
	
	

}
