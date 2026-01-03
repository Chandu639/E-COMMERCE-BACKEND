package com.ecommerce.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.dto.ApiError;
import com.ecommerce.exception.cart.CartItemNotFoundException;
import com.ecommerce.exception.cart.CartNotFoundException;
import com.ecommerce.exception.cart.EmptyCartException;
import com.ecommerce.exception.common.InvalidRequestException;
import com.ecommerce.exception.order.InvalidCartItemException;
import com.ecommerce.exception.order.OrderNotFoundException;
import com.ecommerce.exception.product.ProductNotFoundException;
import com.ecommerce.exception.user.InvalidCredentialsException;
import com.ecommerce.exception.user.UserAlreadyExistsException;
import com.ecommerce.exception.user.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* =========================
       USER EXCEPTIONS
       ========================= */

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "USER_NOT_FOUND",
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                ));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(UserAlreadyExistsException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(
                        "USER_ALREADY_EXISTS",
                        ex.getMessage(),
                        HttpStatus.CONFLICT.value()
                ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentials(InvalidCredentialsException ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(
                        "INVALID_CREDENTIALS",
                        ex.getMessage(),
                        HttpStatus.UNAUTHORIZED.value()
                ));
    }

    /* =========================
       PRODUCT EXCEPTIONS
       ========================= */

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFound(ProductNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "PRODUCT_NOT_FOUND",
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                ));
    }

    /* =========================
       CART EXCEPTIONS
       ========================= */

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiError> handleCartNotFound(CartNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "CART_NOT_FOUND",
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                ));
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ApiError> handleCartItemNotFound(CartItemNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "CART_ITEM_NOT_FOUND",
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                ));
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<ApiError> handleEmptyCart(EmptyCartException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        "EMPTY_CART",
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                ));
    }

    /* =========================
       ORDER EXCEPTIONS
       ========================= */

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiError> handleOrderNotFound(OrderNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                        "ORDER_NOT_FOUND",
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                ));
    }

    @ExceptionHandler(InvalidCartItemException.class)
    public ResponseEntity<ApiError> handleInvalidCartItem(InvalidCartItemException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        "INVALID_CART_ITEM",
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                ));
    }

    /* =========================
       COMMON EXCEPTIONS
       ========================= */

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleInvalidRequest(InvalidRequestException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        "INVALID_REQUEST",
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                ));
    }

    /* =========================
       FALLBACK (VERY IMPORTANT)
       ========================= */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                        "INTERNAL_SERVER_ERROR",
                        "Something went wrong. Please try again later.",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
    }
}
