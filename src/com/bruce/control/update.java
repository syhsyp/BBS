package com.bruce.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bruce.service.users;

/**
 * Servlet implementation class update
 */
@WebServlet("/update")
public class update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public update() {
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
		HttpSession session = request.getSession();
		users user = (users)session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("HomePage.jsp");
		}
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("full_name");
		java.sql.Date birthdate = user.strToDate(request.getParameter("birthdate"));
		String email = request.getParameter("email");
		user.update_password(pwd);
		user.update_fullname(name);
		user.update_birthdate(birthdate);
		user.update_email(email);
		response.sendRedirect("profile.jsp");
	}

}
