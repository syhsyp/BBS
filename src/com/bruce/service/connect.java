package com.bruce.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connect {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/clients";
    
    static final String USER = "root";
    static final String PASS = "syh123456";
    
    private Connection conn = null;
    
    public connect() {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public Connection getConnection() {
    	return this.conn;
    }
    
    public void close() {
    	if (this.conn != null) {
    		try {
    			this.conn.close();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }
}
