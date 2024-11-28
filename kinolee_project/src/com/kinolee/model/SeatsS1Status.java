package com.kinolee.model;

public class SeatsS1Status {
	public static final class Entity {
		public static final String TBL_SEATS_S1_STATUS="SEATS_S1_STATUS";
		public static final String COL_SCREEN_ID="SCREEN_ID";
		public static final String COL_SCREENING_ID="SCREENING_ID";
		public static final String COL_SEAT_ID="SEAT_ID";
		public static final String COL_IS_RESERVED="IS_RESERVED";
	}
	
	private String screenId;
	private String screeningId;
	private String seatId;
	private String isReserved;
	
	public SeatsS1Status(String screenId, String screeningId, String seatId, String isReserved) {
		super();
		this.screenId = screenId;
		this.screeningId = screeningId;
		this.seatId = seatId;
		this.isReserved = isReserved;
	}
	
	//예약상태만 바꿀 수 있게하는 setter 
	public String getIsReserved() {
		return isReserved;
	}

	public void setIsReserved(String isReserved) {
		this.isReserved = isReserved;
	}

	public String getScreenId() {
		return screenId;
	}

	public String getScreeningId() {
		return screeningId;
	}

	public String getSeatId() {
		return seatId;
	}
	
	public static SeatsS1StatusBuilder builder() {
		return new SeatsS1StatusBuilder();
	}
	
	public static class SeatsS1StatusBuilder {
		private String screenId;
		private String screeningId;
		private String seatId;
		private String isReserved;
		
		public SeatsS1StatusBuilder screenId(String screenId) {
			this.screenId=screenId;
			return this;
		}
		public SeatsS1StatusBuilder screeningId(String screeningId) {
			this.screeningId=screeningId;
			return this;
		}
		public SeatsS1StatusBuilder seatId(String seatId) {
			this.seatId=seatId;
			return this;
		}
		public SeatsS1StatusBuilder isReserved(String isReserved) {
			this.isReserved=isReserved;
			return this;
		}
		public SeatsS1Status build() {
			return new SeatsS1Status(screenId,screeningId,seatId, isReserved);
		}
	}
	
	
	
}
