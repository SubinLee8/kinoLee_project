package com.kinolee.model;

public class Screening {
	public static final class Entity {
		public static final String TBL_SCREENINGS="SCREENINGS";
		public static final String COL_SCREENING_ID="SCREENING_ID";
		public static final String COL_MOVIE_ID="MOVIE_ID";
		public static final String COL_SCREENING_DATE="SCREENING_DATE";
		public static final String COL_SCREENING_TIME="SCREENING_TIME";
		public static final String COL_SCREEN_ID="SCREEN_ID";
	}
	
	private String screeningId;
	private String movieId;
	private String screeningDate;
	private String screeningTime;
	private String screenId;
	
	public Screening(String screeningId, String movieId, String screeningDate, String screeningTime, String screenId) {
		super();
		this.screeningId = screeningId;
		this.movieId = movieId;
		this.screeningDate = screeningDate;
		this.screeningTime = screeningTime;
		this.screenId = screenId;
	}
	
	//getter만
	public String getScreeningId() {
		return screeningId;
	}

	public String getMovieId() {
		return movieId;
	}

	public String getScreeningDate() {
		return screeningDate;
	}

	public String getScreeningTime() {
		return screeningTime;
	}

	public String getScreenId() {
		return screenId;
	}
	
	public static ScreeningBuilder builder() {
		return new ScreeningBuilder();
	}
	
	
	public static class ScreeningBuilder{
		private String screeningId;
		private String movieId;
		private String screeningDate;
		private String screeningTime;
		private String screenId;
		
		public ScreeningBuilder screeningId(String screeningId) {
			this.screeningId=screeningId;
			return this;
		}
		public ScreeningBuilder movieId(String movieId) {
			this.movieId=movieId;
			return this;
		}
		public ScreeningBuilder screeningDate(String screeningDate) {
			this.screeningDate=screeningDate;
			return this;
		}
		public ScreeningBuilder screeningTime(String screeningTime) {
			this.screeningTime=screeningTime;
			return this;
		}
		public ScreeningBuilder screenId(String screenId) {
			this.screenId=screenId;
			return this;
		}
		
		public Screening build() {
			return new Screening(screeningId,movieId,screeningDate,screeningTime,screenId);
		}
	}
	
	
	
	
	
}
