package com.rakshith.academy.teacher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.rakshith.academy.classroom.ClassRoom;
import com.rakshith.academy.student.Student;

/**
 * Servlet implementation class TeacherServlet
 */
public class TeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TeacherServlet() {
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
		Teacher teacher = new Teacher();
		
		
		String teacherfname = request.getParameter("teacherfname");
		String teacherlname = request.getParameter("teacherlname");
		
		if(teacherfname.isBlank()) {
			out.println("<SPAN style='color:red'> Teacher First name can't be blank </SPAN>");
		}

		teacher.setTeacherfname(teacherfname);
		teacher.setTeacherlname(teacherlname);

		SessionFactory sessionfactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionfactory.openSession();
		try {
		session.beginTransaction();
		session.save(teacher);
		session.getTransaction().commit();
		} catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
		session.close();
        }

		RequestDispatcher rd = request.getRequestDispatcher("mainpage.html");
		rd.include(request, response);
		out.println("<br></br>");
		out.println("<SPAN style='color:green'> Teacher Details uploaded successfully </SPAN>");
		out.println("<html></body>");

	}

}
