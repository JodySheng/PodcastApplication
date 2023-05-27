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


@WebServlet("/episodereviewcomments")
public class UserEpisodeReviewsComments extends HttpServlet {

  protected EpisodeReviewCommentsDao episodeReviewCommentsDao;

  @Override
  public void init() throws ServletException {
    episodeReviewCommentsDao = EpisodeReviewCommentsDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve and validate UserName.
    String episodeReviewId = req.getParameter("episodesReviewId");
    if (episodeReviewId == null || episodeReviewId.trim().isEmpty()) {
      messages.put("title", "Invalid episodeReviewId.");
    } else {
      messages.put("title", "EpisodeReviewComments for " + episodeReviewId);
    }

    // Retrieve BlogUsers, and store in the request.
    List<EpisodeReviewComments> episodeReviewComments = new ArrayList<>();
    try {
      EpisodesReviews episodesReviews = new EpisodesReviews(Integer.parseInt(episodeReviewId));
      episodeReviewComments = episodeReviewCommentsDao.getEpisodeReviewCommentsForEpisodeReview(episodesReviews);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }
    req.setAttribute("blogPosts", episodeReviewComments);
    req.getRequestDispatcher("/EpisodeReviewComments.jsp").forward(req, resp);
  }
}




