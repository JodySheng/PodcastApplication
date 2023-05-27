package podcast.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import podcast.model.*;

public class EpisodesReviewsDao {

	protected ConnectionManager connectionManager;

	private static EpisodesReviewsDao instance = null;
	protected EpisodesReviewsDao() {
		connectionManager = new ConnectionManager();
	}
	public static EpisodesReviewsDao getInstance() {
		if(instance == null) {
			instance = new EpisodesReviewsDao();
		}
		return instance;
	}
	
	public EpisodesReviews create(EpisodesReviews episodesReview) throws SQLException {
		String insertEpisodesReviews =
			"INSERT INTO EpisodesReviews(UserId,EpisodeId,ReviewsContent) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertEpisodesReviews,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, episodesReview.getUser().getUserId());
			insertStmt.setString(2, episodesReview.getEpisode().getEpisodeId());
			insertStmt.setString(3, episodesReview.getReviewsContent());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int episodesReviewId = -1;
			if(resultKey.next()) {
				episodesReviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			episodesReview.setEpisodeReviewId(episodesReviewId);
			return episodesReview;
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
	 * Update the EpisodesReviewsContent of the EpisodesReviews instance.
	 * This runs a UPDATE statement.
	 */
	public EpisodesReviews updateEpisodesReviewsContent(EpisodesReviews episodesReview, String newEpisodesReviewsContent) throws SQLException {
		String updateEpisodesReviews = "UPDATE EpisodesReviews SET ReviewsContent=? WHERE EpisodeReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateEpisodesReviews);
			updateStmt.setString(1, newEpisodesReviewsContent);
			updateStmt.setInt(2, episodesReview.getEpisodeReviewId());
			updateStmt.executeUpdate();

			// Update the episodesReview param before returning to the caller.
			episodesReview.setReviewsContent(newEpisodesReviewsContent);
			return episodesReview;
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
	 * Delete the EpisodesReviews instance.
	 * This runs a DELETE statement.
	 */
	public EpisodesReviews delete(EpisodesReviews episodesReview) throws SQLException {
		String deleteEpisodesReviews = "DELETE FROM EpisodesReviews WHERE EpisodeReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteEpisodesReviews);
			deleteStmt.setInt(1, episodesReview.getEpisodeReviewId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the EpisodesReviews instance.
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
	 * Get the EpisodesReviews record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single EpisodesReviews instance.
	 * Note that we use UsersDao to retrieve the referenced Users instance.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the EpisodesReviews, Users tables and then build each object.
	 */
	public EpisodesReviews getEpisodesReviewsById(String episodeReviewId) throws SQLException {
		String selectEpisodesReviews =
			"SELECT EpisodeReviewId,UserId,EpisodeId,ReviewsContent " +
			"FROM EpisodesReviews " +
			"WHERE EpisodeReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectEpisodesReviews);
			selectStmt.setString(1, episodeReviewId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			EpisodesDao episodesDao = EpisodesDao.getInstance();
			if(results.next()) {
				int resultEpisodesReviewsId = results.getInt("EpisodeReviewId");
				String userId = results.getString("UserId");			
				String episodeId = results.getString("EpisodeId");
				String reviewsContent = results.getString("reviewsContent");
				
				Users user = usersDao.getUserFromUserId(userId);
				Episodes episode = episodesDao.getEpisodesById(episodeId);
				
				EpisodesReviews episodesReview = new EpisodesReviews(resultEpisodesReviewsId, user, episode, reviewsContent);
				return episodesReview;
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

	public List<EpisodesReviews> getEpisodesReviewsByEpisode(Episodes episode) throws SQLException {
		List<EpisodesReviews>  episodesReviews = new ArrayList<EpisodesReviews>();
		String selectEpisodesReviews =
			"SELECT EpisodeReviewId,UserId,EpisodeId,ReviewsContent " +
			"FROM EpisodesReviews " +
			"WHERE episodeId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectEpisodesReviews);
			selectStmt.setString(1, episode.getEpisodeId());
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int resultEpisodesReviewsId = results.getInt("EpisodeReviewId");
				String userId = results.getString("UserId");			
				String reviewsContent = results.getString("reviewsContent");				
				Users user = usersDao.getUserFromUserId(userId);
				EpisodesReviews episodesReview = new EpisodesReviews(resultEpisodesReviewsId, user, episode, reviewsContent);
				episodesReviews .add(episodesReview);
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
		return episodesReviews;
	}


}
