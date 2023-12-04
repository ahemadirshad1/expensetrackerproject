package com.jsp.expense_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.expense_tracker.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserNameAndPassword(String username, String password);
	Optional<User> findByUserName(String username);
}
