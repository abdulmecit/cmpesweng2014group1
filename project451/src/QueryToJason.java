

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class QueryToJason
 */
@WebServlet("/QueryToJason")
public class QueryToJason extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryToJason() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("query");
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Connection con;
        Statement stmt; 
		try {
			con = DriverManager.getConnection("jdbc:mysql://titan.cmpe.boun.edu.tr:3306/database1", "project1", "2TJZD32R");
			stmt = con.createStatement(); 
		} catch (SQLException e) {
			response.getWriter().write("Database Opening Connection Error: " + e.toString());
        	return;
		}
        
        ResultSet rs;
        ResultSetMetaData rsmd;
		try {
			rs = stmt.executeQuery(query);
			rsmd = rs.getMetaData();
			JSONArray jsonArray = new JSONArray();
	        while (rs.next()) {
	            int total_rows = rsmd.getColumnCount();
	            JSONObject obj = new JSONObject();
	            for (int i = 0; i < total_rows; i++) {
	                obj.put(rsmd.getColumnLabel(i + 1)
	                        .toLowerCase(), rs.getObject(i + 1));
	            }
	            jsonArray.put(obj);
	        }
	        response.getWriter().write(jsonArray.toString());
		} catch (SQLException e) {
			response.getWriter().write("Database Selection Error: " + e.toString());
			return;
		} catch (JSONException e) {
			response.getWriter().write("JSON Parsing Error: " + e.toString());
			return;
		}
	}
}
