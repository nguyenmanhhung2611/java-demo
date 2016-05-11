package tctav.com.hibernate.service;

import java.util.List;

import tctav.com.hibernate.domain.Product;

public interface ProductService {

	public List<Product> findAll();
	
	public Product findById(int id);
	
	public List<Product> cardDetail(int userId, Product product);
	
}
