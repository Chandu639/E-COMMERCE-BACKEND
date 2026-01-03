package com.ecommerce.service.UserService;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.model.UserModel.UserEntity;
import com.ecommerce.repo.UserRepo.*;
import com.ecommerce.exception.user.InvalidCredentialsException;
import com.ecommerce.exception.user.UserAlreadyExistsException;
import com.ecommerce.exception.user.UserNotFoundException;
import com.ecommerce.security.jwtService;
import com.ecommerce.service.OrderService.OrderService;

@Service
public class UserService {
	
	private static final Logger log =
	        LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserRepo repo;
	
	BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

	public void register(UserEntity user) {
		// TODO Auto-generated method stub
		 if (repo.existsByEmail(user.getEmail())) {
		        throw new UserAlreadyExistsException();
		    }
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
		
	}
	
	public String login(UserEntity user) {
		log.info("Login attempt for email={}", user.getEmail());

		UserEntity userSaved=repo.findByEmail(user.getEmail());
		
		if(userSaved==null) {
			throw new UserNotFoundException();
		}
		if (!encoder.matches(user.getPassword(), userSaved.getPassword())) {
			log.warn("Invalid login attempt for email={}", user.getEmail());

	        throw new InvalidCredentialsException();
	    }
	
		return jwtService.generateToken(user);
		}
		
	}
	
	
	


