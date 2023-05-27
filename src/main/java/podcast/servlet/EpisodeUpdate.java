package podcast.servlet;

import podcast.dal.EpisodesDao;
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


@WebServlet("/episodeupdate")
public class EpisodeUpdate extends HttpServlet {
	
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

        // Retrieve user and validate.
        String episodeid = req.getParameter("episodeid");
        if (episodeid == null || episodeid.trim().isEmpty()) {
            messages.put("success", "Please enter a valid episode id.");
        } else {
        	try {
        		Episodes episode = episodesDao.getEpisodesById(episodeid);
        		if(episode == null) {
        			messages.put("success", "episode id does not exist.");
        		}
        		req.setAttribute("episode", episode);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/EpisodeUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String episodeId = req.getParameter("episodeid");
        if (episodeId == null || episodeId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid episode Id.");
        } else {
        	try {
        		Episodes episode = episodesDao.getEpisodesById(episodeId);
        		if(episode == null) {
        			messages.put("success", "episode id does not exist. No update to perform.");
        		} else {
        			String newepisodetitle = req.getParameter("episodetitle");
        			if (newepisodetitle == null || newepisodetitle.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid new episodetitle.");
        	        } else {
        	        	episode = episodesDao.updateEpisodesTitle(episode, newepisodetitle);
        	        	messages.put("success", "Successfully updated " + episodeId);
        	        }
        		}
        		req.setAttribute("episode", episode);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/EpisodeUpdate.jsp").forward(req, resp);
    }
}
