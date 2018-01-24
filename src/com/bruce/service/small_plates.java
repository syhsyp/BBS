package com.bruce.service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class small_plates{
	private String id;
	private String name;
	private String description;
	private String parent_id;
	private java.sql.Date created_date;
	private java.sql.Date last_modified_date;
	private static connect dbc_connect;
	private static final int ACCESS_LEVEL = 2;
	private static final String PLATE = "SELECT * FROM plates_small WHERE id = ?";
	private static final String UPDATE_DES = "UPDATE plates_small SET description = ? WHERE id = ?";
	private static final String CHECK_M = "SELECT * FROM managers WHERE id = ?";
	private static final String CHECK_P = "SELECT * FROM plates_small WHERE id = ?";
	private static final String CHECK_P_NAME = "SELECT * FROM plates_small WHERE name = ?";
	private static final String ADD_M = "INSERT INTO managers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String ADD_R = "INSERT INTO managers_small VALUES (?, ?)";
	private static final String ADD_P = "INSERT INTO plates_small VALUES ((SELECT REPLACE(UUID(), '-', '')), ?, ?, ?, ?, ?)";
	private static final String DELETE = "DELETE FROM managers WHERE id = ?";
	private static final String DELETE_P = "DELETE t1,t2,t3 FROM plates_small t1 LEFT JOIN topics t2 ON t1.id = t2.parent_id LEFT JOIN reply t3 ON t2.id = t3.parent_id WHERE t1.id = ?";
	private static final String DELETE_CHECK = "SET FOREIGN_KEY_CHECKS = 0";
	private static final String CHANGE_PARENT = "UPDATE plates_small SET parent_id = ? AND last_modified_date = ? WHERE id = ?";
	private static final String UPDATE_D = "UPDATE plates_large SET last_modified_date = ? WHERE id = ?";
	private static final String FIND_ID = "SELECT * FROM plates_small WHERE name = ?";
	
	//load a small plate
	public small_plates(String id) {
		this.id = id;
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(PLATE);
			stmt.clearParameters();
			stmt.setString(1, id);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				this.name = results.getString("name");
				this.description = results.getString("description");
				this.created_date = results.getDate("created_date");
				this.last_modified_date = results.getDate("last_modified_date");
				this.parent_id =results.getString("parent_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	//add a new small plate
	public small_plates(String name, String description, String parent_id) {
		//如果已有同名版块，返回false
		if (is_same_name(name)) {
			try {
				throw new SameNameException();
			} catch (SameNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.name = name;
		this.description =description;
		this.created_date = new java.sql.Date(new Date().getTime());
		this.parent_id = parent_id;
		this.last_modified_date = this.created_date;
		
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt_id = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(ADD_P);
			stmt.setString(1, parent_id);
			stmt.setString(2, name);
			stmt.setString(3, description);
			stmt.setDate(4, created_date);
			stmt.setDate(5, created_date);
			stmt.executeUpdate();
			stmt_id = conn.prepareStatement(FIND_ID);
			stmt_id.setString(1, name);
			ResultSet results = stmt_id.executeQuery();
			if (results.next()) this.id = results.getString("id");
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			dbc_connect.close();
		}
	}
	
	//移动小版块到其他大版块
	public void change_parent(String pid) {
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(CHANGE_PARENT);
			stmt.clearParameters();
			stmt.setString(1, pid);
			stmt.setDate(2, new java.sql.Date(new Date().getTime()));
			stmt.setString(3, id);
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
		PreparedStatement stmt_D = null;
		PreparedStatement stmt_pre = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt_pre = conn.prepareStatement(DELETE_CHECK);
			stmt_pre.executeQuery();
			stmt = conn.prepareStatement(DELETE_P);
			stmt.clearParameters();
			stmt.setString(1, id);
			stmt.executeUpdate();
			stmt_D = conn.prepareStatement(UPDATE_D);
			stmt_D.setDate(1, new java.sql.Date(new Date().getTime()));
			stmt_D.setString(2, parent_id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	//判定是否存在同名版块
	public boolean is_same_name(String name) {
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(CHECK_P_NAME);
			stmt.clearParameters();
			stmt.setString(1, name);
			ResultSet results = stmt.executeQuery();
			return results.next();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
		return false;
	}
	
	public String name() {return this.name;}
	public String description() {return this.description;}
	public String created_date() {return this.created_date.toString();}
	public String id() {return this.id;}
}
