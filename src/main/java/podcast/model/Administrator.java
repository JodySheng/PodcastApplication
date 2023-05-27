package podcast.model;

import java.util.Date;

public class Administrator extends Persons{
	protected Date lastLogin;
	
	public Administrator(String userId, String userName, String passwords, Date lastLogin) {
		super(userId, userName, passwords);
		this.lastLogin = lastLogin;
	}
	
	public Administrator(String userId) {
		super(userId);
	}
	
	/** Getters and setters. */
	
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
}
