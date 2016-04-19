package com.tctav.demo.dao;

import java.util.List;

import com.tctav.demo.model.entities.User;

public interface IUser {
	
	public List<User> getAllUser();
	public List<User> getUserByName(String username);
	public User getUserByEmployeeID(int id);
	void insertUser(User user);
}
