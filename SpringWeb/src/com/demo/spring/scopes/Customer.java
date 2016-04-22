package com.demo.spring.scopes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


//@Service
//@Scope("prototype")
public class Customer {
	String message;
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String strmess){
		this.message = strmess;
	}
	
}
