package com.calldatabase.jsp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//<Brent Ufkes> 09-July-2017
//Delete event servlet
public class DeleteEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    	// Connect to database
	        String url = "jdbc:sqlserver://softwareengineeringuc.database.windows.net:1433;database=SoftwareEngineeringUC;user=ufkesba@softwareengineeringuc;password=Scout!2063;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	        Connection connection = null;
	        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        String queryResult = "";
	        
	        String UserID = "test23";
	        String EventID = "1";
	        int EventType = 1;
	        String Title = "SampleEvent";
	        String Description = "SampleDescription";
	        String Location = "Campus";
	        Timestamp DateTimeStart = Timestamp.valueOf("2017-07-28 12:30:00");
	        Timestamp DateTimeEnd = Timestamp.valueOf("2017-07-28 14:30:00");

	        try {
	        	    Class.forName(driver).newInstance();
	                connection = DriverManager.getConnection(url);
	                String schema = connection.getSchema();

	                // Prepared statement to insert data
	                String insertSql = "Delete Event"
	                		+ " WHERE CONVERT(VARCHAR, EventID)=?";
	                System.out.println(insertSql);
					
	                //This will need to be changed to PreparedStatement
	                try (PreparedStatement statement = connection.prepareStatement(insertSql)) {

	                	statement.setString(1, EventID);

                        int count = statement.executeUpdate();
                        System.out.println("Deleted: " + count + " row(s)");
	                
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
