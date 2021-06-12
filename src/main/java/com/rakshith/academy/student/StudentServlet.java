package com.rakshith.academy.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.rakshith.academy.classroom.ClassRoom;

/**
 * Servlet implementation class StudentServlet
 */
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		Student student = new Student();

		ClassRoom classroom = new ClassRoom();
		String classname = request.getParameter("studentclass");
		System.out.println("classname is " + classname);
//		classroom.setClass_name(classname);

		String studentfname = request.getParameter("studentfname");
		String studentlname = request.getParameter("studentlname");

		SessionFactory sessionfactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionfactory.openSession();
		Query query = session.createQuery("from ClassRoom where classroom_name=:paramclass");
		query.setParameter("paramclass", classname);
		List list = query.list();
		
		if(list.isEmpty()) {
			out.println("<SPAN style='color:red'> Class trying to map student doesn't exist. Please create class first or assign existing class </SPAN>");

		} else {
				
		student.setStudent_fname(studentfname);
		student.setStudent_lname(studentlname);
		student.setstudent_class((ClassRoom) list.get(0));
		

		session.beginTransaction();
		session.save(student);
		session.getTransaction().commit();
		session.close();
		
		}

		
		RequestDispatcher rd = request.getRequestDispatcher("mainpage.html");
		rd.include(request, response);
		out.println("<SPAN style='color:green'> Student Details uploaded successfully </SPAN>");
		out.println("<br></br>");
		out.println("<html></body>");

	}

}
