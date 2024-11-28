package com.kinolee.controller;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kinolee.model.Screening;

import static com.kinolee.model.Screening.Entity.*;
import static com.kinolee.model.Movie.Entity.TBL_MOVIES;
import static com.kinolee.oracle.OracleJdbc.PASSWORD;
import static com.kinolee.oracle.OracleJdbc.URL;
import static com.kinolee.oracle.OracleJdbc.USER;

import oracle.jdbc.OracleDriver;

public enum ScreeningsDao {
INSTANCE;
	
	ScreeningsDao() {
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
	
	private static final String SQL_JOIN_MOVIES_WITH_SCREENING_BY_ID=
			String.format("select s.%s,s.%s,s.%s,s.%s,"
					+ "s.%s from %s m, %s s where m.%s=s.%s and m.%s= ",COL_SCREENING_ID,COL_MOVIE_ID,
					COL_SCREENING_DATE,COL_SCREEN_ID,COL_SCREENING_TIME,TBL_MOVIES,TBL_SCREENINGS,COL_MOVIE_ID,COL_MOVIE_ID,
					COL_MOVIE_ID
					)+"?";
//			String.format("select s.%s,s.%s,to_char(s.%s,"+"\'mm/dd\'"+"),s.%s,"
//					+ "s.%s from %s m, %s s where m.%s=s.%s and m.%s= ",COL_SCREENING_ID,COL_MOVIE_ID,
//					COL_SCREENING_DATE,COL_SCREEN_ID,COL_SCREENING_TIME,TBL_MOVIES,TBL_SCREENINGS,COL_MOVIE_ID,COL_MOVIE_ID,
//					COL_MOVIE_ID
//					)+"?";
	
	/**
	 * movieId를 이용해 Movies테이블과 Screenings테이블을 조인해 Screening 행들을 얻어 List<Screening> 반환.
	 * @param movieId
	 * @return List<Screening>
	 */
	public List<Screening> getScreeningByMovieId(String movieId) {
		List<Screening> screenings=new ArrayList<Screening>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_JOIN_MOVIES_WITH_SCREENING_BY_ID);
			stmt.setString(1, movieId);
			rs=stmt.executeQuery();
			while(rs.next()) {
				Screening screening = getScreeningFromResultSet(rs);
				screenings.add(screening);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		return screenings;
	}
	
	
	private static final String SQL_READ_SCREENING_BY_ID="SELECT * FROM " + TBL_SCREENINGS + " WHERE " + COL_SCREENING_ID + " = ?";
	/**
	 * screeningId를 전달받아 이를 가진 Screening 객체 반환
	 * @param screeningId
	 * @return Screening
	 */
	public Screening read(String screeningId) {
		Screening screening=null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_READ_SCREENING_BY_ID);
			stmt.setString(1, screeningId);
			rs=stmt.executeQuery();
			while(rs.next()) {
				screening = getScreeningFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		return screening;
	}
	
	private String SQL_SELECT_SCREENING_BY_DATE_AND_TIME=String.format("select * from %s where %s = ? and %s = ?", 
			TBL_SCREENINGS,COL_SCREENING_DATE,COL_SCREENING_TIME);;
	/**
	 * MovieId, String date, String time을 전달받아 이를 이용해 Screenings테이블에서 Screening객체를 찾아 반환해준다.
	 * @param MovieId
	 * @param date
	 * @param time
	 * @return Screening
	 */
	public Screening read(String MovieId,String date,String time) {
		Screening screening=null;
		List<Screening> screenings=getScreeningByMovieId(MovieId);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_SELECT_SCREENING_BY_DATE_AND_TIME);
			stmt.setString(1, date);
			stmt.setString(2, time);
			rs=stmt.executeQuery();
			while(rs.next()) {
				screening = getScreeningFromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		return screening;
	}

	public Screening getScreeningFromResultSet(ResultSet rs) throws SQLException {
		String screeningId=rs.getString(COL_SCREENING_ID);
		String movieID=rs.getString(COL_MOVIE_ID);
		String screenId=rs.getString(COL_SCREEN_ID);
		String time=rs.getString(COL_SCREENING_TIME);
		String date=rs.getString(COL_SCREENING_DATE);
		return Screening.builder().movieId(movieID).screenId(screenId).screeningDate(date).screeningId(screeningId)
				.screeningTime(time).build();
	}
	
	
	
}
