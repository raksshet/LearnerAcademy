package com.rakshith.academy.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.rakshith.academy.database.DBConnection;

public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		File file = new File(
				"F:\\Rakshith\\Projects\\Phase2Project\\LearnerAcademy\\src\\main\\webapp\\WEB-INF\\config.properties");
		InputStream in = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(in);
		System.out.println("values " + prop.getProperty("url") + " " + prop.getProperty("userid") + " "
				+ prop.getProperty("password"));
		try {
			DBConnection connect = new DBConnection(prop.getProperty("url"), prop.getProperty("userid"),
					prop.getProperty("password"));

			String filterusername = request.getParameter("username");
			String filterpassword = request.getParameter("password");

			Statement stmt = connect.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM user_details" + " WHERE username=" + "'" + filterusername + "'";
			System.out.println(sql);

			ResultSet rst = stmt.executeQuery(sql);
			
			if (!rst.next()) {
				// request.getRequestDispatcher("login.html").include(request, response);
				RequestDispatcher rd = request.getRequestDispatcher("login.html");
				rd.include(request, response);
				out.println("<SPAN style='color:red'> InValid UserName supplied </SPAN>");

			}else {

			
				String username = rst.getString("username");
				String password = rst.getString("user_password");
				filterusername = request.getParameter("username");
				filterpassword = request.getParameter("password");
				System.out.println(" Before Values are " + filterpassword + " " + filterusername + " " + username + " "
						+ password);

				if (filterpassword.equals(password) && filterusername.equals(username)) {
					//System.out.println(" success");
					// request.getRequestDispatcher("mainpage.html").include(request, response);
					RequestDispatcher rd = request.getRequestDispatcher("mainpage.html");
					rd.include(request, response);
					//out.println("<SPAN style='color:red'> Hurray valid Credentilas Supplied </SPAN>");

				} else {
					//System.out.println("else???");
					request.getRequestDispatcher("login.html").include(request, response);
//					RequestDispatcher rd = request.getRequestDispatcher("login.html");
//					rd.include(request, response);
					out.println("<br></br>");
					out.println("<SPAN style='color:red'> Invalid Credentilas Supplied </SPAN>");
					out.println("</body></html>");
				}

			
			
		}

			stmt.close();
			connect.colseConnection();
			// out.println("DB connection closed.<br>");

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}

		out.println("</body</html>");
	}
}
