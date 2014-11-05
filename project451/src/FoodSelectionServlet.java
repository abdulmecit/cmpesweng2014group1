

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FoodSelectionServlet
 */
@WebServlet("/FoodSelectionServlet")
public class FoodSelectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FoodSelectionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String foodIntolerance[] = request.getParameterValues("foodIntolerance"); 
		String healthCondition[] = request.getParameterValues("healthCondition"); 
		String nonPrefered[]= request.getParameterValues("nonPrefered"); 
		
		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    FoodSelectionManager foodSel=new FoodSelectionManager();
	    HttpSession session = request.getSession(false);
	    int user_id=Integer.valueOf(session.getAttribute("user_id").toString());
	    String res=foodSel.addSelection(user_id,foodIntolerance, healthCondition, nonPrefered);    
	    
	}

}
