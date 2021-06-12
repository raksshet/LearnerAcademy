package com.rakshith.academy.subject;

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
 * Servlet implementation class SubjectServlet
 */
public class SubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		Subject subject = new Subject();
		
		
		String subjectname = request.getParameter("subjectname");
		String subjectdesc = request.getParameter("subjectdesc");
		
		if(subjectname.isBlank()) {
			out.println("<SPAN style='color:red'> Subject name can't be blank </SPAN>");
		}

		subject.setSubjectname(subjectname);
		subject.setSubjectdesc(subjectdesc);

		SessionFactory sessionfactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionfactory.openSession();
		try {
		session.beginTransaction();
		session.save(subject);
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
		out.println("<SPAN style='color:green'> Subject Details uploaded successfully </SPAN>");
		out.println("<html></body>");
		
	}

}
