package cmpesweng2014.group1.nutty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import cmpesweng2014.group1.nutty.model.Message;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.User;
import cmpesweng2014.group1.nutty.service.RecipeService;
import cmpesweng2014.group1.nutty.service.RecommendationService;
import cmpesweng2014.group1.nutty.service.SearchService;
import cmpesweng2014.group1.nutty.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller

public class HomeController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private RecommendationService recommService;
	
	// No longer splits from comma when a single item sent as an array parameter
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(
	        String[].class,
	        new StringArrayPropertyEditor(null)); 
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session) {
		return "redirect:index";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, HttpSession session) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (isLogged) {
			User u = (User) session.getAttribute("user");
			Recipe[] recipes = null;
			try {
				recipes = recommService.getRecommendation(u.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(recipes != null){
				model.addAttribute("recommendedRecipes", recipes);
				String[][] recipePhotos = new String[recipes.length][];
				for(int i=0; i<recipes.length; i++){
					recipePhotos[i] = recipeService.getRecipeAllPhotoUrl(recipes[i].getRecipe_id());
				}
				model.addAttribute("recommendedRecipesPhotos", recipePhotos);
			}
		} 
			return "index";
	}


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLogin(Model model, HttpSession session) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (isLogged) {
			return "redirect:index";
		} 
		else {
			User u = new User();
			model.addAttribute("user", u);
			return "login";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			@RequestParam(value = "inputEmail", required = true) String email,
			@RequestParam(value = "inputPassword", required = true) String password,
			RedirectAttributes redirectAttrs, HttpSession session) {
		if (email.equals("") || password.equals("")) {
			redirectAttrs.addFlashAttribute("message", new Message(0, null, "Email and password fields cannot be empty!")); 
			return "redirect:login";
		}
		User u = userService.canLogin(email, password);
		if (u != null) {
			session.setAttribute("user", u);
			session.setAttribute("isLogged", true);
			redirectAttrs.addFlashAttribute("message", new Message(1, null, "You have successfully logged in, " + u.getName() + "."));
			return "redirect:success";
		} 
		else {
			redirectAttrs.addFlashAttribute("message", new Message(0, null, "Invalid email address or password."));
			return "redirect:login";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loginREST")
	public Message loginREST(
			@RequestParam(value = "inputEmail", required = true) String email,
			@RequestParam(value = "inputPassword", required = true) String password) {
		if (email.equals("") || password.equals("")) {
			return new Message(0, null, "Email and password fields cannot be empty!"); 
		}
		User u = userService.canLogin(email, password);
		if (u != null) {
			return new Message(1, u, "You have successfully logged in, " + u.getName() + "."); 
		} 
		else {
			return new Message(0, null, "Invalid email address or password.");
		}
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String viewSignup(Model model, HttpSession session) {
		User u = new User();
		model.addAttribute("user", u);
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(
			@RequestParam(value = "inputName", required = true) String name,
			@RequestParam(value = "inputSurname", required = true) String surname,
			@RequestParam(value = "inputEmail", required = true) String email,
			@RequestParam(value = "inputPassword1", required = true) String password,
			@RequestParam(value = "birthday_year", required = false) String year,
			@RequestParam(value = "birthday_month", required = false) String month,
			@RequestParam(value = "birthday_day", required = false) String day,
			@RequestParam(value = "gender", required = false) Integer gender,
			@RequestParam(value = "link", required = false) String link,
			RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
						
		if (name.equals("") || surname.equals("") || email.equals("") || password.equals("")) {
			redirectAttrs.addFlashAttribute("message", new Message(0, null, "Name, surname, email and password fields cannot be empty!"));
			return "redirect:signup";
		}
		
		java.sql.Date birthday = null;
		if((Integer.valueOf(year) != 0) && (Integer.valueOf(month) != 0) && (Integer.valueOf(day) != 0)){
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date birthdate = formatter.parse(year + "-" + month + "-" + day);
			birthday = new java.sql.Date(birthdate.getTime());
		}	
		
		Message m = userService.createUser(email, password, name, surname, birthday, gender, link);
		
		if (m.getIsSuccess() == 1) {
			User u = (User) m.getData();
			session.setAttribute("user", u);
			session.setAttribute("isLogged", true);
					
			redirectAttrs.addFlashAttribute("message", new Message(1, null, "You've successfully signed up, " + u.getName() + "."));
			return "redirect:success";
		} 
		else {
			redirectAttrs.addFlashAttribute("message", m);
			return "redirect:signup";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/signupREST")
	public Message signupREST(
			@RequestParam(value = "inputName", required = true) String name,
			@RequestParam(value = "inputSurname", required = true) String surname,
			@RequestParam(value = "inputEmail", required = true) String email,
			@RequestParam(value = "inputPassword1", required = true) String password,
			@RequestParam(value = "birthday_year", required = false) String year,
			@RequestParam(value = "birthday_month", required = false) String month,
			@RequestParam(value = "birthday_day", required = false) String day,
			@RequestParam(value = "gender", required = false) Integer gender,
			@RequestParam(value = "link", required = false) String link) throws ParseException {
						
		if (name.equals("") || surname.equals("") || email.equals("") || password.equals("")) {
			return new Message(0, null, "Name, surname, email and password fields cannot be empty!");
		}
		
		java.sql.Date birthday = null;
		if((Integer.valueOf(year) != 0) && (Integer.valueOf(month) != 0) && (Integer.valueOf(day) != 0)){
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date birthdate = formatter.parse(year + "-" + month + "-" + day);
			birthday = new java.sql.Date(birthdate.getTime());
		}
				
		Message m = userService.createUser(email, password, name, surname, birthday, gender, link);
		
		if (m.getIsSuccess() == 1) {
			User u = (User) m.getData();			
			return new Message(1, u, "You've successfully signed up, " + u.getName() + ".");
		} 
		else {
			return m; 
		}
	}
	
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String viewSignupSuccess(HttpSession session) {
		return "success";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.setAttribute("isLogged", false);
		session.setAttribute("user", null);
		session.invalidate();
		return "redirect:index";
	}
	
	@RequestMapping(value = "/basicSearch")
	@ResponseBody
	public String basicSearch(
			@RequestParam(value = "search", required = true) String search,
			@RequestParam(value = "searchOption", required = false, defaultValue = "recipe") String searchOption){

		String answer = "";
		
		if(searchOption.equals("recipe")){
			List<Recipe> recipes = recipeService.getRecipeDao().searchRecipeByName(search);
			if(recipes == null){
				return answer;
			}
			answer = searchOption;
			for(int i=0; i<recipes.size(); i++){
				answer += "|" + recipes.get(i).getName() + ">" + recipes.get(i).getRecipe_id();
			}
		}
		else{
			List<User> users = userService.getUserDao().searchUserByNameSurname(search);
			if(users == null){
				return answer;
			}
			answer = searchOption + "/profile";
			for(int i=0; i<users.size(); i++){
				answer += "|" + users.get(i).getName() + " " + users.get(i).getSurname() + ">" + users.get(i).getId();
			}
		}
		
		return answer;
	}
	
	@ResponseBody
	@RequestMapping(value = "/basicSearchREST")
	public List<?> basicSearchREST(
			@RequestParam(value = "search", required = true) String search,
			@RequestParam(value = "searchOption", required = false, defaultValue = "recipe") String searchOption){
		
		if(searchOption.equals("recipe")){
			List<Recipe> recipes = recipeService.getRecipeDao().searchRecipeByName(search);
			return recipes;
		}
		else{
			List<User> users = userService.getUserDao().searchUserByNameSurname(search);
			return users;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/basicSearchREST2")
	public String[][] basicSearchREST2(
			@RequestParam(value = "search", required = true) String search,
			@RequestParam(value = "searchOption", required = false, defaultValue = "recipe") String searchOption){
		
		String[][] response;
		
		if(searchOption.equals("recipe")){
			List<Recipe> recipes = recipeService.getRecipeDao().searchRecipeByName(search);
			response = new String[recipes.size()][2];
			for(int i=0; i<recipes.size(); i++){
				Recipe r = recipes.get(i);
				response[i][0] = r.getName();
				response[i][1] = r.getRecipe_id() + "";
			}
		}
		else{
			List<User> users = userService.getUserDao().searchUserByNameSurname(search);
			response = new String[users.size()][2];
			for(int i=0; i<users.size(); i++){
				User u = users.get(i);
				response[i][0] = u.getName() + " " + u.getSurname();
				response[i][1] = u.getId() + "";
			}
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/basicSearchREST3")
	public String basicSearchREST3(
			@RequestParam(value = "search", required = true) String search,
			@RequestParam(value = "searchOption", required = false, defaultValue = "recipe") String searchOption){
		
		String answer = "";
		
		if(searchOption.equals("recipe")){
			List<Recipe> recipes = recipeService.getRecipeDao().searchRecipeByName(search);
			if(recipes == null){
				return "[]";
			}	
			answer += "[";
			for(int i=0; i<recipes.size(); i++){
				Recipe r = recipes.get(i);
				String[] photoUrls = recipeService.getRecipeAllPhotoUrl(r.getRecipe_id());
				answer += "{\"name\":\"" + r.getName() +"\", \"id\":\"" + r.getRecipe_id() + "\", \"photoUrl\":\"" + photoUrls[0] + "\"}";
				if(i != (recipes.size()-1)) {
					answer += ",";
				}
			}
			answer += "]";
		}
		else{
			List<User> users = userService.getUserDao().searchUserByNameSurname(search);
			if(users == null){
				return "[]";
			}	
			answer += "[";
			for(int i=0; i<users.size(); i++){
				User u = users.get(i);
				answer += "{\"name\":\"" + u.getName() + " " + u.getSurname() +"\", \"id\":\"" + u.getId() + "\", \"photoUrl\":\"" + u.getPhoto() + "\"}";
				if(i != (users.size()-1)) {
					answer += ",";
				}
			}
			answer += "]";
		}
		return answer;
	}
	
	@RequestMapping(value = "/advancedSearch", method = RequestMethod.GET)
	public String advancedSearch(HttpSession session) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (!isLogged) {
			return "redirect:login";
		} 
		return "advancedSearch";
	}
	
	@ResponseBody
	@RequestMapping(value = "/advancedSearchResults")
	public String advancedSearch(
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "disableSemantic", required = false) boolean disableSemantic,		
			@RequestParam(value = "enableFoodSelection", required = false) boolean enableFoodSelection,
			@RequestParam(value = "enableEaten", required = false) boolean enableEaten,
			@RequestParam(value = "mustHaveIngredientz", required = false) String mustHaveIngredientz,
			@RequestParam(value = "calorieIntervalLow", required = false) Double calorieIntervalLow,
			@RequestParam(value = "calorieIntervalHigh", required = false) Double calorieIntervalHigh,
			@RequestParam(value = "user_id", required = true) Long user_id){
		
		User u = userService.getUserDao().getUserById(user_id);
		
		//Convert from JSON String
		Gson gson = new Gson();
		String[] mustHaveIngredients = gson.fromJson(mustHaveIngredientz, String[].class);
		
		Recipe[] recipes;
		String answer = "";
		if(search == null || search.isEmpty()){
			 recipes = recipeService.getRecipeDao().getAllRecipes();
		}
		else{
			if(disableSemantic){
				String[] tags = search.split(" ");
				recipes = searchService.searchByAllTags(tags, tags.length);
			}
			else{
				recipes = searchService.semanticSearch(search);
			}
		}
		if(recipes != null && mustHaveIngredients != null){				
			for(int i=0; i<mustHaveIngredients.length; i++){
				if(!mustHaveIngredients[i].isEmpty()){
					Recipe[] temp = recipeService.getRecipeDao().mustHaveIngredient(mustHaveIngredients[i]);
					recipes = recipeService.findIntersection(recipes, temp);
				}
			}		
		}
		if(recipes != null){
			if(calorieIntervalLow != null && calorieIntervalHigh != null){
				if(calorieIntervalLow >= 0 && calorieIntervalHigh >= calorieIntervalLow){
					Recipe[] temp = recipeService.getRecipeDao().caloriesBetween(calorieIntervalHigh, calorieIntervalLow);
					recipes = recipeService.findIntersection(recipes, temp);
				}
				else{
					return null;
				}
			}
		}
		if(recipes != null){
			if(enableFoodSelection){
				List<Recipe> recipez = new ArrayList<Recipe>();
				for(int i=0; i<recipes.length;i++){
					if(recommService.canEat(recipes[i].getRecipe_id(), u.getId())){
						recipez.add(recipes[i]);
					}
				}
				recipes = recipez.toArray(new Recipe[recipez.size()]);
			}
		}
		if(recipes != null){
			if(enableEaten){
				List<Recipe> recipez = new ArrayList<Recipe>();
				for(int i=0; i<recipes.length;i++){
					if(!recommService.isEaten(recipes[i].getRecipe_id(), u.getId())){
						recipez.add(recipes[i]);
					}
				}
				recipes = recipez.toArray(new Recipe[recipez.size()]);
			}
		}
		if(recipes != null){
			answer += "[";
			for(int i=0; i<recipes.length; i++){
				String[] photoUrls = recipeService.getRecipeAllPhotoUrl(recipes[i].getRecipe_id());
				answer += "{\"name\":\"" + recipes[i].getName() +"\", \"id\":\"" + recipes[i].getRecipe_id() + "\", \"photoUrl\":\"" + photoUrls[0] + "\"}";
				if(i != (recipes.length-1)) {
					answer += ",";
				}
			}
			answer += "]";
		}
		return answer;
	}
}

