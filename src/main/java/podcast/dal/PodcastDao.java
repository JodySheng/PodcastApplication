package podcast.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import podcast.dal.PodcastDao;
import podcast.model.Podcast;

public class PodcastDao {
	protected ConnectionManager connectionManager;

	private static PodcastDao instance = null;
	protected PodcastDao() {
		connectionManager = new ConnectionManager();
	}
	public static PodcastDao getInstance() {
		if(instance == null) {
			instance = new PodcastDao();
		}
		return instance;
	}

	public Podcast create(Podcast podcast) throws SQLException {
		String insertPodcast =
			"INSERT INTO Podcast(PodcastId,PodcastTitle,Author,Categories,LanguageType) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPodcast);
			insertStmt.setString(1, podcast.getPodcastId());
			insertStmt.setString(2, podcast.getPodcastTitle());
			insertStmt.setString(3, podcast.getAuthor());
			insertStmt.setString(4, podcast.getCategories());
			insertStmt.setString(5, podcast.getLangaugeType());
			insertStmt.executeUpdate();
			return podcast;
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
	 * Update the PodcastTitle of the Podcast instance.
	 * This runs a UPDATE statement.
	 */
	public Podcast updatePodcastTitle(Podcast podcast, String newPodcastTitle) throws SQLException {
		String updatePodcast = "UPDATE Podcast SET PodcastTitle=? WHERE PodcastId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePodcast);
			updateStmt.setString(1, newPodcastTitle);
			updateStmt.setString(2, podcast.getPodcastId());
			updateStmt.executeUpdate();

			// Update the podcast param before returning to the caller.
			podcast.setPodcastTitle(newPodcastTitle);
			return podcast;
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
	 * Delete the Podcast instance.
	 * This runs a DELETE statement.
	 */
	public Podcast delete(Podcast podcast) throws SQLException {
		String deletePodcast = "DELETE FROM Podcast WHERE PodcastId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePodcast);
			deleteStmt.setString(1, podcast.getPodcastId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Podcast instance.
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
	 * Get the Podcast record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Podcast instance.
	 * Note that we use UsersDao to retrieve the referenced Users instance.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the Podcast, Users tables and then build each object.
	 */
	public Podcast getPodcastById(String podcastId) throws SQLException {
		String selectPodcast =
			"SELECT PodcastId,PodcastTitle,Author,Categories,LanguageType " +
			"FROM Podcast " +
			"WHERE PodcastId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPodcast);
			selectStmt.setString(1, podcastId);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultPodcastId = results.getString("PodcastId");
				String podcastTitle = results.getString("PodcastTitle");
				String author = results.getString("Author");
				String catetories = results.getString("Categories");
				String language = results.getString("LanguageType");
			
				Podcast podcast = new Podcast(resultPodcastId, podcastTitle, author, catetories,
						language);
				return podcast;
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

	// We might not need this method. Need to be checked again.
	// Now I just modified the original code from professor
	// from search all blogposts by user to search all Podcasts by author, 
	// and these might not be two interchangeable concept.) by Chiu.
	/**
	 * Get the all the Podcast for an author.
	 */
	public List<Podcast> getPodcastForAuthor(String author) throws SQLException {
		List<Podcast> podcasts = new ArrayList<Podcast>();
		String selectPodcast =
			"SELECT PodcastId,PodcastTitle,Author,Categories,Language " +
			"FROM Podcast " +
			"WHERE Author=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPodcast);
			selectStmt.setString(1, author);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String PodcastId = results.getString("PodcastId");
				String PodcastTitle = results.getString("PodcastTitle");
				String Author = results.getString(
						"Author");
				String Categories = results.getString("Categories");
				String Language = results.getString("Language");
				Podcast podcast = new Podcast(PodcastId, PodcastTitle, Author, Categories, Language);
				podcasts.add(podcast);
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
		return podcasts;
	}
	
	/**
	 * Get the Podcast record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Podcast instance.
	 */
	public Podcast getPodcastByTitle(String podcastTitle) throws SQLException {
		String selectPodcast =
			"SELECT PodcastId,PodcastTitle,Author,Categories,LanguageType " +
			"FROM Podcast " +
			"WHERE PodcastTitle=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPodcast);
			selectStmt.setString(1, podcastTitle);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String podcastId = results.getString("PodcastId");
				String resultPodcastTitle = results.getString("PodcastTitle");
				String author = results.getString("Author");
				String catetories = results.getString("Categories");
				String language = results.getString("LanguageType");
				Podcast podcast = new Podcast(podcastId, resultPodcastTitle, author, catetories, language);
				return podcast;
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
}
