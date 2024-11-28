package com.kinolee.model;

public class Screen {
	public static final class Entity {
		public static final String TBL_SCREENS="SCREENS";
		public static final String COL_SCREEN_ID="SCREEN_ID";
		public static final String COL_SCREEN_NAME="SCREEN_NAME";
	}
	
	private String screenId;
	private String screenName;
	
	public Screen(String screenId, String screenName) {
		super();
		this.screenId = screenId;
		this.screenName = screenName;
	}
	
	//getter만
	public String getScreenId() {
		return screenId;
	}

	public String getScreenName() {
		return screenName;
	}
	
	
	
	
}
