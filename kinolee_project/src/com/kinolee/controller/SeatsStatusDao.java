package com.kinolee.controller;

import static com.kinolee.oracle.OracleJdbc.PASSWORD;
import static com.kinolee.oracle.OracleJdbc.URL;
import static com.kinolee.oracle.OracleJdbc.USER;
import static com.kinolee.model.BookingStatus.Entity.COL_BOOKING_TIME;
import static com.kinolee.model.BookingStatus.Entity.COL_SCREENING_ID;
import static com.kinolee.model.BookingStatus.Entity.COL_SCREEN_ID;
import static com.kinolee.model.BookingStatus.Entity.COL_SEAT_ID;
import static com.kinolee.model.BookingStatus.Entity.COL_USER_ID;
import static com.kinolee.model.BookingStatus.Entity.TBL_BOOKING_STATUS;
import static com.kinolee.model.SeatS1.Entity.COL_SEAT_ID;
import static com.kinolee.model.SeatS1.Entity.TBL_SEATS_S1;
import static com.kinolee.model.SeatsS1Status.Entity.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kinolee.model.Movie;
import com.kinolee.model.Screening;
import com.kinolee.model.SeatsS1Status;

import oracle.jdbc.OracleDriver;

public enum SeatsStatusDao {
INSTANCE;
	
	SeatsStatusDao() {
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
	
	private String SQL_READ_SEATS_STATUS=String.format("select * from %s",TBL_SEATS_S1_STATUS );
	/**
	 * 테이블 seatsS1Status에서 행들을 읽어 리스트로 반환
	 * @return List<SeatsS1Status>
	 */
	public List<SeatsS1Status> read() {
		List<SeatsS1Status> seatsList = new ArrayList<SeatsS1Status>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_READ_SEATS_STATUS);
			rs=stmt.executeQuery();
			while(rs.next()) {
				SeatsS1Status seatStatus = getSeatsStatusFromResultSet(rs);
				seatsList.add(seatStatus);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		return seatsList;
	}
	private static final String SQL_INSERT_SEATS_S1_STATUS_TABLE=String.format("insert into %s (%s,%s,%s,%s)"
			+ "values(?,?,?,\'Y\')",TBL_SEATS_S1_STATUS,COL_SCREEN_ID,COL_SCREENING_ID,COL_SEAT_ID,COL_IS_RESERVED);
	
	/**
	 * Screening 객체와 seatId를 전달받아 SeatsS1Status테이블에 행 추가.
	 * @param screening
	 * @param seatId
	 * @return 성공하면 1, 실패하면 -1
	 */
	public int insertToSeatsStatusTable(Screening screening, String seatId) {
		int result=-1;
		Connection conn=null;
		PreparedStatement stmt = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_INSERT_SEATS_S1_STATUS_TABLE);
			stmt.setString(1, screening.getScreenId());
			stmt.setString(2, screening.getScreeningId());
			stmt.setString(3, seatId);
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
	 * screeningId, screenId,seatId를 전달받아 이를 이용해 SeatsS1Status테이블에 행 추가.
	 * @param screeningId
	 * @param screenId
	 * @param seatId
	 * @return 성공하면 1, 실패하면 -1
	 */
	public int insertToSeatsStatusTable(String screeningId, String screenId, String seatId) {
		int result=-1;
		Connection conn=null;
		PreparedStatement stmt = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_INSERT_SEATS_S1_STATUS_TABLE);
			stmt.setString(1, screenId);
			stmt.setString(2,screeningId);
			stmt.setString(3, seatId);
			stmt.executeUpdate();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt);
		}
		return result;
	}
	
	
	private String SQL_READ_SEATS_STATUS_BY_SCREENING_ID=String.format("select * from %s where %s=? and %s!=%s", TBL_SEATS_S1_STATUS,COL_SCREENING_ID,COL_IS_RESERVED,"\'n\'");
	/**
	 * Screening 객체를 전달받아 ScreeningId를 가진 객체 찾기
	 * @param screening
	 * @return List<SeatsS1Status>
	 */
	public List<SeatsS1Status> read(String screeningId){
		List<SeatsS1Status> seatsList = new ArrayList<SeatsS1Status>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_READ_SEATS_STATUS_BY_SCREENING_ID);
			stmt.setString(1, screeningId);
			rs=stmt.executeQuery();
			while(rs.next()) {
				SeatsS1Status seatStatus = getSeatsStatusFromResultSet(rs);
				seatsList.add(seatStatus);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		return seatsList;
	}

	private SeatsS1Status getSeatsStatusFromResultSet(ResultSet rs) throws SQLException {
		String screenId=rs.getString(COL_SCREEN_ID);
		String screeningId=rs.getString(COL_SCREENING_ID);
		String seatId=rs.getString(COL_SEAT_ID);
		String isReserved=rs.getString(COL_IS_RESERVED);
		return SeatsS1Status.builder().isReserved(isReserved).screenId(screenId).screeningId(screeningId).seatId(seatId).build();
	}
	
	
	public List<String> getSeatsIdList(Screening screening){
		List<SeatsS1Status> bookedseatsList=read(screening.getScreeningId());
		List<String> bookedSeatsId = new ArrayList<>();
		for(SeatsS1Status b :bookedseatsList) {
			bookedSeatsId.add(b.getSeatId());
		}
		return bookedSeatsId;
	}
	
	private static final String SQL_DELETE_FROM_SEATS_STATUS_TABLE=
			String.format("delete from %s where %s=? and %s=?", TBL_SEATS_S1_STATUS,COL_SCREENING_ID,COL_SEAT_ID);
	/**
	 * screeningId와 seatId를 받아 seatsS1Status테이블에서 행 삭제.
	 * @param screeningId
	 * @param seatId
	 * @return 성공하면 1 실패하면 -1
	 */
	public int deleteSeatHistory(String screeningId, String seatId) {
		int result=-1;
		Connection conn=null;
		PreparedStatement stmt = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_DELETE_FROM_SEATS_STATUS_TABLE);
			stmt.setString(1, screeningId);
			stmt.setString(2, seatId.toLowerCase());
			stmt.executeUpdate();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt);
		}
		return result;
	}
	
	private static final String SQL_UPDATE_SEAT_ID_FROM_SEAT_STATUS_TABLE=String.format("update %s set %s=? where %s=? and %s=?", TBL_SEATS_S1_STATUS,COL_SEAT_ID,COL_SCREENING_ID,COL_SEAT_ID);
	/**
	 * screeningId와 seatId를 전달받아 SeatS1Status 테이블에서 해당 행의 seatId를 업데이트한다.
	 * @param screeningId
	 * @param seatId
	 * @return 성공하면 1, 실패하면 -1
	 */
	public int changeHistory(Screening screening, String seatId, String newSeatId) {
		int result=-1;
		Connection conn=null;
		PreparedStatement stmt = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_UPDATE_SEAT_ID_FROM_SEAT_STATUS_TABLE);
			stmt.setString(1, newSeatId.toLowerCase());
			stmt.setString(2, screening.getScreeningId());
			stmt.setString(3, seatId.toLowerCase());
			stmt.executeUpdate();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt);
		}
		return result;
	}
	
	
}
