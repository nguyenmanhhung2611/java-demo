package tctav.com.hibernate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tctav.com.hibernate.dao.UserDAO;
import tctav.com.hibernate.domain.User;
import tctav.com.hibernate.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired 
	private UserDAO userDAO;
	
	@Transactional
	public List<User> findAll() {
		return userDAO.findAll();
	}

	@Transactional
	public User getUserById(int id) {
		return userDAO.getUserById(id);
	}

	@Transactional
	public boolean insertUser(User user) {
		return userDAO.insertUser(user);
	}

	@Transactional
	public boolean updateUser(User user) {
		return userDAO.updateUser(user);
	}

	@Transactional
	public boolean deleteUser(User user) {
		return userDAO.deleteUser(user);
	}

}
