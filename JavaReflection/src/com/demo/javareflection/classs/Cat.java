package com.demo.javareflection.classs;

public class Cat extends Animal implements Say{
	
	public static final String SAY = "meo meo";
	public static final int NUMBER_OF_LEGS = 4;
	
	//overright interface
	public String Say() {
		return SAY;
	}
	
	//abtract class
	public int getNumberofLegs() {
		return NUMBER_OF_LEGS;
	}

	private String name;
	public int age;
	
	public Cat(){}
	
	public Cat(String name)
	{
		this.name = name;
	}
	
	public Cat(String name, int age)
	{
		this.name = name;
		this.age = age;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getAge(){
		return age;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	
	
	
	
}



