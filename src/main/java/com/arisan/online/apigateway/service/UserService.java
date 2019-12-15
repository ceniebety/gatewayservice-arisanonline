package com.arisan.online.apigateway.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.arisan.online.apigateway.domain.Role;
import com.arisan.online.apigateway.domain.User;
import com.arisan.online.apigateway.exception.CustomeException;
import com.arisan.online.apigateway.repository.UserRepository;

public class UserService implements UserDetailsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> users = userRepository.findByUsername(username);
		User currentUser = null;
		if (users.size() > 0) {
			currentUser = users.get(0);
			if (currentUser == null || currentUser.getRoles() == null || currentUser.getRoles().size() == 0) {
				throw new CustomeException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
			}
			HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(currentUser.getRoles().size());
			int count = 0;
			for (Role role : currentUser.getRoles()) {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
				count++;
			}
			UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(username, currentUser.getPassword(), authorities);
			return userDetails;
		}else {
			return null;
		}

	}

}
