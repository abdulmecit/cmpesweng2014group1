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
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewProfile(Model model, HttpSession session) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (isLogged) {
			User u = (User) session.getAttribute("user");
			model.addAttribute("user", u);
			return "profile";
		} else {
			return "redirect:/login";
		}
	}
	
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
	
	@RequestMapping(value = "/homesettings/updateUser", method = RequestMethod.POST)
	public String updateUser(
			@RequestParam(value = "changed", required = true) String changed,
			@RequestParam(value = "inputName", required = false) String name,
			@RequestParam(value = "inputSurname", required = false) String surname,
			@RequestParam(value = "inputEmail", required = false) String email,
			@RequestParam(value = "inputPassword1", required = false) String password,
			@RequestParam(value = "birthday_year", required = false) String year,
			@RequestParam(value = "birthday_month", required = false) String month,
			@RequestParam(value = "birthday_day", required = false) String day,
			@RequestParam(value = "gender", required = false) Integer gender,
			RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
		
		Message msg = new Message();

		if(changed.equals("name")){
			if (name.equals("")) {
				msg.setIsSuccess(0);
				msg.setMessage("Name cannot be empty!");
				return "redirect:/user/homesettings";
			}

			User u = (User) session.getAttribute("user");
			u.setName(name);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setMessage("You've successfully changed your name!");
			
		}else if (changed.equals("surname")){
			if (surname.equals("")) {
				msg.setIsSuccess(0);
				msg.setMessage("Surname cannot be empty!");
				return "redirect:/user/homesettings";
			}
					
			User u = (User) session.getAttribute("user");
			u.setSurname(surname);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setMessage("You've successfully changed your surname!");
			
		}else if (changed.equals("email")){
			if (email.equals("")) {
				msg.setIsSuccess(0);
				msg.setMessage("Email cannot be empty!");
				return "redirect:/user/homesettings";
			}
					
			User u = (User) session.getAttribute("user");
			u.setEmail(email);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setMessage("You've successfully changed your email!");
			
		}else if (changed.equals("password")){
			if (password.equals("")) {
				msg.setIsSuccess(0);
				msg.setMessage("Password cannot be empty!");
				return "redirect:/user/homesettings";
			}

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			User u = (User) session.getAttribute("user");
			u.setPassword(encoder.encode(password));
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setMessage("You've successfully changed your password!");
			
		}else if (changed.equals("birthday")){
			if(Integer.parseInt(year) == 0 || Integer.parseInt(month) == 0 || Integer.parseInt(day) == 0){
				msg.setIsSuccess(0);
				msg.setMessage("0 is not a valid value");
				return "redirect:/user/homesettings";
			}
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date birthdate = formatter.parse(year + "-" + month + "-" + day);
			java.sql.Date birthday = new java.sql.Date(birthdate.getTime());
							
			User u = (User) session.getAttribute("user");
			u.setBirthday(birthday);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setMessage("You've successfully changed your birthday to " + u.getBirthday());
			
		}else if (changed.equals("gender")){
			User u = (User) session.getAttribute("user");
			u.setGender(gender);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setMessage("You've successfully changed your gender!");
			
		}
		else{
			msg.setIsSuccess(0);
			msg.setMessage("A problem occured" + changed);	
		}
		
		redirectAttrs.addFlashAttribute("message", msg);
		return "redirect:/user/homesettings";	
	}
	
	@ResponseBody
	@RequestMapping(value = "/homesettings/updateUserREST")
	public Message updateUserREST(
			@RequestParam(value = "changed", required = true) String changed,
			@RequestParam(value = "inputName", required = false) String name,
			@RequestParam(value = "inputSurname", required = false) String surname,
			@RequestParam(value = "inputEmail", required = false) String email,
			@RequestParam(value = "inputPassword1", required = false) String password,
			@RequestParam(value = "birthday_year", required = false) String year,
			@RequestParam(value = "birthday_month", required = false) String month,
			@RequestParam(value = "birthday_day", required = false) String day,
			@RequestParam(value = "gender", required = false) Integer gender,
			HttpSession session) throws ParseException {
		
		Message msg = new Message();

		if(changed.equals("name")){
			if (name.equals("")) {
				msg.setIsSuccess(0);
				msg.setMessage("Name cannot be empty!");
				return msg;
			}

			User u = (User) session.getAttribute("user");
			u.setName(name);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setData(u);
			msg.setMessage("You've successfully changed your name!");
			
		}else if (changed.equals("surname")){
			if (surname.equals("")) {
				msg.setIsSuccess(0);
				msg.setMessage("Surname cannot be empty!");
				return msg;
			}
					
			User u = (User) session.getAttribute("user");
			u.setSurname(surname);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setData(u);
			msg.setMessage("You've successfully changed your surname!");
			
		}else if (changed.equals("email")){
			if (email.equals("")) {
				msg.setIsSuccess(0);
				msg.setMessage("Email cannot be empty!");
				return msg;
			}
					
			User u = (User) session.getAttribute("user");
			u.setEmail(email);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setData(u);
			msg.setMessage("You've successfully changed your email!");
			
		}else if (changed.equals("password")){
			if (password.equals("")) {
				msg.setIsSuccess(0);
				msg.setMessage("Password cannot be empty!");
				return msg;
			}

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			User u = (User) session.getAttribute("user");
			u.setPassword(encoder.encode(password));
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setData(u);
			msg.setMessage("You've successfully changed your password!");
			
		}else if (changed.equals("birthday")){
			if(Integer.parseInt(year) == 0 || Integer.parseInt(month) == 0 || Integer.parseInt(day) == 0){
				msg.setIsSuccess(0);
				msg.setMessage("0 is not a valid value");
				return msg;
			}
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date birthdate = formatter.parse(year + "-" + month + "-" + day);
			java.sql.Date birthday = new java.sql.Date(birthdate.getTime());
							
			User u = (User) session.getAttribute("user");
			u.setBirthday(birthday);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setData(u);
			msg.setMessage("You've successfully changed your birthday to " + u.getBirthday());
			
		}else if (changed.equals("gender")){
			User u = (User) session.getAttribute("user");
			u.setGender(gender);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setData(u);
			msg.setMessage("You've successfully changed your gender!");
			
		}
		else{
			msg.setIsSuccess(0);
			msg.setMessage("A problem occured" + changed);	
		}
		
		return msg;	
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
	}
}
