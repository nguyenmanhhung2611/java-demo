package tctav.com.hibernate.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tctav.com.hibernate.dao.ProductDAO;
import tctav.com.hibernate.domain.Product;

@SuppressWarnings("unchecked")
@Repository("productDAO")
public class ProductDAOImpl implements ProductDAO {

	@Autowired 
	private SessionFactory sessionFactory;
	
	@Override
	public List<Product> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from Product").list();
	}

	@Override
	public Product findById(int id) {
		Query hqlQuery = sessionFactory.getCurrentSession().createQuery("from Product where id = ?");
		hqlQuery.setInteger(0, id);
		
		List<Product> products = hqlQuery.list();
		if ( products.size() != 0 )
			return products.get(0);
		return null;
	}

}
