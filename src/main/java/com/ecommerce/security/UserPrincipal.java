package com.ecommerce.security;

import java.util.Collection;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.model.UserModel.UserEntity;

public class UserPrincipal implements UserDetails {
	
		/**
	 * 
	 */
	private static final long serialVersionUID = -5954748692763599524L;
		UserEntity user;
	
	public UserPrincipal(UserEntity user) {
		this.user=user;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return  List.of(new SimpleGrantedAuthority("ROLE_"+user.getROLE().name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}
	
	public int getUserId() {
	    return user.getId();
	}


}
