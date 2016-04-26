package com.demo.javareflection.classs;

public class ShowClassCatInfo {

	public static void main(String[] args) {
		// Đối tượng Class mô tả class Cat.
	       Class<Cat> aClass = Cat.class;
	 
	       // Tên class
	       System.out.println("Simple Class Name = " + aClass.getSimpleName());
	 
	       // Lấy ra đối tượng class mô tả class cha của class Cat.
	       Class<?> aSuperClass = aClass.getSuperclass();
	 
	       System.out.println("Simple Class Name of Super class = " + aSuperClass.getSimpleName());
	 
	       // Lấy ra mảng các Class mô tả các Interface mà Cat thi hành.
	       Class<?>[] itfClasses = aClass.getInterfaces();
	 
	       for (Class<?> itfClass : itfClasses) {
	           System.out.println("Interface: " + itfClass.getSimpleName());
	       }
	}

}
