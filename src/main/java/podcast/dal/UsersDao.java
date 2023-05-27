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

import podcast.dal.UsersDao;
import podcast.model.Users;
import podcast.model.Persons;

/**
 * Data access object (DAO) class to interact with the underlying Users table in your
 * MySQL instance. This is used to store {@link Users} into your MySQL instance and 
 * retrieve {@link Users} from MySQL instance.
 * 
 * Reuse Professor Chhay's code.
 */
public class UsersDao extends PersonsDao {
	// Single pattern: instantiation is limited to one object.
	private static UsersDao instance = null;
	protected UsersDao() {
		super();
	}
	public static UsersDao getInstance() {
		if(instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}

	public Users create(Users users) throws SQLException {
		// Insert into the superclass table first.
		create(new Persons(users.getUserId(), users.getUserName(),
				users.getPasswords()));

		String insertUsers = "INSERT INTO Users(UserId, CreatedDate) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUsers);
			insertStmt.setString(1, users.getUserId());
			insertStmt.setTimestamp(2, new Timestamp(users.getCreatedDate().getTime()));
			insertStmt.executeUpdate();
			return users;
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
	public Users updateUserName(Users User, String newUserName) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateUserName(User, newUserName);
		return User;
	}



	/**
	 * Delete the Users instance.
	 * This runs a DELETE statement.
	 */
	public Users delete(Users User) throws SQLException {
		String deleteUser = "DELETE FROM Users WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, User.getUserId());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for UserId=" + User.getUserId());
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
			super.delete(User);

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

	public Users getUserFromUserId(String userId) throws SQLException {
		// To build an Users object, we need the Persons record, too.
		String selectUsers =
			"SELECT Users.UserId AS UserId, UserName, PassWords, CreatedDate " +
			"FROM Users INNER JOIN Persons " +
			"  ON Users.UserId = Persons.UserId " +
			"WHERE Users.UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUsers);
			selectStmt.setString(1, userId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultUserId = results.getString("UserId");
				String userName = results.getString("UserName");
				String passwords = results.getString("PassWords");
				Date createdDate = new Date(results.getTimestamp("CreatedDate").getTime());
				Users User = new Users(resultUserId, userName, passwords, createdDate);
				return User;
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

	public List<Users> getUsersFromUserName(String userName)
			throws SQLException {
		List<Users> Users = new ArrayList<Users>();
		String selectUsers =
			"SELECT Users.UserId AS UserId, UserName, PassWords, CreatedDate " +
			"FROM Users INNER JOIN Persons " +
			"  ON Users.UserId = Persons.UserId " +
			"WHERE Persons.UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUsers);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String userId = results.getString("UserId");
				String resultUserName = results.getString("UserName");
				String passwords = results.getString("PassWords");
				Date createdDate = new Date(results.getTimestamp("CreatedDate").getTime());
				Users User = new Users(userId, resultUserName, passwords, createdDate);
				Users.add(User);
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
		return Users;
	}
}
