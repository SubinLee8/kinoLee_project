package com.kinolee.model;



public class Movie {
	public static final class Entity {
		public static final String TBL_MOVIES="MOVIES";
		public static final String COL_MOVIE_ID="MOVIE_ID";
		public static final String COL_MOVIE_TITLE="MOVIE_TITLE";
		public static final String COL_MOVIE_DIRECTOR="MOVIE_DIRECTOR";
		public static final String COL_MOVIE_RELEASE="MOVIE_RELEASE";
		public static final String COL_MOVIE_POSTER="MOVIE_POSTER";
	}
	
	private String movieId;
	private String movieTitle;
	private String movieDirector;
	private String movieRelease;
	private String moviePoster;
	
	//getter만
	public String getMovieId() {
		return movieId;
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public String getMovieDirector() {
		return movieDirector;
	}
	public String getMovieRelease() {
		return movieRelease;
	}
	public String getMoviePoster() {
		return moviePoster;
	}
	
	public Movie(String movieId, String movieTitle, String movieDirector, String movieRelease, String moviePoster) {
		super();
		this.movieId = movieId;
		this.movieTitle = movieTitle;
		this.movieDirector = movieDirector;
		this.movieRelease = movieRelease;
		this.moviePoster = moviePoster;
	}
	
	public static MovieBuilder builder() {
		return new MovieBuilder();
	}
	
	public static class MovieBuilder {
		private String movieId;
		private String movieTitle;
		private String movieDirector;
		private String movieRelease;
		private String moviePoster;
		
		public MovieBuilder movieId(String movieId) {
			this.movieId=movieId;
			return this;
		}
		public MovieBuilder movieTitle(String movieTitle) {
			this.movieTitle=movieTitle;
			return this;
		}
		public MovieBuilder movieDirector(String movieDirector) {
			this.movieDirector=movieDirector;
			return this;
		}
		public MovieBuilder movieRelease(String movieRelease) {
			this.movieRelease=movieRelease;
			return this;
		}
		public MovieBuilder moviePoster(String moviePoster) {
			this.moviePoster=moviePoster;
			return this;
		}
		
		public Movie build() {
			return new Movie(movieId,movieTitle,movieDirector,movieRelease,moviePoster);
		}
		
		
	}
	
	
}
