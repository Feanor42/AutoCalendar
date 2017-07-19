package com.calldatabase.jsp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.json.JSONArray;


public class GetEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		int userID = 0;
		
		if (session.getAttribute("id") != null) {
			userID = (int) session.getAttribute("id");
		}
		
		System.out.println("sessionid: " + userID);
		
		// Connect to database
		String url = "jdbc:sqlserver://softwareengineeringuc.database.windows.net:1433;database=SoftwareEngineeringUC;user=ufkesba@softwareengineeringuc;password=Scout!2063;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection connection = null;
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

        try {
        		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(url);
                String schema = connection.getSchema();

                // Create and execute a SELECT SQL statement.
                String selectSql = "SELECT * FROM Event WHERE UserID=" + userID;

                try {
                	Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(selectSql);
                    response.getWriter().write(convertToJSON(resultSet).toString());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
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
            	}
                obj.put(column, resultSet.getObject(i + 1));
            }
            jsonArray.put(obj);
        }
        
        // Show json Array as a string
        return jsonArray;
        
    }
}

