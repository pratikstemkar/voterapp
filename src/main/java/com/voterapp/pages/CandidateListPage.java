package com.voterapp.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voterapp.daos.CandidateDAO;
import com.voterapp.pojos.Candidate;
import com.voterapp.pojos.User;

@WebServlet("/candidate_list")
public class CandidateListPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		
		try(PrintWriter out = resp.getWriter()) {
			out.print("<h3> Candidate List </h3>");
			
			HttpSession session = req.getSession();
			User userDetails = (User) session.getAttribute("user_details");

			if(userDetails != null) {
				out.print("<h3>Hello, " + userDetails.getFirstName() + " " + userDetails.getLastName() + "</h3>");
				
				CandidateDAO candidateDao = (CandidateDAO) session.getAttribute("candidate_dao");
				List<Candidate> candidates = candidateDao.getAllCandidates();
				out.print("<form method='post' action='logout'>");
				for(Candidate c : candidates)
				{
					out.print("<h6><input type='radio' "
							+ "name='candidateId' "
							+ "value='" + c.getId() +"'>" + c.getName() + "</input></h6>");
					}
				out.print("<input type='submit' value='Vote'/>");
				out.print("</form>");
			} else {
				out.print("<h5> No Cookie , Session Tracking Failed!!!!!</h5>");
			}
			
//			Cookie[] cookies = req.getCookies();
//			if(cookies != null) {
//				for(Cookie c : cookies)
//					if(c.getName().equals("user_details")) {
//						out.print("<h5> User Details retrieved from Cookie :   " + c.getValue() + "</h5>");
//						break;
//					}
//			} else
//				out.print("<h5> No Cookies , Session Tracking Failed!!!!!</h5>");
			
		} catch(Exception e) {
			throw new ServletException("Error in doGet: " + getClass(), e);
		}
	}
	
}
