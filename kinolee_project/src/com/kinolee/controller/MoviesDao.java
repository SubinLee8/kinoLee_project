package com.kinolee.controller;


import static com.kinolee.oracle.OracleJdbc.PASSWORD;
import static com.kinolee.oracle.OracleJdbc.URL;
import static com.kinolee.oracle.OracleJdbc.USER;
import static com.kinolee.model.Movie.Entity.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kinolee.model.Movie;
import com.kinolee.model.Movie.MovieBuilder;
import com.kinolee.model.User;

import oracle.jdbc.OracleDriver;

public enum MoviesDao {
	INSTANCE;
	
	MoviesDao() {
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
	
	private static final String SQL_READ_CURRENT_MOVIES=String.format("select * from %s where %s <= sysdate", TBL_MOVIES,COL_MOVIE_RELEASE);
	/**
	 * Movies테이블에서 개봉일이 현재 시점에서 이전인 영화들을 List<Movie>로 반환.
	 * @return 현재상영중인 영화 리스트
	 */
	public List<Movie> readCurrentMovies() {
		List<Movie> currentMovies = new ArrayList<Movie>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_READ_CURRENT_MOVIES);
			rs=stmt.executeQuery();
			while(rs.next()) {
				Movie movie = getMovieFromResultSet(rs);
				currentMovies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		
		
		return currentMovies;
	}
    
	/**
	 * ResultSet으로부터 영화객체를 만들어 반환.
	 * @param rs
	 * @return 영화 객체
	 * @throws SQLException
	 */
	private Movie getMovieFromResultSet(ResultSet rs) throws SQLException {
		String movieId=rs.getString(COL_MOVIE_ID);
		String title=rs.getString(COL_MOVIE_TITLE);
		String director=rs.getString(COL_MOVIE_DIRECTOR);
		String release=rs.getString(COL_MOVIE_RELEASE);
		String posterPath=rs.getString(COL_MOVIE_POSTER);
		return Movie.builder().movieId(movieId).movieDirector(director).moviePoster(posterPath).movieTitle(title).movieRelease(release).build();
		}
	
	private static final String SQL_READ_FUTURE_MOVIES=String.format("select * from %s where %s > sysdate", TBL_MOVIES,COL_MOVIE_RELEASE);
	/**
	 * 아직 개봉되지않은 영화리스트를 리턴한다.
	 * @return List<Movie>
	 */
	public List<Movie> readFutureMovies() {
		List<Movie> futureMovies = new ArrayList<Movie>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			stmt=conn.prepareStatement(SQL_READ_FUTURE_MOVIES);
			rs=stmt.executeQuery();
			while(rs.next()) {
				Movie movie = getMovieFromResultSet(rs);
				futureMovies.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt, rs);
		}
		return futureMovies;
	}
	
	private static final String SQL_SEARCH_BY_TITLE=String.format("select * from %s where upper(%s) like upper(?) order by %s desc",TBL_MOVIES,COL_MOVIE_TITLE,COL_MOVIE_TITLE);
	private static final String SQL_SEARCH_BY_DIRECTOR=String.format("select * from %s where upper(%s) like upper(?) order by %s desc",TBL_MOVIES,COL_MOVIE_DIRECTOR,COL_MOVIE_DIRECTOR);
	
	public List<Movie> searchMovieByKeyword(int type,String keyword) {
		List<Movie> movies=new ArrayList<Movie>();
		Connection conn=null;
		PreparedStatement stmt = null;
		ResultSet rs=null;
		try {
			conn=DriverManager.getConnection(URL,USER,PASSWORD);
			
			String searchKeyword = "%"+keyword+"%";
			switch(type) {
			case 0:
				stmt=conn.prepareStatement(SQL_SEARCH_BY_TITLE);
				stmt.setString(1, searchKeyword);
				break;
			case 1:
				stmt=conn.prepareStatement(SQL_SEARCH_BY_DIRECTOR);
				stmt.setString(1, searchKeyword);
				break;
			}
			rs=stmt.executeQuery();
			
			while(rs.next()) {
				movies.add(getMovieFromResultSet(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeResources(conn, stmt,rs);
		}
		return movies;
	}
	
}
