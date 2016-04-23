package com.demo.main.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.demo.main.dao.UserDAO;
import com.demo.main.model.User;
import com.demo.main.model.UserRowMapper;

public class UserDAOImpl extends JdbcDaoSupport implements UserDAO {

	@Override
	public List<User> findAll() {
		String sql = "SELECT * FROM USER";
		 
		List<User> users = new ArrayList<User>();
	
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		for (Map row : rows) {
			User user = new User();
			user.setId((Integer)(row.get("id")));
			user.setName((String)(row.get("name")));
			user.setPasswork((String)(row.get("passwork")));
			users.add(user);
		}
		
		return users;
	}

	@Override
	public User findUserById(int id) {
		String sql = "SELECT * FROM user WHERE id = ?";
		return (User) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new UserRowMapper());
	}
	
}
