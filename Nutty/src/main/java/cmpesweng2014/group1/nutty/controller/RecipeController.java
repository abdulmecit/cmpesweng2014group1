package cmpesweng2014.group1.nutty.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cmpesweng2014.group1.nutty.model.Comment;
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
		
		Comment[] comments = recipeService.getComments(recipeId);
		Map<Comment, Integer> commentsAndLikes = new HashMap<Comment, Integer>();
		for(int i=0; i<comments.length; i++){
			commentsAndLikes.put(comments[i], recipeService.numberOfLikesOfAComment(comments[i]));
		}
		model.addAttribute("commentsAndLikes", commentsAndLikes);
		
		int noOfLikes = recipeService.numberOfLikes(recipeId);
		model.addAttribute("noOfLikes", noOfLikes);
		
		int noOfEats = recipeService.numberOfEats(recipeId);
		model.addAttribute("noOfEats", noOfEats);
		
		double avgHealthRate = recipeService.avgOfHealthRates(recipeId);
		model.addAttribute("avgHealthRate", avgHealthRate);

		double avgCostRate = recipeService.avgOfCostRates(recipeId);
		model.addAttribute("avgCostRate", avgCostRate);

		double avgTasteRate = recipeService.avgOfTasteRates(recipeId);
		model.addAttribute("avgTasteRate", avgTasteRate);

		double avgEaseRate = recipeService.avgOfEaseRates(recipeId);
		model.addAttribute("avgEaseRate", avgEaseRate);
		
		String photoUrl = recipeService.getRecipePhotoUrl(recipeId);
		model.addAttribute("photoUrl", photoUrl);
		
		Long ownerId = recipeService.getRecipeOwnerId(recipeId);
		model.addAttribute("ownerId", ownerId);

		Recipe parent = recipeService.getParentRecipe(recipe);
		model.addAttribute("parent", parent);
		
		Recipe[] children = recipeService.getDerivedRecipes(recipe);
		model.addAttribute("children", children);

		return "viewRecipe";
	}

}