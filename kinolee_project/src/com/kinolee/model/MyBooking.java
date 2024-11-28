package com.kinolee.model;

//예매내역을 확인하는 테이블의 행을 위해서만 사용할 클래스.
public class MyBooking {
	private String bookingId;
	private String screeningId;
	private String movieTitle;
	private String screeningDate;
	private String screeningTime;
	private String screenId;
	private String screenName;
	private String seatId;
	private String bookingTime;
	
	public String getScreenName() {
		return screenName;
	}
	
	public String getBookingId() {
		return bookingId;
	}
	public String getScreeningId() {
		return screeningId;
	}
	public String getMovieTitle() {
		return movieTitle;
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
	public String getSeatId() {
		return seatId;
	}
	
	
	public MyBooking(String bookingId, String screeningId, String movieTitle, String screeningDate,
			String screeningTime, String screenId, String seatId, String bookingTime,String screenName) {
		super();
		this.bookingId = bookingId;
		this.screeningId = screeningId;
		this.movieTitle = movieTitle;
		this.screeningDate = screeningDate;
		this.screeningTime = screeningTime;
		this.screenId = screenId;
		this.seatId = seatId;
		this.bookingTime = bookingTime;
		this.screenName=screenName;
	}
	public String getBookingTime() {
		return bookingTime;
	}
	public static MyBookingBuilder builder(){
		return new MyBookingBuilder();
	}
	
	public static class MyBookingBuilder {
		private String bookingId;
		private String screeningId;
		private String movieTitle;
		private String screeningDate;
		private String screeningTime;
		private String screenId;
		private String seatId;
		private String bookingTime;
		private String screenName;
		
		public MyBookingBuilder screenName(String screenName) {
			this.screenName=screenName;
			return this;
		}
		
		public MyBookingBuilder bookingId(String bookingId){
			this.bookingId=bookingId;
			return this;
		}
		public MyBookingBuilder screeningId(String screeningId) {
			this.screeningId=screeningId;
			return this;
		}
		public MyBookingBuilder movieTitle(String movieTitle) {
			this.movieTitle=movieTitle;
			return this;
		}
		public MyBookingBuilder screeningDate(String screeningDate) {
			this.screeningDate=screeningDate;
			return this;
		}
		public MyBookingBuilder screeningTime(String screeningTime) {
			this.screeningTime=screeningTime;
			return this;
		}
		public MyBookingBuilder screenId(String screenId) {
			this.screenId=screenId;
			return this;
		}
		public MyBookingBuilder seatId(String seatId) {
			this.seatId=seatId;
			return this;
		}
		public MyBookingBuilder bookingTime(String bookingTime) {
			this.bookingTime=bookingTime;
			return this;
		}
		
		public MyBooking build() {
			return new MyBooking(bookingId, screeningId, movieTitle, screeningDate, screeningTime, screenId, seatId,bookingTime,screenName);
		}
		
	}
	
}
