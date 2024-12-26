package com.example.userAuthMicroservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.userAuthMicroservices.entity.UserInfo;

@Repository
public interface userRepository extends JpaRepository<UserInfo, Integer> {
	Optional<UserInfo> findByUsername(String username);
}
