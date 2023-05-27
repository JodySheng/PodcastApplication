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


@WebServlet("/episodes")
public class UserEpisode extends HttpServlet {

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

    // Retrieve and validate UserName.
    String podcastId = req.getParameter("podcastId");
    if (podcastId == null || podcastId.trim().isEmpty()) {
      messages.put("title", "Invalid podcastId.");
    } else {
      messages.put("title", "Episodes for " + podcastId);
    }

    // Retrieve Podcast, and store in the request.
    List<Episodes> episodes = new ArrayList<>();
    try {
      Podcast podcast = new Podcast(podcastId);
      episodes = episodesDao.getEpisodesByPodcast(podcast);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new IOException(e);
    }
    req.setAttribute("Episodes", episodes);
    req.getRequestDispatcher("/Episodes.jsp").forward(req, resp);
  }
}





