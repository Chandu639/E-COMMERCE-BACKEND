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

import com.ecommerce.dto.CartDTO.AddToCartRequestDTO;
import com.ecommerce.dto.CartDTO.CartViewDTO;
import com.ecommerce.exception.common.InvalidRequestException;
import com.ecommerce.model.CartModel.CartEntity;
import com.ecommerce.model.ProductModel.ProductEntity;
import com.ecommerce.model.UserModel.UserEntity;
import com.ecommerce.repo.CartRepo.CartRepo;
import com.ecommerce.repo.ProductRepo.ProductRepo;
import com.ecommerce.repo.UserRepo.UserRepo;
import com.ecommerce.security.UserPrincipal;
import com.ecommerce.service.CartService.CartService;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepo cartRepo;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private CartService cartService;

    // -------- TEST 1 --------
    @Test
    void addToCart_shouldThrowException_whenQuantityIsInvalid() {

        AddToCartRequestDTO request = new AddToCartRequestDTO();
        request.setQuantity(0);

        assertThatThrownBy(() ->
                cartService.addToCart(1, request)
        ).isInstanceOf(InvalidRequestException.class);
    }

    // -------- TEST 2 --------
    @Test
    void addToCart_shouldAddProductSuccessfully() {

        mockAuthenticatedUser(1);

        AddToCartRequestDTO request = new AddToCartRequestDTO();
        request.setQuantity(2);

        UserEntity user = new UserEntity();
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        ProductEntity product = new ProductEntity();
        product.setPrice(BigDecimal.valueOf(100));
        when(productRepo.findById(5)).thenReturn(Optional.of(product));

        CartEntity cart = new CartEntity();
        cart.setUser(user);
        when(cartRepo.findByUser_Id(1)).thenReturn(Optional.of(cart));
        when(cartRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        CartViewDTO result = cartService.addToCart(5, request);

        assertThat(result.getTotalItems()).isEqualTo(1);
        assertThat(result.getTotalPrice()).isEqualByComparingTo("200");
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
