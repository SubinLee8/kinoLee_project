package com.kinolee.model;

public class User {
	public static final class Entity {
		public static final String TBL_USERS="USERS";
		public static final String COL_USER_ID="USER_ID";
		public static final String COL_USER_LAST_NAME="USER_LAST_NAME";
		public static final String COL_USER_FIRST_NAME="USER_FIRST_NAME";
		public static final String COL_PASSWORD="USER_PASSWORD";
		public static final String COL_EMAIL="USER_EMAIL";
	}
	
	private String userId;
	private String userLastName;
	private String userFirstName;
	private String userPassword;
	private String userEmail;
	
	public User(String userId, String userLastName, String userFirstName, String userPassword, String userEmail) {
		super();
		this.userId = userId;
		this.userLastName = userLastName;
		this.userFirstName = userFirstName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	//id만 setter 가 없다.
	public String getUserId() {
		return userId;
	}
	
	public static UserBuilder builder() {
		return new UserBuilder();
	}
	
	public static class UserBuilder {
		private String userId;
		private String userLastName;
		private String userFirstName;
		private String userPassword;
		private String userEmail;
		
		public UserBuilder userId(String userId) {
			this.userId=userId;
			return this;
		}
		public UserBuilder userLastName(String userLastName) {
			this.userLastName=userLastName;
			return this;
		}
		public UserBuilder userFirstName(String userFirstName) {
			this.userFirstName=userFirstName;
			return this;
		}
		public UserBuilder userPassword(String userPassword) {
			this.userPassword=userPassword;
			return this;
		}
		public UserBuilder userEmail(String userEmail) {
			this.userEmail=userEmail;
			return this;
		}
		
		public User build() {
			return new User(userId, userLastName, userFirstName, userPassword, userEmail) ;
		}
		
	}
	
	
	
}
