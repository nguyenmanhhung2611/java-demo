package tctav.com.hibernate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tctav.com.hibernate.dao.ProductDAO;
import tctav.com.hibernate.domain.Product;
import tctav.com.hibernate.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired 
	private ProductDAO productDAO;
	
	@Transactional
	public List<Product> findAll() {
		return productDAO.findAll();
	}

	@Transactional
	public Product findById(int id) {
		return productDAO.findById(id);
	}

	@Transactional
	public List<Product> cardDetail(int userId, Product product) {
		// TODO Auto-generated method stub
		return null;
	}

}
