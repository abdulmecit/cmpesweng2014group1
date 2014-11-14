package cmpesweng2014.group1.nutty;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cmpesweng2014.group1.nutty.model.User;
import cmpesweng2014.group1.nutty.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller

public class HomeController {

	@Autowired
	private UserService userService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpSession session) {
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
			return "redirect:/";
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
			return new ModelAndView("redirect:/");
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
	public String signUp(@ModelAttribute("user") User u, HttpSession session) {
		/*
		if (u.getEmail().equals("") || u.getPassword().equals("")
				|| u.getName().equals("") || u.getSurname().equals("")) {
			return "redirect:signup";
		}*/
		userService.createUser(u.getEmail(), u.getPassword(),
				u.getName(), u.getSurname(), u.getBirthday(), u.getGender());
		session.setAttribute("isLogged", true);
		return "redirect:/";
	}

	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String signOut(HttpSession session) {
		session.setAttribute("isLogged", false);
		session.setAttribute("user", null);
		session.setAttribute("userName", null);
		return "redirect:/";
	}
}
