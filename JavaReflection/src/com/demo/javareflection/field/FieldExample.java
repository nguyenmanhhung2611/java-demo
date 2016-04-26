package com.demo.javareflection.field;

import java.lang.reflect.Field;

import com.demo.javareflection.classs.Cat;

public class FieldExample {
	public static void main(String[] args)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		// Lấy ra đối tượng Class mô tả class Cat
		Class<Cat> aClass = Cat.class;

		// Lấy ra field có tên 'NUMBER_OF_LEGS':
		Field field = aClass.getField("NUMBER_OF_LEGS");

		// Ghi ra kiểu của Field
		Class<?> fieldType = field.getType();

		System.out.println("Field type: " + fieldType.getSimpleName());

		Field ageField = aClass.getField("age");

		Cat tom = new Cat("Tom", 5);

		// Lấy ra giá trị của trường "age" theo cách của Reflect.
		Integer age = (Integer) ageField.get(tom);
		System.out.println("Age = " + age);

		// Sét đặt giá trị mới cho trường "age".
		ageField.set(tom, 7);

		System.out.println("New Age = " + tom.getAge());
		
		Field sayField = aClass.getField("SAY");
		Class<?> fieldTypeSay = sayField.getType();
		System.out.println("Field Type Say " + fieldTypeSay.getSimpleName());
		
	}
}
