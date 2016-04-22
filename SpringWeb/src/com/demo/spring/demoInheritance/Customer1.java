package com.demo.spring.demoInheritance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Customer1 {
	
	private int type;
	private String action;
	private String country;
	
	
	
	public Customer1() {
		super();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public String toString() {
		return this.type + " || " + this.action + " || "  + this.country;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("General.xml");
		Customer1 cus1 = (Customer1) context.getBean("customerInheritanceParrent");
		System.out.println(cus1.toString());
		Customer1 cus = (Customer1) context.getBean("customer");
		System.out.println(cus.toString());
		Customer1 cus2 = (Customer1) context.getBean("customer2");
		System.out.println(cus2.toString());
	}
}
