package com.demo.spring.scopes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActionScope {
	public static void main(String[] args) {
		
		//bean
		ApplicationContext context = new ClassPathXmlApplicationContext("bean-spring-servlet.xml");
		Customer c1 = (Customer) context.getBean("customerscop");
		c1.setMessage("I am Hien");
		System.out.println(c1.getMessage());
		
		Customer c2 = (Customer) context.getBean("customerscop");
		c2.setMessage("I am Tho");
		System.out.println(c2.getMessage());
		
		Customer c3 = (Customer) context.getBean("customerscop");
		//c3.setMessage("I am hung");
		System.out.println(c3.getMessage());
		System.out.println(c1 + "   " + c2 + "    "+ c3);
		
		//annotation@@@@@
//		@SuppressWarnings("resource")
//		ApplicationContext context = new AnnotationConfigApplicationContext(Customer.class);
//		Customer c1 = (Customer) context.getBean("customer");// getBean call class   // tuong tu la bean
//
//		System.out.println(c1.getMessage());
//		
//		Customer c2 
		
//		= (Customer) context.getBean("customer");
//		System.out.println(c2.getMessage());
		
	}
}
