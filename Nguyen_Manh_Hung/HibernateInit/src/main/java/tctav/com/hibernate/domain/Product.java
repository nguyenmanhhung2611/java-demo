package tctav.com.hibernate.domain;

import java.io.Serializable;
import java.sql.Date;
import java.text.NumberFormat;

import javax.persistence.*;

@Entity  // NOTE: Hasn't import org.hibernate.annotations.Entity; It should be import javax.persistence.Entity
@Table(name = "tbl_product")
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "stock_quantity")
	private int stock_quantity;
	
	@Column(name = "sale_price")
	private double sale_price;
	
	@Column(name = "last_update")
	private Date last_update;

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

}
