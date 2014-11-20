package cmpesweng2014.group1.nutty.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cmpesweng2014.group1.nutty.model.Message;
import cmpesweng2014.group1.nutty.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
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
		msg.setIsSuccess(1);
		msg.setMessage("You've successfully changed your name!");
			
		/*		
		Gson gson = new Gson();
		String msgJSON = gson.toJson(msg);
		*/		
		
		redirectAttrs.addFlashAttribute("message", msg);

		return "redirect:/user/homesettings";	
	}
}
