

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import project451.FoodSelectionManager;

/**
 * Servlet implementation class FoodIntoleranceServlet
 */
@WebServlet("/FoodIntoleranceServlet")
public class FoodIntoleranceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String foodIntolerance[] = request.getParameterValues("Food Intolerance and Allergies"); 
		
		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    
	    String res = "";
		if(foodIntolerance == null){
			res = "Nothing selected.";
		}
		else{	    
		    FoodSelectionManager foodSel=new FoodSelectionManager();
		    HttpSession session = request.getSession(false);
		    try{
			    int user_id = Integer.valueOf(session.getAttribute("user_id").toString());
			    res = foodSel.addFoodIntolerance(user_id, foodIntolerance); 
		    }
		    catch(Exception e){
		    	res = "Not logged in.";
		    }
		}
		request.setAttribute("res", res);
	    request.getRequestDispatcher("profilinfo.jsp").forward(request, response);
	}

}
