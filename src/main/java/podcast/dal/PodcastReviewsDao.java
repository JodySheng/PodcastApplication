package podcast.dal;

import podcast.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PodcastReviewsDao {
	protected ConnectionManager connectionManager;

	private static PodcastReviewsDao instance = null;
	protected PodcastReviewsDao() {
		connectionManager = new ConnectionManager();
	}
	public static PodcastReviewsDao getInstance() {
		if(instance == null) {
			instance = new PodcastReviewsDao();
		}
		return instance;
	}

	public PodcastReviews create(PodcastReviews podcastReviews) throws SQLException {
		String insertPodcastReviews =
			"INSERT INTO PodcastReviews(PodcastId,ReviewsContent,Rating,UserId) " +
			"VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPodcastReviews,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, podcastReviews.getPodcast().getPodcastId());
			insertStmt.setString(2, podcastReviews.getReviewsContent());
			insertStmt.setInt(3, podcastReviews.getRating());
			insertStmt.setString(4, podcastReviews.getUser().getUserId());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int podcastReviewId = -1;
			if(resultKey.next()) {
				podcastReviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			podcastReviews.setPodcastReviewId(podcastReviewId);
			return podcastReviews;
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
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}

	/**
	 * Delete the PodcastReviews instance.
	 * This runs a DELETE statement.
	 */
	public PodcastReviews delete(PodcastReviews podcastReviews) throws SQLException {
		String deletePodcastReviews = "DELETE FROM PodcastReviews WHERE PodcastReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePodcastReviews);
			deleteStmt.setInt(1, podcastReviews.getPodcastReviewId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the PodcastReviews instance.
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

	/**
	 * Get the PodcastReviews record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single PodcastReviews instance.
	 * Note that we use PodcastDao and UsersDao to retrieve the referenced
	 * BlogPosts and BlogUsers instances.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the PodcastReviews, Podcast, Users tables and then build each object.
	 */
	public PodcastReviews getPodcastReviewsById(int podcastReviewsId) throws SQLException {
		String selectPodcastReviews =
			"SELECT PodcastReviewId,PodcastId,UserId " +
			"FROM PodcastReviews " +
			"WHERE PodcastReviewId =?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPodcastReviews);
			selectStmt.setInt(1, podcastReviewsId);
			results = selectStmt.executeQuery();
			PodcastDao podcastDao = PodcastDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			if(results.next()) {
				int resultPodcastReviewId = results.getInt("PodcastReviewId");
				String podcastId = results.getString("PodcastId");
				String userId = results.getString("UserId");
				
				Podcast podcast = podcastDao.getPodcastById(podcastId);
				Users users = usersDao.getUserFromUserId(userId);
				PodcastReviews podcastReviews = new PodcastReviews(resultPodcastReviewId, podcast, users);
				return podcastReviews;
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


	
	/**
	  * Update the PodcastReviewsContent of the PodcastReviews instance.
	  * This runs a UPDATE statement.
	  */
    public PodcastReviews updatePodcastReviewsContent(PodcastReviews podcastReview, 
			String newPodcastReviewsContent) throws SQLException {
		String updatePodcastReviews = "UPDATE PodcastReviews SET ReviewsContent=? WHERE PodcastReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
		   connection = connectionManager.getConnection();
		   updateStmt = connection.prepareStatement(updatePodcastReviews);
		   updateStmt.setString(1, newPodcastReviewsContent);
		   updateStmt.setInt(2, podcastReview.getPodcastReviewId());
		   updateStmt.executeUpdate();
	
		   // Update the podcastReview param before returning to the caller.
		   podcastReview.setReviewsContent(newPodcastReviewsContent);
		   return podcastReview;
		} catch (SQLException e) {
		   e.printStackTrace();
		   throw e;
		} finally {
		    if(connection != null) {
			    connection.close();
			} 
		    if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}
    
	/**
	 * Get the all the PodcastReviews for a user.
	 */
	public List<PodcastReviews> getPodcastReviewsForUser(Users users) throws SQLException {
		List<PodcastReviews> podcastReviews = new ArrayList<PodcastReviews>();
		String selectPodcastReviews =
			"SELECT PodcastReviewId,PodcastId,UserId " +
			"FROM podcastReviews " + 
			"WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPodcastReviews);
			selectStmt.setString(1, users.getUserId());
			
			results = selectStmt.executeQuery();
			PodcastDao podcastDao = PodcastDao.getInstance();
			while(results.next()) {
				int podcastReviewId = results.getInt("PodcastReviewId");
				String podcastId = results.getString("PodcastId");
				Podcast podcast = podcastDao.getPodcastById(podcastId);
				PodcastReviews podcastReview = new PodcastReviews(podcastReviewId, podcast, users);
				podcastReviews.add(podcastReview);
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
		return podcastReviews;
	}
    
	/**
	 * Get the all the PodcastReviews for a PodcastId.
	 * Added on 11/13.
	 */
	public List<PodcastReviews> getPodcastReviewsForPodcastId(String podcastId) throws SQLException {
		List<PodcastReviews> podcastReviews = new ArrayList<PodcastReviews>();
		String selectPodcastReviews =
			"SELECT podcastReviewId,PodcastId,UserId " +
			"FROM podcastReviews " + 
			"WHERE PodcastId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPodcastReviews);
			selectStmt.setString(1, podcastId);
			
			results = selectStmt.executeQuery();
			PodcastDao podcastDao = PodcastDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int podcastReviewId = results.getInt("PodcastReviewId");
				String userId = results.getString("UserId");
				Podcast podcast = podcastDao.getPodcastById(podcastId);
				Users users = usersDao.getUserFromUserId(userId);
				PodcastReviews podcastReview = new PodcastReviews(podcastReviewId, podcast, users);
				podcastReviews.add(podcastReview);
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
		return podcastReviews;
	}
    
}
