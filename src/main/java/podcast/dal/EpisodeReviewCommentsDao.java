package podcast.dal;
import podcast.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class EpisodeReviewCommentsDao {

	protected ConnectionManager connectionManager;

	private static EpisodeReviewCommentsDao instance = null;
	protected EpisodeReviewCommentsDao() {
		connectionManager = new ConnectionManager();
	}
	public static EpisodeReviewCommentsDao getInstance() {
		if(instance == null) {
			instance = new EpisodeReviewCommentsDao();
		}
		return instance;
	}


public EpisodeReviewComments create(EpisodeReviewComments episodeReviewComment) throws SQLException {
	String insertEpisodeReviewComments =
		"INSERT INTO EpisodeReviewComments(CommentContent,UserId,EpisodeReviewId) " +
		"VALUES(?,?,?);";
	Connection connection = null;
	PreparedStatement insertStmt = null;
	ResultSet resultKey = null;
	try {
		connection = connectionManager.getConnection();
		insertStmt = connection.prepareStatement(insertEpisodeReviewComments,
			Statement.RETURN_GENERATED_KEYS);
		insertStmt.setString(1, episodeReviewComment.getCommentContent());
		insertStmt.setString(2, episodeReviewComment.getUser().getUserId());
		insertStmt.setInt(3, episodeReviewComment.getEpisodesReview().getEpisodeReviewId());
		insertStmt.executeUpdate();
		
		// Retrieve the auto-generated key and set it, so it can be used by the caller.
		resultKey = insertStmt.getGeneratedKeys();
		int episodeCommentId = -1;
		if(resultKey.next()) {
			episodeCommentId = resultKey.getInt(1);
		} else {
			throw new SQLException("Unable to retrieve auto-generated key.");
		}
		episodeReviewComment.setEpisodeCommentId(episodeCommentId);
		return episodeReviewComment;
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
 * Delete the EpisodeReviewComments instance.
 * This runs a DELETE statement.
 */
public EpisodeReviewComments delete(EpisodeReviewComments episodeReviewComment) throws SQLException {
	String deleteEpisodeReviewComments = "DELETE FROM EpisodeReviewComments WHERE EpisodeCommentId=?;";
	Connection connection = null;
	PreparedStatement deleteStmt = null;
	try {
		connection = connectionManager.getConnection();
		deleteStmt = connection.prepareStatement(deleteEpisodeReviewComments);
		deleteStmt.setInt(1, episodeReviewComment.getEpisodeCommentId());
		deleteStmt.executeUpdate();

		// Return null so the caller can no longer operate on the EpisodeReviewComments instance.
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
 * Get the EpisodeReviewComments record by fetching it from your MySQL instance.
 * This runs a SELECT statement and returns a single EpisodeReviewComments instance.
 * Note that we use PodcastDao and UsersDao to retrieve the referenced
 * BlogPosts and BlogUsers instances.
 * One alternative (possibly more efficient) is using a single SELECT statement
 * to join the EpisodeReviewComments, Podcast, Users tables and then build each object.
 */
public EpisodeReviewComments getEpisodeReviewCommentsById(int episodeReviewCommentId) throws SQLException {
	String selectEpisodeReviewComments =
		"SELECT EpisodeCommentId,CommentContent,UserId,EpisodeReviewId " +
		"FROM EpisodeReviewComments " +
		"WHERE EpisodeCommentId =?;";
	Connection connection = null;
	PreparedStatement selectStmt = null;
	ResultSet results = null;
	try {
		connection = connectionManager.getConnection();
		selectStmt = connection.prepareStatement(selectEpisodeReviewComments);
		selectStmt.setInt(1, episodeReviewCommentId);
		results = selectStmt.executeQuery();
		EpisodesReviewsDao episodesReviewsDao = EpisodesReviewsDao.getInstance();
		UsersDao usersDao = UsersDao.getInstance();
		if(results.next()) {
			int resultEpisodeCommentId = results.getInt("EpisodeCommentId");
			String commentContent = results.getString("CommentContent");
			String userId = results.getString("UserId");
			String episodeReviewId = results.getString("EpisodeReviewId");
			
			EpisodesReviews episodesReview =  episodesReviewsDao.getEpisodesReviewsById(episodeReviewId);
			Users user = usersDao.getUserFromUserId(userId);
			EpisodeReviewComments podcastReviewComment = new EpisodeReviewComments(resultEpisodeCommentId,commentContent, user, episodesReview);
			return podcastReviewComment;
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
 * Get the all the EpisodeReviewComments for a EpisodeReview.
 */
public List<EpisodeReviewComments> getEpisodeReviewCommentsForEpisodeReview(EpisodesReviews episodesReview) throws SQLException {
	List<EpisodeReviewComments> episodeReviewComments = new ArrayList<EpisodeReviewComments>();
	String selectEpisodeReviewComments =
			"SELECT EpisodeCommentId,CommentContent,UserId,EpisodeReviewId " +
			"FROM EpisodeReviewComments " +
			"WHERE EpisodeReviewId =?;";
	Connection connection = null;
	PreparedStatement selectStmt = null;
	ResultSet results = null;
	try {
		connection = connectionManager.getConnection();
		selectStmt = connection.prepareStatement(selectEpisodeReviewComments);
		selectStmt.setInt(1, episodesReview.getEpisodeReviewId());
		results = selectStmt.executeQuery();
		UsersDao usersDao = UsersDao.getInstance();
		while(results.next()) {
			int resultEpisodeCommentId = results.getInt("EpisodeCommentId");
			String commentContent = results.getString("CommentContent");
			String userId = results.getString("UserId");
			
			Users user = usersDao.getUserFromUserId(userId);
			EpisodeReviewComments podcastReviewComment = new EpisodeReviewComments(resultEpisodeCommentId,commentContent, user, episodesReview);
			episodeReviewComments.add(podcastReviewComment);
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
	return episodeReviewComments;
}


	/**
	 * Update the EpisodeReviewCommentsContent of the EpisodeReviewComments instance.
	 * This runs a UPDATE statement.
	 */
	public EpisodeReviewComments updateEpisodeReviewCommentsContent(EpisodeReviewComments episodeReviewComment,
			String newCommentsContent) throws SQLException {
        String updateEpisodeReviewComments = "UPDATE EpisodeReviewComments SET CommentContent=? WHERE EpisodeCommentId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
	    try {
		    connection = connectionManager.getConnection();
		    updateStmt = connection.prepareStatement(updateEpisodeReviewComments);
		    updateStmt.setString(1, newCommentsContent);
		    updateStmt.setInt(2, episodeReviewComment.getEpisodeCommentId());
		    updateStmt.executeUpdate();
		    episodeReviewComment.setCommentContent(newCommentsContent);
		    return episodeReviewComment;
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

}

