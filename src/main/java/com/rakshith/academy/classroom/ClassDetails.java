package com.rakshith.academy.classroom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.rakshith.academy.database.DBConnection;

/**
 * Servlet implementation class ClassDetails
 */
public class ClassDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		File file = new File(
				"F:\\Rakshith\\Projects\\Phase2Project\\NewLearnerAcademy\\src\\main\\webapp\\WEB-INF\\newconfig.properties");
		InputStream in = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(in);
		System.out.println("values " + prop.getProperty("url") + " " + prop.getProperty("userid") + " "
				+ prop.getProperty("password"));
		try {
			DBConnection connect = new DBConnection(prop.getProperty("url"), prop.getProperty("userid"),
					prop.getProperty("password"));

			String classname = request.getParameter("classname");
			
			Statement stmt = connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select clr.classroom_name,tea.teacher_fname,tea.teacher_lname,suj.subject_name,suj.subject_desc from classrooms_teachers clt join classrooms_subjects clb on clt.classroom_id=clb.classroom_id\r\n"
					+ "join teachers_subjects tsu on tsu.teacher_id = clt.teacher_id and tsu.subject_id=clb.subject_id\r\n"
					+ "join classrooms clr on clt.classroom_id=clr.classroom_id\r\n"
					+ "join teachers tea on  tea.teacher_id = tsu.teacher_id\r\n"
					+ "join subjects suj on suj.subject_id=clb.subject_id" +" WHERE clr.classroom_name=" + "'" + classname + "'";
					;
			System.out.println(sql);
			ResultSet rst = stmt.executeQuery(sql);
			
			out.println("<table border=1 width=50% height=50%>");
			out.println("<tr><th>ClassRoom Name</th><th>Teacher First Name</th><th>Teacher Last Name</th><th>Subject Name</th><th>Subject Description</th><tr>");
			
			while (rst.next())
			{
				
				String roomname = rst.getString("clr.classroom_name");  
                String teacherfname = rst.getString("tea.teacher_fname");
                String teacherlname = rst.getString("tea.teacher_lname");
                String subname = rst.getString("suj.subject_name");
                String subdesc = rst.getString("suj.subject_desc");
                out.println("<tr><td>" + roomname + "</td><td>" + teacherlname + "</td><td>" + teacherfname + "</td><td>" + subname + "</td><td>" + subdesc + "</td></tr>");   
			}
			out.println("</table>");
			out.println("</html></body>");
			
//			ResultSetMetaData rsmd = (ResultSetMetaData) rst.getMetaData();
//			int columnsNumber = rsmd.getColumnCount();
//			while (rst.next()) {
//			    for (int i = 1; i <= columnsNumber; i++) {
//			        if (i > 1) System.out.print(",  ");
//			        String columnValue = rst.getString(i);
//			        out.println("Class")
//			        out.println(columnValue);
//			    }
//			    System.out.println("");
//			}
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		
	}

}
	
}
