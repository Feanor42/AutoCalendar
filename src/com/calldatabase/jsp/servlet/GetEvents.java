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

import org.json.JSONObject;
import org.json.JSONArray;


public class GetEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Connect to database
        
		String url = "jdbc:sqlserver://softwareengineeringuc.database.windows.net:1433;database=SoftwareEngineeringUC;user=ufkesba@softwareengineeringuc;password=Scout!2063;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection connection = null;
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

        try {
                connection = DriverManager.getConnection(url);
                String schema = connection.getSchema();

                // Create and execute a SELECT SQL statement.
                String selectSql = "SELECT * FROM Event WHERE UserID=0";

                try {
                	Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(selectSql);
                    //convertToJSON(resultSet);
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
            for (int i = 0; i < rows; i++) {
                JSONObject obj = new JSONObject();
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), resultSet.getObject(i + 1));
                jsonArray.put(obj);
            }
        }
        
        // Show json Array as a string
        System.out.println(jsonArray.toString());
        return jsonArray;
        
    }
}

