package com.authenticate;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//<Brent Ufkes> 18-July-2017
//Login servlet
public class Login extends HttpServlet {
	
	public static final String SALT = "my-salt-text";
	
	private static final long serialVersionUID = 1L;
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 	//initializing variables
		    String Username = request.getParameter("username");
			String Password = request.getParameter("password");
			
		 	Login demo = new Login();
		 
			//Login redirect
			HttpSession session = request.getSession();
			
			//demo.login(Username, Password);
			
			if (demo.login(Username, Password, session) == true) {
				session.setAttribute("username", Username);
			}
			else {
				session.setAttribute("loginerror", "Invalid username/password combination.");
			}
			response.sendRedirect("index.jsp");
	 }
	 
	 
	public Boolean login(String username, String password, HttpSession session) {
			Boolean isAuthenticated = false;

			// remember to use the same SALT value use used while storing password
			// for the first time.
			String saltedPassword = SALT + password;
			String hashedPassword = generateHash(saltedPassword);
			int userID = 0;
			String storedPasswordHash = "";
			
	    	// Connect to database
	        String url = "jdbc:sqlserver://softwareengineeringuc.database.windows.net:1433;database=SoftwareEngineeringUC;user=ufkesba@softwareengineeringuc;password=Scout!2063;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	        Connection connection = null;
	        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        
	        try {
        	    Class.forName(driver).newInstance();
                connection = DriverManager.getConnection(url);
                String schema = connection.getSchema();
				
             // Create and execute a SELECT SQL statement.
                String selectSql = "SELECT * FROM [User] WHERE CONVERT(VARCHAR,Username)='" + username + "'";
                System.out.println(selectSql);

                try {
                	Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(selectSql);
                    
                    while(resultSet.next()){
                    	userID = resultSet.getInt("ID");
                    	storedPasswordHash = resultSet.getString("Password");
                    	session.setAttribute("id", userID);
                    	
                    }
                    System.out.println("[ID]: " + userID);
                    System.out.println("hashed: " + hashedPassword);
                    System.out.println("DB: " + storedPasswordHash);
                    
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
        }
        catch (Exception e) {
                e.printStackTrace();
        }
	        
			
		if(hashedPassword.equals(storedPasswordHash)){
			isAuthenticated = true;
		}else{
			isAuthenticated = false;
		}
		return isAuthenticated;
	}

	public static String generateHash(String input) {
			StringBuilder hash = new StringBuilder();

			try {
				MessageDigest sha = MessageDigest.getInstance("SHA-1");
				byte[] hashedBytes = sha.digest(input.getBytes());
				char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
						'a', 'b', 'c', 'd', 'e', 'f' };
				for (int idx = 0; idx < hashedBytes.length; ++idx) {
					byte b = hashedBytes[idx];
					hash.append(digits[(b & 0xf0) >> 4]);
					hash.append(digits[b & 0x0f]);
				}
			} catch (NoSuchAlgorithmException e) {
				// handle error here.
			}

			return hash.toString();
	}
	
}