package com.demo.spring.service;

import java.sql.SQLException;

import com.demo.spring.model.User;

public interface UserService {
	public User getUserByName(String username) throws SQLException ;
}
