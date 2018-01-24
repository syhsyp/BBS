package com.bruce.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class manager{
	private String id;
	private String username;
	private String pwd;
	private String full_name;
	private java.sql.Date birthdate;
	private String email;
	private java.sql.Date register_date;
	private java.sql.Date last_login_date;
	private int level;
	private static connect dbc_connect;
	private static final String LOGIN_SQL = "SELECT * FROM managers WHERE username = ? AND password = ?";
	private static final String ADD_USER = "INSERT INTO managers VALUES ((SELECT REPLACE(UUID(), '-', '')),?,?,?,?,?,?,?,3)";
	private static final String ADD_ACESS = "UPDATE managers SET level = ? WHERE id = ?";
	private static final String FIND = "SELECT * FROM managers WHERE id = ?";
	private static final String DELETE = "DELETE FROM managers WHERE id = ?";
	private static final String UPDATE_B = "UPDATE managers SET birthdate = ? WHERE id = ?";
	private static final String UPDATE_E = "UPDATE managers SET email = ? WHERE id = ?";
	private static final String UPDATE_LOGIN = "UPDATE managers SET last_login_date = ? WHERE id = ?";
	
	public manager(){}
	
	public manager(String username, String pwd, String full_name, java.sql.Date birthdate, String email) {
		this.username = username;
		this.pwd = pwd;
		this.full_name = full_name;
		this.birthdate = birthdate;
		this.email = email;
		this.register_date = new java.sql.Date(new Date().getTime());
		dbc_connect = new connect();
		this.last_login_date = this.register_date;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(ADD_USER);
			stmt.clearParameters();
			stmt.setString(1, username);
			stmt.setString(2, pwd);
			stmt.setString(3, full_name);
			stmt.setDate(4, birthdate);
			stmt.setString(5, email);
			stmt.setDate(6, register_date);
			stmt.setDate(7, register_date);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	public boolean validate(String user_name, String password) {
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtT = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(LOGIN_SQL);
			stmt.clearParameters();
			stmt.setString(1, user_name);
			stmt.setString(2, password);
			ResultSet results = stmt.executeQuery();
			//if validate success, get user info
			if (results.next()) {
				this.username = user_name;
				this.pwd = password;
				this.id = results.getString("id");
				this.birthdate = results.getDate("birthdate");
				this.email = results.getString("email");
				this.register_date = results.getDate("register_date");
				this.last_login_date = results.getDate("last_login_date");
				this.level = results.getInt("level");
				stmtT = conn.prepareStatement(UPDATE_LOGIN);
				stmtT.setDate(1, new java.sql.Date(new Date().getTime()));
				stmtT.setString(2, id);
				stmtT.executeUpdate();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
		return false;
	}
	
	public void add_access(int mlevel) {
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(ADD_ACESS);
			stmt.clearParameters();
			stmt.setInt(1, mlevel);
			stmt.setString(2, id);
			stmt.executeUpdate();
			this.level = mlevel;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	public int level() {return this.level;}
	public String birthdate() {return this.birthdate.toString();}
	public String email() {return this.email;}
	public String last() {return last_login_date.toString();}
	public String id() {return this.id;}
	
	public void update_birthdate(java.sql.Date birthdate) {
		this.birthdate = birthdate;
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(UPDATE_B);
			stmt.clearParameters();
			stmt.setDate(1, birthdate);
			stmt.setString(2, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	//pattern match“∆µΩ«∞∂À
	public void update_email(String email) {
		this.email= email;
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(UPDATE_E);
			stmt.clearParameters();
			stmt.setString(1, email);
			stmt.setString(2, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}

	public void destroy() {
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(DELETE);
			stmt.clearParameters();
			stmt.setString(1, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
}
