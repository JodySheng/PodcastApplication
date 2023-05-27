package podcast.dal;

import podcast.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PodcastReviewCommentsDao {
	protected ConnectionManager connectionManager;

	private static PodcastReviewCommentsDao instance = null;
	protected PodcastReviewCommentsDao() {
		connectionManager = new ConnectionManager();
	}
	public static PodcastReviewCommentsDao getInstance() {
		if(instance == null) {
			instance = new PodcastReviewCommentsDao();
		}
		return instance;
	}

	public PodcastReviewComments create(PodcastReviewComments podcastReviewComments) throws SQLException {
		String insertPodcastReviewComments =
			"INSERT INTO PodcastReviewComments(UserId,PodcastReviewId,CommentContent) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPodcastReviewComments,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, podcastReviewComments.getUsers().getUserId());
			insertStmt.setInt(2, podcastReviewComments.getPodcastReviews().getPodcastReviewId());
			insertStmt.setString(3, podcastReviewComments.getCommentContent());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int podcastCommentId = -1;
			if(resultKey.next()) {
				podcastCommentId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			podcastReviewComments.setPodcastCommentId(podcastCommentId);
			return podcastReviewComments;
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
	 * Update the comments content of the PodcastReviewComments instance.
	 * This runs a UPDATE statement.
	 */
	public PodcastReviewComments updateContent(PodcastReviewComments podcastReviewComments, String newContent) throws SQLException {
		String updatePodcastReviewComments = "UPDATE PodcastReviewComments SET CommentContent=? WHERE PodcastCommentId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePodcastReviewComments);
			updateStmt.setString(1, newContent);
			updateStmt.setInt(2, podcastReviewComments.getPodcastCommentId());
			updateStmt.executeUpdate();

			// Update the blogComment param before returning to the caller.
			podcastReviewComments.setCommentContent(newContent);
			return podcastReviewComments;
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
	 * Delete the PodcastReviewComments instance.
	 * This runs a DELETE statement.
	 */
	public PodcastReviewComments delete(PodcastReviewComments podcastReviewComments) throws SQLException {
		String deletePodcastReviewComments = "DELETE FROM PodcastReviewComments WHERE PodcastCommentId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePodcastReviewComments);
			deleteStmt.setInt(1, podcastReviewComments.getPodcastCommentId());
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

	/**
	 * Get the PodcastReviewComments record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single PodcastReviewComments instance.
	 * Note that we use PodcastDao and UsersDao to retrieve the referenced
	 */
	public PodcastReviewComments getPodcastReviewCommentsById(int podcastCommentId) throws SQLException {
		String selectPodcastReviewComments =
			"SELECT PodcastCommentId,UserId,PodcastReviewId,CommentContent " +
			"FROM PodcastReviewComments " +
			"WHERE PodcastCommentId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPodcastReviewComments);
			selectStmt.setInt(1, podcastCommentId);
			PodcastReviewsDao podcastReviewsDao = PodcastReviewsDao.getInstance();
			UsersDao usersDao = UsersDao.getInstance();
			results = selectStmt.executeQuery();
			if(results.next()) {
				int resultPodcastCommentId = results.getInt("PodcastCommentId");
				String userId = results.getString("UserId");
				int podcastReviewId = results.getInt("PodcastReviewId");
				String commentContent = results.getString("CommentContent");
				
				Users users = usersDao.getUserFromUserId(userId);
				PodcastReviews podcastReviews = podcastReviewsDao.getPodcastReviewsById(podcastReviewId);
				PodcastReviewComments podcastReviewComments = new PodcastReviewComments(resultPodcastCommentId,
						users, podcastReviews, commentContent);
				return podcastReviewComments;
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
	 * Get the all the BlogComments for a user.
	 */
	public List<PodcastReviewComments> getPodcastReviewCommentsForUser(Users users) throws SQLException {
		List<PodcastReviewComments> podcastReviewComments = new ArrayList<PodcastReviewComments>();
		String selectPodcastReviewComments =
			"SELECT PodcastCommentId,UserId,PodcastReviewId,CommentContent " +
			"FROM PodcastReviewComments " +
			"WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPodcastReviewComments);
			selectStmt.setString(1, users.getUserId());
			results = selectStmt.executeQuery();
			PodcastReviewsDao podcastReviewsDao = PodcastReviewsDao.getInstance();
			while(results.next()) {
				int podcastCommentId = results.getInt("PodcastCommentId");
				int podcastReviewId = results.getInt("PodcastReviewId");
				String CommentContent = results.getString("CommentContent");

				PodcastReviews podcastReviews = podcastReviewsDao.getPodcastReviewsById(podcastReviewId);
				PodcastReviewComments podcastReviewComment = new PodcastReviewComments(podcastCommentId, users, podcastReviews,
						CommentContent);
				podcastReviewComments.add(podcastReviewComment);
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
		return podcastReviewComments;
	}
	
	/**
	 * Get the all the PodcastReviewComments for a PodcastReview.
	 */
	public List<PodcastReviewComments> getPodcastReviewCommentsForPost(PodcastReviews podcastReviews) throws SQLException {
		List<PodcastReviewComments> podcastReviewComments = new ArrayList<PodcastReviewComments>();
		String selectPodcastReviewComments =
			"SELECT PodcastCommentId,UserId,PodcastReviewId,CommentContent " +
			"FROM PodcastReviewComments " +
			"WHERE PodcastReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPodcastReviewComments);
			selectStmt.setInt(1, podcastReviews.getPodcastReviewId());
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int podcastCommentId = results.getInt("PodcastCommentId");
				String userId = results.getString("UserId");
				String CommentContent = results.getString("CommentContent");

				Users users = usersDao.getUserFromUserId(userId);
				PodcastReviewComments podcastReviewComment = new PodcastReviewComments(podcastCommentId, users, podcastReviews,
						CommentContent);
				podcastReviewComments.add(podcastReviewComment);
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
		return podcastReviewComments;
	}
}
