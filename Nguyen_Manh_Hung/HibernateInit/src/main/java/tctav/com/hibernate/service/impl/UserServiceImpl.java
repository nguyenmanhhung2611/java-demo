package tctav.com.hibernate.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tctav.com.hibernate.dao.UserDAO;
import tctav.com.hibernate.domain.User;
import tctav.com.hibernate.model.UserLoginModel;
import tctav.com.hibernate.service.UserService;
import tctav.com.hibernate.utils.Common;

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

	@Transactional
	public String checkLogin(UserLoginModel userLoginModel, HttpSession session) {
		if ( (userLoginModel.getUserName() != null &&  userLoginModel.getUserName() != "") && 
			  userLoginModel.getPassword() != null &&  userLoginModel.getPassword() != "") {
			User user = userDAO.getUserByUserName(userLoginModel.getUserName());
			if ( user != null && Common.sha256Encode(userLoginModel.getPassword()).equals(user.getPassword()) && user.getIsactive() == 1 ) {
				session.setAttribute("userSession", user);
				return "redirect:/listproduct";
			}
		}
		return "index";
	}

}
