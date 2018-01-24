package com.bruce.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bruce.service.users;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		HttpSession session = request.getSession();
		users user = new users();
		if (name == null) {
			session.setAttribute("error","User name cannot be empty!");
			response.sendRedirect("login.jsp");
		} else if (pwd == null) {
			session.setAttribute("error","Password cannot be empty!");
			response.sendRedirect("login.jsp");
		}
		if (user.validate(name, pwd)) {
			session.setAttribute("user", user);
			session.removeAttribute("error");
			response.sendRedirect("HomePage.jsp");
		} else {
			session.setAttribute("error","Incorrect user name or password!");
			session.setMaxInactiveInterval(1);
			response.sendRedirect("login.jsp");
		}
	}

}
