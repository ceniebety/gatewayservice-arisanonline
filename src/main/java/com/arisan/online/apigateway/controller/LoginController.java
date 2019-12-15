package com.arisan.online.apigateway.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arisan.online.apigateway.dto.LoginDto;
import com.arisan.online.apigateway.service.LoginService;
import com.arisan.online.apigateway.service.UserService;
import com.arisan.online.apigateway.util.ConstantsVariable;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/common/login")
@CrossOrigin
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping()
	public ResponseEntity<?> loginController(@RequestBody LoginDto dto) {
		String token = loginService.login(dto.getUsername(), dto.getPassword());
		HttpHeaders headers = new HttpHeaders();
		List<String> headerlist = new ArrayList<>();
		List<String> exposeList = new ArrayList<>();
		headerlist.add("Content-Type");
		headerlist.add(" Accept");
		headerlist.add("X-Requested-With");
		headerlist.add("Authorization");
		headers.setAccessControlAllowHeaders(headerlist);
		exposeList.add("Authorization");
		headers.setAccessControlExposeHeaders(exposeList);
		headers.set("Authorization", token);
		JSONObject data = new JSONObject();
		data.put("token", token);
		data.put("header",headers);
		JSONObject response = new JSONObject();
		response.put(ConstantsVariable.CONST_DATA, data);
		response.put(ConstantsVariable.CONST_SUCCESS, true);
		response.put(ConstantsVariable.CONST_ERROR, null);
		response.put(ConstantsVariable.CONST_MESSAGE, "login succesed");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
