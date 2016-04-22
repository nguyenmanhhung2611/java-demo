package com.demo.spring.model;


public class User {
	private int id;
	
	
	private String user_name;
	
	private String password;
	private String last_name;
	private String first_name;
	private boolean isactive;
	
	public User(){}
	
	public User(int id, String user_name, String password, String last_name, String first_name, boolean isactive) {
		super();
		this.id = id;
		this.user_name = user_name;
		this.password = password;
		this.last_name = last_name;
		this.first_name = first_name;
		this.isactive = isactive;
	}
	
	
	public int getId(){
		return id;
	}
	


	public void setId(int id){
		this.id = id;
	}
	
	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getLast_name() {
		return last_name;
	}


	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}


	public String getFirst_name() {
		return first_name;
	}


	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}


	public boolean isactive() {
		return isactive;
	}


	public void setAcountant(boolean isactive) {
		this.isactive = isactive;
	}


	
	
}
