package main;

import java.lang.reflect.Method;

public class ListMethod {
	
	// Protected method
	protected void info() {

	}

	public static void testMethod1() {

	}

	public void testMethod2() {

	}
	
	public static void main(String[] args) {
		// Lấy ra danh sách các method public của class này
		// Bao gồm các các method thừa kế từ class cha, hoặc các interface.
		Method[] arrMethod = ListMethod.class.getMethods();
		for (Method method : arrMethod) {
			System.out.println("Method" + method.getName());
		}
	}
	
}
