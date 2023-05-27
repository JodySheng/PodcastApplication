package podcast.servlet;

import podcast.dal.*;
import podcast.model.Episodes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/episodedelete")
public class UserEpisodeDelete extends HttpServlet {

  protected EpisodesDao episodesDao;

  @Override
  public void init() throws ServletException {
    episodesDao = EpisodesDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    // Provide a title and render the JSP.
    messages.put("id", "Delete Episode");
    req.getRequestDispatcher("/EpisodeDelete.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve and validate name.
    String episodesId = req.getParameter("EpisodesId");
    if (episodesId == null || episodesId.trim().isEmpty()) {
      messages.put("id", "Invalid EpisodesId");
      messages.put("disableSubmit", "true");
    } else {
      // Delete the EpisodesId.
      Episodes episode = new Episodes(episodesId);
      try {
        episode = episodesDao.delete(episode);
        // Update the message.
        if (episode == null) {
          messages.put("id", "Successfully deleted " + episodesId);
          messages.put("disableSubmit", "true");
        } else {
          messages.put("id", "Failed to delete " + episodesId);
          messages.put("disableSubmit", "false");
        }
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/EpisodeDelete.jsp").forward(req, resp);
  }
}



