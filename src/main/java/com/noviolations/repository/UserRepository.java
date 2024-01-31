package com.noviolations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noviolations.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);

}
