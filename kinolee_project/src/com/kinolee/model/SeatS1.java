package com.kinolee.model;

public class SeatS1 {
	public static final class Entity {
		public static final String TBL_SEATS_S1="SEATS_S1";
		public static final String COL_SCREEN_ID="SCREEN_ID";
		public static final String COL_SEAT_ID="SEAT_ID";
	}
	
	private String screenId;
	private String seatId;
	
	public SeatS1(String screenId, String seatId) {
		super();
		this.screenId = screenId;
		this.seatId = seatId;
	}
	
	//getter만
	public String getScreenId() {
		return screenId;
	}

	public String getSeatId() {
		return seatId;
	}
	
}
