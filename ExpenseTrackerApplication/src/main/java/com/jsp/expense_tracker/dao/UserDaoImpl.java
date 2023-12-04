package com.jsp.expense_tracker.dao;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.jsp.expense_tracker.entity.User;
import com.jsp.expense_tracker.repository.UserRepository;

@Component
public class UserDaoImpl implements UserDao {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public User registerUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User loginUser(String username, String password) {
		return userRepository.findByUserNameAndPassword(username, password).orElse(null);
	}

	@Override
	public boolean deleteUser(int userId) {
		
			try
			{
			userRepository.deleteById(userId);
			return true;
			}
			catch(EmptyResultDataAccessException e)
			{
				return false;
			}
	}
	@Override
	public User updateUserData(User user) {
		User updateUser = userRepository.findById(user.getUserId()).orElse(null);
		if(updateUser!=null)
		{
			updateUser.setUserName(user.getUserName());
			updateUser.setFullName(user.getFullName());
			updateUser.setMobile(user.getMobile());
			updateUser.setEmail(user.getEmail());
			updateUser.setPassword(user.getPassword());
			
			userRepository.save(updateUser);
			System.out.println(updateUser);
			return updateUser;
		}
		return null;
	}

	@Override
	public User forgotPassword(String username, String password) {
		User user = userRepository.findByUserName(username).orElse(null);
		if(user != null)
		{
			user.setPassword(password);
			userRepository.save(user);
			return user;
		}
		
		return null;
	}

	@Override
	public User checkUser(int userId) {
		User user = userRepository.findById(userId).orElse(null);
		if( user != null)
		{
			return user;
		}
		return null;
	}
	
	

}
