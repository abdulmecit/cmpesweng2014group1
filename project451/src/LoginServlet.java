import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
        //Setup response message
        response.setContentType("text/html; charset=UTF-8");
                
        String name = request.getParameter("username");
        String pwd = request.getParameter("password");
        
        PrintWriter out = response.getWriter();
		LoginManager LogMan = new LoginManager();
        
        if(LogMan.checkUser(name, pwd)){
            RequestDispatcher rs = request.getRequestDispatcher("loginsuccess.jsp");
            rs.forward(request, response);
        }
        else{
           out.println("Username or Password is Incorrect");
           RequestDispatcher rs = request.getRequestDispatcher("login.jsp");
           rs.include(request, response);
        }
	}
}
