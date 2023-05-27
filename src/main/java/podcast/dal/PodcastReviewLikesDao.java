package podcast.dal;

import podcast.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PodcastReviewLikesDao {
	protected ConnectionManager connectionManager;
	
	private static PodcastReviewLikesDao instance = null;
	protected PodcastReviewLikesDao() {
		connectionManager = new ConnectionManager();
	}
	public static PodcastReviewLikesDao getInstance() {
		if(instance == null) {
			instance = new PodcastReviewLikesDao();
		}
		return instance;
	}

	public PodcastReviewLikes create(PodcastReviewLikes podcastReviewLikes) throws SQLException {
		String insertPodcastReviewLikes =
			"INSERT INTO PodcastReviewLikes(UserId,PodcastReviewId,Likes) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPodcastReviewLikes,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, podcastReviewLikes.getUsers().getUserId());
			insertStmt.setInt(2, podcastReviewLikes.getPodcastReviews().getPodcastReviewId());
			insertStmt.setBoolean(3, podcastReviewLikes.isLikes());
			insertStmt.executeUpdate();
			
			// WE WILL NOT USE THE FOLLOWING CODES SINCE WE DON'T HAVE A AUTO~GENERATED KEY IN THIS CLASS.
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
//			resultKey = insertStmt.getGeneratedKeys();
//			int podcastCommentId = -1;
//			if(resultKey.next()) {
//				podcastCommentId = resultKey.getInt(1);
//			} else {
//				throw new SQLException("Unable to retrieve auto-generated key.");
//			}
//			podcastReviewComments.setPodcastCommentId(podcastCommentId);
			return podcastReviewLikes;
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
	 * Update the like of the PodcastReviewLikes instance.
	 * This runs a UPDATE statement.
	 */
	public PodcastReviewLikes updateLike(PodcastReviewLikes podcastReviewLikes, boolean newLikes) throws SQLException {
		String updatePodcastReviewLikes = "UPDATE PodcastReviewLikes SET Likes=? WHERE UserId=? AND PodcastReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePodcastReviewLikes);
			updateStmt.setBoolean(1, newLikes);
			updateStmt.setString(2, podcastReviewLikes.getUsers().getUserId());
			updateStmt.setInt(3, podcastReviewLikes.getPodcastReviews().getPodcastReviewId());
			updateStmt.executeUpdate();

			// Update the blogComment param before returning to the caller.
			podcastReviewLikes.setLikes(newLikes);
			return podcastReviewLikes;
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
	 * Delete the PodcastReviewLikes instance.
	 * This runs a DELETE statement.
	 */
	public PodcastReviewLikes delete(PodcastReviewLikes podcastReviewLikes) throws SQLException {
		String deletePodcastReviewLikes = "DELETE FROM PodcastReviewLikes WHERE UserId=? AND PodcastReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePodcastReviewLikes);
			deleteStmt.setString(1, podcastReviewLikes.getUsers().getUserId());
			deleteStmt.setInt(2, podcastReviewLikes.getPodcastReviews().getPodcastReviewId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogComments instance.
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
	
	public String getCategoryMayLikedByUser(Users users) throws SQLException {
		String mostFreqLikedCategory = "";
		// Get a list of all podcastReview liked by this user.
		List<PodcastReviewLikes> podcastReviewLikes = this.getPodcastReviewLikesForUser(users);
		// Use a map to record the category and the frequency in the podcastReview liked by this user.
		Map<String, Integer> categoryFreqMap = new HashMap<>();
		for (PodcastReviewLikes reviewLike : podcastReviewLikes) {
			String category = reviewLike.getPodcastReviews().getPodcast().getCategories();
			categoryFreqMap.put(category, categoryFreqMap.getOrDefault(category, 0) + 1);
		}
		
		// Loop overt the map entry to find the most frequently appeared value (= category)
		int maxFreq = Integer.MIN_VALUE;		
		for (Entry<String, Integer> entry : categoryFreqMap.entrySet()) {
			if (entry.getValue() > maxFreq) {
				mostFreqLikedCategory = entry.getKey();
				maxFreq = entry.getValue();
			}
		}
		// Return the category that appears most frequently in the podcastReviews liked by the user.
		
		return mostFreqLikedCategory;
	}

	/**
	 * Get the all the BlogComments for a user.
	 */
	public List<PodcastReviewLikes> getPodcastReviewLikesForUser(Users users) throws SQLException {
		List<PodcastReviewLikes> podcastReviewLikes = new ArrayList<PodcastReviewLikes>();
		String selectPodcastReviewLikes =
				"SELECT UserId,PodcastReviewId,Likes " +
				"FROM PodcastReviewLikes " +
				"WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPodcastReviewLikes);
			selectStmt.setString(1, users.getUserId());
			results = selectStmt.executeQuery();
			PodcastReviewsDao podcastReviewsDao = PodcastReviewsDao.getInstance();
			while(results.next()) {
				int podcastReviewId = results.getInt("PodcastReviewId");
				boolean likes = results.getBoolean("Likes");

				PodcastReviews podcastReviews = podcastReviewsDao.getPodcastReviewsById(podcastReviewId);
				PodcastReviewLikes podcastReviewLike = new PodcastReviewLikes(users, podcastReviews,
						likes);
				podcastReviewLikes.add(podcastReviewLike);
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
		return podcastReviewLikes;
	}
	
	/**
	 * Get the all the PodcastReviewLikes for a PodcastReview.
	 */
	public List<PodcastReviewLikes> getPodcastReviewLikesForReview(PodcastReviews podcastReviews) throws SQLException {
		List<PodcastReviewLikes> podcastReviewLikes = new ArrayList<PodcastReviewLikes>();
		String selectPodcastReviewLikes =
			"SELECT UserId,PodcastReviewId,Likes " +
			"FROM PodcastReviewLikes " +
			"WHERE PodcastReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPodcastReviewLikes);
			selectStmt.setInt(1, podcastReviews.getPodcastReviewId());
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				String userId = results.getString("UserId");
				boolean likes = results.getBoolean("Likes");
				
				Users users = usersDao.getUserFromUserId(userId);
				PodcastReviewLikes podcastReviewLike = new PodcastReviewLikes(users, podcastReviews, likes);
				podcastReviewLikes.add(podcastReviewLike);
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
		return podcastReviewLikes;
	}
}
