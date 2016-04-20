package com.demo.main.autowire_annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Customer {
	// you want autowired this field.
	@Autowired
	@Qualifier("PersonBean2")
	private Person person;

	private int type;
	private String action;

	/*@Autowired
	  @Qualifier("PersonBean2")
	public Customer(Person person) {
		this.person = person;
	}*/
	
	public Person getPerson() {
		return person;
	}
	
	//@Autowired
	public void setPerson(Person person) {
		this.person = person;
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
	
	@Override
	public String toString() {
		return person + " || " + type + " || " + action;
	}

}
