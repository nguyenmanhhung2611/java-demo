package tctav.com.hibernate.dao;

import java.util.List;
import tctav.com.hibernate.domain.User;

public interface UserDAO {
	
	public List<User> findAll();
	
	public User getUserById(int id);
	
	public boolean insertUser(User user);
	
	public boolean updateUser(User user);
	
	public boolean deleteUser(User user);
	
}
