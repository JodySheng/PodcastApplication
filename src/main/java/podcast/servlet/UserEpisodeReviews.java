package podcast.servlet;

import podcast.dal.*;
import podcast.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/episodereviews")
public class UserEpisodeReviews extends HttpServlet {

  protected EpisodesReviewsDao episodesReviewsDao;

  @Override
  public void init() throws ServletException {
    episodesReviewsDao = EpisodesReviewsDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve and validate UserName.
    String episodesTitle = req.getParameter("episodesTitle");
    if (episodesTitle == null || episodesTitle.trim().isEmpty()) {
      messages.put("title", "Invalid episodesTitle.");
    } else {
      messages.put("title", "EpisodeReviews for " + episodesTitle);
    }

    // Retrieve BlogUsers, and store in the request.
    List<EpisodesReviews> episodeReviews = new ArrayList<>();
    try {
      Episodes episode = new Episodes(episodesTitle);
      episodeReviews = episodesReviewsDao.getEpisodesReviewsByEpisode(episode);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }
    req.setAttribute("blogPosts", episodeReviews);
    req.getRequestDispatcher("/EpisodeReviewComments.jsp").forward(req, resp);
  }
}