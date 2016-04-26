package com.demo.javareflection.contructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.demo.javareflection.classs.Cat;

public class CuntructorJava {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// Lấy ra đối tượng Class mô tả class Cat
		Class<Cat> aClass = Cat.class;

		// Lấy ra cấu tử có tham số (String,int) của class Cat

		Constructor<?> constructor = aClass.getConstructor(String.class, int.class);

		// Lấy ra thông tin kiểu tham số của cấu tử.
		Class<?>[] paramClasses = constructor.getParameterTypes();

		for (Class<?> paramClass : paramClasses) {
			System.out.println("Param: " + paramClass.getSimpleName());
		}

		// Khởi tạo đối tượng Cat theo cách thông thường.
		Cat tom = new Cat("Tom", 3);
		System.out.println("Cat 1: " + tom.getName() + ", age =" + tom.getAge());

		// Khởi tạo đối tượng Cat theo cách của reflect.
		Cat tom2 = (Cat) constructor.newInstance("Tom", 2);
		System.out.println("Cat 2: " + tom.getName() + ", age =" + tom2.getAge());
	}
}
