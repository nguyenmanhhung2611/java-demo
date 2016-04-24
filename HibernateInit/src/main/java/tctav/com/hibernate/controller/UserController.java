package tctav.com.hibernate.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tctav.com.hibernate.domain.User;
import tctav.com.hibernate.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired 
	private UserService userService;
	
	private String getListUser() {
		List<User> users = userService.findAll();
		String strUsers = "";
		for (User user : users) {
			// System.out.println("=========== Index: " + users.indexOf(user));
			// String strEnd = users.indexOf(user) == (users.size() - 1) ? "" : " || ";
			strUsers += user.getId() + ", " + user.getName() + ", " + user.getPasswork() + "<br/>";
		}
		return strUsers;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		model.addAttribute("dataUser", getListUser());
		
		return "home";
	}
	
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public String getUserById(Model model, @PathVariable("userId") int Id) {
		
		String strUsers = "";
		User user = userService.getUserById(Id);
		if ( user != null )
			strUsers = user.getId() + ", " + user.getName() + ", " + user.getPasswork() + "<br/>";
		
		model.addAttribute("dataUser", strUsers);
		
		return "home";
	}
	
	@RequestMapping(value = "/insert/{name}/{password}", method = RequestMethod.GET)
	public String insertUser(Model model, @PathVariable("name") String name, @PathVariable("password") String password) {
		
		User user = new User();
		user.setName(name);
		user.setPasswork(password);
		userService.insertUser(user);
		
		model.addAttribute("dataUser", getListUser());
		
		return "home";
	}
	
	@RequestMapping(value = "/update/{userId}/{newName}/{newPassword}", method = RequestMethod.GET)
	public String updateUser(Model model, @PathVariable("userId") int id, @PathVariable("newName") String name,
							@PathVariable("newPassword") String password) {
		
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setPasswork(password);
		userService.updateUser(user);
		
		model.addAttribute("dataUser", getListUser());
		
		return "home";
	}
	
	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
	public String deleteUser(Model model, @PathVariable("userId") int id) {
		
		User user = new User();
		user.setId(id);
		userService.deleteUser(user);
		
		model.addAttribute("dataUser", getListUser());
		
		return "home";
	}
	
}
