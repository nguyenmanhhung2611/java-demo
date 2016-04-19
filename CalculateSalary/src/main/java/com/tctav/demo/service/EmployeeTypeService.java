/**
 * 
 */
package com.tctav.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tctav.demo.dao.impl.EmployeeTypeImpl;
import com.tctav.demo.model.entities.EmployeeType;

/**
 * @author hiennt
 *
 */
@Service
public class EmployeeTypeService {
	@Autowired
	private EmployeeTypeImpl emDao;

	public EmployeeType getEmTypeByID(int id) {
		EmployeeType emtype = emDao.getTypeByEmployeeID(id);
		return emtype;
	}

}
