package servlets;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InputNameServlet
 */
@WebServlet("/InputNameServlet")
public class InputNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
/*	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Define parameters 
        String name = request.getParameter("name");
        
        //Setup response message
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        
        //Validate input
        if(name.trim().equals("")){
        	response.getWriter().write("Failed: Name Cannot Be Empty");
        	return;
        }
        
	    //Access the database table
        Connection con;
        Statement stmt;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	con = DriverManager.getConnection("jdbc:mysql://titan.cmpe.boun.edu.tr:3306/database1", "project1", "2TJZD32R");
        	stmt = con.createStatement();
        }
        catch(Exception e){
        	response.getWriter().write("Database Opening Connection Error: " + e.toString());
        	return;
        }
        
        //Insert into database
		String query = "INSERT INTO database1.deneme (name) VALUES ('" + name + "')";
		try{
		stmt.executeUpdate(query);
		}
		catch(Exception e){
			response.getWriter().write("Database Insertion Error: " + e.toString());
			return;
		}  

	  //Close all
	  try {
		con.close();
	  } 
	  catch (Exception e) {
      	response.getWriter().write("Database Closing Connection Error: " + e.toString());
      	return;
	  }
	  
	  response.getWriter().write("Success!");
           
      }
	
}
