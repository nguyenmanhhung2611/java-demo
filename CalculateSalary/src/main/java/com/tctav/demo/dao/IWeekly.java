/**
 * 
 */
package com.tctav.demo.dao;

import java.util.List;

import com.tctav.demo.model.entities.WeeklySalary;

/**
 * @author hien.nt
 *
 */
public interface IWeekly {
	public List<WeeklySalary> getWeeklySalaryWithEmployeeID(int ID);

	public void insertSalary(WeeklySalary weekSa);
	
	public void updateByUserID(WeeklySalary weekSa);
	
}
