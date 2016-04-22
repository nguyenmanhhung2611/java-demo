package com.demo.spring.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBMapper<T> {
	T doMapping(ResultSet rs) throws SQLException;
}
