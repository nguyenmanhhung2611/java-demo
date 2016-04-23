package com.demo.main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.main.dao.UserDAO;
import com.demo.main.model.User;

public class TestConnectDBMySQL {
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
		
		// Get all users
		UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		List<User> users = userDAO.findAll();
        for(User user: users){
        	 System.out.println(user.getId() + " || " + user.getName() + " || " + user.getPasswork());
        }
        
        System.out.println("====================================");
        
        // Get user by id
        User user = userDAO.findUserById(2);
        System.out.println(user.getId() + " || " + user.getName() + " || " + user.getPasswork());
	}
}
