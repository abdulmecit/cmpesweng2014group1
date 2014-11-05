package project451;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FoodSelectionManager {

	public String addSelections(int user_id, String[] foodIntolerance, String[] healthConditions, String[] nonPreferred){
		Connection con;
        try{
        	Class.forName("com.mysql.jdbc.Driver");
        	con = DriverManager.getConnection("jdbc:mysql://titan.cmpe.boun.edu.tr:3306/database1", "project1", "2TJZD32R");
        }
        catch(Exception e){
        	return "Database Opening Connection Error: " + e.toString();	
        }        
        int[] foodIntoleranceIds = new int[foodIntolerance.length];
        for(int i=0; i<foodIntolerance.length; i++){
			PreparedStatement ps;
			try{
		    	ps = con.prepareStatement("SELECT fs_id FROM database1.FoodSelection WHERE fs_name=?");
		        ps.setString(1, foodIntolerance[i]);
			}
			catch(Exception e){
				return "Query Building Error: " + e.toString();	
			}
	        ResultSet rs;
	        try {
				rs = ps.executeQuery();
	        } 
	        catch (Exception e){
				return "Query Running Error: " + e.toString();
			}
	        try {
	        	if(rs.next()){
	        		foodIntoleranceIds[i] = rs.getInt(1);
	        	}
	        }
	        catch (Exception e){
	        	return "Result Processing Error: " + e.toString(); 
	        } 
        }
        int[] healthConditionsIds = new int[healthConditions.length];
        for(int i=0; i<healthConditions.length; i++){
			PreparedStatement ps;
			try{
		    	ps = con.prepareStatement("SELECT fs_id FROM database1.FoodSelection WHERE fs_name=?");
		        ps.setString(1, healthConditions[i]);
			}
			catch(Exception e){
				return "Query Building Error: " + e.toString();	
			}
	        ResultSet rs;
	        try {
				rs = ps.executeQuery();
	        } 
	        catch (Exception e){
				return "Query Running Error: " + e.toString();
			}
	        try {
	        	if(rs.next()){
	        		healthConditionsIds[i] = rs.getInt(1);
	        	}
	        }
	        catch (Exception e){
	        	return "Result Processing Error: " + e.toString(); 
	        } 
        }
        int[] nonPreferredIds = new int[nonPreferred.length];
        for(int i=0; i<nonPreferred.length; i++){
			PreparedStatement ps;
			try{
		    	ps = con.prepareStatement("SELECT ing_id FROM database1.Ingredient WHERE ing_name=?");
		        ps.setString(1, nonPreferred[i]);
			}
			catch(Exception e){
				return "Query Building Error: " + e.toString();	
			}
	        ResultSet rs;
	        try {
				rs = ps.executeQuery();
	        } 
	        catch (Exception e){
				return "Query Running Error: " + e.toString();
			}
	        try {
	        	if(rs.next()){
	        		nonPreferredIds[i] = rs.getInt(1);
	        	}
	        }
	        catch (Exception e){
	        	return "Result Processing Error: " + e.toString(); 
	        } 
        }
        for(int i=0; i<foodIntoleranceIds.length; i++){
			PreparedStatement ps;
			try{
		    	ps = con.prepareStatement("INSERT INTO database1.HasSelection (user_id, fs_id) VALUES (?,?)");
		        ps.setInt(1, user_id);
		        ps.setInt(2, foodIntoleranceIds[i]);
			}
			catch(Exception e){
				return "Query Building Error: " + e.toString();	
			}	  
	        try {
				ps.executeUpdate();
	        } 
	        catch (Exception e){
				return "Database Insertion Error: " + e.toString();
			}
        }
        for(int i=0; i<healthConditionsIds.length; i++){
			PreparedStatement ps;
			try{
		    	ps = con.prepareStatement("INSERT INTO database1.HasSelection (user_id, fs_id) VALUES (?,?)");
		        ps.setInt(1, user_id);
		        ps.setInt(2, healthConditionsIds[i]);
			}
			catch(Exception e){
				return "Query Building Error: " + e.toString();	
			}	  
	        try {
				ps.executeUpdate();
	        } 
	        catch (Exception e){
				return "Database Insertion Error: " + e.toString();
			}
        }
        for(int i=0; i<nonPreferredIds.length; i++){
			PreparedStatement ps;
			try{
		    	ps = con.prepareStatement("INSERT INTO database1.Unprefer (user_id, ing_id) VALUES (?,?)");
		        ps.setInt(1, user_id);
		        ps.setInt(2, nonPreferredIds[i]);
			}
			catch(Exception e){
				return "Query Building Error: " + e.toString();	
			}	  
	        try {
				ps.executeUpdate();
	        } 
	        catch (Exception e){
				return "Database Insertion Error: " + e.toString();
			}
        }
		try {
			con.close();
		} 
		catch (Exception e) {
			return "Database Closing Connection Error: " + e.toString();
		}  
		return "Success!";
	}
}
