package com.blogapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	Optional<User> findByUserEmail(String userEmail);
}
