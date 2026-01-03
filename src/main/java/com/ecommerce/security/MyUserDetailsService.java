package com.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.model.UserModel.UserEntity;
import com.ecommerce.repo.UserRepo.UserRepo;

@Service
@Profile("local")



public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity user=repo.findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserPrincipal(user);
	}

	
	
	
}
