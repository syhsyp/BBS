package com.bruce.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bruce.service.users;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		users user = new users();
		String user_name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String full_name = request.getParameter("full_name");
		java.sql.Date birthdate = user.strToDate(request.getParameter("birthdate"));
		String email = request.getParameter("email");
		user = new users(user_name, pwd, full_name, birthdate, email);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		response.sendRedirect("HomePage.jsp");
	}

}
