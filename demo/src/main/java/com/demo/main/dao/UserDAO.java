package com.demo.main.dao;

import java.util.List;

import com.demo.main.model.User;

public interface UserDAO {
	
	public List<User> findAll();
	
	public User findUserById(int id);
}
