package com.arisan.online.apigateway.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenConfigure extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{

	 private JwtTokenProvider jwtTokenProvider;

	    public JwtTokenConfigure(JwtTokenProvider jwtTokenProvider) {
	        this.jwtTokenProvider = jwtTokenProvider;
	    }

	    @Override
	    public void configure(HttpSecurity http) throws Exception {
	        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
	        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	    }
}
