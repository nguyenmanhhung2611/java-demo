package com.demo.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Developer {
	
	private Language language;

	// autowire by constructor
	public Developer(Language language) {
		this.language = language;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		Developer de = (Developer) context.getBean("developer");
		System.out.println(de.toString());
	}
}
