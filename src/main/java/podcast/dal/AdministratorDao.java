package podcast.dal;

import podcast.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import podcast.model.Administrator;
import podcast.dal.AdministratorDao;

/**
 * Data access object (DAO) class to interact with the underlying Administrator table in your
 * MySQL instance. This is used to store {@link Administrator} into your MySQL instance and 
 * retrieve {@link Administrator} from MySQL instance.
 * 
 * Reuse Professor Chhay's code.
 */
public class AdministratorDao extends PersonsDao {
	// Single pattern: instantiation is limited to one object.
	private static AdministratorDao instance = null;
	protected AdministratorDao() {
		super();
	}
	public static AdministratorDao getInstance() {
		if(instance == null) {
			instance = new AdministratorDao();
		}
		return instance;
	}
	
	public Administrator create(Administrator administrator) throws SQLException {
		// Insert into the superclass table first.
		create(new Persons(administrator.getUserId(), administrator.getUserName(),
				administrator.getPasswords()));
		String insertAdministrator = "INSERT INTO Administrator(UserId, LastLogin) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAdministrator);
			insertStmt.setString(1, administrator.getUserId());
			insertStmt.setTimestamp(2, new Timestamp(administrator.getLastLogin().getTime()));
			insertStmt.executeUpdate();
			return administrator;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}

	/**
	 * Update the userName of the Users instance.
	 * This runs a UPDATE statement.
	 */
	public Administrator updateUserName(Administrator administrator, String newUserName) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateUserName(administrator, newUserName);
		return administrator;
	}

	/**
	 * Delete the Administrators instance.
	 * This runs a DELETE statement.
	 */
	public Administrator delete(Administrator administrator) throws SQLException {
		String deleteAdministrator = "DELETE FROM Administrator WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAdministrator);
			deleteStmt.setString(1, administrator.getUserId());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for UserId=" + administrator.getUserId());
			}

			// Then also delete from the superclass.
			
			// Notes: (THIS IS THE OLD NOTE FOR BLOG DEMO FROM PROFESSOR, NOT PODCAST. by Chiu)
			// 1. Due to the fk constraint (ON DELETE CASCADE), we could simply call
			//    super.delete() without even needing to delete from Administrators first.
			// 2. BlogPosts has a fk constraint on BlogUsers with the reference option
			//    ON DELETE SET NULL. If the BlogPosts fk reference option was instead
			//    ON DELETE RESTRICT, then the caller would need to delete the referencing
			//    BlogPosts before this BlogUser can be deleted.
			//    Example to delete the referencing BlogPosts:
			//    List<BlogPosts> posts = BlogPostsDao.getBlogPostsForUser(blogUser.getUserName());
			//    for(BlogPosts p : posts) BlogPostsDao.delete(p);
			super.delete(administrator);

			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

	public Administrator getAdministratorFromUserId(String userId) throws SQLException {
		// To build an Users object, we need the Persons record, too.
		String selectAdministrator =
			"SELECT Administrator.UserId AS UserId, UserName, PassWords, LastLogin " +
			"FROM Administrator INNER JOIN Persons " +
			"  ON Administrator.UserId = Persons.UserId " +
			"WHERE Administrator.UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAdministrator);
			selectStmt.setString(1, userId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultUserId = results.getString("UserId");
				String userName = results.getString("UserName");
				String passwords = results.getString("PassWords");
				Date lastLogin = new Date(results.getTimestamp("LastLogin").getTime());
				Administrator administrator = new Administrator(resultUserId, userName, passwords, lastLogin);
				return administrator;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}

	public List<Administrator> getAdministratorFromUserName(String userName)
			throws SQLException {
		List<Administrator> administrators = new ArrayList<Administrator>();
		String selectAdministrators =
			"SELECT Administrator.UserId AS UserId, UserName, PassWords, LastLogin " +
			"FROM Administrator INNER JOIN Persons " +
			"  ON Administrator.UserId = Persons.UserId " +
			"WHERE Persons.UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAdministrators);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String userId = results.getString("UserId");
				String resultUserName = results.getString("UserName");
				String passwords = results.getString("PassWords");
				Date lastLogin = new Date(results.getTimestamp("LastLogin").getTime());
				Administrator administrator = new Administrator(userId, resultUserName, passwords, lastLogin);
				administrators.add(administrator);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return administrators;
	}
}
