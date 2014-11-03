package project451;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginManager {

	public String addUser(String email, String password, String name, String surname, String birthday, int gender){
		Connection con;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	con = DriverManager.getConnection("jdbc:mysql://titan.cmpe.boun.edu.tr:3306/database1", "project1", "2TJZD32R");
        }
        catch(Exception e){
        	return "Database Opening Connection Error: " + e.toString();	
        }
		PreparedStatement ps;
		try{
	    	ps = con.prepareStatement("SELECT name FROM database1.User WHERE email=?");
	        ps.setString(1, email);
		}
		catch(Exception e){
			return "Query Building Error: " + e.toString();	
		}
        ResultSet rs;
        int count;
        try {
			rs = ps.executeQuery();
			rs.last();
			count = rs.getRow();
			rs.beforeFirst();
        } 
        catch (SQLException e1){
			return "Database Error: " + e1.toString();
		}       
        if(count!=0){
        	return "This email is already registered!";
        }
        int isBanned = 0;
		try{
	    	ps = con.prepareStatement("INSERT INTO database1.User (email, password, name, surname, birthday, gender, isBanned) "
	    			+ "VALUES ('" + email + "', '" + password + "', '" + name + "', '" + surname + "', STR_TO_DATE('" + birthday + "', '%d/%m/%Y'), '" + gender + "', '" + isBanned + "')");
	        ps.setString(1, email);
	        ps.setString(2, password);
	        ps.setString(3, name);
	        ps.setString(4, surname);
	        ps.setString(5, birthday);
	        ps.setInt(6, gender);
	        ps.setInt(7, isBanned);
		}
		catch(Exception e){
			return "Query Building Error: " + e.toString();	
		}
		try{
			ps.executeUpdate();
		}
		catch(Exception e){
			return "Database Insertion Error: " + e.toString();
		}  
		try {
			con.close();
		} 
		catch (Exception e) {
			return "Database Closing Connection Error: " + e.toString();
		}  
		return "Success";
	}
	
	public String checkUser(String email, String pwd){
		
		Connection con;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	con = DriverManager.getConnection("jdbc:mysql://titan.cmpe.boun.edu.tr:3306/database1", "project1", "2TJZD32R");
        }
        catch(Exception e){
        	return "Database Opening Connection Error: " + e.toString();	
        }
		PreparedStatement ps;
		try{
	    	ps = con.prepareStatement("SELECT name FROM database1.User WHERE email=? and password=?");
	        ps.setString(1, email);
	        ps.setString(2, pwd);
		}
		catch(Exception e){
			return "Query Building Error: " + e.toString();	
		}
        ResultSet rs;
        String username = "";
        int count;
        try {
        	rs = ps.executeQuery();
			rs.last();
			count = rs.getRow();
        }
        catch (Exception e) {
			return "Query Running Error: " + e.toString();
		}       
        if(count > 1){
        	return "Multiple users with same email and password!"; //Should never reach here
        }
        else if(count == 0){
        	return "Invalid email address or password! Please try again.";
        }
        else{ 
        	try{
	    		rs.beforeFirst();
	    		if(rs.next()){
	    			username = rs.getString(1);
	    		}
        	}
        	catch (Exception e){
        		return "Result Processing Error: " + e.toString();
        	}
        }
		try {
			con.close();
		} 
		catch (Exception e) {
			return "Database Closing Connection Error: " + e.toString();
		}  
		return "You've successfully logged in, " + username;
	}
	
}
