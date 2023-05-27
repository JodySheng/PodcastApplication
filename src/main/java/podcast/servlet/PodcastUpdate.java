package podcast.servlet;

import podcast.dal.EpisodesDao;
import podcast.dal.PodcastDao;
import podcast.model.Episodes;
import podcast.model.Podcast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/podcastupdate")
public class PodcastUpdate extends HttpServlet {
	
	protected PodcastDao podcastDao;
	
	@Override
	public void init() throws ServletException {
		podcastDao = PodcastDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String podcastid = req.getParameter("podcastid");
        if (podcastid == null || podcastid.trim().isEmpty()) {
            messages.put("success", "Please enter a valid episode id.");
        } else {
        	try {
        		Podcast podcast = podcastDao.getPodcastById(podcastid);
        		if(podcast == null) {
        			messages.put("success", "episode id does not exist.");
        		}
        		req.setAttribute("podcast", podcast);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PodcastUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String podcastId = req.getParameter("podcastid");
        if (podcastId == null || podcastId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid episode Id.");
        } else {
        	try {
        		Podcast podcast = podcastDao.getPodcastById(podcastId);
        		if(podcast == null) {
        			messages.put("success", "episode id does not exist. No update to perform.");
        		} else {
        			String newpodcasttitle = req.getParameter("newtitle");
        			if (newpodcasttitle == null || newpodcasttitle.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid new episodetitle.");
        	        } else {
        	        	podcast = podcastDao.updatePodcastTitle(podcast, newpodcasttitle);
        	        	messages.put("success", "Successfully updated " + podcastId);
        	        }
        		}
        		req.setAttribute("podcast", podcast);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PodcastUpdate.jsp").forward(req, resp);
    }
}
