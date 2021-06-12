package com.rakshith.academy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnect {
	
	private Connection connection;
	
	public DataBaseConnect(String dbURL,String user,String password) throws ClassNotFoundException,SQLException {
		
		Class.forName("com.mysql.jdbc.Driver");
		this.connection=DriverManager.getConnection(dbURL,user,password);
		
	}
	
	public Connection getConnection()
	{
		return this.connection;
	}
	
	public void CloseConnection() throws SQLException
	{
		
		if(this.connection!=null)
			this.connection.close();
	}
}
