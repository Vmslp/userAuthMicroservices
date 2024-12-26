package com.example.userAuthMicroservices.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.userAuthMicroservices.entity.UserInfo;
import com.example.userAuthMicroservices.repository.userRepository;

@Service
public class userServiceImpl implements IuserService {

	@Autowired
	private userRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<UserInfo> getAllUsers() {
		return repository.findAll();
	}

	@Override
	public UserInfo saveUser(UserInfo user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}

	@Override
	public UserInfo signup(UserInfo user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}

	@Override
	public UserInfo login(String username, String password) {
		return repository.findByUsername(username)
				.filter(user -> passwordEncoder.matches(password, user.getPassword())) 
				.orElse(null); 
	}

}
