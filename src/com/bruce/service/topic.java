package com.bruce.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class topic {
	private String id;
	private String parent_id;
	private String creater_id;
	private String title;
	private String content;
	private java.sql.Date created_date;
	private java.sql.Date last_modified_date;
	private static connect dbc_connect;
	private static final String FIND_T = "SELECT * FROM topics WHERE id = ?";
	private static final String UPDATE_CONTENT = "UPDATE topics SET content = ? WHERE id = ?";
	private static final String DELETE = "DELETE t1,t2 FROM topics t1 LEFT JOIN reply t2 ON t1.id = t2.parent_id WHERE t1.id = ?";
	private static final String DELETE_CHECK = "SET FOREIGN_KEY_CHECKS = 0";
	private static final String ADD = "INSERT INTO topics VALUES((SELECT REPLACE(UUID(), '-', '')), ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_D = "UPDATE topics SET last_modified_date = ? WHERE id = ?";
	private static final String UPDATE_D_P = "UPDATE plates_small SET last_modified_date = ? WHERE id = ?";
	private static final String FIND_ID = "SELECT * FROM topics WHERE creater_id = ? AND title = ? AND content = ?";
	
	// load an existing topic
	public topic(String id) {
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(FIND_T);
			stmt.clearParameters();
			stmt.setString(1, id);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				this.parent_id = results.getString("parent_id");
				this.creater_id = results.getString("creater_id");
				this.title = results.getString("title");
				this.content = results.getString("content");
				this.created_date = results.getDate("created_date");
				this.last_modified_date = results.getDate("last_modified_date");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	// create a new topic
	public topic(String parent_id, String creater_id, String title, String content) {
		this.parent_id = parent_id;
		this.creater_id = creater_id;
		this.title = title;
		this.content = content;
		this.created_date = new java.sql.Date(new Date().getTime());
		this.last_modified_date = this.created_date;
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt_id = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(ADD);
			stmt.clearParameters();
			stmt.setString(1, parent_id);
			stmt.setString(2, creater_id);
			stmt.setString(3, title);
			stmt.setString(4, content);
			stmt.setDate(5, created_date);
			stmt.setDate(6, created_date);
			stmt.executeUpdate();
			PreparedStatement stmtT = conn.prepareStatement(UPDATE_D);
			stmtT.setDate(1, new java.sql.Date(new Date().getTime()));
			stmtT.setString(2, parent_id);
			stmtT.executeUpdate();
			stmt_id = conn.prepareStatement(FIND_ID);
			stmt_id.setString(1, creater_id);
			stmt_id.setString(2, title);
			stmt_id.setString(3, content);
			ResultSet results = stmt_id.executeQuery();
			if (results.next()) this.id = results.getString("id");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	// update topic content
	public void update_content(String content) {
		dbc_connect = new connect();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = dbc_connect.getConnection();
			stmt = conn.prepareStatement(UPDATE_CONTENT);
			stmt.clearParameters();
			stmt.setString(1, content);
			stmt.setString(2, id);
			stmt.executeUpdate();
			this.content = content;
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
	
	// delete a topic
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
			stmt = conn.prepareStatement(DELETE);
			stmt.clearParameters();
			stmt.setString(1, id);
			stmt.executeUpdate();
			PreparedStatement stmtT = conn.prepareStatement(UPDATE_D_P);
			stmtT.setDate(1, new java.sql.Date(new Date().getTime()));
			stmtT.setString(2, parent_id);
			stmtT.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbc_connect.close();
		}
	}
	
	public String content() {return this.content;}
	public String id() {return this.id;}
}
