package tctav.com.hibernate.dao;

import java.util.List;

import tctav.com.hibernate.domain.Product;

public interface ProductDAO {
	
	public List<Product> findAll();
	
	public Product findById(int id);
	
}
