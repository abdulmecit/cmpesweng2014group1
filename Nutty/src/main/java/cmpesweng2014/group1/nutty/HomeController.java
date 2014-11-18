package cmpesweng2014.group1.nutty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cmpesweng2014.group1.nutty.model.Message;
import cmpesweng2014.group1.nutty.model.User;
import cmpesweng2014.group1.nutty.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller

public class HomeController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session) {
		return "redirect:index";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home(Model model, HttpSession session) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (isLogged) {
			return "index";
		} else {
			return "redirect:login";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewSignin(Model model,
			@ModelAttribute("user") Object command, HttpSession session) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (isLogged) {
			return "redirect:index";
		} else {
			User u = new User();
			model.addAttribute("user", u);
			return "login";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView signIn(
			@RequestParam(value = "inputEmail", required = true) String email,
			@RequestParam(value = "inputPassword", required = true) String password,
			HttpServletRequest request, HttpSession session) {
		if (email.equals("") || password.equals("")) {
			return new ModelAndView("redirect:login");
		}
		User u = userService.canLogin(email, password);
		if (u != null) {
			session.setAttribute("user", u);
			session.setAttribute("userName", u.getName());
			session.setAttribute("isLogged", true);
			return new ModelAndView("redirect:index");
		} else {
			return new ModelAndView("redirect:login");
		}
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String viewSignup(Model model,
			@ModelAttribute("user") Object command, HttpSession session) {
		User u = new User();
		model.addAttribute("user", u);
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public @ResponseBody ModelAndView signUp(
			@RequestParam(value = "inputName", required = true) String name,
			@RequestParam(value = "inputSurname", required = true) String surname,
			@RequestParam(value = "inputEmail", required = true) String email,
			@RequestParam(value = "inputPassword1", required = true) String password,
			@RequestParam(value = "birthday_year", required = false) String year,
			@RequestParam(value = "birthday_month", required = false) String month,
			@RequestParam(value = "birthday_day", required = false) String day,
			@RequestParam(value = "gender", required = false) Integer gender,
			HttpServletRequest request, HttpSession session) throws ParseException {
				
		if (name.equals("") || surname.equals("") || email.equals("") || password.equals("")) {
			return new ModelAndView("redirect:signin");
		}
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date birthdate = formatter.parse(year + "-" + month + "-" + day);
		java.sql.Date birthday = new java.sql.Date(birthdate.getTime());
		
		System.out.println(email + " " + password + " " + name + " " + surname + " " + birthday + " " + gender);
		
		User u = userService.createUser(email, password, name, surname, birthday, gender);

		if (u != null) {
			session.setAttribute("user", u);
			session.setAttribute("userName", u.getName());
			session.setAttribute("isLogged", true);
			
			
			Message msg = new Message();
			msg.setIsSuccess(1);
			msg.setMessage("You've successfully signed up!");
			//String msg = "Hey you!";
			return new ModelAndView("signupsuccess", "message", msg);
		} else {
			return new ModelAndView("redirect:signin");
		}
	}
	
	@RequestMapping(value = "/signupsuccess", method = RequestMethod.GET)
	public String viewSignupSuccess(HttpSession session) {
		return "signupsuccess";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String signOut(HttpSession session) {
		session.setAttribute("isLogged", false);
		session.setAttribute("user", null);
		session.setAttribute("userName", null);
		return "redirect:index";
	}
	
	/*
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	*/
}
