

package podcast.servlet;

import podcast.dal.*;
import podcast.model.EpisodeReviewComments;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/episodereviewcommentdelete")
public class UserEpisodeReviewCommentDelete extends HttpServlet {

  protected EpisodeReviewCommentsDao episodeReviewCommentsDao;

  @Override
  public void init() throws ServletException {
    episodeReviewCommentsDao = episodeReviewCommentsDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    // Provide a title and render the JSP.
    messages.put("title", "Delete EpisodeReviewComments");
    req.getRequestDispatcher("/EpisodesReviewCommentDelete.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve and validate name.
    String episodeCommentId = req.getParameter("EpisodeCommentId");
    if (episodeCommentId == null || episodeCommentId.trim().isEmpty()) {
      messages.put("title", "Invalid EpisodeCommentId");
      messages.put("disableSubmit", "true");
    } else {
      // Delete the EpisodeCommentId.
      EpisodeReviewComments episodeComment = new EpisodeReviewComments(Integer.parseInt(episodeCommentId));
      try {
        episodeComment = episodeReviewCommentsDao.delete(episodeComment);
        // Update the message.
        if (episodeComment == null) {
          messages.put("title", "Successfully deleted " + episodeCommentId);
          messages.put("disableSubmit", "true");
        } else {
          messages.put("title", "Failed to delete " + episodeCommentId);
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




