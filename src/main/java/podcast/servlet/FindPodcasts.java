package podcast.servlet;


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

import podcast.dal.PodcastDao;
import podcast.model.Podcast;


/**
 * FindUsers is the primary entry point into the application.
 * 
 * Note the logic for doGet() and doPost() are almost identical. However, there is a difference:
 * doGet() handles the http GET request. This method is called when you put in the /findusers
 * URL in the browser.
 * doPost() handles the http POST request. This method is called after you click the submit button.
 * 
 * To run:
 * 1. Run the SQL script to recreate your database schema: http://goo.gl/86a11H.
 * 2. Insert test data. You can do this by running blog.tools.Inserter (right click,
 *    Run As > JavaApplication.
 *    Notice that this is similar to Runner.java in our JDBC example.
 * 3. Run the Tomcat server at localhost.
 * 4. Point your browser to http://localhost:8080/BlogApplication/findusers.
 */
@WebServlet("/findpodcasts")
public class FindPodcasts extends HttpServlet {
	
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

        List<Podcast> podcast = new ArrayList<Podcast>();
        
        // Retrieve and validate name.
        // firstname is retrieved from the URL query string.
        String title= req.getParameter("title");
        if (title == null || title.trim().isEmpty()) {
            messages.put("success", "Please enter a valid author.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
        		podcast = podcastDao.getPodcastForAuthor(title);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + title);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previousAuthor", title);
        }
        req.setAttribute("podcast", podcast);
        
        req.getRequestDispatcher("/FindPodcasts.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Podcast> podcast = new ArrayList<Podcast>();
        
        // Retrieve and validate name.
        // firstname is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindUsers.jsp).
        String author = req.getParameter("author");
        if (author == null || author.trim().isEmpty()) {
            messages.put("success", "Please enter a valid author.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
        		podcast = podcastDao.getPodcastForAuthor(author);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + author);
        }
        req.setAttribute("podcast", podcast);
        
        req.getRequestDispatcher("/FindPodcasts.jsp").forward(req, resp);
    }
}
