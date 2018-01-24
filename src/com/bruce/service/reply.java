package com.bruce.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class reply {
	private String id;
	private String parent_id;
	private String replier_id;
	private String content;
	private java.sql.Date created_date;
	private static connect dbc_connect;
	private static final String FIND_R = "SELECT * FROM reply WHERE id = ?";
	private static final String DELETE = "DELETE FROM reply WHERE id = ?";
	private static final String UPDATE_CONTENT = "UPDATE reply SET content = ? WHERE id = ?";
	private static final String ADD = "INSERT INTO reply VALUES((SELECT REPLACE(UUID(), '-', '')), ?, ?, ?, ?)";
	private static final String UPDATE_D = "UPDATE topics SET last_modified_date = ? WHERE id = ?";
	private static final String FIND_ID = "SELECT * FROM reply WHERE parent_id = ? AND replier_id = ? AND content = ?";
	
	//加载已有回复
	public reply(String id) {
		this.id = id;
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(FIND_R);
			stmt.clearParameters();
			stmt.setString(1, id);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				this.parent_id = results.getString("parent_id");
				this.replier_id = results.getString("creater_id");
				this.content = results.getString("content");
				this.created_date = results.getDate("created_date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	//新建回复
	public reply(String parent_id, String replier_id, String content) {
		this.parent_id = parent_id;
		this.replier_id = replier_id;
		this.content = content;
		this.created_date = new java.sql.Date(new Date().getTime());
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt_D = null;
		PreparedStatement stmt_id = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(ADD);
			stmt.clearParameters();
			stmt.setString(1, parent_id);
			stmt.setString(2, replier_id);
			stmt.setString(3, content);
			stmt.setDate(4, created_date);
			stmt.executeUpdate();
			stmt_D = conn.prepareStatement(UPDATE_D);
			stmt_D.setDate(1, new java.sql.Date(new Date().getTime()));
			stmt_D.setString(2, parent_id);
			stmt_D.executeUpdate();
			stmt_id = conn.prepareStatement(FIND_ID);
			stmt_id.setString(1, parent_id);
			stmt_id.setString(2, replier_id);
			stmt_id.setString(3, content);
			ResultSet results = stmt_id.executeQuery();
			if (results.next()) this.id = results.getString("id");
			PreparedStatement stmtT = conn.prepareStatement(UPDATE_D);
			stmtT.setDate(1, new java.sql.Date(new Date().getTime()));
			stmtT.setString(2, id);
			stmtT.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	//删除回复
	public void destroy() {
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt_D = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(DELETE);
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
	
	public String content() {return this.content;}
	public String id() {return this.id;}
	
//	//修改回复内容
//	public void update_content(String content) {
//		dbc_connect = new connect();
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = dbc_connect.getConnection();
//			stmt = conn.prepareStatement(UPDATE_CONTENT);
//			stmt.clearParameters();
//			stmt.setString(1, content);
//			stmt.setString(2, id);
//			stmt.executeUpdate();
//			this.content = content;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			dbc_connect.close();
//		}
//	}
}
