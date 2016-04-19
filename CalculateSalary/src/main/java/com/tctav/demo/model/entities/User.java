package com.tctav.demo.model.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class User {
	private int id;
	private int employeetypeid;
	private String username;
	private String password;

	private String firstname;
	private String lastname;
	private int isaccountant;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmployeetypeid() {
		return employeetypeid;
	}

	public void setEmployeetypeid(int employeetypeid) {
		this.employeetypeid = employeetypeid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getIsaccountant() {
		return isaccountant;
	}

	public void setIsaccountant(int isaccountant) {
		this.isaccountant = isaccountant;
	}

	public User() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public static void main(String[] args) {
		Object o = new Object();
		o.toString();

		System.out.println("thong tin:" + o.toString());
		System.out.println("thong tinkldaskldas:" + o);
		System.out.println("thong User:" + new User().toString());
		System.out.println("thong User:" + new User());

		System.out.println("thong User:" + new EmployeeType());
		System.out.println("thong User:" + new EmployeeType().toString());
	}

}
