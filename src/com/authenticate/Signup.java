package com.authenticate;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//<Brent Ufkes> 13-July-2017
//Signup servlet
public class Signup extends HttpServlet {
	
	Map<String, String> DB = new HashMap<String, String>();
	public static final String SALT = "my-salt-text";
	
	private static final long serialVersionUID = 1L;
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 	//initializing variables
		    String Username = request.getParameter("username");
			String Password = request.getParameter("password");
			String Password2 = request.getParameter("password2");
			String Email = request.getParameter("email");
			
		 	Signup demo = new Signup();
		 
			//Login redirect
			HttpSession session = request.getSession();
			
			if (Password.equals(Password2)) {
				session.setAttribute("username", Username);
				demo.signup(Username, Password, Email);
			}
			else {
				session.setAttribute("signuperror", "Your password and confirmation password do not match.");
			}
			response.sendRedirect("index.jsp");
	 }
	 
	
	 public void signup(String username, String password, String email) {
			String saltedPassword = SALT + password;
			String hashedPassword = generateHash(saltedPassword);
			
	        String url = "jdbc:sqlserver://softwareengineeringuc.database.windows.net:1433;database=SoftwareEngineeringUC;user=ufkesba@softwareengineeringuc;password=Scout!2063;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	        Connection connection = null;
	        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        
	        try {
        	    Class.forName(driver).newInstance();
                connection = DriverManager.getConnection(url);
                String schema = connection.getSchema();

                // Prepared statement to insert data
                String insertSql = "INSERT INTO [User] (Username, Password, Email)"
                		+ " VALUES (?,?,?)";
                System.out.println(insertSql);
				
                //This will need to be changed to PreparedStatement
                try (PreparedStatement statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                	statement.setString(1, username);
                	statement.setString(2, hashedPassword);
                	statement.setString(3, email);

                    int count = statement.executeUpdate();
                    System.out.println("Inserted: " + count + " row(s)");
                }
        }
        catch (Exception e) {
                e.printStackTrace();
        }
			
			
			//Replace this with adding to User Database
			//DB.put(username, hashedPassword);
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