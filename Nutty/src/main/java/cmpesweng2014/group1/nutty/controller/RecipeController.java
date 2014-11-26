package cmpesweng2014.group1.nutty.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cmpesweng2014.group1.nutty.model.IngredientAmount;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.service.RecipeService;

@Controller
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;

	@RequestMapping(value = "/addRecipe", method = RequestMethod.GET)
	public String viewAddRecipe(HttpSession session) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (isLogged) {
			return "addRecipe";
		} 
		else {
			return "redirect:/login";
		}	
	}
	
	@RequestMapping(value = "/recipe/{recipeId}", method = RequestMethod.GET)
	public String viewRecipe(@PathVariable int recipeId, Model model, HttpSession session){
		Recipe recipe = recipeService.getRecipe(recipeId);
		model.addAttribute("recipe", recipe);
		IngredientAmount[] ingredientAmounts = recipeService.getIngredientAmounts(recipeId);
		model.addAttribute("ingredientAmounts", ingredientAmounts);
		return "viewRecipe";
	}

}