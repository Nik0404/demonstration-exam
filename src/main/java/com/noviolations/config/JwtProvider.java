package com.noviolations.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtProvider {
	
	private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());
	
	public String generateToken(Authentication auth) {
		String jwt = Jwts.builder().setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 86400000))
				.claim("email", auth.getName())
				.signWith(null).compact();
		
		return jwt;
	}
	
	public String getEmailFromJwtToke(String jwt) {
		jwt = jwt.substring(7);
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(jwt).getBody();
		
		String  email = String.valueOf(claims.get("email"));
		
		return email;
	}
	
}