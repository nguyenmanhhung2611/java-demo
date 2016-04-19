/**
 * 
 */
package com.tctav.demo.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tctav.demo.dao.impl.UserDAO;
import com.tctav.demo.service.SalaryServices;
import com.tctav.demo.service.UserService;

/**
 * @author hien.nt
 *
 */
public class SalaryServicesTest {

	/**
	 * TO DO	
	 * - Check Insert Info Employee
	 * 		- check ArrayParam is contain item blank
	 * 		- check ArrayParam is not contain item blank	 * 
	 */
		
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SalaryServices salaryServices;
	
	@Before
	public void setUp() throws Exception {
		salaryServices = new SalaryServices();
	}

	@Test
	public void testArrayContainItemBlank() {
		// Give
		ArrayList<String> array = new ArrayList<String>();
		array.add("1");
		array.add("2");
		array.add("1");
		array.add("");
		array.add("");
		array.add("1");
		array.add("1");
		array.add("1");
		array.add("1");
		array.add("1");
		
		// When
		boolean test = salaryServices.CalculateSalary(array);
		
		// Then
		assertFalse(test);
	}

}
