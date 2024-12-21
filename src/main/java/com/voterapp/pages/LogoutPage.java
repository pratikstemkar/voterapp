package com.voterapp.pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voterapp.daos.CandidateDAO;
import com.voterapp.daos.UserDAO;
import com.voterapp.pojos.User;

@WebServlet("/logout")
public class LogoutPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		try(PrintWriter out = resp.getWriter()) {
			HttpSession session = req.getSession();
			session.invalidate();
			out.print("<h5> You have already voted ...logging you out !</h5>");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		try(PrintWriter out = resp.getWriter()) {
			int candidateId = Integer.parseInt(req.getParameter("candidateId"));
			
			HttpSession session = req.getSession();
			UserDAO userDao = (UserDAO) session.getAttribute("user_dao");
			CandidateDAO candidateDao = (CandidateDAO) session.getAttribute("candidate_dao");
			User user = (User) session.getAttribute("user_details");
			
			out.print("<h5> Hello ," + user.getFirstName() + " " + user.getLastName() + "</h5>");
			out.print("<h6> " + userDao.updateVotingStatus(user.getUserId()) + "</h6>");
			out.print("<h6> " + candidateDao.incrementVotes(candidateId) + "</h6>");
			
			session.invalidate();
			out.print("<h6>  You have logged out.....</h6>");
		} catch (Exception e) {
			throw new ServletException("Error in Post Method: " + getClass(), e);
		}
	}

}
