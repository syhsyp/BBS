package com.bruce.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.*;

public class users {
	private String id;
	private String username;
	private String pwd;
	private String full_name;
	private java.sql.Date birthdate;
	private String email;
	private java.sql.Date register_date;
	private java.sql.Date last_login_date;
	private static connect dbc_connect;
	private static final String LOGIN_SQL = "SELECT * FROM users WHERE username = ? AND password = ?";
	private static final String LOGIN_ID = "SELECT * FROM users WHERE id = ?";
	private static final String ADD_USER = "INSERT INTO users VALUES ((SELECT REPLACE(UUID(), '-', '')),?,?,?,?,?,?,?)";
	private static final String DELETE = "DELETE FROM users WHERE id = ?";
	private static final String UPDATE_B = "UPDATE users SET birthdate = ? WHERE id = ?";
	private static final String UPDATE_E = "UPDATE users SET email = ? WHERE id = ?";
	private static final String UPDATE_N = "UPDATE users SET full_name = ? WHERE id = ?";
	private static final String UPDATE_PWD = "UPDATE users SET password = ? WHERE id = ?";
	private static final String UPDATE_LOGIN = "UPDATE users SET last_login_date = ? WHERE id = ?";
	
	public users() {}
	
	public users(String id){
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtT = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(LOGIN_ID);
			stmt.clearParameters();
			stmt.setString(1, id);
			ResultSet results = stmt.executeQuery();
			//if validate success, get user info
			if (results.next()) {
				this.username = results.getString("username");
				this.pwd = results.getString("password");
				this.full_name = results.getString("full_name");
				this.id = results.getString("id");
				this.birthdate = results.getDate("birthdate");
				this.email = results.getString("email");
				this.register_date = results.getDate("register_date");
				this.last_login_date = results.getDate("last_login_date");
				stmtT = conn.prepareStatement(UPDATE_LOGIN);
				stmtT.setDate(1, new java.sql.Date(new Date().getTime()));
				stmtT.setString(2, id);
				stmtT.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	//create a new user account and insert tuple into the database
	public users(String username, String pwd, String full_name, java.sql.Date birthdate, String email) {
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
	
	//login validation
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
				this.full_name = results.getString("full_name");
				this.id = results.getString("id");
				this.birthdate = results.getDate("birthdate");
				this.email = results.getString("email");
				this.register_date = results.getDate("register_date");
				this.last_login_date = results.getDate("last_login_date");
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
	
	public String pwd() {return this.pwd;}
	public java.sql.Date birthdate() {return this.birthdate;}
	public String email() {return this.email;}
	public String last() {return last_login_date.toString();}
	public String id() {return this.id;}
	public String name() {return this.full_name;}
	
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
//		String pattern = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
//		if (Pattern.matches(pattern, email)) {
//			this.email = email;
//		}
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
	
	public void update_fullname(String fullname) {
		this.full_name= fullname;
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(UPDATE_N);
			stmt.clearParameters();
			stmt.setString(1, full_name);
			stmt.setString(2, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	public void update_password(String password) {
		this.pwd= password;
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(UPDATE_PWD);
			stmt.clearParameters();
			stmt.setString(1, pwd);
			stmt.setString(2, id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	public java.sql.Date strToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		java.util.Date d = null; 
		try { 
			d = format.parse(str); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
		return new java.sql.Date(d.getTime());
	}
}
