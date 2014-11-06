package project451;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManager {

	@SuppressWarnings("resource")
	public String addUser(String email, String password, String name, String surname, String birthday, String gender){
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
		int gndr = 0;
		if(gender != null && gender.equalsIgnoreCase("female")){
			gndr = 1;
		}
		String[] brthdy = birthday.split("-");
		for(int i=0; i<brthdy.length; i++){
			if(brthdy[i].equals("0")) birthday = null;
		}
        int isBanned = 0;
		try{
			if(birthday != null && gender != null){
		    	ps = con.prepareStatement("INSERT INTO database1.User (email, password, name, surname, birthday, gender, isBanned) VALUES "
		    			+ "(?,?,?,?,?,?,?)");
		        ps.setString(1, email);
		        ps.setString(2, hashedSaltedPass);
		        ps.setString(3, name);
		        ps.setString(4, surname);
		        ps.setDate(5, Date.valueOf(birthday));
				ps.setInt(6, gndr);
		        ps.setInt(7, isBanned);
			}
			else if(birthday != null && gender == null){
		    	ps = con.prepareStatement("INSERT INTO database1.User (email, password, name, surname, birthday, isBanned) VALUES "
		    			+ "(?,?,?,?,?,?)");
		        ps.setString(1, email);
		        ps.setString(2, hashedSaltedPass);
		        ps.setString(3, name);
		        ps.setString(4, surname);
		        ps.setDate(5, Date.valueOf(birthday));
		        ps.setInt(6, isBanned);
			}
			else if(birthday == null && gender != null){
		    	ps = con.prepareStatement("INSERT INTO database1.User (email, password, name, surname, gender, isBanned) VALUES "
		    			+ "(?,?,?,?,?,?)");
		        ps.setString(1, email);
		        ps.setString(2, hashedSaltedPass);
		        ps.setString(3, name);
		        ps.setString(4, surname);
				ps.setInt(5, gndr);
		        ps.setInt(6, isBanned);
			}
			else{	// birthday == null && gender == null
		    	ps = con.prepareStatement("INSERT INTO database1.User (email, password, name, surname, isBanned) VALUES "
		    			+ "(?,?,?,?,?)");
		        ps.setString(1, email);
		        ps.setString(2, hashedSaltedPass);
		        ps.setString(3, name);
		        ps.setString(4, surname);
		        ps.setInt(5, isBanned);
			}
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
	    	ps = con.prepareStatement("SELECT password, user_id FROM database1.User WHERE email=?");
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
		int user_id=0;
		try{
			rs.beforeFirst();
			if(rs.next())
				hashedSaltedPass = rs.getString(1);
				user_id=rs.getInt(2);
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
	
	public String getFullName(int user_id){
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
	    	ps = con.prepareStatement("SELECT name, surname FROM database1.User WHERE user_id=?");
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
		String name="";
		String surname="";
		try{
			rs.beforeFirst();
			if(rs.next()){
				name=rs.getString(1);
				surname=rs.getString(2);
			}
		}
		catch(Exception e){
    		return "Result Processing Error: " + e.toString();
		}
		
		return name + " " + surname;
	}
	
	public String getBirthday(int user_id){
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
	    	ps = con.prepareStatement("SELECT birthday FROM database1.User WHERE user_id=?");
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
		Date birthday = null;
		try{
			rs.beforeFirst();
			if(rs.next()){
				birthday =rs.getDate(1);
			}
		}
		catch(Exception e){
    		return "Result Processing Error: " + e.toString();
		}
		
		return birthday.toString();
	}
	
	public String getGender(int user_id){
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
	    	ps = con.prepareStatement("SELECT gender FROM database1.User WHERE user_id=?");
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
		int gender = 0;
		try{
			rs.beforeFirst();
			if(rs.next()){
				gender =rs.getInt(1);
			}
		}
		catch(Exception e){
    		return "Result Processing Error: " + e.toString();
		}
		String gndr="Male";
		if(gender == 1) gndr="Female";
		
		return gndr;
	}
}
