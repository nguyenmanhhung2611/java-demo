package tctav.com.hibernate.model;

import java.sql.Date;
import java.text.NumberFormat;

import tctav.com.hibernate.domain.Product;

public class ProductModel {
	
	private int id;
	private String name;
	private String code;
	private int stock_quantity;
	private double sale_price;
	private Date last_update;
//	private double totalPrice;

	public ProductModel(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.code = product.getCode();
		this.stock_quantity = product.getStock_quantity();
		this.sale_price = product.getSale_price();
		this.last_update = product.getLast_update();
		//this.totalPrice = product.getSale_price() * product.getStock_quantity();
	}
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStock_quantity() {
		return stock_quantity;
	}

	public void setStock_quantity(int stock_quantity) {
		this.stock_quantity = stock_quantity;
	}

	public double getSale_price() {
		return sale_price;
	}

	public String getSalePriceString() {
		return NumberFormat.getInstance().format(this.sale_price);
	}
	
	public void setSale_price(double sale_price) {
		this.sale_price = sale_price;
	}

	public Date getLast_update() {
		return last_update;
	}

	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}

	/*public double getTotalPrice() {
		return totalPrice;
	}

	public String getTotalPriceString() {
		//return NumberFormat.getInstance().format(this.totalPrice);
		return "";
	}
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}*/

	

}
