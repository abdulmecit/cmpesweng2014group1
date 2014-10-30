package project451;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginManager {

	public String addUser(String email, String password){
		Connection con;
        Statement stmt;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	con = DriverManager.getConnection("jdbc:mysql://titan.cmpe.boun.edu.tr:3306/database1", "project1", "2TJZD32R");
        	stmt = con.createStatement();
        }
        catch(Exception e){
        	return "Database Opening Connection Error: " + e.toString();	
        }
        ResultSet rs;
        int count;
        try {
			rs = stmt.executeQuery("SELECT * FROM database1.Users WHERE email='" + email + "'");
			rs.last();
			count = rs.getRow();
			rs.beforeFirst();
        } catch (SQLException e1) {
			// TODO Auto-generated catch block
			return "Database Error: " + e1.toString();
		}       
        if(count!=0){
        	return "This email is already registered!";
        }
        String username="deneme";
        String query = "INSERT INTO database1.Users (email, password, username) VALUES ('" + email + "', '" + password + "', '" + username + "')";
		try{
		stmt.executeUpdate(query);
		}
		catch(Exception e){
			return "Database Insertion Error: " + e.toString();
		}  
		//Close all
		try {
			con.close();
		} 
		catch (Exception e) {
			return "Database Closing Connection Error: " + e.toString();
		}  
		return "Success";
	}
	
	public boolean checkUser(String name, String pwd){
		
		boolean result = false;
		
		try{
	    	Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://titan.cmpe.boun.edu.tr:3306/database1", "project1", "2TJZD32R");
			PreparedStatement ps = con.prepareStatement("SELECT * FROM database1.Users WHERE username=? and password=?");
	        ps.setString(1, name);
	        ps.setString(2, pwd);
	        ResultSet rs = ps.executeQuery();
	        result = rs.next();
			con.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
}
