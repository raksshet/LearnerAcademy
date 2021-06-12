package com.rakshith.academy.classroom;

import java.io.IOException;
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

import com.rakshith.academy.subject.Subject;
import com.rakshith.academy.teacher.Teacher;

/**
 * Servlet implementation class ClassServlet
 */
public class ClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClassServlet() {
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
		System.out.println("Hurrray ima in Class Servlet");
		
		Integer classroomid= Integer.parseInt(request.getParameter("classroomid"));
		String classname = request.getParameter("classname");
		Integer subjectid= Integer.parseInt(request.getParameter("subjectid"));
		String subjectname = request.getParameter("subjectname");
		String subjectdesc = request.getParameter("subjectdesc");
		Integer classcapacity = Integer.parseInt(request.getParameter("classcapacity"));
		Integer teacherid= Integer.parseInt(request.getParameter("teacherid"));
		String teacherfname = request.getParameter("teacherfname");
		String teacherlname = request.getParameter("teacherlname");
		
		
		SessionFactory sessionfactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionfactory.openSession();
		session.beginTransaction();
		Query classquery = session.createQuery("from ClassRoom where classroom_name=:paramclass");
		classquery.setParameter("paramclass", classname);
		List<ClassRoom> classlist = classquery.list();
		
		
		Query subjectquery = session.createQuery("from Subject where subject_name=:paramsubject");
		subjectquery.setParameter("paramsubject", subjectname);
		List<Subject> subjectlist = subjectquery.list();
		
		if(classlist.isEmpty() && subjectlist.isEmpty() ) {
			
			System.out.println(classlist.toString());
			
			ClassRoom classroom = classlist.get(0);
			Subject subject = new Subject(subjectname,subjectdesc);
			
			classroom.addSubject(subject);
			subject.addClassRoom(classroom);
			
			session.save(classroom);
			session.save(subject);
			session.getTransaction().commit();
			
		} else if (!classlist.isEmpty() && !subjectlist.isEmpty() ) {
			
			//ClassRoom classroom = new ClassRoom(classname,classcapacity);
			
						
			ClassRoom classroom = (ClassRoom) session.get(ClassRoom.class, 2);
			Subject subject = new Subject(subjectname,subjectdesc);
			
			//classroom.addSubject(subject);
			subject.addClassRoom(classroom);
			
			
			
			//session.saveOrUpdate(classlist);
			//session.save(subject);
			session.getTransaction().commit();
			
		} 
			
		
		RequestDispatcher rd = request.getRequestDispatcher("classform.html");
		rd.include(request, response);
	}
}
