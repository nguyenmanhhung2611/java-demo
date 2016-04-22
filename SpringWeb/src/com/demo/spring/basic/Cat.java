package com.demo.spring.basic;

public class Cat implements Animal{

	private String name;
	
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	@Override
	public String makeSound() {
		return "Meo meo" + name;
	}

}
