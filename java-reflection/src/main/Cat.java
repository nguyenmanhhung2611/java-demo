package main;

public class Cat extends Animal implements Say {

	public static final String SAY = "Meo meo";
    public static final int NUMBER_OF_LEGS = 4;
    
    private String name;  // Private field.
    public int age;       // Private field
 
    public Cat() {
 
    }
 
    public Cat(String name) {
        this.name = name;
        this.age = 1;
    }
 
    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }
 
    public String getName() {
        return this.name;
    }
 
    // Private Method.
    private void setName(String name) {
        this.name = name;
    }
 
    public int getAge() {
        return this.age;
    }
 
    public void setAge(int age) {
        this.age = age;
    }
	
	@Override
	public String say() {
		// TODO Auto-generated method stub
		return SAY;
	}

	@Override
	public int getNumberOfLegs() {
		// TODO Auto-generated method stub
		return NUMBER_OF_LEGS;
	}

}
