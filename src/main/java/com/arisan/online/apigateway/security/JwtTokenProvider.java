package com.arisan.online.apigateway.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.arisan.online.apigateway.util.ConstantsVariable;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	private String secretKey = null;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(ConstantsVariable.CONST_SECURITY_SECRETKEY.getBytes());
	}

	public String createToken(String username, List<String> roles) {

		Claims claims = Jwts.claims().setSubject(username);
		claims.put(ConstantsVariable.CONST_SECURITY_AUTH, roles);

		Date now = new Date();
		Date validity = new Date(now.getTime() + ConstantsVariable.CONST_SECURITY_VALIDITYINMILISECONDS);

		String token = Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();
		return token;
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader(ConstantsVariable.CONST_AUTHORIZATION);
		if (bearerToken != null) {
			return bearerToken;
		}
		return null;
	}

	public boolean validateToken(String token) throws JwtException, IllegalArgumentException {
		Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		return true;
	}

	public UserDetails getUserDetails(String token) {
		String userName = getUsername(token);
		List<String> roleList = getRoleList(token);
		HashSet<GrantedAuthority> roleGrantAuth = convertRoles(roleList);
		UserDetails userDetails = new User(userName, token, roleGrantAuth);
		return userDetails;
	}

	@SuppressWarnings("unchecked")
	public List<String> getRoleList(String token) {
		return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(ConstantsVariable.CONST_SECURITY_AUTH);
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public HashSet<GrantedAuthority> convertRoles(List<String> roles){
		HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(roles.size());
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		return authorities;
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = getUserDetails(token);
		return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
	}
}
