package com.demo.spring.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.demo.spring.db.ConnectDB;
import com.demo.spring.model.dao.DBMapper;


public class BasicDao {
	private Connection conn;

	{
		try {
			openConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

	// open connect
	protected void openConnection() throws SQLException {
		if (conn == null || conn.isClosed()) {
				new ConnectDB();
				conn = ConnectDB.getConnection();
		}
	}

	// close connect
	protected void closeConnection() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}
	
	protected Connection getConnection() {
		return conn;
	}

	// get list_Product
	protected <T> List<T> getList(DBMapper<T> mapper, String sql,
			Object[] parameters) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		addParameters(parameters, pstmt);

		ResultSet rs = pstmt.executeQuery();

		List<T> list = new ArrayList<T>();
		while (rs.next()) {
			list.add(mapper.doMapping(rs));
		}
		return list;
	}

	protected <T> List<T> getList(DBMapper<T> mapper, String sql)
			throws SQLException {
		return getList(mapper, sql, null);
	}

	protected <T> T getSingleObject(DBMapper<T> mapper, String sql,
			Object[] parameters) throws SQLException {
		PreparedStatement pstmt = conn.prepareStatement(sql);
		addParameters(parameters, pstmt);

		ResultSet rs = pstmt.executeQuery();
		T object = null;
		if (rs.next()) {
			object = mapper.doMapping(rs);
		}
		return object;
	}

	// add_parameter in sql
	protected void addParameters(Object[] parameters, PreparedStatement pstmt)
			throws SQLException {
		if (parameters == null) {
			return;
		}
		for (int index = 0; index < parameters.length; index++) {
			addParameter(pstmt, index, parameters[index]);
		}
	}

	// convert type parameter
	protected void addParameter(PreparedStatement pstmt, int index,
			Object object) throws SQLException {
		if (object instanceof String) {
			pstmt.setString(index + 1, (String) object);
		} else if (object instanceof Integer) {
			pstmt.setInt(index + 1, (Integer) object);
		} else if (object instanceof Double) {
			pstmt.setDouble(index + 1, (Double) object);
		} else if (object instanceof Float) {
			pstmt.setFloat(index + 1, (Float) object);
		} else if (object instanceof Long) {
			pstmt.setLong(index + 1, (Long) object);
		} else {
			pstmt.setObject(index + 1, object);
		}
	}

	

	protected Integer getLastInsertedId() throws SQLException {
		String sql = "SELECT LAST_INSERT_ID() as last_inserted_id";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		Integer lastId = rs.next() ? rs.getInt("last_inserted_id") : null;
		return lastId;
	}

	//auto don rac
	@Override
	protected void finalize() throws Throwable {
		System.out.println("GC will destroy me, now I will close Connection");
		try {
			closeConnection();
		} catch (Throwable e) {

		}
		super.finalize();
	}
}
