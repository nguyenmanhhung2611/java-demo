/**
 * 
 */
package jp.co.transcosmos.dm3.core.vo;

import java.math.BigDecimal;

/**
 * @author hiennt
 *
 */
public class TblProduct {
	private int id;
	private String name;
	private String code;
	private int stockQuantity;
	private Double salePrice;

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

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public static String doubleToString(double doubleValue) {
		return new BigDecimal(doubleValue).toPlainString();
	}
	
	public String getSalePriceString() {
		return doubleToString(salePrice);
	    }

}
