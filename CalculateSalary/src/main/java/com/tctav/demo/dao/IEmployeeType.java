/**
 * 
 */
package com.tctav.demo.dao;

import com.tctav.demo.model.entities.EmployeeType;

/**
 * @author hiennt
 *
 */
public interface IEmployeeType {
	public EmployeeType getTypeByEmployeeID(int userID);
}
