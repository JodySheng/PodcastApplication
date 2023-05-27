package podcast.dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import podcast.dal.EpisodesDao;
import podcast.model.*;

public class EpisodesDao {

	protected ConnectionManager connectionManager;

	private static EpisodesDao instance = null;
	protected EpisodesDao() {
		connectionManager = new ConnectionManager();
	}
	public static EpisodesDao getInstance() {
		if(instance == null) {
			instance = new EpisodesDao();
		}
		return instance;
	}
	
	public Episodes create(Episodes episode) throws SQLException {
		String insertEpisodes =
			"INSERT INTO Episodes(EpisodeId,EpisodesTitle,PublishDate,Descriptions,AudioLength,Url,PodcastId) " +
			"VALUES(?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertEpisodes);
			insertStmt.setString(1, episode.getEpisodeId());
			insertStmt.setString(2, episode.getEpisodesTitle());
			insertStmt.setTimestamp(3, new Timestamp(episode.getPublishDate().getTime()));
			insertStmt.setString(4, episode.getDescriptions());
			insertStmt.setInt(5, episode.getAudioLength());
			insertStmt.setString(6, episode.getUrl());
			insertStmt.setString(7, episode.getPodcast().getPodcastId());
			insertStmt.executeUpdate();
			return episode;
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
	 * Update the EpisodesTitle of the Episodes instance.
	 * This runs a UPDATE statement.
	 */
	public Episodes updateEpisodesTitle(Episodes episode, String newEpisodesTitle) throws SQLException {
		String updateEpisodes = "UPDATE Episodes SET EpisodesTitle=? WHERE EpisodeId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateEpisodes);
			updateStmt.setString(1, newEpisodesTitle);
			updateStmt.setString(2, episode.getEpisodeId());
			updateStmt.executeUpdate();

			// Update the episode param before returning to the caller.
			episode.setEpisodesTitle(newEpisodesTitle);
			return episode;
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
	 * Get the Episodes record by Id.
	 * This runs a SELECT statement and returns a single Episodes instance.
	 * Note that we use UsersDao to retrieve the referenced Users instance.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the Episodes, Users tables and then build each object.
	 */
	public Episodes getEpisodesById(String episodeId) throws SQLException {
		String selectEpisodes =
			"SELECT EpisodeId,EpisodesTitle,PublishDate,Descriptions,AudioLength,Url,PodcastId " +
			"FROM Episodes " +
			"WHERE EpisodeId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		PodcastDao podcastDao = PodcastDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectEpisodes);
			selectStmt.setString(1, episodeId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultEpisodeId = results.getString("EpisodeId");
				String episodesTitle = results.getString("EpisodesTitle");
				Date date = results.getDate("PublishDate");
				String description = results.getString("Descriptions");
				int audioLength = results.getInt("AudioLength");
				String url = results.getString("Url");
				String podcastId = results.getString("PodcastId");
	
				
				Podcast podcast = podcastDao.getPodcastById(podcastId);
				Episodes episode = new Episodes(resultEpisodeId, episodesTitle, date, description, audioLength, url, podcast);
				return episode;
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
	 * Get the Episodes record by Title.
	 * This runs a SELECT statement and returns a single Episodes instance.
	 * Note that we use UsersDao to retrieve the referenced Users instance.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the Episodes, Users tables and then build each object.
	 */
	public List<Episodes> getEpisodesByTitle(String Title) throws SQLException {
		List<Episodes> episodes = new ArrayList<Episodes>();
		String selectEpisodes =
			"SELECT EpisodeId,EpisodesTitle,PublishDate,Descriptions,AudioLength,Url,PodcastId " +
			"FROM Episodes " +
			"WHERE EpisodesTitle=?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		PodcastDao podcastDao = PodcastDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectEpisodes);
			selectStmt.setString(1, Title);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultEpisodeId = results.getString("EpisodeId");
				String episodesTitle = results.getString("EpisodesTitle");
				Date date = results.getDate("PublishDate");
				String description = results.getString("Descriptions");
				int audioLength = results.getInt("AudioLength");
				String url = results.getString("Url");
				String podcastId = results.getString("PodcastId");
	
				
				Podcast podcast = podcastDao.getPodcastById(podcastId);
				Episodes episode = new Episodes(resultEpisodeId, episodesTitle, date, description, audioLength, url, podcast);
				episodes.add(episode);
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
		return episodes;
	}
	
	/**
	 * Get the Episodes record by Title.
	 * This runs a SELECT statement and returns a single Episodes instance.
	 * Note that we use UsersDao to retrieve the referenced Users instance.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the Episodes, Users tables and then build each object.
	 */
	public List<Episodes> getEpisodesByPodcast(Podcast podcast) throws SQLException {
		List<Episodes> episodes = new ArrayList<Episodes>();
		String selectEpisodes =
			"SELECT EpisodeId,EpisodesTitle,PublishDate,Descriptions,AudioLength,Url,PodcastId " +
			"FROM Episodes " +
			"WHERE PodcastId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;

		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectEpisodes);
			selectStmt.setString(1, podcast.getPodcastId());
			results = selectStmt.executeQuery();
			while(results.next()) {
				String resultEpisodeId = results.getString("EpisodeId");
				String episodesTitle = results.getString("EpisodesTitle");
				Date date = results.getDate("PublishDate");
				String description = results.getString("Descriptions");
				int audioLength = results.getInt("AudioLength");
				String url = results.getString("Url");
	
	
				Episodes episode = new Episodes(resultEpisodeId, episodesTitle, date, description, audioLength, url, podcast);
				episodes.add(episode);
				
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
		return episodes;
	}
	
	/**
	 * Delete the Episodes instance.
	 * This runs a DELETE statement.
	 */
	public Episodes delete(Episodes episode) throws SQLException {
		String deleteEpisodes = "DELETE FROM Episodes WHERE EpisodeId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteEpisodes);
			deleteStmt.setString(1, episode.getEpisodeId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Episodes instance.
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


}
