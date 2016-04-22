package com.demo.spring.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.model.Contact;
import com.demo.spring.model.User;
import com.demo.spring.service.impl.UserServiceImpl;

@Controller
@SessionAttributes
public class AllController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String HelloSpring(Model m) {
		m.addAttribute("mess", "Welcome To Spring MVC");
		return "base.definition";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView showContacts() {
		return new ModelAndView("contact", "contact", new Contact());

	}

	@RequestMapping(value = "/addContact", method = RequestMethod.POST)
	public String addContact(@Valid Contact contact,
			BindingResult bindingResult, ModelMap mm) {
		if (bindingResult.hasErrors()) {
			return "contact";
		} else {
//			mm.addAttribute("firstname", contact.getFirstname());
//			mm.addAttribute("lastname", contact.getLastname());
//			mm.addAttribute("email", contact.getEmail());
//			mm.addAttribute("telephone", contact.getTelephone());
			//mm.addAttribute("birthday", contact.getBirthday());
			// return "redirect:contact.jsp";
			return "showinfo";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String Login() {
		return "login";
	}

	@RequestMapping(value = "/showinfo", method = RequestMethod.GET)
	public String Login1() {
		return "showinfo";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String Login(@RequestParam(value = "UserName") String username,
			@RequestParam(value = "Password") String password, Model m) {

		try {
			User user = new UserServiceImpl().getUserByName(username);
			if (user == null) {
				m.addAttribute("mess", "Sai thong tin dang nhap");
			} else {
				boolean check = new UserServiceImpl()
						.checkLogin(user, password);
				if (check == true) {
					// HttpSession session =
					return "product";
				} else {
					m.addAttribute("mess", "Sai thong tin dang nhap");
				}
			}
		} catch (SQLException e) {
			m.addAttribute("mess", "Co error");
			return "login";
		}
		return "login";

	}

	// @RequestMapping(value = "/login", method = RequestMethod.POST)
	// public void Login1(Model m, HttpServletRequest request,
	// HttpServletResponse response) throws IOException {
	// String username = request.getParameter("UserName");
	// String password = request.getParameter("Password");
	//
	// try {
	// User user = new UserServiceImpl().getUserByName(username);
	// if (user == null) {
	// m.addAttribute("mess", "Sai thong tin dang nhap");
	// } else {
	// boolean check = new UserServiceImpl()
	// .checkLogin(user, password);
	// if (check == true) {
	// response.sendRedirect("product");
	// } else {
	// m.addAttribute("mess", "Sai thong tin dang nhap");
	// }
	// }
	// } catch (SQLException e) {
	// m.addAttribute("mess", "Co error");
	// response.sendRedirect("login");
	// }
	// response.sendRedirect("login");
	// }

}
