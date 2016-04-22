package com.demo.spring.controller;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.spring.demoInheritance.Customer1;
import com.demo.spring.scopes.Customer;

@Controller
public class HelloServlet {

//	@Autowired
//	private Customer1 cus;

//	 @Resource(name="customer2")
//	 private Customer1 cus;
//	 @Resource(name="customerscop")
//	 private Customer cusscop;
//	 @Resource(name="customerscop")
//	 private Customer cusscop2;
//
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String HelloSpring(Model m ) {
//		m.addAttribute("mess",cus.getAction() + cus.getType() + cus.getCountry());
//		return "hello";
//	}
//
//	@RequestMapping("/tho")
//	public String Hello(Model m) {
//		m.addAttribute("mess", "Hello Tho");
//		return "hello";
//	}
//	
//	@RequestMapping(value = "/scop", method= RequestMethod.GET )
//	public String Scop(Model m)
//	{
//		cusscop.setMessage("tho");
//		m.addAttribute("mess", cusscop.getMessage());
//		m.addAttribute("mess1", cusscop2.getMessage());
//		return "hello";
//	}
	
	
}
