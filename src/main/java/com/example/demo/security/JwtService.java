package com.example.demo.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String SECRET_KEY = "33c2377be14ea8047d2d81a7a1326280debd158923dc0a20bf4258e97499e832";

	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
		
	}
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {

		byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(Map<String, Object> extraClaims,UserDetails  userDetails) {
		try {
	        System.out.println("Generating token for: " + userDetails.getUsername());
	        String token = Jwts.builder()
	            .setClaims(extraClaims)
	            .setSubject(userDetails.getUsername())
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes expiration
	            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
	            .compact();
	        System.out.println("Generated Token: " + token); // Debug statement
	        return token;
	    } catch (Exception e) {
	        System.out.println("Error generating token: " + e.getMessage());
	        throw e;
	    }
	}
	
	//for generating tokens without extra claims
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(),userDetails);
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUsername(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}
