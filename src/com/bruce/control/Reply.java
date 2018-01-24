package com.bruce.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bruce.service.reply;
import com.bruce.service.users;

/**
 * Servlet implementation class Reply
 */
@WebServlet("/Reply")
public class Reply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reply() {
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
		String prev_id = request.getParameter("id");
		String content = request.getParameter("content");
		HttpSession session = request.getSession();
		users user = (users) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("login.jsp");
		} else {
			reply temp = new reply(prev_id, user.id(), content);
			response.sendRedirect("singleTopic.jsp?id=" + prev_id);
		}
	}

}
