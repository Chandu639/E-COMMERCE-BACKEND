package com.ecommerce.model.OrderModel;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderItemEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    // Owning order (aggregate root)
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "order_id", nullable = false)
	    private OrderEntity order;

	    // Snapshot product info
	    @Column(name = "product_id", nullable = false)
	    private int productId;

	    @Column(nullable = false)
	    private int quantity;

	    @Column(nullable = false)
	    private BigDecimal price;
}
