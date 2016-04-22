package com.demo.spring.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
	public static Connection getConnection() {
		Connection conn = null;
		String userdb = "root";
		String passdb = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopproducts", userdb, passdb);
			System.out.println("ket noi thanh cong");
		} catch (Exception e) {
			System.out.println("ket noi that bai");
		}
		return conn;
	}
}
