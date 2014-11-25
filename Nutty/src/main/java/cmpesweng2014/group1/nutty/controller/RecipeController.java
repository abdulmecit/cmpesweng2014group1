package cmpesweng2014.group1.nutty.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.service.RecipeService;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
	
	private RecipeService recipeService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session) {
		return "redirect:addRecipe";
	}
	
	@RequestMapping(value = "/addRecipe", method = RequestMethod.GET)
	public String viewAddRecipe(Model model, HttpSession session) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (isLogged) {
			return "addRecipe";
		} 
		else {
			return "redirect:/login";
		}	
	}

}