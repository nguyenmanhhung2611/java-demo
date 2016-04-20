package com.demo.main.autowire_annotation;

public class Person {
	
	private String name;
	private String address;
	private int age;
	// Set, get properties

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return name + " || " + address + " || " + age;
	}
}
