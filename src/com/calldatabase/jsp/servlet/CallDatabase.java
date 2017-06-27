package com.calldatabase.jsp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CallDatabase extends HttpServlet {
	static final long serialVersionUID = 1L;

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	// Connect to database
        String url = "jdbc:sqlserver://softwareengineeringuc.database.windows.net:1433;database=SoftwareEngineeringUC;user=ufkesba@softwareengineeringuc;password=Scout!2063;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
        Connection connection = null;
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String queryResult = "";

        try {
        	    Class.forName(driver).newInstance();
                connection = DriverManager.getConnection(url);
                String schema = connection.getSchema();

                // Create and execute a SELECT SQL statement.
                String selectSql = "SELECT * FROM [User]";

                try (Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(selectSql)) {

                            // Print results from select statement
                            queryResult = "First two columns: ";
                            int i = 0;
                            while (resultSet.next())
                            {
                            	if (i > 0) {
                            		queryResult += ", ";
                            	}
                                queryResult += resultSet.getString(1) + " "
                                    + resultSet.getString(2);
                                i++;
                            }
                    }
        }
        catch (Exception e) {
                e.printStackTrace();
        }
        
    	response.setContentType("text/plain");
    	response.setCharacterEncoding("UTF-8");
    	response.getWriter().write(queryResult);
             
        }
}
