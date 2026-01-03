package com.ecommerce.exception.user;

import java.security.PublicKey;

public class UserAlreadyExistsException extends RuntimeException{

	
	public UserAlreadyExistsException() {
		super("User Already Exists");
	}
}
