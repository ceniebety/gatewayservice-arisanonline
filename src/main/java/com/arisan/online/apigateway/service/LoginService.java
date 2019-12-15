package com.arisan.online.apigateway.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.arisan.online.apigateway.domain.Role;
import com.arisan.online.apigateway.domain.User;
import com.arisan.online.apigateway.exception.CustomeException;
import com.arisan.online.apigateway.repository.UserRepository;
import com.arisan.online.apigateway.security.JwtTokenProvider;


@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	 @Autowired
	 private AuthenticationManager authenticationManager;

	/** Save **/
	public void insertUser(User user) {
		userRepository.save(user);
	}

	/** Create New Token */
    public String createNewToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        List<String>roleList = jwtTokenProvider.getRoleList(token);
        String newToken =  jwtTokenProvider.createToken(username,roleList);
        return newToken;
    }
    
    /**Login Process*/
    public String login(String username, String password) {
    	 try {
//             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
//                     password));
             List<User> users = userRepository.findByUsername(username);
             if (users == null || users.size() == 0) {
                 throw new CustomeException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
             }else {
            	 User user = users.get(0);
            	 String token =  jwtTokenProvider.createToken(username, user.getRoles().stream()
                         .map((Role role)-> "ROLE_"+role.getRoleName()).filter(Objects::nonNull).collect(Collectors.toList()));
                 return token;
             }
         } catch (AuthenticationException e) {
             throw new CustomeException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
         }
    }
}
