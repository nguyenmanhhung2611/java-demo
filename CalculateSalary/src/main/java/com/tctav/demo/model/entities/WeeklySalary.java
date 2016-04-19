/**
 * 
 */
package com.tctav.demo.model.entities;

/**
 * @author hien.nt
 *
 */
public class WeeklySalary {
	private int idSalary;
	private int userID;
	private double basicSalary;
	private int workedHour;
	private int grossSale;
	private float commissionRate;
	private int grossSalary;
	private int netSalary;
	private String comment;
	private String createDate;

	public WeeklySalary(int idSalary, int user, double basicSalary, int workedHour, int grossSale, float commissionRate,
			int grossSalary, int netSalary, String comment, String createDate) {
		super();
		this.idSalary = idSalary;
		this.userID = user;
		this.basicSalary = basicSalary;
		this.workedHour = workedHour;
		this.grossSale = grossSale;
		this.commissionRate = commissionRate;
		this.grossSalary = grossSalary;
		this.netSalary = netSalary;
		this.comment = comment;
		this.createDate = createDate;
	}

	public WeeklySalary() {
		super();
	}

	public int getIdSalary() {
		return idSalary;
	}

	public void setIdSalary(int idSalary) {
		this.idSalary = idSalary;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public double getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
	}

	public int getWorkedHour() {
		return workedHour;
	}

	public void setWorkedHour(int workedHour) {
		this.workedHour = workedHour;
	}

	public int getGrossSale() {
		return grossSale;
	}

	public void setGrossSale(int grossSale) {
		this.grossSale = grossSale;
	}

	public float getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(float commissionRate) {
		this.commissionRate = commissionRate;
	}

	public int getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(int grossSalary) {
		this.grossSalary = grossSalary;
	}

	public int getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(int netSalary) {
		this.netSalary = netSalary;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
