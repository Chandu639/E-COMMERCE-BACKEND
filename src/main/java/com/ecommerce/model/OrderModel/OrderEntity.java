package com.ecommerce.model.OrderModel;

import java.math.BigDecimal;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Id;


import com.ecommerce.model.UserModel.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data

@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Order belongs to user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // Order lifecycle state
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    // Snapshot total price
    @Column(nullable = false)
    private BigDecimal totalPrice;

    // Order items owned by order
    @OneToMany(
        mappedBy = "order",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    // Audit fields
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ---- lifecycle hooks ----
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
