package com.kinolee.controller;

import static com.kinolee.oracle.OracleJdbc.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.kinolee.model.User;
import static com.kinolee.model.User.Entity.*;
import oracle.jdbc.OracleDriver;

public enum UsersDao {
	Instance;
	
	UsersDao() {
		try {
			// 오라클 jdbc 드라이버를 메모리에 로딩/등록.
			DriverManager.registerDriver(new OracleDriver());
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(stmt!=null)stmt.close();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void closeResources (Connection conn, PreparedStatement stmt) {
		closeResources(conn, stmt,null);
	}
	
	private static final String SQL_UPDATE_USERS = String.format("update %s set %s=?, %s=?,%s=?,%s=? where %s=?", 
			TBL_USERS,COL_PASSWORD,COL_USER_FIRST_NAME,COL_USER_LAST_NAME,COL_EMAIL,COL_USER_ID);
	/**
	 * User객체를 전달받아 Users table의 userId를 제외한 컬럼들을 업데이트한다.
	 * @param user
	 * @return 성공하면 1, 실패하면 -1.
	 */
	public int updateUsers(User user) {
		int result=-1;
		Connection conn=null;
		PreparedStatement stmt = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_UPDATE_USERS);
			stmt.setString(1, user.getUserPassword());
			stmt.setString(2, user.getUserFirstName());
			stmt.setString(3, user.getUserLastName());
			stmt.setString(4,user.getUserEmail());
			stmt.setString(5, user.getUserId());
			stmt.executeUpdate();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt);
		}
		return result;
	}
	private static final String SQL_SELECT_ALL = String.format("select * from %s", TBL_USERS);
	/**
	 * Users 테이블의 모든 행을 읽어 List<User> 반환, 행이 없을 시 null 반환.
	 * @return List<User>
	 */
	public List<User> read() {
		List<User> users = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_SELECT_ALL);
			rs=stmt.executeQuery();
			while(rs.next()) {
				User user = getUserFromResultSet(rs);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		
		return users;
	}
	
	private static final String SQL_CHECK_ID = "SELECT * FROM " + TBL_USERS + " WHERE " + COL_USER_ID + " = ?";
	/**
	 * id를 전달받아 해당 id를 가진 User객체 반환, 해당 User가 존재하지않으면 null 반환.
	 * @param id
	 * @param password
	 * @return User
	 */
	public User read(String id){
		User user=null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_CHECK_ID);
			stmt.setString(1, id);
			rs=stmt.executeQuery();
			while(rs.next()) {
				user = getUserFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		return user;
		
	}
	
	private static final String SQL_INSERT_TO_USERS_TABLE=String.format("insert into %s (%s,%s,%s,%s,%s)"
			+ "values (?,?,?,?,?)", TBL_USERS,COL_USER_ID,COL_USER_LAST_NAME,COL_USER_FIRST_NAME,COL_PASSWORD,COL_EMAIL);
	
	/**
	 * 전달받은 user객체로 Users테이블에 새로운 유저정보 업로드.
	 * @param user
	 * @return 성공하면 1 실패하면 -1
	 */
	public int insertUserToUsersTable(User user) {
		int result=-1;
		Connection conn=null;
		PreparedStatement stmt = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_INSERT_TO_USERS_TABLE);
			stmt.setString(1, user.getUserId());
			stmt.setString(2, user.getUserLastName());
			stmt.setString(3, user.getUserFirstName());
			stmt.setString(4,user.getUserPassword());
			stmt.setString(5, user.getUserEmail());
			stmt.executeUpdate();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt);
		}
		return result;
	}
	
	
	/**
	 * resultset을 전달받아 읽은 행을 User 객체로 반환
	 * @param rs
	 * @return User
	 * @throws SQLException
	 */
	private User getUserFromResultSet(ResultSet rs) throws SQLException {
		String id=rs.getString(COL_USER_ID);
		String password=rs.getString(COL_PASSWORD);
		String email=rs.getString(COL_EMAIL);
		String firstName=rs.getString(COL_USER_FIRST_NAME);
		String lastName=rs.getString(COL_USER_LAST_NAME);
		return User.builder().userEmail(email).userFirstName(firstName).userLastName(lastName)
		.userId(id).userPassword(password).build();
	}
	
}
