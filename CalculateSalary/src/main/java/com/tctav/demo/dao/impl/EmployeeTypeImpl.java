/**
 * 
 */
package com.tctav.demo.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tctav.demo.dao.IEmployeeType;
import com.tctav.demo.model.entities.EmployeeType;

/**
 * @author hiennt
 *
 */
@Repository
public class EmployeeTypeImpl implements IEmployeeType {

	@Autowired
	IEmployeeType EmployeeTypeMapper;

	@Override
	public EmployeeType getTypeByEmployeeID(int userID) {
		return EmployeeTypeMapper.getTypeByEmployeeID(userID);
	}

}
