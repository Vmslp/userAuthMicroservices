package com.example.userAuthMicroservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userAuthMicroservices.dto.AuthRequest;
import com.example.userAuthMicroservices.entity.UserInfo;
import com.example.userAuthMicroservices.service.IuserService;
import com.example.userAuthMicroservices.service.JwtService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class userController {

	@Autowired
	private IuserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping
	public String saveUser(@RequestBody UserInfo user) {
		userService.saveUser(user);
		return "user successfully saved ";
	}

	@GetMapping("/getall")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<UserInfo> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserInfo user) {
		UserInfo savedUser = userService.signup(user);
		return ResponseEntity.ok(savedUser);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserInfo user) {
		UserInfo authenticatedUser = userService.login(user.getUsername(), user.getPassword());
		if (authenticatedUser != null) {
			return ResponseEntity.ok(authenticatedUser);
		} else {
			return ResponseEntity.status(401).body("Invalid credentials");
		}
	}
}