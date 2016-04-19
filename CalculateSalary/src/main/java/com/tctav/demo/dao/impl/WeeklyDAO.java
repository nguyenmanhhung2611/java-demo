/**
 * 
 */
package com.tctav.demo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tctav.demo.dao.IWeekly;
import com.tctav.demo.model.entities.WeeklySalary;

/**
 * @author hien.nt
 *
 */
@Repository
public class WeeklyDAO {
	
	@Autowired
	IWeekly weeklyMapper;
	
	public List<WeeklySalary> getWeeklySalaryWithEmployeeID(int ID) {
		return weeklyMapper.getWeeklySalaryWithEmployeeID(ID);
	}

	public void insertSalary(WeeklySalary weekSa) {
		weeklyMapper.insertSalary(weekSa);
	}
	
	public void updateByUserID(WeeklySalary weekSa) {
		weeklyMapper.updateByUserID(weekSa);
	}
}