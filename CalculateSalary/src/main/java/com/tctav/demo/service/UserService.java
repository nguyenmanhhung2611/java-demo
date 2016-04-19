package com.tctav.demo.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tctav.demo.dao.impl.UserDAO;
import com.tctav.demo.model.entities.User;

@Service
@Component
public class UserService {
	@Autowired
	private UserDAO userDAO;
	List<User> listUser;
	User user;
	
	/**
	 * to authenticate user base on the username, password
	 * @param user
	 * @return
	 */
	public User authenticated(String username, String password) {
		String pass = sha256Encode(password);
		if (username == null || username.isEmpty()) {
			return null;
		}
		
		listUser = userDAO.getUserByName(username);
		if (listUser.isEmpty()) {
			return null;
		}
		
		for (User u : listUser) {
			if (pass.equals(u.getPassword()) && ( 1 == u.getIsaccountant())) {
				return u;
			}
		}
		return null;
	}
	
	
	
	public List<User> getUserByName (String username) {		
		List<User> user = userDAO.getUserByName(username);
		return user;
	}
	
	public User getUserByID (int id) {		
		User user = userDAO.getUserByEmployeeID(id);
		return user;
	}
	
	
	public List<User> getAllEmployee() {
		listUser = userDAO.getAllUser();
		return listUser;
	}
	/**
     * Convert a raw text string to SHA-256 encoded string
     * 
     * @param input
     *            : the string need to be encoded to SHA-256
     * @return: the SHA-256 encoded of input string
     * @author hien.nt
     */
	private String sha256Encode(String input) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(input.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Exception in class Utils at function : sha256Encode(String input) : " + e.getMessage());
		}
		return null;
	}
}
