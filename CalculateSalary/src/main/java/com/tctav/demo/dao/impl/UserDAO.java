package com.tctav.demo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tctav.demo.dao.IUser;
import com.tctav.demo.model.entities.User;

@Repository
public class UserDAO implements IUser{
	@Autowired
	IUser userMapper;

	public List<User> getAllUser() {
		return userMapper.getAllUser();
	}

	
	@Override
	public List<User> getUserByName(String username) {
		return userMapper.getUserByName(username);
	}

	
	public void insertUser(User user) {
		userMapper.insertUser(user);
	}


	@Override
	public User getUserByEmployeeID(int id) {		
		return userMapper.getUserByEmployeeID(id);
	}


}
