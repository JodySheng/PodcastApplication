package podcast.servlet;

import podcast.dal.PodcastReviewCommentsDao;
import podcast.model.PodcastReviewComments;
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


@WebServlet("/podcastreviewcomments")
public class UserPodcastReviewComments extends HttpServlet {
	
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
		
		// Retrieve and validate UserName.
        String userid = req.getParameter("userid");
    	String podcastreviewid = req.getParameter("podcastreviewid");

        
        // Retrieve BlogUsers, and store in the request.
        List<PodcastReviewComments> podcastReviewComments = new ArrayList<PodcastReviewComments>();
        try {
			if (podcastreviewid != null && !podcastreviewid.trim().isEmpty()) {
				int podcastreviewid1 = Integer.parseInt(podcastreviewid);
				PodcastReviews podcastreview = new PodcastReviews(podcastreviewid1);
				podcastReviewComments = podcastReviewCommentsDao.getPodcastReviewCommentsForPost(podcastreview);
				messages.put("title", "PodcastReviewComments for podcastreviewid " + podcastreviewid);
			}
			else if (userid != null && !userid.trim().isEmpty()) {
	        	Users user = new Users(userid);
	        	podcastReviewComments = podcastReviewCommentsDao.getPodcastReviewCommentsForUser(user);
	        	messages.put("title", "PodcastReviewComments for UserId " + userid);
			}
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("podcastReviewComments", podcastReviewComments);
        req.getRequestDispatcher("/PodcastReviewComments.jsp").forward(req, resp);
	}
}
