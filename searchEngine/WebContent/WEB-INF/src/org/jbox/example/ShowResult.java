package org.jbox.example;

import java.awt.Color;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jbox.dao.Page;

public class ShowResult extends HttpServlet {

	private static final long serialVersionUID = -8919422794481772785L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		MyJbox myJbox = (MyJbox) session.getAttribute("myJbox");
		String query =  (String) session.getAttribute("query");
		Page[] allPages = (Page[]) session.getAttribute("allPages");
		int pageNum = 1;
		String tempPageNum = request.getParameter("pageNum");
		if(tempPageNum!=null&&tempPageNum.length()!=0){
			pageNum = Integer.parseInt(tempPageNum);
		}
		int size = allPages.length>10*pageNum?10:allPages.length%10;
		Page[] pages = new Page[size];
		for(int i=0;i<pages.length;i++){
			pages[i] = allPages[(pageNum-1)*10+i];
			pages[i].setText(myJbox.highlight(pages[i].getText(), query, Color.RED));
			pages[i].setTitle(myJbox.highlight(pages[i].getTitle(), query, Color.RED));
		}
		request.setAttribute("pages", pages);
		RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
		rd.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
