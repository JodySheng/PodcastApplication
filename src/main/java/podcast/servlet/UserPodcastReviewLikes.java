package podcast.servlet;

import podcast.dal.PodcastReviewLikesDao;
import podcast.dal.PodcastReviewsDao;
import podcast.model.Podcast;
import podcast.model.PodcastReviewLikes;
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


@WebServlet("/podcastreviewlikes")
public class UserPodcastReviewLikes extends HttpServlet {
	
	protected PodcastReviewLikesDao podcastReviewLikesDao;
	
	@Override
	public void init() throws ServletException {
		podcastReviewLikesDao = PodcastReviewLikesDao.getInstance();
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
        List<PodcastReviewLikes> podcastReviewLikes = new ArrayList<PodcastReviewLikes>();
        try {
        	if (podcastreviewid != null && !podcastreviewid.trim().isEmpty()) {
        		int podcastreviewid1 = Integer.parseInt(podcastreviewid);
				PodcastReviews podcastreview = new PodcastReviews(podcastreviewid1);
        		podcastReviewLikes = podcastReviewLikesDao.getPodcastReviewLikesForReview(podcastreview);
				messages.put("title", "PodcastReviewLikes for podcastreviewid " + podcastreviewid);
			}
        	Users user = new Users(userid);
        	podcastReviewLikes = podcastReviewLikesDao.getPodcastReviewLikesForUser(user);
        	messages.put("title", "PodcastReviewLikes for UserId " + userid);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("podcastReviewLikes", podcastReviewLikes);
        req.getRequestDispatcher("/PodcastReviewLikes.jsp").forward(req, resp);
	}
}
