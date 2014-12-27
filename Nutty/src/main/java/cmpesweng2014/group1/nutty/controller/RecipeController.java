package cmpesweng2014.group1.nutty.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

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
	
	// No longer splits from comma when a single item sent as an array parameter
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(
	        String[].class,
	        new StringArrayPropertyEditor(null)); 
	}

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
							@RequestParam(value = "link[]", required = true) String[] link,
							@RequestParam(value = "ingredient[]", required = true) String[] ingredients,
							@RequestParam(value = "amount[]", required = true) String[] amounts,
							@RequestParam(value = "measType[]", required = true) String[] meas_types, 
							@RequestParam(value = "tag[]", required = false) String[] tagz,
			RedirectAttributes redirectAttrs, HttpSession session) {
		User u = (User) session.getAttribute("user");
		
		String[] tags = {};
		//Remove empty and duplicate tags			
		if(tagz != null){
			HashSet<String> tagSet = new HashSet<String>(Arrays.asList(tagz));
			tagSet.remove(new String(""));
			tags = tagSet.toArray(new String[0]);
		}
		
		double[] parsedAmounts = new double[amounts.length];
		//Check if entered amounts are valid
		for(int i=0; i<amounts.length; i++){
			double parsedAmount = recipeService.parseAmount(amounts[i]);
			if(parsedAmount <= 0.0){
				redirectAttrs.addFlashAttribute("message", new Message(0, null, "Your recipe had invalid ingredient amount values."));	
				return "redirect:/addRecipe";
			}	
			else
				parsedAmounts[i] = parsedAmount;
		}
		
		Recipe r = recipeService.createRecipe(recipeName, description, portion, link, ingredients, amounts, parsedAmounts, meas_types, u, tags);
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
							@RequestParam(value = "link[]", required = true) String link,
							@RequestParam(value = "ingredient[]", required = true) String ingredientz,
							@RequestParam(value = "amount[]", required = true) String amountz,
							@RequestParam(value = "measType[]", required = true) String meas_typez, 
							@RequestParam(value = "user_id", required = true) Long user_id,
							@RequestParam(value = "tag[]", required = false) String tagz) {
		
		String[] tags = {};
		String[] urls = {};
		//Convert from JSON String
		Gson gson = new Gson();
		String[] ingredients = gson.fromJson(ingredientz, String[].class);
		String[] amounts = gson.fromJson(amountz, String[].class);
		String[] meas_types = gson.fromJson(meas_typez, String[].class);
		
		if(tagz != null){
			//Remove empty and duplicate tags			
			HashSet<String> tagSet = new HashSet<String>(Arrays.asList(gson.fromJson(tagz, String[].class)));
			tagSet.remove(new String(""));
			tags = tagSet.toArray(new String[0]);
		}
		
		if(link != null){
			//Remove empty and duplicate links			
			HashSet<String> linkSet = new HashSet<String>(Arrays.asList(gson.fromJson(link, String[].class)));
			linkSet.remove(new String(""));
			urls = linkSet.toArray(new String[0]);
		}
		
		double[] parsedAmounts = new double[amounts.length];
		//Check if entered amounts are valid
		for(int i=0; i<amounts.length; i++){
			double parsedAmount = recipeService.parseAmount(amounts[i]);
			if(parsedAmount <= 0.0)
				return new Message(0, null, "Your recipe had invalid ingredient amount values.");
			else
				parsedAmounts[i] = parsedAmount;
		}
		
		User u = userService.getUserDao().getUserById(user_id);	
		Recipe r = recipeService.createRecipe(recipeName, description, portion, urls, ingredients, amounts, parsedAmounts, meas_types, u, tags);
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
		model.addAttribute("comments", comments);
	
		if(comments != null){
			String[] commenters = new String[comments.length];
			int[] commentLikes = new int[comments.length];
			for(int i=0; i<comments.length; i++){
				User u = userService.getUserDao().getUserById(comments[i].getUser_id());
				commenters[i] = u.getName() + " " + u.getSurname();
				commentLikes[i] = recipeService.numberOfLikesOfAComment(comments[i]);
			}
			model.addAttribute("commenters", commenters);
			model.addAttribute("commentLikes", commentLikes);
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
		
		double avgOverall=recipeService.avgOfAllRates(recipeId);
		model.addAttribute("avgOverall", avgOverall);
		
		int numOfHealthRate=recipeService.voterCountHealth(recipeId);
		model.addAttribute("numOfHealthRate", numOfHealthRate);
		
		int numOfTasteRate=recipeService.voterCountTaste(recipeId);
		model.addAttribute("numOfTasteRate", numOfTasteRate);
		
		int numOfEaseRate=recipeService.voterCountEase(recipeId);
		model.addAttribute("numOfEaseRate", numOfEaseRate);
		
		int numOfCostRate=recipeService.voterCountCost(recipeId);
		model.addAttribute("numOfCostRate", numOfCostRate);
		
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		
		if(isLogged){	
			
			User u = (User) session.getAttribute("user");
			long user_id=u.getId();
			
			int shareOfUser=recipeService.isShared(user_id, recipeId);
			model.addAttribute("shareOfUser", shareOfUser);
			
			int healthRateOfUser=recipeService.getHealthRateForUser(recipeId, user_id);
			model.addAttribute("healthRateOfUser", healthRateOfUser);
			
			int costRateOfUser=recipeService.getCostRateForUser(recipeId, user_id);
			model.addAttribute("costRateOfUser", costRateOfUser);
			
			int tasteRateOfUser=recipeService.getTasteRateForUser(recipeId, user_id);
			model.addAttribute("tasteRateOfUser", tasteRateOfUser);
			
			int easeRateOfUser=recipeService.getEaseRateForUser(recipeId, user_id);
			model.addAttribute("easeRateOfUser", easeRateOfUser);
			
			int likeOfUser=recipeService.getLikeForUser(recipeId, user_id);
			model.addAttribute("likeOfUser", likeOfUser);
			
			int eatenOfUser=recipeService.getEatenForUser(recipeId, user_id);
			model.addAttribute("eatenOfUser", eatenOfUser);	
			
			int reportOfUser=recipeService.getRecipeDao().hasReportedRecipe(recipeId, user_id);
			model.addAttribute("reportOfUser", reportOfUser);
		}
		
		String[] photoUrl = recipeService.getRecipeAllPhotoUrl(recipeId);
		model.addAttribute("photoUrl", photoUrl);
		
		Long ownerId = recipeService.getRecipeOwnerId(recipeId);
		model.addAttribute("ownerId", ownerId);
		
		User recipe_owner = userService.getUserDao().getUserById(ownerId);
		model.addAttribute("ownerName", recipe_owner.getName() + " " + recipe_owner.getSurname());

		Recipe parent = recipeService.getParentRecipe(recipe);
		model.addAttribute("parent", parent);
		
		Recipe[] children = recipeService.getDerivedRecipes(recipe);
		model.addAttribute("children", children);

		Tag[] tags=recipeService.getAllTags(recipeId);
		if(tags != null)
			model.addAttribute("tags", tags);
		
		return "Recipe";
	}
	
	@RequestMapping(value = "/derivedRecipe/{recipeId}", method = RequestMethod.GET)
	public String fillDerivedRecipe(@PathVariable int recipeId, Model model, HttpSession session){
		Recipe recipe = recipeService.getRecipe(recipeId);
		model.addAttribute("recipe", recipe);
		IngredientAmount[] ingredientAmounts = recipeService.getIngredientAmounts(recipeId);
		model.addAttribute("ingredientAmounts", ingredientAmounts);
		Map<Integer, String[]> measTypesMap = new HashMap<Integer, String[]>();
		for(int i=0; i<ingredientAmounts.length; i++){
			int ing_id = ingredientAmounts[i].getIng_id();
			String[] meas_types = recipeService.getRecipeDao().getMeasTypesByIngId(ing_id);
			measTypesMap.put(i, meas_types);
		}
		model.addAttribute("measTypesMap", measTypesMap);
		Tag[] tags=recipeService.getAllTags(recipeId);
		if(tags != null)
			model.addAttribute("tags", tags);
		String[] photoUrl = recipeService.getRecipeAllPhotoUrl(recipeId);
		model.addAttribute("photoUrl", photoUrl);
		
		return "derivedRecipe";
	}
	
	@RequestMapping(value = "/derivedRecipe/{recipeId}", method = RequestMethod.POST)
	public String addDerivedRecipe(@PathVariable int recipeId, @RequestParam(value = "recipeName", required = true) String recipeName,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "portion", required = true) int portion,
			@RequestParam(value = "link[]", required = true) String[] link,
			@RequestParam(value = "ingredient[]", required = true) String[] ingredients,
			@RequestParam(value = "amount[]", required = true) String[] amounts,
			@RequestParam(value = "measType[]", required = true) String[] meas_types,
			@RequestParam(value = "tag[]", required = true) String[] tagz,
			RedirectAttributes redirectAttrs, HttpSession session) {
		User u = (User) session.getAttribute("user");

		String[] tags = {};
		if(tagz != null){
			//Remove empty and duplicate tags
			HashSet<String> tagSet = new HashSet<String>(Arrays.asList(tagz));
			tagSet.remove(new String(""));
			tags = tagSet.toArray(new String[0]);
		}
		
		double[] parsedAmounts = new double[amounts.length];
		//Check if entered amounts are valid
		for(int i=0; i<amounts.length; i++){
			double parsedAmount = recipeService.parseAmount(amounts[i]);
			if(parsedAmount <= 0.0){
				redirectAttrs.addFlashAttribute("message", new Message(0, null, "Your recipe had invalid ingredient amount values."));
				return "redirect:/derivedRecipe/"+recipeId;
			}
			else
				parsedAmounts[i] = parsedAmount;
		}

		Recipe r = recipeService.deriveRecipe(recipeName, description, portion, link, ingredients, amounts, parsedAmounts, meas_types, u, recipeService.getRecipe(recipeId), tags);
		if(r != null){
			redirectAttrs.addFlashAttribute("message", new Message(1, null, "Your new version is successfully added to the system."));
			return "redirect:/success";
		}
		redirectAttrs.addFlashAttribute("message", new Message(0, null, "Your recipe couldn't added to the system."));
		return "redirect:/derivedRecipe/"+recipeId;
	}
	
	@RequestMapping(value = "/editRecipe/{recipeId}", method = RequestMethod.GET)
	public String fillEditRecipe(@PathVariable int recipeId, Model model, HttpSession session){
		Recipe recipe = recipeService.getRecipe(recipeId);
		model.addAttribute("recipe", recipe);
		IngredientAmount[] ingredientAmounts = recipeService.getIngredientAmounts(recipeId);
		model.addAttribute("ingredientAmounts", ingredientAmounts);
		Map<Integer, String[]> measTypesMap = new HashMap<Integer, String[]>();
		for(int i=0; i<ingredientAmounts.length; i++){
			int ing_id = ingredientAmounts[i].getIng_id();
			String[] meas_types = recipeService.getRecipeDao().getMeasTypesByIngId(ing_id);
			measTypesMap.put(i, meas_types);
		}
		model.addAttribute("measTypesMap", measTypesMap);
		Tag[] tags=recipeService.getAllTags(recipeId);
		if(tags != null)
			model.addAttribute("tags", tags);
		String[] photoUrl = recipeService.getRecipeAllPhotoUrl(recipeId);
		model.addAttribute("photoUrl", photoUrl);
		
		return "editRecipe";
	}
	
	@RequestMapping(value = "/editRecipe/{recipeId}", method = RequestMethod.POST)
	public String editRecipe(@PathVariable int recipeId, @RequestParam(value = "recipeName", required = true) String recipeName,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "portion", required = true) int portion,
			@RequestParam(value = "link[]", required = true) String[] link,
			@RequestParam(value = "ingredient[]", required = true) String[] ingredients,
			@RequestParam(value = "amount[]", required = true) String[] amounts,
			@RequestParam(value = "measType[]", required = true) String[] meas_types,
			@RequestParam(value = "tag[]", required = true) String[] tagz,
			RedirectAttributes redirectAttrs, HttpSession session) {

		String[] tags = {};
		if(tagz != null){
			//Remove empty and duplicate tags
			HashSet<String> tagSet = new HashSet<String>(Arrays.asList(tagz));
			tagSet.remove(new String(""));
			tags = tagSet.toArray(new String[0]);
		}
		
		double[] parsedAmounts = new double[amounts.length];
		//Check if entered amounts are valid
		for(int i=0; i<amounts.length; i++){
			double parsedAmount = recipeService.parseAmount(amounts[i]);
			if(parsedAmount == 0.0){
				redirectAttrs.addFlashAttribute("message", new Message(0, null, "Your recipe had invalid ingredient amount values."));
				return "redirect:/editRecipe/"+recipeId;
			}
			else
				parsedAmounts[i] = parsedAmount;
		}

		Recipe r = recipeService.editRecipe(recipeId, recipeName, description, portion, link, ingredients, amounts, parsedAmounts, meas_types, tags);
		if(r != null){
			redirectAttrs.addFlashAttribute("message", new Message(1, null, "Your new version is successfully added to the system."));
			return "redirect:/success";
		}
		redirectAttrs.addFlashAttribute("message", new Message(0, null, "Your recipe couldn't added to the system."));
		return "redirect:/editRecipe/"+recipeId;
	}
	
	@RequestMapping(value = "/deleteRecipe")
	public String deleteRecipe(
			@RequestParam(value = "recipe_id", required = true) int recipe_id
			){
		recipeService.getRecipeDao().deleteRecipe(recipe_id);
		return "redirect:index";
	}
	
	@RequestMapping(value = "/deleteComment")
	public String deleteComment(
			@RequestParam(value = "comment_id", required = true) int comment_id
			){
		recipeService.getCommentDao().deleteComment(comment_id);
		return "redirect:index";
	}
	@RequestMapping(value = "/reportComment")
	public String reportComment(
			@RequestParam(value = "comment_id", required = true) int comment_id,
			@RequestParam(value = "user_id", required = true) Long user_id
			){
		recipeService.getCommentDao().reportComment(comment_id, user_id);
		return "Recipe";
	}
	@RequestMapping(value = "/reportRecipe")
	public String reportRecipe(
			@RequestParam(value = "recipe_id", required = true) int recipe_id,
			@RequestParam(value = "user_id", required = true) Long user_id
			){
		recipeService.getRecipeDao().reportRecipe(recipe_id, user_id);
		return "Recipe";
	}	
	@RequestMapping(value = "/rateRecipe")
	public String rateRecipe(
			@RequestParam(value = "changed", required = true) String changed,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "recipe_id", required = true) int recipe_id,
			@RequestParam(value = "value", required = true) int value
			){
		
		Recipe recipe = recipeService.getRecipe(recipe_id);
		User u = userService.getUserDao().getUserById(user_id);	
		recipeService.getRecipeDao().evaluateRecipe(changed, value, u, recipe);
		
		return "Recipe";
	}
	
	@RequestMapping(value = "/commentRecipe")
	public String commentRecipe(
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "recipe_id", required = true) int recipe_id,
			@RequestParam(value = "comment", required = true) String value
			) {
		
		Recipe recipe = recipeService.getRecipe(recipe_id);
		User u = userService.getUserDao().getUserById(user_id);	
		recipeService.commentRecipe(value, u, recipe);		
		return "redirect:recipe/"+recipe_id;
	}
	
	@RequestMapping(value = "/editComment")
	public String editComment(
			@RequestParam(value = "comment_id", required = true) int comment_id,
			@RequestParam(value = "recipe_id", required = true) int recipe_id,
			@RequestParam(value = "text", required = true) String text
			) {
		
		recipeService.getCommentDao().editComment(comment_id, text);		
		return "redirect:recipe/"+recipe_id;
	}
	
	
	@RequestMapping(value = "/shareRecipe")
	public String shareRecipe(
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "recipe_id", required = true) int recipe_id,
			@RequestParam(value = "value", required = true) int value
			) {		
		if(value==1){
			recipeService.shareRecipe(user_id, recipe_id);	
		}
		else{
			recipeService.cancelShare(user_id, recipe_id);
		}
		return "Recipe";
	}
	
	@RequestMapping(value = "/likeComment")
	public String likeComment(
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "comment_id", required = true) int comment_id,
			@RequestParam(value = "value", required = true) int value
			) {
		Comment comment = recipeService.getCommentDao().getCommentById(comment_id);
		User u = userService.getUserDao().getUserById(user_id);	
		if(value==1){
			recipeService.likeComment(comment, u);
		}
		else{
			recipeService.getBackLikeOfAComment(comment, u);;
		}
		return "Recipe";
	}
	
	@ResponseBody
	@RequestMapping(value = "/recipeREST/{recipeId}")
	public SuperRecipe viewRecipeREST(@PathVariable int recipeId){
		
		SuperRecipe sr = new SuperRecipe();
		
		Recipe recipe = recipeService.getRecipe(recipeId);
		sr.setRecipe(recipe);
		
		IngredientAmount[] ingredientAmounts = recipeService.getIngredientAmounts(recipeId);
		sr.setIngredientAmounts(ingredientAmounts);
				
		Comment[] comments = recipeService.getComments(recipeId);
		sr.setComments(comments);

		if(comments != null){
			String[] commenterNames = new String[comments.length];
			long[][] commentLikerIds = new long[comments.length][];
			String[][] commentLikerNames = new String[comments.length][];
			for(int i=0; i<comments.length; i++){
				User commenter = userService.getUserDao().getUserById(comments[i].getUser_id());
				commenterNames[i] = commenter.getName() + " " + commenter.getSurname();
				User[] commentLikers = recipeService.usersWhoLike(comments[i]);
				for(int j=0; j<commentLikers.length; j++){
					commentLikerIds[i][j] = commentLikers[j].getId();
					commentLikerNames[i][j] = commentLikers[j].getName() + " " + commentLikers[j].getSurname();
				}
			}
			sr.setCommenterNames(commenterNames);
			sr.setCommentLikerIds(commentLikerIds);
			sr.setCommentLikerNames(commentLikerNames);
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
		
		String[] photoUrl = recipeService.getRecipeAllPhotoUrl(recipeId);
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
	@RequestMapping(value = "/elrOfUser")
	public Map<String,Integer> elrOfUser(@RequestParam(value = "recipeId", required = true) int recipeId, 
			@RequestParam(value = "user_id", required = true) long user_id){
		
		Map<String, Integer> elr = new HashMap<String, Integer>();		

		int healthRateOfUser=recipeService.getHealthRateForUser(recipeId, user_id);
		elr.put("healthRateOfUser", healthRateOfUser);
		
		int costRateOfUser=recipeService.getCostRateForUser(recipeId, user_id);
		elr.put("costRateOfUser", costRateOfUser);
		
		int tasteRateOfUser=recipeService.getTasteRateForUser(recipeId, user_id);
		elr.put("tasteRateOfUser", tasteRateOfUser);
		
		int easeRateOfUser=recipeService.getEaseRateForUser(recipeId, user_id);
		elr.put("easeRateOfUser", easeRateOfUser);
		
		int likeOfUser=recipeService.getLikeForUser(recipeId, user_id);
		elr.put("likeOfUser", likeOfUser);
		
		int eatenOfUser=recipeService.getEatenForUser(recipeId, user_id);
		elr.put("eatenOfUser", eatenOfUser);
		
		int shareOfUser=recipeService.isShared(user_id, recipeId);
		elr.put("shareOfUser", shareOfUser);
		return elr;
	}
	
	@ResponseBody
	@RequestMapping(value = "/allIngredients")
	public Ingredient[] allIngredients() {
		return recipeService.getAllIngredients();
	}
	
	@ResponseBody
	@RequestMapping(value = "/someIngredients")
	public Map<Integer, String> someIngredients(@RequestParam(value = "filter", required = true) String filter) {
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
	public Ingredient[] someIngredients2(@RequestParam(value = "filter", required = true) String filter) {
		Ingredient[] matchedIngredients = recipeService.getSomeIngredients(filter);
		if(matchedIngredients == null)
			return new Ingredient[0];
		return matchedIngredients;
	}
	
	@ResponseBody
	@RequestMapping(value = "/someIngredients3")
	public String[] someIngredients3(@RequestParam(value = "filter", required = true) String filter) {
		Ingredient[] matchedIngredients = recipeService.getSomeIngredients(filter);
		if(matchedIngredients == null)
			return new String[0];
		HashSet<String> ingredientTypes = new HashSet<String>();
		for(int i=0; i<matchedIngredients.length; i++){
			String[] temp = matchedIngredients[i].getIng_name().split(",");
			if(temp[0].toUpperCase().contains(filter.toUpperCase()))
				ingredientTypes.add(temp[0]);
		}
		return ingredientTypes.toArray(new String[ingredientTypes.size()]);
	}
	
	@ResponseBody
	@RequestMapping(value = "/measTypesOfIngr")
	public String[] measTypesOfIngr(@RequestParam(value = "ing_id", required = true) int ing_id) {
		return recipeService.getRecipeDao().getMeasTypesByIngId(ing_id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/measTypesOfIngr2")
	public String[] measTypesOfIngr(@RequestParam(value = "ing_name", required = true) String ing_name) {
		return recipeService.getRecipeDao().getMeasTypesByIngName(ing_name);
	}
	
	@RequestMapping(value = "/recipeComments")
	@ResponseBody
	public String recipeComments(
			@RequestParam(value = "recipeId", required = true) int recipeId,
			@RequestParam(value = "user_id", defaultValue = "-1") long user_id){

		String answer = "";
		
		Comment comments[] = recipeService.getComments(recipeId);
		
		if(comments == null || comments.length == 0){
			return answer;
		}
		answer += "[";
		for(int i=0; i<comments.length; i++){
			answer += "{\"comment_id\":\"" + comments[i].getComment_id() +"\", \"comment_text\":\"" + comments[i].getText() + "\", ";
			User u = userService.getUserDao().getUserById(comments[i].getUser_id());
			answer += "\"commenter_id\":\"" + u.getId() + "\", \"commenter_name\":\"" + u.getName() + " " + u.getSurname() + "\"";
			
			if(user_id != -1){
				int isReportedByMe = recipeService.getCommentDao().hasReportedComment(comments[i].getComment_id(), user_id);
				answer += ", \"isReportedByMe\": \"" + isReportedByMe + "\"";
			}
			User[] likers = recipeService.usersWhoLike(comments[i]);
			if(likers != null){
				answer += ", \"likers\": [";
				for(int j=0; j<likers.length; j++){
					answer += "{\"liker_id\":\"" + likers[j].getId() + "\", \"liker_name\":\"" + likers[j].getName() + " " + likers[j].getSurname() + "\"}";	
					if(j != (likers.length-1)) {
						answer += ", ";
					}
				}
				answer += "]";
			}
			answer += "}";
			if(i != (comments.length-1)) {
				answer += ", ";
			}
		}
		answer += "]";
		return answer;
	}
	
	@RequestMapping(value = "/isReportedForComment")
	public int isReportedForComment(
			@RequestParam(value = "comment_id", required = true) int comment_id,
			@RequestParam(value = "user_id", required = true) Long user_id
			){
		return recipeService.getCommentDao().hasReportedComment(comment_id, user_id);
	}
}