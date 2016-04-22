package com.demo.spring.basic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class action {
	public static void main(String[] args) {
//		House h = new House();
//		Animal an1 = new Dog();
//		Animal an2 = new Cat();
//		h.setanimal1(an1);
//		h.setanimal2(an2);
		
//		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
//		//BeanFactory bf = (BeanFactory) context;
//		House house = (House) context.getBean("houseBean");
//		
//		System.out.println(house.getanimal1().makeSound());
//		System.out.println(house.getanimal2().makeSound());
		
		
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Beans.xml"});
		House obj = (House) context.getBean("houseBean");
		System.out.println(obj.getanimal1().makeSound());
		System.out.println(obj.getanimal2().makeSound());
		
		
	}
	
		
}
