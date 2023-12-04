package com.jsp.expense_tracker.dao;

import com.jsp.expense_tracker.entity.User;

public interface UserDao {
	User registerUser (User user);
	
	User loginUser (String username, String password);
	
	boolean deleteUser(int userId);
	
	User updateUserData(User user);
	
	User forgotPassword(String username, String password);
	
	User checkUser(int userId);
}
