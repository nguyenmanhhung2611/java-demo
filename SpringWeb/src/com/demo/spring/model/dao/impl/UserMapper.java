package com.demo.spring.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.demo.spring.model.User;
import com.demo.spring.model.dao.DBMapper;

public class UserMapper implements DBMapper<User>{

	@Override
	public User doMapping(ResultSet rs) throws SQLException {
		User u = new User();
		u.setId(rs.getInt("id"));
		u.setUser_name(rs.getString("user_name"));
		u.setAcountant(rs.getBoolean("isactive"));
		u.setFirst_name(rs.getString("first_name"));
		u.setLast_name(rs.getString("last_name"));
		u.setPassword(rs.getString("password"));
		return u;
	}

}
