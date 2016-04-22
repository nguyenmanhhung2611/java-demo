package com.demo.spring.model.dao.impl;

import java.sql.SQLException;

import com.demo.spring.model.User;
import com.demo.spring.model.dao.UserDao;

public class UserDaoImpl extends BasicDao implements UserDao {

	@Override
	public User getUserByName(String username) throws SQLException {
		String sql = "select * from tbl_user where user_name = ? ";
		UserMapper usermap = new UserMapper();
		Object[] parameters = { username };
		return getSingleObject(usermap, sql, parameters);
	}
	
	
	

	@Override
	public User getUserById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
