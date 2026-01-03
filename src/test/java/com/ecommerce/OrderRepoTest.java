package com.ecommerce;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ecommerce.model.OrderModel.OrderEntity;
import com.ecommerce.model.OrderModel.OrderItemEntity;
import com.ecommerce.model.UserModel.UserEntity;
import com.ecommerce.repo.OrderRepo.OrderRepo;

import jakarta.persistence.EntityManager;

@DataJpaTest
class OrderRepoTest {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private EntityManager em;

    // -------- TEST 5 --------
    @Test
    void shouldFetchOrderWithItems() {

        UserEntity user = new UserEntity();
        em.persist(user);

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        em.persist(order);

        OrderItemEntity item = new OrderItemEntity();
        item.setOrder(order);
        item.setProductId(1);
        item.setQuantity(1);
        item.setPrice(BigDecimal.TEN);
        em.persist(item);

        em.flush();
        em.clear();

        Optional<OrderEntity> result =
                orderRepo.findOrderWithItems(order.getId(), user.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getOrderItems()).hasSize(1);
    }
}

