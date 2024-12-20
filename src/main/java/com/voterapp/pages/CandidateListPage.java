package com.voterapp.pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/candidate_list")
public class CandidateListPage extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		
		try(PrintWriter out = resp.getWriter()) {
			out.print("<h5> In candidate list page ....</h5>");
			Cookie[] cookies = req.getCookies();
			if(cookies != null) {
				for(Cookie c : cookies)
					if(c.getName().equals("user_details")) {
						out.print("<h5> User Details retrieved from Cookie :   " + c.getValue() + "</h5>");
						break;
					}
			} else
				out.print("<h5> No Cookies , Session Tracking Failed!!!!!</h5>");
		}
	}
	
}
