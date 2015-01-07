package cmpesweng2014.group1.nutty;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import cmpesweng2014.group1.nutty.model.Event;
import cmpesweng2014.group1.nutty.model.Mail;
import cmpesweng2014.group1.nutty.model.Message;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.User;
import cmpesweng2014.group1.nutty.service.EventService;
import cmpesweng2014.group1.nutty.service.MailService;
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
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private EventService eventService;
	
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
			Object currentUrl = session.getAttribute("currentUrl");
			String from_page = currentUrl == null ? "/nutty" : (String) currentUrl;
			model.addAttribute("from_page", from_page);

			User u = new User();
			model.addAttribute("user", u);
			return "login";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			@RequestParam(value = "inputEmail", required = true) String email,
			@RequestParam(value = "inputPassword", required = true) String password,
			@RequestParam(value = "from_page", required = true) String from_page,
			RedirectAttributes redirectAttrs, HttpSession session) {
		if (email.equals("") || password.equals("")) {
			redirectAttrs.addFlashAttribute("message", new Message(0, null, "Email and password fields cannot be empty!")); 
			return "redirect:login";
		}
		User u = userService.canLogin(email, password);
		if (u != null) {
			session.setAttribute("user", u);
			session.setAttribute("isLogged", true);
			session.setAttribute("message", new Message(1, from_page, "You have successfully logged in, " + u.getName() + "."));
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
	
	@RequestMapping(value = "/forgotPass")
	public String forgotPassword(@RequestParam(value = "email", required = true) String email,
			RedirectAttributes redirectAttrs, HttpServletRequest request) throws URISyntaxException {
		SecureRandom random = new SecureRandom();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Mail m = new Mail();
		
		//Check if an user with the given email exists in the database
		User u = userService.getUserDao().getUserByEmail(email);
		
		if(u == null){
			String content = "You (or someone else) entered this email address when trying to reset the password for their Nutty account.\n\nHowever, this email address is not registered in our database and therefore the reset password request has failed.\n\nIf you indeed have a Nutty account, please try again with your registered email address.\n\nIf not, feel free to ignore this email.\n\nKind regards,\nNutty Customer Service";
			m.setTo(email);
			m.setSubject("Reset Password Request");
			m.setContent(content);
		}
		else{
			String content = "You (or someone else) entered this email address when trying to reset the password for their Nutty account.\n\n";

			content += "If you are the one who initiated the reset process, please click on the following link or copy/paste it into your browser to decide on a new password:\n\n";
			
			//Create a password reset request
			String token = new BigInteger(130, random).toString(32);		
			userService.getUserDao().addPasswordResetRequest(encoder.encode(token), u.getId());	
			
			//Get domain url
			String current_url = request.getRequestURL().toString();
			URI uri = new URI(current_url);
		    String domain = uri.getAuthority();

			content += "http://" + domain + "/nutty/resetPass?t=" + token + "&a=1&u="+u.getId()+"\n\n";
			
			content += "If you are NOT the person who initiated the reset process, please click on the following link or copy/paste it into your browser to cancel this request:\n\n";
			content += "http://" + domain + "/nutty/resetPass?t=" + token + "&a=0&u="+u.getId()+"\n\n";
			
			content += "Kind regards,\nNutty Customer Service";

			m.setTo(email);
			m.setSubject("Reset Password Request");
			m.setContent(content);
		}
		try{
			mailService.sendMail(m);
		}
		catch(MailException e){
			redirectAttrs.addFlashAttribute("message", new Message(0, null, "An error has occurred when trying to send the email."));
			return "redirect:login";
		}
		redirectAttrs.addFlashAttribute("message", new Message(1, null, "Instructions are sent to " + email + ".")); 
		return "redirect:login";
	}

	@RequestMapping(value = "/resetPass", method = RequestMethod.GET)
	public String validateResetPass(
			@RequestParam(value = "u", required = true) Long user_id,
			@RequestParam(value = "t", required = true) String token,
			@RequestParam(value = "a", required = true) int isAllowed,
			RedirectAttributes redirectAttrs, HttpSession session) {	
		
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		
		if(isLogged){
			return "redirect:index";
		}
		
		if(isAllowed == 0){
			if(!userService.tokensDoMatch(token, user_id)){
				redirectAttrs.addFlashAttribute("message", new Message(0, null, "URL didn't get verified, password reset failed.")); 
				return "redirect:login";
			}
			userService.getUserDao().deletePasswordResetRequest(user_id);
			redirectAttrs.addFlashAttribute("message", new Message(1, null, "Password reset request is successfully cancelled.")); 
			return "redirect:login";
		}
		else if(isAllowed == 1){
			if(!userService.tokensDoMatch(token, user_id)){
				redirectAttrs.addFlashAttribute("message", new Message(0, null, "URL didn't get verified, password reset failed.")); 
				return "redirect:login";
			}
			else{
				Calendar currentTime = Calendar.getInstance();
				Calendar tokenTime = Calendar.getInstance();
				tokenTime.setTimeInMillis(userService.getUserDao().getTimestampById(user_id).getTime());
				
				long diff = currentTime.getTimeInMillis() - tokenTime.getTimeInMillis();
				long threshold = 3600000; //1 hour in milliseconds
				if(diff > threshold){
					redirectAttrs.addFlashAttribute("message", new Message(0, null, "Your token has expired, password reset failed.")); 
					return "redirect:login";
				}
				userService.getUserDao().deletePasswordResetRequest(user_id);
				redirectAttrs.addFlashAttribute("user_id", user_id);
				return "redirect:resetPassword";
			}
		}
		else{
			redirectAttrs.addFlashAttribute("message", new Message(0, null, "URL didn't get verified, password reset failed.")); 
			return "redirect:login";
		}
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String viewResetPass() {
		return "resetPassword";
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public String resetPass(
			@RequestParam(value = "inputPassword", required = true) String password,
			@RequestParam(value = "inputPassword2", required = true) String password2,
			@RequestParam(value = "user_id", required = true) long user_id,
			RedirectAttributes redirectAttrs) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();	
		User u = (User) userService.getUserDao().getUserById(user_id);
		
		if(!password.equals(password2)){
			redirectAttrs.addFlashAttribute("message", new Message(0, null, "Entered passwords do not match!")); 
			return "redirect:login";
		}
		
		u.setPassword(encoder.encode(password));
		userService.getUserDao().updateUser(u);
		redirectAttrs.addFlashAttribute("message", new Message(1, null, "Your password is successfully changed")); 
		return "redirect:login";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String viewSignup(Model model, HttpSession session) {
		Object currentUrl = session.getAttribute("currentUrl");
		String from_page = currentUrl == null ? "/nutty" : (String) currentUrl;
		model.addAttribute("from_page", from_page);
		
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
			@RequestParam(value = "from_page", required = true) String from_page,
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
					
			session.setAttribute("message", new Message(1, from_page, "You've successfully signed up, " + u.getName() + "."));
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
	
	@RequestMapping(value = "/advancedSearch/{searchKey}", method = RequestMethod.GET)
	public String advancedSearchTag(@PathVariable String searchKey, HttpSession session, Model model) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (!isLogged) {
			return "redirect:/login";
		} 
		model.addAttribute("searchKey", searchKey);
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
		
		Gson gson = new Gson();
		String[] mustHaveIngredients = {};
		
		if(mustHaveIngredientz != null){
			//Remove empty and duplicate "must have ingredients"		
			HashSet<String> mustHaveIngredientSet = new HashSet<String>(Arrays.asList(gson.fromJson(mustHaveIngredientz, String[].class)));
			mustHaveIngredientSet.remove(new String(""));
			mustHaveIngredients = mustHaveIngredientSet.toArray(new String[0]);
		}
		
		Recipe[] recipes;
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
		if(recipes != null){				
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
		return gson.toJson(recipes);
	}
	
	@RequestMapping(value = "/signUpOrLogin", method = RequestMethod.POST) //for facebook login
	@ResponseBody
	public String signUpOrLogin(
			@RequestParam(value = "email", required = true) String email,
			RedirectAttributes redirectAttrs) {
		
		if(userService.getUserDao().getUserByEmail(email) == null){
			return "0"; //signup
		}
		return "1";//login
	}
	
	@RequestMapping(value = "/removeSessionAttr", method = RequestMethod.POST) 
	@ResponseBody
	public void removeSessionAttr(
			@RequestParam(value = "attrName", required = true) String attrName,
			HttpSession session) {
		
			session.removeAttribute(attrName);
	}
	
	@RequestMapping(value = "/getRecentEvents")
	@ResponseBody
	public List<Event> getRecentEvents(
			@RequestParam(value = "user_id", required = true) long user_id){
		
		User[] users = userService.getFollowingList(user_id);	
		if(users == null)
			return null;			
		return eventService.getAllRecentEventsOfUserList(users);
	}
	
	@RequestMapping(value = "/bestOfHealthCategory")
	@ResponseBody
	public String[] getBestOfHealth(HttpSession session){
        Object logged = session.getAttribute("isLogged");
        Recipe[] allRecipes = null;
        boolean isLogged = logged == null ? false : (Boolean) logged;
        if (isLogged) {
            User u = (User) session.getAttribute("user");
            allRecipes = null;
            try {
                allRecipes = recommService.getRecommendation(u.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            allRecipes = recipeService.getAllRecipes();
        }

		Random rand = new Random();
		int randHealth = rand.nextInt(2);

		Recipe[] health = searchService.sortByRate(allRecipes, "health");				
		String[] response = new String[3];
		
		String[] urls;
		response[0] = health[randHealth].getRecipe_id() + "";
		response[1] = health[randHealth].getName();
		urls = recipeService.getRecipeAllPhotoUrl(Integer.parseInt(response[0]));
		response[2] = urls[0];

		return response;
	}
	
	@RequestMapping(value = "/bestOfEaseCategory")
	@ResponseBody
	public String[] getBestOfEase(HttpSession session){
		Object logged = session.getAttribute("isLogged");
        Recipe[] allRecipes = null;
        boolean isLogged = logged == null ? false : (Boolean) logged;
        if (isLogged) {
            User u = (User) session.getAttribute("user");
            allRecipes = null;
            try {
                allRecipes = recommService.getRecommendation(u.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            allRecipes = recipeService.getAllRecipes();
        }
		Random rand = new Random();
		int randEase = rand.nextInt(2);

		Recipe[] ease = searchService.sortByRate(allRecipes, "ease");		
		String[] response = new String[3];
		
		String[] urls;
		response[0] = ease[randEase].getRecipe_id() + "";
		response[1] = ease[randEase].getName();
		urls = recipeService.getRecipeAllPhotoUrl(Integer.parseInt(response[0]));
		response[2] = urls[0];

		return response;
	}
	
	@RequestMapping(value = "/bestOfTasteCategory")
	@ResponseBody
	public String[] getBestOfTaste(HttpSession session){
		Object logged = session.getAttribute("isLogged");
        Recipe[] allRecipes = null;
        boolean isLogged = logged == null ? false : (Boolean) logged;
        if (isLogged) {
            User u = (User) session.getAttribute("user");
            allRecipes = null;
            try {
                allRecipes = recommService.getRecommendation(u.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            allRecipes = recipeService.getAllRecipes();
        }
		Random rand = new Random();
		int randTaste = rand.nextInt(2);
		
		Recipe[] taste = searchService.sortByRate(allRecipes, "taste");		
		String[] response = new String[3];
		
		String[] urls;
		response[0] = taste[randTaste].getRecipe_id() + "";
		response[1] = taste[randTaste].getName();
		urls = recipeService.getRecipeAllPhotoUrl(Integer.parseInt(response[0]));
		response[2] = urls[0];

		return response;
	}
	
	@RequestMapping(value = "/bestOfCostCategory")
	@ResponseBody
	public String[] getBestOfCost(HttpSession session){
		Object logged = session.getAttribute("isLogged");
        Recipe[] allRecipes = null;
        boolean isLogged = logged == null ? false : (Boolean) logged;
        if (isLogged) {
            User u = (User) session.getAttribute("user");
            allRecipes = null;
            try {
                allRecipes = recommService.getRecommendation(u.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            allRecipes = recipeService.getAllRecipes();
        }
		Random rand = new Random();
		int randCost = rand.nextInt(2);

		Recipe[] cost = searchService.sortByRate(allRecipes, "cost");
		String[] response = new String[3];
		
		String[] urls;
		response[0] = cost[randCost].getRecipe_id() + "";
		response[1] = cost[randCost].getName();
		urls = recipeService.getRecipeAllPhotoUrl(Integer.parseInt(response[0]));
		response[2] = urls[0];

		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/sortBy")
	public String sortBy(
			@RequestParam(value = "results", required = true) String results,
			@RequestParam(value = "type", required = true) String type){
		
		//Convert from JSON String
		Gson gson = new Gson();
		Recipe[] recipes = gson.fromJson(results, Recipe[].class);
		
		Recipe[] sortedRecipes=searchService.sortByRate(recipes, type);
		
		return jsonEncode(sortedRecipes);
	}
	
	private String jsonEncode(Recipe[] sortedRecipes){
		String answer="";
		if(sortedRecipes != null){
			answer += "[";
			for(int i=0; i<sortedRecipes.length; i++){
				String[] photoUrls = recipeService.getRecipeAllPhotoUrl(sortedRecipes[i].getRecipe_id());
				answer += "{\"name\":\"" + sortedRecipes[i].getName() +"\", \"id\":\"" + sortedRecipes[i].getRecipe_id() + "\", \"photoUrl\":\"" + photoUrls[0] + "\"}";
				if(i != (sortedRecipes.length-1)) {
					answer += ",";
				}
			}
			answer += "]";
		}
		return answer;
	}
	
}

