package com.demo.spring.basic;

public class House {
	Animal animal1;
	Animal animal2;
	
	//Class
	public House(Dog dog, Cat cat){
		this.animal1 = dog;
		this.animal2 = cat;
	}
	
	//Interface Animal
//	public House(Animal animal2, Animal animal1){
//		this.animal2 = animal2;
//		this.animal1 = animal1;
//	}
	
	public Animal getanimal1()
	{
		return animal1;
	}
	
	public void setanimal1(Animal animal1){
		this.animal1 = animal1;
	}
	
	public Animal getanimal2(){
		return animal2;
	}
	
	public void setanimal2(Animal animal2)
	{
		this.animal2 = animal2;
	}
	
	
}
