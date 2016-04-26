package com.demo.javareflection.classs;

import java.lang.reflect.Method;

public class ListMethod {
	protected void info(){
		
	} 
	
	public static void testMethod1(){
		
	}
	
	private void testMethod2(){
		
	}
	
	public static void main(String[] args) {
		// Lấy ra danh sách các method public của class này
	   // Bao gồm các các method thừa kế từ class cha, hoặc các interface.
		
		Method[] method = ListMethod.class.getMethods();
		for(Method m : method)
		{
			System.out.println("Method:  " + m.getName());
		}
		
	}

}
