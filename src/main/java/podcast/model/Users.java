package podcast.model;

import java.util.Date;

public class Users extends Persons {
	protected Date createdDate;
	
	public Users(String userId, String userName, String passwords, Date createdDate) {
		super(userId, userName, passwords);
		this.createdDate = createdDate;
	}
	
	public Users(String userId) {
		super(userId);
	}
	
	/** Getters and setters. */
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
