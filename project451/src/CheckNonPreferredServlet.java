

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import project451.FoodSelectionManager;

/**
 * Servlet implementation class CheckNonPreferredServlet
 */
@WebServlet("/CheckNonPreferredServlet")
public class CheckNonPreferredServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckNonPreferredServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
	    HttpSession session = request.getSession(false);
	    int user_id = Integer.valueOf(session.getAttribute("user_id").toString());
	    FoodSelectionManager FoodSel = new FoodSelectionManager();
	    String res = FoodSel.getNonPreferred(user_id);
		response.getWriter().write(res);
	}

}
