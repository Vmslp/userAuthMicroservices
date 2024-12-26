package com.example.userAuthMicroservices.service;

import java.util.List;

import com.example.userAuthMicroservices.entity.UserInfo;

public interface IuserService {

	UserInfo saveUser(UserInfo user);

	List<UserInfo> getAllUsers();

	UserInfo signup(UserInfo user);

	UserInfo login(String username, String password);

}
