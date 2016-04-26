package com.demo.javareflection.classs;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ShowMemberInfo {
	public static void main(String[] args) {
		 
	       // Lấy ra đối tượng Class mô tả class Cat
	       Class<Cat> aClass = Cat.class;
	 
	       // Lấy ra danh sách các cấu tử của Cat.
	       Constructor<?>[] constructors = aClass.getConstructors();
	 
	       System.out.println(" ==== CONSTRUCTORs:  ===== ");
	 
	       for (Constructor<?> constructor : constructors) {
	           System.out.println("Constructor: " + constructor.getName());
	       }
	 
	       // Lấy ra danh sách các method public của Cat
	       // Bao gồm cả các method thừa kế từ class cha và các interface
	       Method[] methods = aClass.getMethods();
	 
	       System.out.println(" ==== METHODs:   ====== ");
	       for (Method method : methods) {
	           System.out.println("Method: " + method.getName());
	       }
	 
	       // Lấy ra danh sách các field public
	       // Kể các các public field thừa kế từ các class cha, và các interface
	       Field[] fields = aClass.getFields();
	 
	       System.out.println(" ==== FIELDs:    ====== ");
	       for (Field field : fields) {
	           System.out.println("Field: " + field.getName());
	       }
	 
	   }
}
