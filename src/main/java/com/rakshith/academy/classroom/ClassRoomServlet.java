package com.rakshith.academy.classroom;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rakshith.academy.teacher.Teacher;

/**
 * Servlet implementation class ClassRoomServlet
 */
public class ClassRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClassRoomServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		ClassRoom classroom = new ClassRoom();

		String classroomname = request.getParameter("classroomname");
		Integer classcapacity = Integer.parseInt(request.getParameter("classcapacity"));

		if (classroomname.isEmpty()) {
			out.println("<SPAN style='color:red'> ClassRoom name can't be blank </SPAN>");
		}

		classroom.setClassname(classroomname);
		classroom.setClasscapacity(classcapacity);

		SessionFactory sessionfactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionfactory.openSession();
		try {
			session.beginTransaction();
			session.save(classroom);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}

		RequestDispatcher rd = request.getRequestDispatcher("mainpage.html");
		rd.include(request, response);
		out.println("<br></br>");
		out.println("<SPAN style='color:green'> ClassRoom Details uploaded successfully </SPAN>");
		out.println("<html></body>");
	}

}
