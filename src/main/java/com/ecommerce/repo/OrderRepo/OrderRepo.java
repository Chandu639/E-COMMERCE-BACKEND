package com.ecommerce.repo.OrderRepo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.OrderModel.OrderEntity;

public interface OrderRepo extends JpaRepository<OrderEntity, Integer> {
	 Page<OrderEntity> findByUser_IdOrderByCreatedAtDesc(
	            int userId,
	            Pageable pageable
	    );
	 @Query("""
		        SELECT o
		        FROM OrderEntity o
		        JOIN FETCH o.orderItems
		        WHERE o.id = :orderId AND o.user.id = :userId
		    """)
		    Optional<OrderEntity> findOrderWithItems(
		            @Param("orderId") int orderId,
		            @Param("userId") int userId
		    );
		}

