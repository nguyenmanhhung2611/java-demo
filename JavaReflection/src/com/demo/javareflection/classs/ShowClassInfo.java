package com.demo.javareflection.classs;

import java.lang.reflect.Modifier;

public class ShowClassInfo {
	public static void main(String[] args) {

		// Lấy ra đối tượng 'Class' mô tả class ShowClassInfo
		Class<ShowClassInfo> aclass = ShowClassInfo.class;

		// Ghi ra tên class, bao gồm cả tên package.
		System.out.println("Class Name: " + aclass.getName());

		//// Ghi ra tên đơn giản của Class
		System.out.println("Class Simple Name: " + aclass.getSimpleName());

		// Thông tin Package.
		Package p = aclass.getPackage();
		System.out.println("Package Name: " + aclass.getName());

		// Modifier
		int modifier = aclass.getModifiers();

		//
		boolean isPublic = Modifier.isPublic(modifier);
		boolean isInterface = Modifier.isInterface(modifier);
		boolean isAbstract = Modifier.isAbstract(modifier);
		boolean isFinal = Modifier.isFinal(modifier);
		boolean isProtect = Modifier.isProtected(modifier);

		// true
		System.out.println("Is Public: " + isPublic);
		// true
		System.out.println("Is Final: " + isFinal);
		// false
		System.out.println("Is Interface: " + isInterface);
		// false
		System.out.println("Is Abstract: " + isAbstract);

	}
}
