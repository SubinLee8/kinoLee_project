package com.kinolee.controller;

import static com.kinolee.model.SeatS1.Entity.*;
import static com.kinolee.oracle.OracleJdbc.PASSWORD;
import static com.kinolee.oracle.OracleJdbc.URL;
import static com.kinolee.oracle.OracleJdbc.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kinolee.model.SeatS1;


import oracle.jdbc.OracleDriver;

public enum SeatS1Dao {
INSTANCE;
	
	SeatS1Dao() {
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
	
	private static final String SQL_SELECT_ALL = String.format("select * from %s", TBL_SEATS_S1);
	
	public List<SeatS1> read() {
		List<SeatS1> seatS1 = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_SELECT_ALL);
			rs=stmt.executeQuery();
			while(rs.next()) {
				SeatS1 seat = getSeatsS1FromResultSet(rs);
				seatS1.add(seat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		
		return seatS1;
	}
	
	public List<String> readSeatId() {
		List<String> seatS1 = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_SELECT_ALL);
			rs=stmt.executeQuery();
			while(rs.next()) {
				String seatId = rs.getString(COL_SEAT_ID);
				seatS1.add(seatId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		
		return seatS1;
	}

	private SeatS1 getSeatsS1FromResultSet(ResultSet rs) throws SQLException {
		return new SeatS1(rs.getString(COL_SCREEN_ID), rs.getString(COL_SEAT_ID));
	}
	
}
