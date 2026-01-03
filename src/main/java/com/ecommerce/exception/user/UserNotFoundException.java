package com.ecommerce.exception.user;


public class UserNotFoundException extends RuntimeException{
	 
	public UserNotFoundException() {
		super("User Not Found");
	}

}
