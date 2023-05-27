package podcast.model;


/**
 * Persons is a simple, plain old java objects (POJO).
 * 
 * Persons/PersonsDao is the superclass for Administrator/AdministratorDao and
 * Users/UsersDao. Our implementation of Persons is a concrete class. This allows 
 * us to create records in the Persons MySQL table without having the associated records
 * in the Administrators or BlogUsers MySQL tables. Alternatively, Persons could be an
 * interface or an abstract class, which would force a Persons record to be created only
 * if an Administrators or BlogUsers record is created, too.
 * 
 * Reuse Professor Chhay's code.
 */
public class Persons {
	protected String userId;
	protected String userName;
	protected String passwords;
	
	public Persons(String userId, String userName, String passwords) {
		this.userId = userId;
		this.userName = userName;
		this.passwords = passwords;
	}
	
	public Persons(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswords() {
		return passwords;
	}

	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
}
