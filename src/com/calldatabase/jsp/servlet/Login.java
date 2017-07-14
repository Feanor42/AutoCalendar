package com.calldatabase.jsp.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// <Brent Ufkes> 13-July-2017
// Login servlet
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 System.out.println("Login attempt");
		 //response.getWriter().write("Success");
		 // session.username
		 HttpSession session = request.getSession();
		 String username = (String)request.getParameter("username");
		 session.setAttribute("username", username);
		 
		 response.sendRedirect("index.jsp");
		    
	 }
	
}
