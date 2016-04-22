package com.demo.spring.model.dao;

import java.sql.SQLException;

import com.demo.spring.model.User;


public interface UserDao {
	public User getUserByName(String username) throws SQLException ;
	public User getUserById(int id) throws SQLException ;
}
