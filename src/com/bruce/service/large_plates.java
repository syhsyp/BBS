package com.bruce.service;
import java.sql.*;
import java.util.Date;

public class large_plates {
	private String id;
	private String name;
	private String description;
	private java.sql.Date created_date;
	private java.sql.Date last_modified_date;
	private static connect dbc_connect;
	private static final int ACCESS_LEVEL = 1;
	private static final String PLATE = "SELECT * FROM plates_large WHERE id = ?";
	private static final String UPDATE_DES = "UPDATE plates_large SET description = ? WHERE id = ?";
	private static final String CHECK_M = "SELECT * FROM managers WHERE id = ?";
	private static final String CHECK_P = "SELECT * FROM plates_large WHERE id = ?";
	private static final String CHECK_P_NAME = "SELECT * FROM plates_large WHERE name = ?";
	private static final String ADD_M = "INSERT INTO managers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String ADD_R = "INSERT INTO managers_large VALUES (?, ?)";
	private static final String ADD_P = "INSERT INTO plates_large VALUES ((SELECT REPLACE(UUID(), '-', '')), ?, ?, ?, ?)";
	private static final String DELETE = "DELETE FROM managers WHERE id = ?";
	private static final String DELETE_P = "DELETE t1,t2,t3,t4 FROM plates_large t1 LEFT JOIN plates_small t2 ON t1.id = t2.parent_id LEFT JOIN topics t3 ON t2.id = t3.parent_id LEFT JOIN reply t4 ON t3.id = t4.parent_id WHERE t1.id = ?";
	private static final String DELETE_CHECK = "SET FOREIGN_KEY_CHECKS = 0";
	private static final String UPDATE_D = "UPDATE plates_large SET last_modified_date = ? WHERE id = ?";
	private static final String FIND_ID = "SELECT * FROM plates_large WHERE name = ?";
	
	//load a large plate
	public large_plates(String id) {
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	//add a new large plate
	public large_plates(String name, String description) {
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
		this.last_modified_date = this.created_date;
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt_id = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(ADD_P);
			stmt.setString(1, name);
			stmt.setString(2, description);
			stmt.setDate(3, created_date);
			stmt.setDate(4, created_date);
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
	
	public void destroy() {
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	public boolean add_large_manager(String pid, String manager_id) {
		//如果不是对应权限管理员返回false
		if (is_manager(manager_id) != ACCESS_LEVEL) {
			return false;
		}
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			//检查版块是否存在
			stmt = conn.prepareStatement(CHECK_P);
			if (!stmt.executeQuery().next()) return false;
			//add the manager to the db
			stmt1 = conn.prepareStatement(ADD_M);
			stmt1.clearParameters();
			stmt1.setString(1, id);
			stmt1.setString(2, manager_id);
			stmt1.executeUpdate();
			//add the relation between large plate and large plate
			stmt2 = conn.prepareStatement(ADD_R);
			stmt2.setString(1, manager_id);
			stmt2.setString(2, pid);
			stmt2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
		return true;
	}
	
	//撤销小版块管理员
	public boolean delete_large_manager(String manager_id) {
		//如果不是对应权限管理员返回false
		if (is_manager(manager_id) != 2) {
			return false;
		}
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(DELETE);
			stmt.setString(1, manager_id);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			dbc_connect.close();
		}
		return true;
	}
	
	public String name() {return this.name;}
	public String description() {return this.description;}
	public String created_date() {return this.created_date.toString();}
	public String id() {return this.id;}
		
	//更新本大板块文字介绍
	public void updateDescription(String content) {
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtT = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(UPDATE_DES);
			stmt.clearParameters();
			stmt.setString(1, content);
			stmt.setString(2, id);
			stmt.executeUpdate();
			this.description = content;
			stmtT = conn.prepareStatement(UPDATE_D);
			stmtT.setDate(1, new java.sql.Date(new Date().getTime()));
			stmtT.setString(2, id);
			stmtT.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	//判定是否为管理员并返回权限级别，如果没有设置权限返回3
	public int is_manager(String id) {
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(CHECK_M);
			stmt.clearParameters();
			stmt.setString(1, id);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				return results.getInt("level");
			} else {
				return 3;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
		return 3;
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
}