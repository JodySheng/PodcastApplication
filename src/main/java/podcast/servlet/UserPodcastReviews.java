package podcast.servlet;

import podcast.dal.PodcastReviewsDao;
import podcast.model.Podcast;
import podcast.model.PodcastReviews;
import podcast.model.Users;

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


@WebServlet("/podcastreviews")
public class UserPodcastReviews extends HttpServlet {
	
	protected PodcastReviewsDao podcastReviewsDao;
	
	@Override
	public void init() throws ServletException {
		podcastReviewsDao = PodcastReviewsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
		// Retrieve and validate UserName.
        String userid = req.getParameter("userid");
        String postid = req.getParameter("podcastid");

        
        // Retrieve BlogUsers, and store in the request.
        List<PodcastReviews> podcastReviews = new ArrayList<PodcastReviews>();
        try {
        	if (postid != null && !postid.trim().isEmpty()) {
        		podcastReviews = podcastReviewsDao.getPodcastReviewsForPodcastId(postid);
        		messages.put("title", "PodcastReviews for postid " + postid);
        	}
        		
            else if (userid != null && !userid.trim().isEmpty()) {
	        	Users user = new Users(userid);
	        	podcastReviews = podcastReviewsDao.getPodcastReviewsForUser(user);
	        	messages.put("title", "PodcastReviews for UserId " + userid);
        	}
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("podcastReviews", podcastReviews);
        req.getRequestDispatcher("/PodcastReviews.jsp").forward(req, resp);
	}
}
