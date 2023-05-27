package podcast.servlet;

import podcast.dal.*;
import podcast.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/usercreate")
public class UserCreate extends HttpServlet {

  protected UsersDao usersDao;

  @Override
  public void init() throws ServletException {
    usersDao = UsersDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    //Just render the JSP.
    req.getRequestDispatcher("/UserCreate.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve and validate name.
    String userName = req.getParameter("username");
    if (userName == null || userName.trim().isEmpty()) {
      messages.put("success", "Invalid UserName");
    } else {
      // Create the User.
      String userId = req.getParameter("userId");
      String password = req.getParameter("password");
      // dob must be in the format yyyy-mm-dd.
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String created = req.getParameter("createdDate");
      Date createdDate = new Date();
      try {
        createdDate = dateFormat.parse(created);
      } catch (ParseException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      try {
        Users user = new Users(userId, userName, password, createdDate);
        user = usersDao.create(user);
        messages.put("success", "Successfully created " + userName);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/UserCreate.jsp").forward(req, resp);
  }
}



