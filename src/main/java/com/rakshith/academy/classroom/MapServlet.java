package com.rakshith.academy.classroom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.rakshith.academy.database.DBConnection;

/**
 * Servlet implementation class MapServlet
 */
public class MapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Integer classroomid;
	Integer subjectid;
	Integer teacherid;
	int count = 0;

	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MapServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		File file = new File(
				"F:\\Rakshith\\Projects\\Phase2Project\\NewLearnerAcademy\\src\\main\\webapp\\WEB-INF\\newconfig.properties");
		InputStream in = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(in);
		SessionFactory sessionfactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionfactory.openSession();

		if (request.getParameter("classroomid") != "") {

			classroomid = Integer.parseInt(request.getParameter("classroomid"));
			count++;
		}

		if (request.getParameter("subjectid") != "") {
			subjectid = Integer.parseInt(request.getParameter("subjectid"));
			count++;
		}

		if (request.getParameter("teacherid") != "") {
			teacherid = Integer.parseInt(request.getParameter("teacherid"));
			count++;
		}

		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		
		
		
		
		if (count < 2) {
			RequestDispatcher rd = request.getRequestDispatcher("mapid.html");
			rd.include(request, response);
			out.println("<br></br>");
			out.println("<SPAN style='color:red'> You need to input atlesat 2 ids </SPAN>");
			count=0;
		} else {
			
			RequestDispatcher rd1 = request.getRequestDispatcher("mapid.html");
			rd1.include(request, response);
			out.println("<br></br>");
			
			
			session.beginTransaction();

			if (request.getParameter("classroomid") != "") {

				if (request.getParameter("subjectid") != "") {

					Query classquery = session.createQuery("from ClassRoom where classroom_id=:paramclassid");
					classquery.setParameter("paramclassid", classroomid);

					Query subjectquery = session.createQuery("from Subject where subject_id=:paramsubjectid");
					subjectquery.setParameter("paramsubjectid", subjectid);

					List classlist = classquery.list();
					List subjectlist = subjectquery.list();

					if (classlist.isEmpty()) {

						
						out.println("<br></br>");
						out.println(
								"<SPAN style='color:red'> Class ID trying to map  doesn't exist hence class-subject is not mapped </SPAN>");
					} else {
						if (subjectlist.isEmpty()) {
							out.println("<br></br>");
							out.println(
									"<SPAN style='color:red'> Subject ID trying to map  doesn't exist hence class-subject is not mapped </SPAN>");

						}else {
							
							try {
								
								DBConnection connect;
								
								connect = new DBConnection(prop.getProperty("url"), prop.getProperty("userid"),
										prop.getProperty("password"));
								
								Statement stmt = connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_READ_ONLY);
													
								String sql2 = "INSERT INTO classrooms_subjects(subject_id,classroom_id) VALUES (" + subjectid + ","
										+ classroomid + " )";
								System.out.println(sql2);
								stmt.executeUpdate(sql2);
								out.println("<br></br>");
								out.println(
										"<SPAN style='color:green'> class-subject mapping done successfully </SPAN>");
					
							} catch (ClassNotFoundException | SQLException e) {
					
								e.printStackTrace();
					
							}
							
						}

					}

				}

			} else {
				out.println("<br></br>");
				out.println("<SPAN style='color:red'> Class ID is blank hence class-subject is not mapped </SPAN>");
			}
			
			if (request.getParameter("classroomid") != "") {

				if (request.getParameter("teacherid") != "") {

					Query classquery = session.createQuery("from ClassRoom where classroom_id=:paramclassid");
					classquery.setParameter("paramclassid", classroomid);

					Query teacherquery = session.createQuery("from Teacher where teacher_id=:paramteacherid");
					teacherquery.setParameter("paramteacherid", teacherid);

					List classlist = classquery.list();
					List teacherlist = teacherquery.list();

					if (classlist.isEmpty()) {

						out.println("<br></br>");
						out.println(
								"<SPAN style='color:red'> Class ID trying to map  doesn't exist hence class-teacher is not mapped </SPAN>");
					} else {
						if (teacherlist.isEmpty()) {
							out.println("<br></br>");
							out.println(
									"<SPAN style='color:red'> Teacher ID trying to map  doesn't exist hence class-teacher is not mapped </SPAN>");

						}else {
							
							try {
								DBConnection connect;
								
								connect = new DBConnection(prop.getProperty("url"), prop.getProperty("userid"),
										prop.getProperty("password"));
								Statement stmt = connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_READ_ONLY);
													
																					
								String sql2 = "INSERT INTO classrooms_teachers(teacher_id,classroom_id) VALUES (" + teacherid + ","
										+ classroomid + " )";
								System.out.println(sql2);
								stmt.executeUpdate(sql2);
								out.println("<br></br>");
								out.println(
										"<SPAN style='color:green'> class-teacher mapping done successfully </SPAN>");
								
					
							} catch (ClassNotFoundException | SQLException e) {
					
								e.printStackTrace();
					
							}
							
						}

					}

				}

			} else {
				out.println("<br></br>");
				out.println("<SPAN style='color:red'> Class ID is blank hence class-teacher is not mapped </SPAN>");
			}
			
			if (request.getParameter("subjectid") != "") {

				if (request.getParameter("teacherid") != "") {

					Query subjectquery = session.createQuery("from Subject where subject_id=:paramsubjectid");
					subjectquery.setParameter("paramsubjectid", subjectid);

					Query teacherquery = session.createQuery("from Teacher where teacher_id=:paramteacherid");
					teacherquery.setParameter("paramteacherid", teacherid);

					List subjectlist = subjectquery.list();
					List teacherlist = teacherquery.list();

					if (subjectlist.isEmpty()) {

						out.println("<br></br>");
						out.println(
								"<SPAN style='color:red'> Subject ID trying to map  doesn't exist hence subject-teacher is not mapped </SPAN>");
					} else {
						if (teacherlist.isEmpty()) {
							out.println("<br></br>");
							out.println(
									"<SPAN style='color:red'> Teacher ID trying to map  doesn't exist hence subject-teacher is not mapped </SPAN>");

						}else {
							
							try {
								DBConnection connect;
								
								connect = new DBConnection(prop.getProperty("url"), prop.getProperty("userid"),
										prop.getProperty("password"));
								
								Statement stmt = connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_READ_ONLY);
													
								String sql2 = "INSERT INTO teachers_subjects(subject_id,teacher_id) VALUES (" + subjectid + ","
										+ teacherid + " )";
								System.out.println(sql2);
								stmt.executeUpdate(sql2);
								out.println("<br></br>");
								out.println(
										"<SPAN style='color:green'> subject-teacher mapping done successfully </SPAN>");
					
							} catch (ClassNotFoundException | SQLException e) {
													
								e.printStackTrace();
					
							}
							
						}

					}

				}

			} else {
				out.println("<br></br>");
				out.println("<SPAN style='color:red'> Subject ID is blank hence class-teacher is not mapped </SPAN>");
			}
		}
		
	}
	
}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

//			Query query = session.createQuery("from ClassRoom where classroom_id=:paramclassid");
//			query.setParameter("paramclassid", classroomid);
//			List list = query.list();
//			if (list.isEmpty()) {
//				classroomid = 0;
//				RequestDispatcher rd = request.getRequestDispatcher("mapid.html");
//				rd.include(request, response);
//				out.println("<br></br>");
//				out.println(
//						"<SPAN style='color:red'> Class ID trying to map  doesn't exist. Please create class first or assign existing class </SPAN>");
//			} else {
//				query = session.createQuery("from Subject where subject_id=:paramsubjectid");
//				query.setParameter("paramsubjectid", subjectid);
//				list = query.list();
//				if (list.isEmpty()) {
//					subjectid = 0;
//					RequestDispatcher rd = request.getRequestDispatcher("mapid.html");
//					rd.include(request, response);
//					out.println("<br></br>");
//					out.println(
//							"<SPAN style='color:red'> Class ID trying to map  doesn't exist. Please create class first or assign existing class </SPAN>");
//				}
//
//			}
//
//		}
//
//	}
//
//}
//
////
////		if (request.getParameter("classroomid")!="") {
////
////			classroomid = Integer.parseInt(request.getParameter("classroomid"));
////			count++;
////			
////			Query query = session.createQuery("from ClassRoom where classroom_id=:paramclassid");
////			query.setParameter("paramclassid", classroomid);
////			List list = query.list();
////			if(list.isEmpty()) {
////				
////				RequestDispatcher rd = request.getRequestDispatcher("mapid.html");
////				rd.include(request, response);
////				out.println("<br></br>");
////				out.println("<SPAN style='color:red'> Class ID trying to map  doesn't exist. Please create class first or assign existing class </SPAN>");
////				
////			} else if  
////				 query = session.createQuery("from ClassRoom where subject_id=:paramsubjectid");
////				query.setParameter("paramsubjectid", subjectid);
////				list = query.list();
////				if(list.isEmpty()) {
////					RequestDispatcher rd = request.getRequestDispatcher("mapid.html");
////					rd.include(request, response);
////					out.println("<br></br>");
////					out.println("<SPAN style='color:red'> Subject ID trying to map  doesn't exist. Please create class first or assign existing class </SPAN>");
////					
////				}else 
////					query = session.createQuery("from ClassRoom where teacher_id=:paramteacherid");
////					query.setParameter("paramteacherid", teacherid);
////					list = query.list();
////					if(list.isEmpty()) {
////						RequestDispatcher rd = request.getRequestDispatcher("mapid.html");
////						rd.include(request, response);
////						out.println("<br></br>");
////						out.println("<SPAN style='color:red'> Teacher ID trying to map  doesn't exist. Please create class first or assign existing class </SPAN>");
////						
////					}
////
////				}
////				
////			}
////			
////
////		}
////		 {
////
////			
////			session.beginTransaction();
////		}
////
////		try {
////			DBConnection connect = new DBConnection(prop.getProperty("url"), prop.getProperty("userid"),
////					prop.getProperty("password"));
////			Statement stmt = connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
////					ResultSet.CONCUR_READ_ONLY);
////
////			String sql2 = "INSERT INTO classrooms_subjects(subject_id,classroom_id) VALUES (" + subjectid + ","
////					+ classroomid + " )";
////			System.out.println(sql2);
////			stmt.executeUpdate(sql2);
////
////		} catch (ClassNotFoundException | SQLException e) {
////
////			e.printStackTrace();
////
////		}
////
////	}
////
////}
