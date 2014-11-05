import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import project451.LoginManager;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        response.setContentType("text/plain; charset=UTF-8");         
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");  
		LoginManager LogMan = new LoginManager();
  		String msg = LogMan.checkUser(email, pwd); 
  		int user_id=0;
  		if(msg.startsWith("ok")){
  			user_id=Integer.valueOf(msg.substring(2));
  			msg=LogMan.getUsername(Integer.valueOf(msg.substring(2)));
  		} 		
  		HttpSession session = request.getSession();
		session.setAttribute("login_message", msg);
		session.setAttribute("user_id", user_id);
		
		response.getWriter().write(msg);
	}
}
