package tctav.com.hibernate.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tctav.com.hibernate.dao.UserDAO;
import tctav.com.hibernate.domain.User;

@SuppressWarnings("unchecked")
@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	@Autowired 
	private SessionFactory sessionFactory;
	
	@Override
	public List<User> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from User").list();
	}

	@Override
	public User getUserById(int id) {
		// Way 1
		/*Query hqlQuery = sessionFactory.getCurrentSession().createQuery("from User where id = ?");
		hqlQuery.setInteger(0, id);
		
		List<User> users = hqlQuery.list();
		if ( users.size() != 0 )
			return users.get(0);
		return null;*/
		
		// Way 2
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public boolean insertUser(User user) {
		try {
			sessionFactory.getCurrentSession().save(user);
		} catch(Exception ex) {
			return false;
		}
		return true;
	}

	@Override
	public boolean updateUser(User user) {
		try {
			sessionFactory.getCurrentSession().update(user);
			/*User user = (User) sessionFactory.getCurrentSession().get(User.class, ID);
			user.setPassword(password);
			sessionFactory.getCurrentSession().update(user);*/
		} catch(Exception ex) {
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteUser(User user) {
		try {
			sessionFactory.getCurrentSession().delete(user);
		} catch(Exception ex) {
			return false;
		}
		return true;
	}

}
