package com.kinolee.controller;

import static com.kinolee.oracle.OracleJdbc.PASSWORD;
import static com.kinolee.oracle.OracleJdbc.URL;
import static com.kinolee.oracle.OracleJdbc.USER;
import static com.kinolee.model.BookingStatus.Entity.*;
import static com.kinolee.model.Screening.Entity.TBL_SCREENINGS;
import static com.kinolee.model.Screening.Entity.COL_SCREENING_DATE;
import static com.kinolee.model.Screening.Entity.COL_SCREENING_TIME;
import static com.kinolee.model.Screening.Entity.COL_MOVIE_ID;
import static com.kinolee.model.Movie.Entity.COL_MOVIE_TITLE;
import static com.kinolee.model.Movie.Entity.TBL_MOVIES;
import static com.kinolee.model.Screen.Entity.TBL_SCREENS;
import static com.kinolee.model.Screen.Entity.COL_SCREEN_NAME;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kinolee.model.MyBooking;
import com.kinolee.model.MyBooking.MyBookingBuilder;
import com.kinolee.model.Screening;
import com.kinolee.model.User;

import oracle.jdbc.OracleDriver;

public enum BookingDao {
	INSTANCE;

	BookingDao() {
		try {
			// 오라클 jdbc 드라이버를 메모리에 로딩/등록.
			DriverManager.registerDriver(new OracleDriver());
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void closeResources(Connection conn, PreparedStatement stmt) {
		closeResources(conn, stmt, null);
	}

	private static final String SQL_INSERT_BOOKING_TABLE = String.format(
			"insert into %s (%s,%s,%s,%s,%s)" + "values(?,?,?,?,systimestamp)", TBL_BOOKING_STATUS, COL_USER_ID,
			COL_SCREENING_ID, COL_SCREEN_ID, COL_SEAT_ID, COL_BOOKING_TIME);

	/**
	 * Screening, User 객체, 선택된 좌석id를 String으로 가지는 리스트를 받아 객체로 만들어
	 * BookingStatusTable에 insert한다.
	 * 
	 * @param screening
	 * @param user
	 * @param clickedButtonId
	 * @return 성공하면 1, 실패하면 -1
	 */
	public int insertToBookingTable(Screening screening, User user, String buttonId) {
		int result = -1;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.prepareStatement(SQL_INSERT_BOOKING_TABLE);
			stmt.setString(1, user.getUserId());
			stmt.setString(2, screening.getScreeningId());
			stmt.setString(3, screening.getScreenId());
			stmt.setString(4, buttonId);
			stmt.executeUpdate();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt);
		}
		return result;
	}

	
	private static final String SQL_READ_MY_BOOKING=String.format("select b.%s, s.%s, m.%s, s.%s, s.%s, n.%s, b.%s, b.%s, s.%s"
		+ " from %s b, %s s, %s m, %s n"
		+ " where b.%s=? and b.%s=s.%s and s.%s=m.%s "
		+ "and s.%s>to_char(systimestamp,\'yyyy-mm-dd\') and s.%s=n.%s",COL_BOOKING_ID,COL_SCREENING_ID,COL_MOVIE_TITLE,
		COL_SCREENING_DATE,COL_SCREENING_TIME,COL_SCREEN_NAME,COL_SEAT_ID,COL_BOOKING_TIME,COL_SCREEN_ID,
		TBL_BOOKING_STATUS, TBL_SCREENINGS,TBL_MOVIES,TBL_SCREENS,
		COL_USER_ID,COL_SCREENING_ID,COL_SCREENING_ID,COL_MOVIE_ID,COL_MOVIE_ID,COL_SCREENING_DATE,COL_SCREEN_ID,COL_SCREEN_ID
		);
	/**
	 * userId를 전달받아 이것으로 BookingStatus테이블에서 해당 유저가 현재시간으로부터 상영일이 지난 영화 예매 내역을 가져와
	 * 이 테이블을 Screening테이블과 조인하여 "예매번호","상영id","제목","상영일","상영시간","상영관","상영관id","좌석","예매시간"를 가지는 객체 반환.
	 * @param userId
	 * @return List<MyBooking>
	 */
	public List<MyBooking> getBookingInfoList(String userId){
		List<MyBooking> bookingInfoList=new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_READ_MY_BOOKING);
			stmt.setString(1, userId);
			rs=stmt.executeQuery();
			while(rs.next()) {
				MyBooking myBooking= getMyBookingFromResultSet(rs);
				bookingInfoList.add(myBooking);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		return bookingInfoList;
	}

	private MyBooking getMyBookingFromResultSet(ResultSet rs) throws SQLException {
		String bookingId=rs.getString(COL_BOOKING_ID);
		String screeningId=rs.getString(COL_SCREENING_ID);
		String movieTitle=rs.getString(COL_MOVIE_TITLE);
		String screeningDate=rs.getString(COL_SCREENING_DATE);
		String screeningTime=rs.getString(COL_SCREENING_TIME);
		String bookingTime=rs.getString(COL_BOOKING_TIME);
		String seatId=rs.getString(COL_SEAT_ID);
		String screenName=rs.getString(COL_SCREEN_NAME);
		String screenId=rs.getString(COL_SCREEN_ID);
		return MyBooking.builder().bookingId(bookingId).screenName(screenName).screeningId(screeningId)
				.movieTitle(movieTitle).screeningDate(screeningDate).screeningTime(screeningTime)
				.seatId(seatId).bookingTime(bookingTime).screenId(screenId).build();
	}
	
	private static String SQL_DELETE_FROM_BOOKING_TABLE=
			String.format("delete from %s where %s=?", TBL_BOOKING_STATUS,COL_BOOKING_ID);
	/**
	 * bookingId를 전달받아 Booking_status테이블에서 행 삭제.
	 * @param bookingId
	 * @return 성공하면 1, 실패하면 -1
	 */
	public int deleteBookingHistory(String bookingId) {
		int result=-1;
		Connection conn=null;
		PreparedStatement stmt = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_DELETE_FROM_BOOKING_TABLE);
			stmt.setString(1, bookingId);
			stmt.executeUpdate();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt);
		}
		return result;
		
	}
	
	private static String SQL_UPDATE_SEATID_FROM_BOOKING_TABLE=String.format("update %s set %s=? where %s=?", TBL_BOOKING_STATUS,COL_SEAT_ID,COL_BOOKING_ID);
	
	/**
	 * bookingId와 seatId를 전달받아 BookingStatus테이블의 해당 행의 seatId를 전달받은 seatId로 변경.
	 * @param bookingId
	 * @param seatId
	 * @return 성공하면 1 실패하면 -1
	 */
	public int changeSeat(String bookingId, String seatId) {
		int result=-1;
		Connection conn=null;
		PreparedStatement stmt = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_UPDATE_SEATID_FROM_BOOKING_TABLE);
			stmt.setString(1, seatId.toLowerCase());
			stmt.setString(2, bookingId);
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
