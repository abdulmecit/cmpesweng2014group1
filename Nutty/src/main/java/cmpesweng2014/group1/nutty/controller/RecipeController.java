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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cmpesweng2014.group1.nutty.model.Comment;
import cmpesweng2014.group1.nutty.model.Ingredient;
import cmpesweng2014.group1.nutty.model.IngredientAmount;
import cmpesweng2014.group1.nutty.model.Message;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.SuperRecipe;
import cmpesweng2014.group1.nutty.model.Tag;
import cmpesweng2014.group1.nutty.model.User;
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
	
	@RequestMapping(value = "/addRecipe", method = RequestMethod.POST)
	public String addRecipe(@RequestParam(value = "recipeName", required = true) String recipeName,
							@RequestParam(value = "description", required = true) String description,
							@RequestParam(value = "portion", required = true) int portion,
							@RequestParam(value = "link", required = true) String link,
							@RequestParam(value = "ingredient[]", required = true) String[] ingredients,
							@RequestParam(value = "amount[]", required = true) double[] amounts, 
							@RequestParam(value = "tag[]", required = true) String[] tags,
			RedirectAttributes redirectAttrs, HttpSession session) {
		User u = (User) session.getAttribute("user");
		Recipe r = recipeService.createRecipe(recipeName, description, portion, link, ingredients, amounts, u, tags);
		if(r != null){
			redirectAttrs.addFlashAttribute("message", new Message(1, null, "Your recipe is successfully added to the system."));
			return "redirect:/success";
		}
		redirectAttrs.addFlashAttribute("message", new Message(0, null, "Your recipe couldn't added to the system."));
		return "redirect:/addRecipe";
	}
	
	@ResponseBody
	@RequestMapping(value = "/addRecipeREST")
	public Message addRecipeREST(@RequestParam(value = "recipeName", required = true) String recipeName,
							@RequestParam(value = "description", required = true) String description,
							@RequestParam(value = "portion", required = true) int portion,
							@RequestParam(value = "link", required = true) String link,
							@RequestParam(value = "ingredient[]", required = true) String[] ingredients,
							@RequestParam(value = "amount[]", required = true) double[] amounts, 
							@RequestParam(value = "user", required = true) User u, 
							@RequestParam(value = "tag[]", required = true) String[] tags,
			HttpSession session) {
		Recipe r = recipeService.createRecipe(recipeName, description, portion, link, ingredients, amounts, u, tags);
		if(r != null){
			return new Message(1, r, "Your recipe is successfully added to the system.");		
		}
		return new Message(0, null, "Your recipe couldn't added to the system.");
	}
	
	@RequestMapping(value = "/recipe/{recipeId}", method = RequestMethod.GET)
	public String viewRecipe(@PathVariable int recipeId, Model model, HttpSession session){
		Recipe recipe = recipeService.getRecipe(recipeId);
		model.addAttribute("recipe", recipe);
		
		IngredientAmount[] ingredientAmounts = recipeService.getIngredientAmounts(recipeId);
		model.addAttribute("ingredientAmounts", ingredientAmounts);
		
		Comment[] comments = recipeService.getComments(recipeId);
		if(comments != null){
			Map<Comment, Integer> commentsAndLikes = new HashMap<Comment, Integer>();
			for(int i=0; i<comments.length; i++){
				commentsAndLikes.put(comments[i], recipeService.numberOfLikesOfAComment(comments[i]));
			}
			model.addAttribute("commentsAndLikes", commentsAndLikes);
		}
		
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
		
		return "Recipe";
	}
	
	@ResponseBody
	@RequestMapping(value = "/recipeREST/{recipeId}")
	public SuperRecipe viewRecipeREST(@PathVariable int recipeId, Model model, HttpSession session){
		
		SuperRecipe sr = new SuperRecipe();
		
		Recipe recipe = recipeService.getRecipe(recipeId);
		sr.setRecipe(recipe);
		
		IngredientAmount[] ingredientAmounts = recipeService.getIngredientAmounts(recipeId);
		sr.setIngredientAmounts(ingredientAmounts);
		
		Comment[] comments = recipeService.getComments(recipeId);
		sr.setComments(comments);

		if(comments != null){
			String[] commenters = new String[comments.length];
			int[] commentLikes = new int[comments.length];
			for(int i=0; i<comments.length; i++){
				User u = userService.getUserDao().getUserById(comments[i].getUser_id());
				commenters[i] = u.getName() + " " + u.getSurname();
				commentLikes[i] = recipeService.numberOfLikesOfAComment(comments[i]);
			}
			sr.setCommenters(commenters);
			sr.setCommentLikes(commentLikes);
		}
		
		int noOfLikes = recipeService.numberOfLikes(recipeId);
		sr.setNoOfLikes(noOfLikes);
		
		int noOfEats = recipeService.numberOfEats(recipeId);
		sr.setNoOfEats(noOfEats);
		
		double avgHealthRate = recipeService.avgOfHealthRates(recipeId);
		sr.setAvgHealthRate(avgHealthRate);
		
		double avgCostRate = recipeService.avgOfCostRates(recipeId);
		sr.setAvgCostRate(avgCostRate);

		double avgTasteRate = recipeService.avgOfTasteRates(recipeId);
		sr.setAvgTasteRate(avgTasteRate);

		double avgEaseRate = recipeService.avgOfEaseRates(recipeId);
		sr.setAvgEaseRate(avgEaseRate);
		
		String photoUrl = recipeService.getRecipePhotoUrl(recipeId);
		sr.setPhotoUrl(photoUrl);
		
		Long ownerId = recipeService.getRecipeOwnerId(recipeId);
		User u = userService.getUserDao().getUserById(ownerId);
		sr.setOwner(u.getName() + " " + u.getSurname());

		Recipe parent = recipeService.getParentRecipe(recipe);
		sr.setParent(parent);
		
		Recipe[] children = recipeService.getDerivedRecipes(recipe);
		sr.setChildren(children);

		Tag[] tags=recipeService.getAllTags(recipeId);
		sr.setTags(tags);
		
		return sr;
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
				result.put(ingr[i].getId(), ingr[i].getIng_name());
			return result;
		}
		return null;
	}	
	
	@ResponseBody
	@RequestMapping(value = "/someIngredients2")
	public Ingredient[] someIngredients2(@RequestParam(value = "filter", required = true) String filter,
			HttpSession session) {
		return recipeService.getSomeIngredients(filter);
	}
	
	@RequestMapping(value = "/recipeComments")
	@ResponseBody
	public String recipeComments(
			@RequestParam(value = "recipeId", required = true) int recipeId,
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