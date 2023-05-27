package podcast.servlet;


import podcast.dal.PodcastReviewsDao;
import podcast.model.PodcastReviews;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/podcastreviewdelete")
public class PodcastReviewDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete Podcast Review");        
        req.getRequestDispatcher("/PodcastReviewDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String podcastreviewid = req.getParameter("podcastreviewid");
        if (podcastreviewid == null || podcastreviewid.trim().isEmpty()) {
            messages.put("title", "Invalid podcastreview id");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the BlogUser.
        	int podcastreviewid1 = Integer.parseInt(podcastreviewid);
        	PodcastReviews podcastReview = new PodcastReviews(podcastreviewid1);
	        try {
	        	podcastReview = podcastReviewsDao.delete(podcastReview);
	        	// Update the message.
		        if (podcastReview == null) {
		            messages.put("title", "Successfully deleted " + podcastReview);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + podcastReview);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PodcastReviewDelete.jsp").forward(req, resp);
    }
}
