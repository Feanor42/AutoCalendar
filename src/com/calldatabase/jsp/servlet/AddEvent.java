package com.calldatabase.jsp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//<Brent Ufkes> 06-July-2017
//Add event servlet
public class AddEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    	// Connect to database
	        String url = "jdbc:sqlserver://softwareengineeringuc.database.windows.net:1433;database=SoftwareEngineeringUC;user=ufkesba@softwareengineeringuc;password=Scout!2063;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	        Connection connection = null;
	        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        String queryResult = "test";
	        
	        String UserID = "'0'";
	        String EventID = "'1'";
	        int EventType = 1;
	        String Title = "'SampleEvent'";
	        String Description = "'SampleDescription'";
	        String Location = "'Campus'";
	        String DateTimeStart = "'2017-07-28 13:30:00'";
	        String DateTimeEnd = "'2017-07-28 14:30:00'";

	        try {
	        	    Class.forName(driver).newInstance();
	                connection = DriverManager.getConnection(url);
	                String schema = connection.getSchema();

	                // Prepared statement to insert data
	                String insertSql = "INSERT INTO Event (UserID, EventID, EventType, Title, Description, Location, DateTimeStart, DateTimeEnd)"
	                		+ " VALUES ("
	                		+ UserID + ","
	                		+ EventID + ","
	                		+ EventType + ","
	                		+ Title + ","
	                		+ Description + ","
	                		+ Location + ","
	                		+ DateTimeStart + ","
	                		+ DateTimeEnd + ")";
	                System.out.println(insertSql);
					
	                try (Statement statement = connection.createStatement();
	                        ResultSet resultSet = statement.executeQuery(insertSql)) {

	                }
	                queryResult = insertSql;
	        }
	        catch (Exception e) {
	                e.printStackTrace();
	        }
	        
	    	response.setContentType("text/plain");
	    	response.setCharacterEncoding("UTF-8");
	    	response.getWriter().write(queryResult);
	             
	        }
	
}
