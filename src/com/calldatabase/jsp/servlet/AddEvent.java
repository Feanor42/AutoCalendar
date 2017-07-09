package com.calldatabase.jsp.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;




//<Brent Ufkes> 06-July-2017
//Add event servlet
public class AddEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 	//initializing variables
		 	int UserID = 0;
	        String EventID = "1";
	        int EventType = 1;
	        String Title = "SampleEvent";
	        String Description = "SampleDescription";
	        String Location = "Campus";
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
				//Location = jObj.getString("location");
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
	                String insertSql = "INSERT INTO Event (UserID, EventType, Title, Description, DateTimeStart, DateTimeEnd)"
	                		+ " VALUES (?,?,?,?,?,?)";
	                System.out.println(insertSql);
					
	                //This will need to be changed to PreparedStatement
	                try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
	                	statement.setInt(1, UserID);
	                	//statement.setString(2, EventID);
	                	statement.setInt(2, EventType);
	                	statement.setString(3, Title);
	                	statement.setString(4, Description);
	                	//statement.setString(5, Location);
	                	statement.setString(5, DateTimeStart);
	                	statement.setString(6, DateTimeEnd);

                        int count = statement.executeUpdate();
                        System.out.println("Inserted: " + count + " row(s)");
	                
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
