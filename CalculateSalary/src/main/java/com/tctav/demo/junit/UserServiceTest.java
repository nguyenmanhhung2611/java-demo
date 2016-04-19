package com.tctav.demo.junit;
///**
// * 
// */
//package com.demo.junit;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.demo.dao.UserDAO;
//import com.demo.model.User;
//import com.demo.service.UserService;
//
///**
// * @author hien.nt
// *
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:servlet-context.xml"})
//public class UserServiceTest {
//	
//	/**
//	 * @throws java.lang.Exception
//	 */
//	
//	/**
//	 * TO DO
//	 * - Check login 
//	 * 		- check username or password is blank
//	 * 		- check username and password is blank(empty)
//	 * 		- check username or password is not exits in DB
//	 * 		- check username and password is not exits in DB
//	 * 		- check username, password is exits in DB and isAccountant == 1(is Accountant)
//	 * 		- check username, password is exits in DB and isAccountant == 0(not Accountant)
//	 * - Check get Info Employee With Employee ID
//	 * 		- check Employee ID is null
//	 * 		- check Employee ID is blank
//	 * 		- check Employee ID is not mapping in DB
//	 * 		- check Employee ID is mapping in DB
//	 * - Get Info List Employee 
//	 * 		- check List Employee is empty
//	 * 		- check List Employee is not empty
//	 */
//		
//	@Autowired
//	private UserDAO userDAO;
//	
//	@Autowired
//	private UserService userService;
//	
////	@Before
////	public void setUp() throws Exception {
////		userService = new UserService();
////	}
//	
//	@Test
//	public void testUserOrPassBlank() {
//		// Give
//		User u = new User();
//		u.setUsername("");
//		u.setPassword("123");
//		u.setIsaccountant(1);
//		
//		//When
//		boolean userSer = userService.authenticated(u);
//		
//		//Then
//		assertFalse(userSer);
//	}
//		
//	@Test
//	public void testUserAndPassBlank() {
//		// Give
//		User u = new User();
//		u.setUsername("");
//		u.setPassword("");
//		u.setIsaccountant(1);
//		
//		//When
//		boolean userSer = userService.authenticated(u);
//		
//		//Then
//		assertFalse(userSer);
//	}
//		
//	@Test
//	public void testUserOrPassNotExits() {
//		// Give
//		User u = new User();
//		u.setUsername("hieu");
//		u.setPassword("abbabab");
//		u.setIsaccountant(1);
//		
//		//When
//		boolean userSer = userService.authenticated(u);
//		
//		//Then
//		assertFalse(userSer);
//	}
//	
//	@Test
//	public void testUserAndPassNotExits() {
//		// Give
//		User u = new User();
//		u.setUsername("abc");
//		u.setPassword("abc");
//		u.setIsaccountant(1);
//		
//		//When
//		boolean userSer = userService.authenticated(u);
//		
//		//Then
//		assertFalse(userSer);
//	}
//	
//	
//	@Test
//	public void testUserAndPassExitsNotIsAccountant() {
//		// Give
//		User u = new User();
//		u.setUsername("hieu");
//		u.setPassword("afc8e16842061ea3dbb023bf5f08d1bc3a728429313fab0cba30f60954ff9064");
//		u.setIsaccountant(0);
//		
//		//When
//		boolean userSer = userService.authenticated(u);
//		
//		//Then
//		assertFalse(userSer);
//	}
//	
//	
//	@Test
//	public void testUserAndPassExitsIsAccountant() {
//		// Give
//		User u = new User();
//		u.setUsername("hieu");
//		u.setPassword("afc8e16842061ea3dbb023bf5f08d1bc3a728429313fab0cba30f60954ff9064");
//		u.setIsaccountant(1);
//		
//		//When
//		boolean userSer = userService.authenticated(u);
//		
//		//Then
//		assertTrue(userSer);
//	}
//	
//	@Test
//	public void testEmployeeIDIsNull() {
//		// Give
//		User u = new User();
//		u.setUsername("hieu");
//		u.setPassword("afc8e16842061ea3dbb023bf5f08d1bc3a728429313fab0cba30f60954ff9064");
//		u.setIsaccountant(1);
//		
//		
//		//When
//		boolean userSer = userService.authenticated(u);
//		
//		//Then
//		assertTrue(userSer);
//	}
//	
//	@Test
//	public void testEmployeeIDIsBlank() {
//		// Give
//		User u = new User();
//		u.setUsername("hieu");
//		u.setPassword("afc8e16842061ea3dbb023bf5f08d1bc3a728429313fab0cba30f60954ff9064");
//		u.setIsaccountant(1);
//		
//		
//		//When
//		boolean userSer = userService.authenticated(u);
//		
//		//Then
//		assertTrue(userSer);
//	}
//	
//	@Test
//	public void testEmployeeIDIsNotExits() {
//		// Give
//		User u = new User();
//		u.setUsername("hieu");
//		u.setPassword("afc8e16842061ea3dbb023bf5f08d1bc3a728429313fab0cba30f60954ff9064");
//		u.setIsaccountant(1);
//		
//		
//		//When
//		boolean userSer = userService.authenticated(u);
//		
//		//Then
//		assertTrue(userSer);
//	}
//	
//	@Test
//	public void testEmployeeIDIsExits() {
//		// Give
//		User u = new User();
//		u.setUsername("hieu");
//		u.setPassword("afc8e16842061ea3dbb023bf5f08d1bc3a728429313fab0cba30f60954ff9064");
//		u.setIsaccountant(1);
//		
//		
//		//When
//		boolean userSer = userService.authenticated(u);
//		
//		//Then
//		assertTrue(userSer);
//	}
//	
//	@Test
//	public void testListEmployeeIsEmpty() {
//		// Give
//		User u = new User();
//		u.setUsername("hieu");
//		u.setPassword("afc8e16842061ea3dbb023bf5f08d1bc3a728429313fab0cba30f60954ff9064");
//		u.setIsaccountant(1);
//		
//		
//		//When
//		boolean userSer = userService.authenticated(u);
//		
//		//Then
//		assertTrue(userSer);
//	}
//	
//	@Test
//	public void testListEmployee() {
//		// Give
//		User u = new User();
//		u.setUsername("hieu");
//		u.setPassword("afc8e16842061ea3dbb023bf5f08d1bc3a728429313fab0cba30f60954ff9064");
//		u.setIsaccountant(1);
//		
//		
//		//When
//		boolean userSer = userService.authenticated(u);
//		
//		//Then
//		assertTrue(userSer);
//	}
//}
