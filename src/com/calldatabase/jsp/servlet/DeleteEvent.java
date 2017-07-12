package com.calldatabase.jsp.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

//<Brent Ufkes> 09-July-2017
//Delete event servlet
public class DeleteEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 	//initializing variables
		 	int UserID = 0;
	        String Title = "";
	        String Description = "";
	        String DateTimeStart = "";
	        String DateTimeEnd = "";
		 
	        //building json object
		 	StringBuilder sb = new StringBuilder();
		    BufferedReader br = request.getReader();
		    String str;
		    
		    JSONObject jObj;
		    
		    
		    //updating parameters
		    while( (str = br.readLine()) != null ){
		        sb.append(str);
		    }    
		    
		    try {
				jObj = new JSONObject(sb.toString());
				UserID = 0;
				Title = jObj.getString("title");
				Description = jObj.getString("description");
				DateTimeStart = jObj.getString("start");
				DateTimeEnd = jObj.getString("end");
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 
	    	// Connect to database
	        String url = "jdbc:sqlserver://softwareengineeringuc.database.windows.net:1433;database=SoftwareEngineeringUC;user=ufkesba@softwareengineeringuc;password=Scout!2063;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	        Connection connection = null;
	        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        String queryResult = "";
	        

	        try {
	        	    Class.forName(driver).newInstance();
	                connection = DriverManager.getConnection(url);
	                String schema = connection.getSchema();

	                // Prepared statement to insert data
	                String deleteSql = "Delete Event"
	                		+ " WHERE DateTimeStart='" + DateTimeStart + "' AND DateTimeEnd='" + DateTimeEnd + "'";
	                System.out.println(deleteSql);
					
	                try {
	                	Statement statement = connection.createStatement();
	                    ResultSet resultSet = statement.executeQuery(deleteSql);
	                    //response.getWriter().write(convertToJSON(resultSet).toString());
	                }
	                catch (Exception e) {
	                    e.printStackTrace();
	                }
	        }
	        catch (Exception e) {
	                e.printStackTrace();
	        }
	        
//	    	response.setContentType("text/plain");
//	    	response.setCharacterEncoding("UTF-8");
//	    	response.getWriter().write(queryResult);
	             
	        }
	
}
