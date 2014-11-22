package cmpesweng2014.group1.nutty.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cmpesweng2014.group1.nutty.model.Message;
import cmpesweng2014.group1.nutty.model.User;
import cmpesweng2014.group1.nutty.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/homesettings", method = RequestMethod.GET)
	public String viewHomeSettings(Model model, HttpSession session) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (isLogged) {
			User u = (User) session.getAttribute("user");
			model.addAttribute("user", u);
			return "homesettings";
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/homesettings/changeName", method = RequestMethod.POST)
	public String changeName(
			@RequestParam(value = "inputName", required = true) String name,
			RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
		Message msg = new Message();
		if (name.equals("")) {
			msg.setIsSuccess(0);
			msg.setMessage("Name cannot be empty!");
			return "redirect:/user/homesettings";
		}		
		User u = (User) session.getAttribute("user");
		session.setAttribute("userName", name);
		u.setName(name);
		UserService service = new UserService();
		service.getUserDao().updateUser(u);
		msg.setIsSuccess(1);
		msg.setMessage("You've successfully changed your name!");
			
		/*		
		Gson gson = new Gson();
		String msgJSON = gson.toJson(msg);
		*/		
		
		redirectAttrs.addFlashAttribute("message", msg);

		return "redirect:/user/homesettings";	
	}
	
	@RequestMapping(value = "/homesettings/changeSurname", method = RequestMethod.POST)
	public String changeSurname(
			@RequestParam(value = "inputSurname", required = true) String surname,
			RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
		
		Message msg = new Message();
				
		if (surname.equals("")) {
			msg.setIsSuccess(0);
			msg.setMessage("Surname cannot be empty!");
			return "redirect:/user/homesettings";
		}
				
		User u = (User) session.getAttribute("user");
		u.setSurname(surname);
		UserService service = new UserService();
		service.getUserDao().updateUser(u);
		msg.setIsSuccess(1);
		msg.setMessage("You've successfully changed your surname!");
			
		/*		
		Gson gson = new Gson();
		String msgJSON = gson.toJson(msg);
		*/		
		
		redirectAttrs.addFlashAttribute("message", msg);

		return "redirect:/user/homesettings";	
	}
	
	@RequestMapping(value = "/homesettings/changeEmail", method = RequestMethod.POST)
	public String changeEmail(
			@RequestParam(value = "inputEmail", required = true) String email,
			RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
		
		Message msg = new Message();
				
		if (email.equals("")) {
			msg.setIsSuccess(0);
			msg.setMessage("Email cannot be empty!");
			return "redirect:/user/homesettings";
		}
				
		User u = (User) session.getAttribute("user");
		u.setEmail(email);
		UserService service = new UserService();
		service.getUserDao().updateUser(u);
		msg.setIsSuccess(1);
		msg.setMessage("You've successfully changed your email!");
			
		/*		
		Gson gson = new Gson();
		String msgJSON = gson.toJson(msg);
		*/		
		
		redirectAttrs.addFlashAttribute("message", msg);

		return "redirect:/user/homesettings";	
	}
	
	@RequestMapping(value = "/homesettings/changePassword", method = RequestMethod.POST)
	public String changePassword(
			@RequestParam(value = "inputPassword1", required = true) String password,
			RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
		
		Message msg = new Message();
				
		if (password.equals("")) {
			msg.setIsSuccess(0);
			msg.setMessage("Password cannot be empty!");
			return "redirect:/user/homesettings";
		}
				
		User u = (User) session.getAttribute("user");
		u.setPassword(password);
		UserService service = new UserService();
		service.getUserDao().updateUser(u);
		msg.setIsSuccess(1);
		msg.setMessage("You've successfully changed your password!");
			
		/*		
		Gson gson = new Gson();
		String msgJSON = gson.toJson(msg);
		*/		
		
		redirectAttrs.addFlashAttribute("message", msg);

		return "redirect:/user/homesettings";	
	}
	
	@RequestMapping(value = "/checkPassword", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkPassword(
			String password,
			HttpSession session){
				
		if (password.equals("")) {
			return false;
		}
				
		User u = (User) session.getAttribute("user");
		String realPassword = u.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(encoder.matches(password, realPassword)){
			return true;
		}else{
			return false;
		}
			
		/*		
		Gson gson = new Gson();
		String msgJSON = gson.toJson(msg);
		*/		
	}
	
	@RequestMapping(value = "/homesettings/changeBirthday", method = RequestMethod.POST)
	public String changeBirthday(
			@RequestParam(value = "birthday_year", required = false) String year,
			@RequestParam(value = "birthday_month", required = false) String month,
			@RequestParam(value = "birthday_day", required = false) String day,
			RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
		
		Message msg = new Message();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date birthdate = formatter.parse(year + "-" + month + "-" + day);
		java.sql.Date birthday = new java.sql.Date(birthdate.getTime());
				
		if (birthday.equals("")) {
			msg.setIsSuccess(0);
			msg.setMessage("Birthday cannot be empty!");
			return "redirect:/user/homesettings";
		}
				
		User u = (User) session.getAttribute("user");
		u.setBirthday(birthdate);
		UserService service = new UserService();
		service.getUserDao().updateUser(u);
		msg.setIsSuccess(1);
		msg.setMessage("You've successfully changed your birthday!");
			
		/*		
		Gson gson = new Gson();
		String msgJSON = gson.toJson(msg);
		*/		
		
		redirectAttrs.addFlashAttribute("message", msg);

		return "redirect:/user/homesettings";	
	}
	
	@RequestMapping(value = "/homesettings/changeGender", method = RequestMethod.POST)
	public String changeGender(
			@RequestParam(value = "gender", required = true) int gender,
			RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
		
		Message msg = new Message();
				
		User u = (User) session.getAttribute("user");
		u.setGender(gender);
		UserService service = new UserService();
		service.getUserDao().updateUser(u);
		msg.setIsSuccess(1);
		msg.setMessage("You've successfully changed your gender!");
			
		/*		
		Gson gson = new Gson();
		String msgJSON = gson.toJson(msg);
		*/		
		
		redirectAttrs.addFlashAttribute("message", msg);

		return "redirect:/user/homesettings";	
	}
}
