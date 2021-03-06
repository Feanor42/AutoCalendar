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
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




//<Brent Ufkes> 06-July-2017
//Add event servlet
public class AddEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	
		 	HttpSession session = request.getSession(true);
		 	
		 	//initializing variables
		 	int UserID = (int) session.getAttribute("id");
		 	int EventID = 0;
	        int EventType = 1;
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
				Title = jObj.getString("title");
				Description = jObj.getString("description");
				DateTimeStart = jObj.getString("start");
				DateTimeEnd = jObj.getString("end");
				
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
	                String insertSql = "INSERT INTO Event (UserID, EventType, Title, Description, DateTimeStart, DateTimeEnd)"
	                		+ " VALUES (?,?,?,?,?,?)";
	                System.out.println(insertSql);
					
	                //This will need to be changed to PreparedStatement
	                try (PreparedStatement statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
	                	statement.setInt(1, UserID);
	                	statement.setInt(2, EventType);
	                	statement.setString(3, Title);
	                	statement.setString(4, Description);
	                	statement.setString(5, DateTimeStart);
	                	statement.setString(6, DateTimeEnd);

                        int count = statement.executeUpdate();
                        System.out.println("Inserted: " + count + " row(s)");
                        
                        //After Query, save the auto generated EventID to be sent back
                        ResultSet rs = statement.getGeneratedKeys();
                        if (rs.next()) {
                            EventID=rs.getInt(1);   
                                    System.out.println("Auto Generated Primary Key " + EventID); 
                         }
       
	                }
	        }
	        catch (Exception e) {
	                e.printStackTrace();
	        }
	             
	    	
	    	//After event was added, send JSONArray of it back as response
            String selectSql = "SELECT * FROM Event WHERE EventID=" + EventID;
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
	            		case "eventid":  column = "id";
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
	            		case "userid":  column = "userid";
	            		break;
	            	}
	                obj.put(column, resultSet.getObject(i + 1));
	            }
	            jsonArray.put(obj);
	        }
	        
	        // Show json Array as a string
	        return jsonArray;
	        
	    }
}
