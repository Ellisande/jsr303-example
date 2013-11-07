package example.jsr.signup;

import example.jsr.account.Account;

public class EnhancedSignupForm {

	private String email;

	private String password;
	
	private Integer favoriteNumber;
	
	private String userName;
	
	private Boolean isPublicProfile;

	public Integer getFavoriteNumber() {
		return favoriteNumber;
	}

	public void setFavoriteNumber(Integer favoriteNumber) {
		this.favoriteNumber = favoriteNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsPublicProfile() {
		return isPublicProfile;
	}

	public void setIsPublicProfile(Boolean isPublicProfile) {
		this.isPublicProfile = isPublicProfile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Account createAccount() {
		return new Account(getEmail(), getPassword(), "ROLE_USER");
	}
}
