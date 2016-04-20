package com.demo.main.autowire_annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("autowire_annotation.xml");
		Customer cus = (Customer) context.getBean("CustomerBean");
		System.out.println(cus.toString());
	}
}
