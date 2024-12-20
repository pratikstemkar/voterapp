package com.voterapp.pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.voterapp.daos.UserDAO;
import com.voterapp.daos.UserDaoImpl;
import static com.voterapp.utils.DBUtil.*;

@WebServlet("/voter_register")
public class RegisterServlet extends HttpServlet {
	private UserDAO userDao;
	
	@Override
	public void init() throws ServletException {
		try {
			openConnection();
			userDao = new UserDaoImpl();
		} catch(Exception e) {
			throw new ServletException("Error in Init: " + getClass(), e);
		}
	}	

	@Override
	public void destroy() {
		try {
			System.out.println("Inside destroy!");
			userDao.cleanUp();
			closeConnection();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		
		try(PrintWriter out = resp.getWriter()) {
			String firstName = req.getParameter("fn");
			String lastName = req.getParameter("ln");
			String email = req.getParameter("em");
			String password = req.getParameter("pass");
			Date dob = Date.valueOf(req.getParameter("dob"));
			int age = LocalDate.now().getYear() - dob.toLocalDate().getYear();
			
			if(userDao.findUser(email) != null) {
				out.print("<h3>Email already exists!</h3><br /><a href='voter_registration.html'>Try Again!</a>");
			} else if(age <= 21) {
				out.print("<h3>Age of user is not equal or greater than 21</h3><br /><a href='voter_registration.html'>Try Again!</a>");
			} else {
				int modifiedRows = userDao.createUser(firstName, lastName, email, password, dob);
				
				if(modifiedRows == 1) {
					out.print("<h3>User added successfully!</h3><br /><a href='login.html'>Login Again!</a>");
				} else {
					out.print("<h3>User addition failed!</h3><br /><a href='voter_registration.html'>Try Again!</a>");
				}
			}
			
		} catch(Exception e) {
			throw new ServletException("Error in Post Method: " + getClass(), e);
		}
	}

}
