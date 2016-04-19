package com.demo.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Customer {
	
	private Person person;

	/*public Customer(Person person) {
		this.person = person;
	}*/

	public void setPerson(Person person) {
		this.person = person;
	}
		
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		Customer cus = (Customer) context.getBean("customer");
		System.out.println(cus.toString());
	}
}
