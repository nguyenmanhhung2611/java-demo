/**
 * 
 */
package com.tctav.demo.model.entities;

/**
 * @author hiennt
 *
 */
public class EmployeeType {
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EmployeeType(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public EmployeeType() {
		super();
	}

}
