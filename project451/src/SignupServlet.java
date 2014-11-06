

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project451.LoginManager;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String email = request.getParameter("email");
		 String password = request.getParameter("password");
		 String name = request.getParameter("name");
		 String surname = request.getParameter("surname");
		 String birthday = request.getParameter("birthday");
		 String gender = request.getParameter("gender");
		 		 
		 response.setContentType("text/plain");
	     response.setCharacterEncoding("UTF-8");
		 LoginManager LogMan=new LoginManager();
		 String res=LogMan.addUser(email, password, name, surname, birthday, gender);
		 response.getWriter().write(res); 
	}

}
