package com.ecommerce.security;
import java.security.Key;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ecommerce.model.UserModel.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@Service
public class jwtService {
	
	
	private static String secretKey="";
			
			public jwtService() throws NoSuchAlgorithmException {
		KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
		SecretKey key=keyGen.generateKey();
		secretKey=Encoders.BASE64.encode(key.getEncoded());
				
	}
	public static String generateToken(UserEntity user) {
		       return Jwts.builder()
			        .claim("role",user.getROLE())
			        .setSubject(user.getEmail())
			        .setIssuedAt(new Date(System.currentTimeMillis()))
			        .setExpiration(new Date(System.currentTimeMillis()+60*60*60))
			        .signWith(generateKey())
			        .compact();
	}
	
	public static Key generateKey() {
		byte[] bytes=Decoders.BASE64.decode(secretKey);
				return Keys.hmacShaKeyFor(bytes);
	}
	
	 public static String extractUserName(String token) {
	        return extractAllClaims(token).getSubject();
	    }

	    // ✅ validate token
	    public static boolean validateToken(String token, UserDetails userDetails) {
	        String username = extractUserName(token);
	        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	    }

	    // ---------------- helper methods ----------------

	    private static boolean isTokenExpired(String token) {
	        return extractAllClaims(token).getExpiration().before(new Date());
	    }

	    private static Claims extractAllClaims(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(generateKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }
	

}
