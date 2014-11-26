package cmpesweng2014.group1.nutty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cmpesweng2014.group1.nutty.model.Message;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.User;
import cmpesweng2014.group1.nutty.service.RecipeService;
import cmpesweng2014.group1.nutty.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller

public class HomeController {

	@Autowired
	private UserService userService;
	private RecipeService recipeService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session) {
		return "redirect:index";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpSession session) {
			return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLogin(Model model, HttpSession session) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (isLogged) {
			return "redirect:index";
		} 
		else {
			User u = new User();
			model.addAttribute("user", u);
			return "login";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			@RequestParam(value = "inputEmail", required = true) String email,
			@RequestParam(value = "inputPassword", required = true) String password,
			RedirectAttributes redirectAttrs, HttpSession session) {
		if (email.equals("") || password.equals("")) {
			redirectAttrs.addFlashAttribute("message", new Message(0, null, "Email and password fields cannot be empty!")); 
			return "redirect:login";
		}
		User u = userService.canLogin(email, password);
		if (u != null) {
			session.setAttribute("user", u);
			session.setAttribute("isLogged", true);
			redirectAttrs.addFlashAttribute("message", new Message(1, null, "You have successfully logged in, " + u.getName() + "."));
			return "redirect:success";
		} 
		else {
			redirectAttrs.addFlashAttribute("message", new Message(0, null, "Invalid email address or password."));
			return "redirect:login";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loginREST")
	public Message loginREST(
			@RequestParam(value = "inputEmail", required = true) String email,
			@RequestParam(value = "inputPassword", required = true) String password,
			HttpSession session) {
		if (email.equals("") || password.equals("")) {
			return new Message(0, null, "Email and password fields cannot be empty!"); 
		}
		User u = userService.canLogin(email, password);
		if (u != null) {
			return new Message(1, u, "You have successfully logged in, " + u.getName() + "."); 
		} 
		else {
			return new Message(0, null, "Invalid email address or password.");
		}
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String viewSignup(Model model, HttpSession session) {
		User u = new User();
		model.addAttribute("user", u);
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(
			@RequestParam(value = "inputName", required = true) String name,
			@RequestParam(value = "inputSurname", required = true) String surname,
			@RequestParam(value = "inputEmail", required = true) String email,
			@RequestParam(value = "inputPassword1", required = true) String password,
			@RequestParam(value = "birthday_year", required = false) String year,
			@RequestParam(value = "birthday_month", required = false) String month,
			@RequestParam(value = "birthday_day", required = false) String day,
			@RequestParam(value = "gender", required = false) Integer gender,
			RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
						
		if (name.equals("") || surname.equals("") || email.equals("") || password.equals("")) {
			redirectAttrs.addFlashAttribute("message", new Message(0, null, "Name, surname, email and password fields cannot be empty!"));
			return "redirect:signup";
		}
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date birthdate = formatter.parse(year + "-" + month + "-" + day);
		java.sql.Date birthday = new java.sql.Date(birthdate.getTime());
				
		User u = userService.createUser(email, password, name, surname, birthday, gender);
		
		if (u != null) {
			session.setAttribute("user", u);
			session.setAttribute("isLogged", true);
					
			redirectAttrs.addFlashAttribute("message", new Message(1, null, "You've successfully signed up, " + u.getName() + "."));
			return "redirect:success";
		} 
		else {
			redirectAttrs.addFlashAttribute("message",new Message(0, null, "Signup failed."));
			return "redirect:signup";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/signupREST")
	public Message signupREST(
			@RequestParam(value = "inputName", required = true) String name,
			@RequestParam(value = "inputSurname", required = true) String surname,
			@RequestParam(value = "inputEmail", required = true) String email,
			@RequestParam(value = "inputPassword1", required = true) String password,
			@RequestParam(value = "birthday_year", required = false) String year,
			@RequestParam(value = "birthday_month", required = false) String month,
			@RequestParam(value = "birthday_day", required = false) String day,
			@RequestParam(value = "gender", required = false) Integer gender,
			HttpSession session) throws ParseException {
						
		if (name.equals("") || surname.equals("") || email.equals("") || password.equals("")) {
			return new Message(0, null, "Name, surname, email and password fields cannot be empty!");
		}
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date birthdate = formatter.parse(year + "-" + month + "-" + day);
		java.sql.Date birthday = new java.sql.Date(birthdate.getTime());
				
		User u = userService.createUser(email, password, name, surname, birthday, gender);
		
		if (u != null) {		
			return new Message(1, u, "You've successfully signed up, " + u.getName() + ".");
		} 
		else {
			return new Message(0, null, "Signup failed."); 
		}
	}
	
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String viewSignupSuccess(HttpSession session) {
		return "success";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String signOut(HttpSession session) {
		session.setAttribute("isLogged", false);
		session.setAttribute("user", null);
		return "redirect:index";
	}
	
	@RequestMapping(value = "/basicsearchrecipe", method = RequestMethod.POST)
	@ResponseBody
	public boolean basicSearchRecipe(
			@RequestParam(value = "search", required = true) String search,
			HttpSession session){

		List<Recipe> recipes = (List<Recipe>) recipeService.getRecipeDao().searchRecipeByName(search);//userService.getUserDao().updateUser(u);
		
		if (recipes.size() == 0){
			return false;
		}
		session.setAttribute("recipes", recipes);
		return true;
	}
	
}
