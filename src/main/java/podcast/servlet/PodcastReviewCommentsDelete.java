package podcast.servlet;


import podcast.dal.PodcastReviewCommentsDao;
import podcast.dal.PodcastReviewsDao;
import podcast.model.PodcastReviewComments;
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


@WebServlet("/podcastreviewcommentdelete")
public class PodcastReviewCommentsDelete extends HttpServlet {
	
	protected PodcastReviewCommentsDao podcastReviewCommentsDao;
	
	@Override
	public void init() throws ServletException {
		podcastReviewCommentsDao = PodcastReviewCommentsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Podcast Review Comments");        
        req.getRequestDispatcher("/PodcastReviewCommentDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String podcastreviewcommentid = req.getParameter("podcastreviewcommentid");
        if (podcastreviewcommentid == null || podcastreviewcommentid.trim().isEmpty()) {
            messages.put("title", "Invalid podcastreviewcommentid");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the BlogUser.
        	int podcastreviewcommentid1 = Integer.parseInt(podcastreviewcommentid);
        	PodcastReviewComments podcastReviewComment = new PodcastReviewComments(podcastreviewcommentid1);
	        try {
	        	podcastReviewComment = podcastReviewCommentsDao.delete(podcastReviewComment);
	        	// Update the message.
		        if (podcastReviewComment == null) {
		            messages.put("title", "Successfully deleted " + podcastReviewComment);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + podcastReviewComment);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PodcastReviewCommentDelete.jsp").forward(req, resp);
    }
}
