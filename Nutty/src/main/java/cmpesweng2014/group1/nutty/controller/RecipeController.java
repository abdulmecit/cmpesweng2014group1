package cmpesweng2014.group1.nutty.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cmpesweng2014.group1.nutty.dao.CommentDao;
import cmpesweng2014.group1.nutty.model.Comment;
import cmpesweng2014.group1.nutty.model.Ingredient;
import cmpesweng2014.group1.nutty.model.IngredientAmount;
import cmpesweng2014.group1.nutty.model.Message;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.Tag;
import cmpesweng2014.group1.nutty.service.RecipeService;
import cmpesweng2014.group1.nutty.service.UserService;

@Controller
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;

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
	/*
	@RequestMapping(value = "/addRecipe", method = RequestMethod.POST)
	public String addRecipe(@RequestParam(value = "directions", required = true) String directions, 
							@RequestParam(value = "amounts[]", required = true) double[] amounts, 
							@RequestParam(value = "ingredients[]", required = true) String[] ingredients,
			RedirectAttributes redirectAttrs, HttpSession session) {
		
		recipeService.createRecipe(name, description, portion, photo_id, ingredients, amounts, user)
		return "success";
	}*/
	
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

		Tag[] tags=recipeService.getAllTags(recipeId);
		model.addAttribute("tags", tags);
		
		return "viewRecipe";
	}
	
	@ResponseBody
	@RequestMapping(value = "/allIngredients")
	public Ingredient[] allIngredients(HttpSession session) {
		return recipeService.getAllIngredients();
	}
	
	@ResponseBody
	@RequestMapping(value = "/someIngredients")
	public Map<Integer, String> someIngredients(@RequestParam(value = "filter", required = true) String filter, 
			HttpSession session) {
		Ingredient[] ingr = recipeService.getSomeIngredients(filter);
		if(ingr != null){
			Map<Integer, String> result = new HashMap<Integer, String>();
			for(int i=0; i<ingr.length; i++)
				result.put(ingr[i].getId(), ingr[i].getName());
			return result;
		}
		return null;
	}	
	
	@RequestMapping(value = "/recipeComments", method = RequestMethod.POST)
	@ResponseBody
	public String recipeComments(
			int recipeId,
			HttpSession session){

		String answer = "";
		
		Comment comments[] = recipeService.getComments(recipeId);
		
		if(comments == null || comments.length == 0){
			return answer;
		}
		for(int i=0; i<comments.length; i++){
			String userName = userService.getUserDao().getUserById((comments[i].getUser_id())).getName() + " " + userService.getUserDao().getUserById((comments[i].getUser_id())).getSurname();
			answer += "|" + userName + ">" + comments[i].getText();
		}
		return answer;
	}

}