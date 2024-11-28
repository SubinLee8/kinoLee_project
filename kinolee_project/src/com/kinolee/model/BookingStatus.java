package com.kinolee.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;


public class BookingStatus {
	public static final class Entity {
		public static final String TBL_BOOKING_STATUS="BOOKING_STATUS";
		public static final String COL_BOOKING_ID="BOOKING_ID";
		public static final String COL_USER_ID="USER_ID";
		public static final String COL_SCREENING_ID="SCREENING_ID";
		public static final String COL_SCREEN_ID="SCREEN_ID";
		public static final String COL_SEAT_ID="SEAT_ID";
		public static final String COL_BOOKING_TIME="BOOKING_TIME";
	}
	
	
	private Integer bookingId;
	private String userId;
	private String screeningId;
	private String screenId;
	private String seatId;
	private LocalDateTime bookingTime;
	
	public BookingStatus(Integer bookingId, String userId, String screeningId, String screenId, String seatId,
			LocalDateTime bookingTime) {
		super();
		this.bookingId = bookingId;
		this.userId = userId;
		this.screeningId = screeningId;
		this.screenId = screenId;
		this.seatId = seatId;
		this.bookingTime=bookingTime;
	}

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getScreeningId() {
		return screeningId;
	}

	public void setScreeningId(String screeningId) {
		this.screeningId = screeningId;
	}

	public String getScreenId() {
		return screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}
	
	//bookingId만 setter가 없다.
	public Integer getBookingId() {
		return bookingId;
	}
	
	
	public static BookingStatusBuilder builder() {
		return new BookingStatusBuilder();
	}
	
	public static class BookingStatusBuilder {
		private Integer bookingId;
		private String userId;
		private String screeningId;
		private String screenId;
		private String seatId;
		private LocalDateTime bookingTime;
		
		public BookingStatusBuilder bookingId(Integer bookingId) {
			this.bookingId=bookingId;
			return this;
		}
		
		public BookingStatusBuilder userId(String userId) {
			this.userId=userId;
			return this;
		}
		
		public BookingStatusBuilder screeningId(String screeningId) {
			this.screeningId=screeningId;
			return this;
		}
		
		public BookingStatusBuilder screenId(String screenId) {
			this.screenId=screenId;
			return this;
		}
		
		public BookingStatusBuilder seatId(String seatId) {
			this.seatId=seatId;
			return this;
		}
		
		public BookingStatusBuilder bookingTime(LocalDateTime bookingTime) {
			this.bookingTime=bookingTime;
			return this;
		}
		
		public BookingStatusBuilder bookingTime(Timestamp bookingTime) {
			if (bookingTime != null)
				this.bookingTime=bookingTime.toLocalDateTime();
			return this;
		}
		
		public BookingStatus build() {
			return new BookingStatus(bookingId, userId, screeningId,screenId, seatId, bookingTime);
		}
	}
	
	
}
