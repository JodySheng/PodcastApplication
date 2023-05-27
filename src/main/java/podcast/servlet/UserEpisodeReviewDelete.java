package podcast.servlet;

import podcast.dal.*;
import podcast.model.EpisodesReviews;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/episodereviewdelete")
public class UserEpisodeReviewDelete extends HttpServlet {

  protected EpisodesReviewsDao episodesReviewsDao;

  @Override
  public void init() throws ServletException {
    episodesReviewsDao = episodesReviewsDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    // Provide a title and render the JSP.
    messages.put("title", "Delete EpisodesReviews");
    req.getRequestDispatcher("/EpisodesReviewsDelete.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve and validate name.
    String episodeReviewId = req.getParameter("EpisodeReviewId");
    if (episodeReviewId == null || episodeReviewId.trim().isEmpty()) {
      messages.put("title", "Invalid EpisodeCommentId");
      messages.put("disableSubmit", "true");
    } else {
      // Delete the EpisodeCommentId.
      EpisodesReviews episodeReview = new EpisodesReviews(Integer.parseInt(episodeReviewId));
      try {
        episodeReview = episodesReviewsDao.delete(episodeReview);
        // Update the message.
        if (episodeReview == null) {
          messages.put("title", "Successfully deleted " + episodeReviewId);
          messages.put("disableSubmit", "true");
        } else {
          messages.put("title", "Failed to delete " + episodeReviewId);
          messages.put("disableSubmit", "false");
        }
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/EpisodesReviewCommentDelete.jsp").forward(req, resp);
  }
}




