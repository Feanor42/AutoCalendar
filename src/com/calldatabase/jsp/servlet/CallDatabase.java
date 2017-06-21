package com.calldatabase.jsp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.lang.Object;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CallDatabase extends HttpServlet {
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
                System.out.println("Successful connection - Schema: " + schema);

                System.out.println("Query data example:");
                System.out.println("=========================================");

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
        
        //Print new HTML screen
        PrintWriter out = response.getWriter();
        out.println (
                  "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" +" +
                      "http://www.w3.org/TR/html4/loose.dtd\">\n" +
                  "<html> \n" +
                    "<head> \n" +
                      "<meta http-equiv=\"Content-Type\" content=\"text/html; " +
                        "charset=ISO-8859-1\"> \n" +
                      "<title> CallDatabase </title> \n" +
                    "</head> \n" +
                    "<body> <div align='center'> \n" +
                      "<style= \"font-size=\"12px\" color='black'\"" + "\">" +
                        queryResult + " <br> " +
                    "</font></body> \n" +
                  "</html>" 
                );      
        }
}
