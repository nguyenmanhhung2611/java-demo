package tctav.com.hibernate.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import tctav.com.hibernate.domain.User;
import tctav.com.hibernate.model.UserLoginModel;

public interface UserService {

	public List<User> findAll();
	
	public User getUserById(int id);
	
	public String checkLogin(UserLoginModel userLoginModel, HttpSession session);
	
	public boolean insertUser(User user);
	
	public boolean updateUser(User user);
	
	public boolean deleteUser(User user);
}
