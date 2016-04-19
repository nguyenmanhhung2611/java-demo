package com.tctav.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tctav.demo.dao.impl.UserDAO;
import com.tctav.demo.dao.impl.WeeklyDAO;
import com.tctav.demo.model.entities.EmployeeType;
import com.tctav.demo.model.entities.User;
import com.tctav.demo.model.entities.WeeklySalary;
import com.tctav.demo.service.EmployeeTypeService;
import com.tctav.demo.service.SalaryServices;
import com.tctav.demo.service.UserService;

@Controller
@SessionAttributes(value = "user")
public class HomeController {
	
	User user;
	EmployeeType emtype;
	
	List<User> listUser;
	List<WeeklySalary> weekly;
	
	@Autowired
	SalaryServices sala;

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmployeeTypeService emtypeService;
	
	@Autowired
	UserDAO userDAO;
	@Autowired
	WeeklyDAO weeklyDAO;
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String homePage(Model model) {
		return "redirect:/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		// add user to login's view
		model.addAttribute("user", new User());
		return "login";
	}

	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody String logincheck(@RequestParam(value = "username") final String username,
											@RequestParam(value = "password") final String password, Model model) {
		User user = userService.authenticated(username, password);
		String result = "";
		if (null != user) {
			model.addAttribute("user", user);
			result = "loginOK";
		} else {
			result = "login";
		}
		return result;
		
	}

	@RequestMapping(value = "/homeEmployee", method = RequestMethod.GET)
	public String homeEmployee(Model model) {

		listUser =  userService.getAllEmployee();
		model.addAttribute("listUser", listUser);
		return "listemployee";
	}

	@RequestMapping(value = "/homeSalary", method = RequestMethod.POST)
	public String homeSalary(Model model, @RequestParam(value = "id") final String id,
										@RequestParam(value = "emID") final String emID) {
		
		int userID = Integer.parseInt(id);
		int employID = Integer.parseInt(emID);		
		if(employID == 1)
		{
			//userService;
		}
		
		listUser = userService.getUserByName("hieu");		
		user = userService.getUserByID(userID);		
		
		emtype = emtypeService.getEmTypeByID(employID);
		
		model.addAttribute("user", user);
		model.addAttribute("Employeetype", emtype);
		
		return "salary";
	}


	@RequestMapping(value = "/calculation", method = RequestMethod.POST)
	public String calculate(Model model, 
			@RequestParam(value = "week_salary") final String week_salary,
			
			@RequestParam(value = "hourlyworked") final String hourlyworked,
			@RequestParam(value = "wageperhour") final String wageperhour,
			
			@RequestParam(value = "basic_salary") final String basic_salary,
			@RequestParam(value = "gross_saled") final String gross_saled,
			@RequestParam(value = "commission_rate") final String commission_rate,
			
			@RequestParam(value = "comment") final String comment){
		

		System.out.println("normal:" + week_salary + ":::: " + comment);
		System.out.println("hourly:" + hourlyworked + ":::: " + wageperhour + ":::" + comment);
		System.out.println("sale:" + basic_salary + ":::: " + gross_saled + ":::" + comment + ":::" + commission_rate);
		System.out.println("abc:" + commission_rate );
		
		if(emtype.getId() == 1) {
			if(sala.calculateNormal(emtype.getId(), week_salary, comment)) {
				return "insertsuccess";
			} 			
		}
		
		if(emtype.getId() == 2) {
			if(sala.calculateHourly(emtype.getId(), hourlyworked, wageperhour, comment)) {
				return "insertsuccess";
			} 			
		}
		
		if(emtype.getId() == 3) {
			if(sala.calculateSale(emtype.getId(), basic_salary, gross_saled, commission_rate, comment)) {
				return "insertsuccess";
			} 			
		}
		
		return "inserterror";
	}
	

	@RequestMapping(value = "/homeWeeklySalary", method = RequestMethod.POST)
	public String homeWeeklySalary(Model model, @RequestParam(value = "ID") final String id,
			@RequestParam(value = "week_salary") final String week_salary,
			@RequestParam(value = "hourlyworked") final String hourlyworked,
			@RequestParam(value = "wageperhour") final String wageperhour,
			@RequestParam(value = "gross_saled") final String gross_saled,
			@RequestParam(value = "basic_salary") final String basic_salary,
			@RequestParam(value = "commission_rate") final String commission_rate,
			@RequestParam(value = "comment") final String comment) {

		
		int ID;
		if (!"".equals(id)) {
			ID = Integer.parseInt(id);
		} else
			ID = 0;

		int USERID;
		if (!"".equals(id)) {
			USERID = Integer.parseInt(id);
		} else
			USERID = 0;

		int BASICSALARY = 1000;
		int WORKEDHOUR;
		if (!"".equals(hourlyworked)) {
			WORKEDHOUR = Integer.parseInt(hourlyworked);
		} else
			WORKEDHOUR = 0;
		
		int GROSSSALE;
		if (!"".equals(gross_saled)) {
			GROSSSALE = Integer.parseInt(gross_saled);
		} else
			GROSSSALE = 0;
		
		float COMMISRATE;
		if (!"".equals(commission_rate)) {
			COMMISRATE = Integer.parseInt(commission_rate);
		} else
			COMMISRATE = 0;
		
		int GROSSSALARY;
		if (!"".equals(commission_rate)) {
			GROSSSALARY = (int) (150 + (5000 * 0.05));
		} else
			GROSSSALARY = 0;
		
		int NETSALARY = GROSSSALARY - 100;

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		String date = formatter.format(cal.getTime());	
		
		String COMMENT = comment;
		
		ArrayList<String> salaryParam = new ArrayList<String>();
		salaryParam.add("" + ID);
		salaryParam.add("" + USERID);
		salaryParam.add("" + BASICSALARY);
		salaryParam.add("" + WORKEDHOUR);
		salaryParam.add("" + GROSSSALE);
		salaryParam.add("" + COMMISRATE);
		salaryParam.add("" + GROSSSALARY);
		salaryParam.add("" + NETSALARY);
		salaryParam.add("" + COMMENT);
		salaryParam.add("" + date);
		
		if(sala.CalculateSalary(salaryParam)) {
			return "insertsuccess";
		} 
		return "inserterror";
	
	}
	
}
