package project451;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        PasswordManager PassMan = new PasswordManager();
        String hashedSaltedPass = "";
        try{
        	hashedSaltedPass = PassMan.createHash(password);
        }
        catch(Exception e){
			return "Password Hashing Error: " + e.toString();	
        }
        int isBanned = 0;
		try{
	    	ps = con.prepareStatement("INSERT INTO database1.User (email, password, name, surname, birthday, gender, isBanned) VALUES "
	    			+ "(?,?,?,?,?,?,?)");
	        ps.setString(1, email);
	        ps.setString(2, hashedSaltedPass);
	        ps.setString(3, name);
	        ps.setString(4, surname);
	        ps.setDate(5, Date.valueOf(birthday));
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
		return "Success!";
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
	    	ps = con.prepareStatement("SELECT password, name, user_id FROM database1.User WHERE email=?");
	        ps.setString(1, email);
		}
		catch(Exception e){
			return "Query Building Error: " + e.toString();	
		}
		ResultSet rs;
        int count;
		try{
        	rs = ps.executeQuery();
			rs.last();
			count = rs.getRow();
		}
		catch(Exception e){
			return "Query Running Error: " + e.toString();	
		}
        if(count > 1){
        	return "Multiple users with same email!"; //Should never reach here
        }
        else if(count == 0){
        	return "Invalid email address or password! Please try again.";
        }
		String hashedSaltedPass = "";
		String username = "";
		int user_id=0;
		try{
			rs.beforeFirst();
			if(rs.next())
				hashedSaltedPass = rs.getString(1);
				username = rs.getString(2);
				user_id=rs.getInt(3);
		}
		catch(Exception e){
    		return "Result Processing Error: " + e.toString();
		}
        PasswordManager PassMan = new PasswordManager();
        boolean isValid = false;
        try{
        	isValid = PassMan.validatePassword(pwd, hashedSaltedPass);
        }
        catch(Exception e){
			return "Password Hash Validation Error: " + e.toString();	
        }
        if(!isValid){
        	return "Invalid email address or password! Please try again.";
        }
		try {
			con.close();
		} 
		catch (Exception e) {
			return "Database Closing Connection Error: " + e.toString();
		}  
		
		return "ok"+String.valueOf(user_id);
	}
	
	public String getUsername(int user_id){
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
	    	ps = con.prepareStatement("SELECT name FROM database1.User WHERE user_id=?");
	        ps.setInt(1, user_id);
		}
		catch(Exception e){
			return "Query Building Error: " + e.toString();	
		}
		ResultSet rs;
        int count;
		try{
        	rs = ps.executeQuery();
			rs.last();
			count = rs.getRow();
		}
		catch(Exception e){
			return "Query Running Error: " + e.toString();	
		}
		if(count > 1){
        	return "Multiple users with same user id!"; //Should never reach here
        }
        else if(count == 0){
        	return "Invalid user id";
        }
		String username="";
		try{
			rs.beforeFirst();
			if(rs.next())
				username=rs.getString(1);
		}
		catch(Exception e){
    		return "Result Processing Error: " + e.toString();
		}
		return "You've successfully logged in "+username;
       
       
	}
	
}
