package com.demo.spring.service.impl;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.demo.spring.model.User;
import com.demo.spring.model.dao.impl.UserDaoImpl;
import com.demo.spring.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public User getUserByName(String username) throws SQLException {
		return new UserDaoImpl().getUserByName(username);
	}

	public boolean checkLogin(User user, String password) {
		return user.getPassword().equals(password);
	}
	
//	public User getSessionUser(HttpSession session) {
//		Object user = session.getAttribute("MY_SESSION");
//		if (user != null && user instanceof User) {
//			return (User) user;
//		} else {
//			return null;
//		}
//	}
	
}
