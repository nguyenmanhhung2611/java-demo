/**
 * 
 */
package com.tctav.demo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tctav.demo.dao.impl.WeeklyDAO;
import com.tctav.demo.model.entities.User;
import com.tctav.demo.model.entities.WeeklySalary;

/**
 * @author hien.nt
 *
 */
@Service
public class SalaryServices {

	List<WeeklySalary> weekly = new ArrayList<WeeklySalary>();

	@Autowired
	WeeklyDAO weeklyDAO;

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public boolean CalculateSalary(ArrayList<String> salaryParam) {
		WeeklySalary weekSa = new WeeklySalary();
		weekSa.setIdSalary(Integer.parseInt(salaryParam.get(0)));
		weekSa.setUserID(new User().getEmployeetypeid());
		weekSa.setBasicSalary(Integer.parseInt(salaryParam.get(1)));
		weekSa.setWorkedHour((Integer.parseInt(salaryParam.get(2))));
		weekSa.setGrossSale(Integer.parseInt(salaryParam.get(3)));
		weekSa.setCommissionRate(Float.parseFloat(salaryParam.get(4)));
		weekSa.setGrossSalary(Integer.parseInt(salaryParam.get(5)));
		weekSa.setNetSalary(Integer.parseInt(salaryParam.get(6)));
		weekSa.setCreateDate(salaryParam.get(8));
		weekSa.setComment(salaryParam.get(7));

		weekly = weeklyDAO.getWeeklySalaryWithEmployeeID(weekSa.getUserID());

		if (checkInsert(weekSa)) {
			return true;
		}
		return false;
	}

	private boolean checkInsert(WeeklySalary weekSa) {
		if (weekly.size() == 0) {
			weeklyDAO.insertSalary(weekSa);
			return true;
		}
		return false;
	}

	private boolean checkUpdate(WeeklySalary weekSa) {
		if (weekly.size() != 0) {
			weeklyDAO.updateByUserID(weekSa);
			return true;
		}
		return false;
	}

	public boolean calculateNormal(int userID, String basicsalary, String comment) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		String date = formatter.format(cal.getTime());
		try {
			int basic = Integer.parseInt(basicsalary);

			WeeklySalary salaryNormal = new WeeklySalary();

			salaryNormal.setUserID(userID);
			salaryNormal.setBasicSalary(basic);
			salaryNormal.setWorkedHour(0);
			salaryNormal.setGrossSale(0);
			salaryNormal.setCommissionRate(0);
			salaryNormal.setGrossSalary(0);
			salaryNormal.setNetSalary(basic);
			salaryNormal.setCreateDate(date);
			salaryNormal.setComment(comment);

			weekly = weeklyDAO.getWeeklySalaryWithEmployeeID(userID);
			System.out.println("database: " + weekly);
			System.out.println("id: " + userID);
			if (weekly.isEmpty()) {
				if (checkInsert(salaryNormal)) {
					System.out.println("insert");
					return true;
				}
			} else {
				if (checkUpdate(salaryNormal)) {
					System.out.println("update");
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public boolean calculateHourly(int userID, String hourlyworked, String wageperhour, String comment) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		String date = formatter.format(cal.getTime());
		try {

			int hourly = Integer.parseInt(hourlyworked);
			int wage = Integer.parseInt(wageperhour);

			WeeklySalary salaryHourly = new WeeklySalary();

			salaryHourly.setUserID(userID);
			salaryHourly.setBasicSalary(0);
			salaryHourly.setWorkedHour(hourly);
			salaryHourly.setGrossSale(0);
			salaryHourly.setCommissionRate(0);
			salaryHourly.setGrossSalary(0);
			salaryHourly.setNetSalary(hourly * wage);
			salaryHourly.setCreateDate(date);
			salaryHourly.setComment(comment);

			weekly = weeklyDAO.getWeeklySalaryWithEmployeeID(userID);
			System.out.println("database: " + weekly);
			System.out.println("id: " + userID);
			if (weekly.isEmpty()) {
				if (checkInsert(salaryHourly)) {
					return true;
				} else {
					if (checkUpdate(salaryHourly)) {
						System.out.println("update");
						return true;
					}
				}
			}

		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public boolean calculateSale(int userID, String basic_salary, String gross_saled, String commission_rate,
			String comment) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		String date = formatter.format(cal.getTime());
		try {
			int basicsalary = Integer.parseInt(basic_salary);
			int gross = Integer.parseInt(gross_saled);
			int commission = Integer.parseInt(commission_rate);

			WeeklySalary salaryHourly = new WeeklySalary();

			salaryHourly.setUserID(userID);
			salaryHourly.setBasicSalary(basicsalary);
			salaryHourly.setWorkedHour(0);
			salaryHourly.setGrossSale(gross);
			salaryHourly.setCommissionRate(commission);
			salaryHourly.setGrossSalary(0);
			salaryHourly.setNetSalary(basicsalary * gross);
			salaryHourly.setCreateDate(date);
			salaryHourly.setComment(comment);

			weekly = weeklyDAO.getWeeklySalaryWithEmployeeID(userID);
			System.out.println("database: " + weekly);
			System.out.println("id: " + userID);
			if (weekly.isEmpty()) {
				if (checkInsert(salaryHourly)) {
					return true;
				} else {
					if (checkUpdate(salaryHourly)) {
						System.out.println("update");
						return true;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
