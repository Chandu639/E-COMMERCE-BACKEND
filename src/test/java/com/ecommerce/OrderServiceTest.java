package com.ecommerce;



import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecommerce.exception.cart.EmptyCartException;
import com.ecommerce.model.CartModel.CartEntity;
import com.ecommerce.model.CartModel.CartItemEntity;
import com.ecommerce.model.UserModel.UserEntity;
import com.ecommerce.repo.CartRepo.CartRepo;
import com.ecommerce.repo.OrderRepo.OrderRepo;
import com.ecommerce.repo.UserRepo.UserRepo;
import com.ecommerce.security.UserPrincipal;
import com.ecommerce.service.OrderService.OrderService;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CartRepo cartRepo;

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private OrderService orderService;

    // -------- TEST 3 --------
    @Test
    void placeOrder_shouldFail_whenCartIsEmpty() {

        mockAuthenticatedUser(1);

        when(userRepo.findById(1))
                .thenReturn(Optional.of(new UserEntity()));

        CartEntity cart = new CartEntity();
        when(cartRepo.findByUser_Id(1))
                .thenReturn(Optional.of(cart));

        assertThatThrownBy(() ->
                orderService.placeOrder()
        ).isInstanceOf(EmptyCartException.class);
    }

    // -------- TEST 4 --------
    @Test
    void placeOrder_shouldCreateOrder_andClearCart() {

        mockAuthenticatedUser(1);

        UserEntity user = new UserEntity();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        CartEntity cart = new CartEntity();
        CartItemEntity item = new CartItemEntity();
        item.setProductId(1);
        item.setQuantity(2);
        item.setPrice(BigDecimal.TEN);
        cart.getCartItems().add(item);

        when(cartRepo.findByUser_Id(1)).thenReturn(Optional.of(cart));
        when(orderRepo.save(any())).thenAnswer(i -> i.getArgument(0));
        when(cartRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        orderService.placeOrder();

        assertThat(cart.getCartItems()).isEmpty();
    }

    // -------- AUTH MOCK HELPER --------
    private void mockAuthenticatedUser(int userId) {

        UserPrincipal principal = mock(UserPrincipal.class);
        when(principal.getUserId()).thenReturn(userId);

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(principal);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);
    }
}
