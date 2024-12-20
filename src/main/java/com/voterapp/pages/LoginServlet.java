package com.voterapp.pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voterapp.daos.CandidateDAO;
import com.voterapp.daos.CandidateDaoImpl;
import com.voterapp.daos.UserDAO;
import com.voterapp.daos.UserDaoImpl;
import com.voterapp.pojos.User;

import static com.voterapp.utils.DBUtil.*;

@WebServlet(value = "/authenticate", loadOnStartup = 1)
public class LoginServlet extends HttpServlet {
	public static final long serialVersionUID = 1L;
	
	private UserDAO userDao;
	private CandidateDAO candidateDao;
	
	@Override
	public void init() throws ServletException {
		try {
			openConnection();
			userDao = new UserDaoImpl();
			candidateDao = new CandidateDaoImpl();
		} catch(Exception e) {
			throw new ServletException("Error in Servlet Initialization: " + getClass(), e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		
		try (PrintWriter out = resp.getWriter()) {
			String email = req.getParameter("em");
			String password = req.getParameter("pass");
			User user = userDao.authenticateUser(email, password);
			
			if(user == null)
				out.print("<h5>Invalid Email or Password , Please" + "<a href='login.html'>Retry</a></h5>");
			else {
//				Cookie c1 = new Cookie("user_details", user.toString());
//				resp.addCookie(c1);
				
				HttpSession session = req.getSession();
				session.setAttribute("user_details", user);
				session.setAttribute("user_dao", userDao);
				session.setAttribute("candidate_dao", candidateDao);
				
				if(user.getRole().equals("admin")) {
					resp.sendRedirect("admin_page");
				} else {
					if(user.isStatus())
						resp.sendRedirect("logout");
					else
						resp.sendRedirect("candidate_list");
				}
			}
		} catch(Exception e) {
			throw new ServletException("Error in Post Method: " + getClass(), e);
		}
	}
	

	@Override
	public void destroy() {
		try {
			System.out.println("In Destroy()");
			userDao.cleanUp();
			candidateDao.cleanUp();
			closeConnection();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
