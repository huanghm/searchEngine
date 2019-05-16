package org.jbox.example;


import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbox.dao.Page;
public class SearchAction extends HttpServlet {

	private static final long serialVersionUID = -8919422794481772785L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MyJbox myjbox = new MyJbox();
		String query = request.getParameter("query");
		Page[] ps = null;
		if (query != null && query.length() != 0) {
			ps = myjbox.search(query);
		}
		request.getSession().setAttribute("allPages", ps);
		request.getSession().setAttribute("myJbox", myjbox);
		request.getSession().setAttribute("query", query);
		RequestDispatcher dis = request.getRequestDispatcher("/showResult");
		dis.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
