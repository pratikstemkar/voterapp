package com.voterapp.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.voterapp.daos.CandidateDAO;
import com.voterapp.pojos.Candidate;

@WebServlet("/admin_page")
public class AdminHomePage extends HttpServlet {
	public static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		
		try(PrintWriter out = resp.getWriter()) {
			out.print("<h5> In admin home page ....</h5>");
			
			HttpSession session = req.getSession();
			CandidateDAO candidateDao = (CandidateDAO) session.getAttribute("candidate_dao");
			
			out.print("<h4>Top 2 Candidates: </h4>");
			out.print("<ul>");
			for(Candidate c : candidateDao.getTop2Candidates())
				out.print("<li>" + c.getName() + " - " + c.getParty() + " - " + c.getVotes() + "</li>");
			out.print("</ul>");
			
			out.print("<h4>Party Wise Data: </h4>");
			out.print("<ol>");
			for(Map.Entry<String, Integer> c : candidateDao.getPartyWiseData().entrySet())
				out.print("<li>" + c.getKey() + " - " + c.getValue() + "</li>");
			out.print("</ol>");
			
		} catch (Exception e) {
			throw new ServletException("Error in Get Method: " + getClass(), e);
		}
	}
	
}
