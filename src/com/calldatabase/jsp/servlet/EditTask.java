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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//<Brent Ufkes> 13-July-2017
//Update event servlet
public class EditTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//initializing variables
		 	int UserID = 0;
		 	int TaskID = 0;
	        String Title = "";
	        String Description = "";
	        String DateTimeDue = "";
	        String DateTimeStart = "";
	        String DateTimeEnd = "";
	        String Priority = "";
	        String TimeToComplete = "";
	        String AssignDate = "";
		 
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
				TaskID = jObj.getInt("id");
				Title = jObj.getString("title");
				Description = jObj.getString("description");
				DateTimeDue = jObj.getString("dueDate");
				DateTimeStart = jObj.getString("start");
				DateTimeEnd = jObj.getString("end");
				Priority = jObj.getString("priority");
				TimeToComplete = jObj.getString("timeToComplete");
				AssignDate = jObj.getString("assignDate");
				
				System.out.println(jObj.toString());
				
			} catch (JSONException e1) {
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
	                String insertSql = "UPDATE Task SET UserID=?, Title=?, Description=?, "
	                		+ "Priority=?, EstimatedTime=?, DateDue=?, " 
	                		+ "DateTimeStart=?, DateTimeEnd=?, AssignDate=? "
	                		+ " WHERE TaskID=?";
	                System.out.println(insertSql);
					
	                //This will need to be changed to PreparedStatement
	                try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
	                	statement.setInt(1, UserID);
	                	statement.setString(2, Title);
	                	statement.setString(3, Description);
	                	statement.setString(4, Priority);
	                	statement.setString(5, TimeToComplete);
	                	statement.setString(6, DateTimeDue);
	                	statement.setString(7, DateTimeStart);
	                	statement.setString(8, DateTimeEnd);
	                	statement.setString(9, AssignDate);
	                	statement.setInt(10, TaskID);

                     int count = statement.executeUpdate();
                     System.out.println("Updated: " + count + " row(s)");
	                
	                }
	                queryResult = insertSql;
	        }
	        catch (Exception e) {
	                e.printStackTrace();
	        }
	             
	    	
	    	//After event was added, send JSONArray of it back as response
         String selectSql = "SELECT * FROM Task WHERE TaskID=" + TaskID;
         System.out.println(selectSql);

         try {
         	Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSql);
             
             JSONArray array = convertToJSON(resultSet);
             JSONObject object = new JSONObject();
             for(int n = 0; n < array.length(); n++)
             {
                 object = array.getJSONObject(n);
             }
             response.getWriter().write(object.toString());
         }
         catch (Exception e) {
             e.printStackTrace();
         }
	    	
	 }
	
	 public static JSONArray convertToJSON(ResultSet resultSet)
	            throws Exception {
	        JSONArray jsonArray = new JSONArray();
	        while (resultSet.next()) {
	            int rows = resultSet.getMetaData().getColumnCount();
	            JSONObject obj = new JSONObject();
	            for (int i = 0; i < rows; i++) {
	            	String column = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
	            	switch (column) {
	            		case "taskid":  column = "id";
	            		break;
	            		case "title":  column = "title";
	            		break;
	            		case "datetimestart":  column = "start";
	            		break;
	            		case "datetimeend":  column = "end";
	            		break;
	            		case "eventtype":  column = "eventType";
	            		break;
	            		case "description":  column = "description";
	            		break;
	            		case "datedue":  column = "dueDate";
	            		break;	
	            		case "priority":  column = "priority";
	            		break;	
	            		case "estimatedtime":  column = "timeToComplete";
	            		break;	
	            		case "assigndate":  column = "assignDate";
	            		break;	
	            	}
	                obj.put(column, resultSet.getObject(i + 1));
	            }
	            jsonArray.put(obj);
	        }
	        
	        // Show json Array as a string
	        System.out.println(jsonArray.toString());
	        return jsonArray;
	        
	    }
		 
}
