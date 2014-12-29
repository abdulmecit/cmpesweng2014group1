package cmpesweng2014.group1.nutty.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
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

import cmpesweng2014.group1.nutty.model.FoodSelection;
import cmpesweng2014.group1.nutty.model.Message;
import cmpesweng2014.group1.nutty.model.PrivacyOptions;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.SuperUser;
import cmpesweng2014.group1.nutty.model.User;
import cmpesweng2014.group1.nutty.service.RecipeService;
import cmpesweng2014.group1.nutty.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RecipeService recipeService;
	
	// No longer splits from comma when a single item sent as an array parameter
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(
	        String[].class,
	        new StringArrayPropertyEditor(null)); 
	}
	
	@RequestMapping(value = "/profile/{userId}", method = RequestMethod.GET)
	public String viewProfile(@PathVariable Long userId, Model model, HttpSession session){
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (isLogged) {
			User u = (User) userService.getUserDao().getUserById(userId);
			model.addAttribute("visitedUser", u);

			User visitingUser = (User) session.getAttribute("user");
			boolean isFollower = userService.isFollower(visitingUser.getId(), u.getId());
			model.addAttribute("isFollower", isFollower);
			
			int numberOfFollowers = userService.getNumberOfFollowers(u.getId());
			model.addAttribute("numberOfFollowers", numberOfFollowers);
			
			int numberOfFollowing = userService.getNumberOfFollowing(u.getId());
			model.addAttribute("numberOfFollowing", numberOfFollowing);
			
			User[] followers = userService.getFollowerList(u.getId());
			model.addAttribute("followers", followers);
			
			User[] followings = userService.getFollowingList(u.getId());
			model.addAttribute("followings", followings);
			
			String badge = userService.getBadge(u.getId()).getName();
			model.addAttribute("badge", badge);
			
			return "profile";
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/preferences", method = RequestMethod.GET)
	public String viewFoodPreferences(Model model, HttpSession session) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (isLogged) {
			User u = (User) session.getAttribute("user");
			model.addAttribute("user", u);
			return "preferences";
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/preferences", method = RequestMethod.POST)
	public String addFoodPreferences(
			@RequestParam(value = "FoodSelection[]",required = false) String[] foodSelection,
			@RequestParam(value = "OtherPreferences[]", required = false) String[] unpreferred,
			RedirectAttributes redirectAttrs, HttpSession session) {
		User u = (User) session.getAttribute("user");

		userService.addFoodSelection(u, foodSelection);
		userService.addUnpreferredFood(u, unpreferred);
			
		redirectAttrs.addFlashAttribute("message", new Message(1, null, "Your selections are successfully added to the system."));
		return "redirect:/success";
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
			@RequestParam(value = "link", required = false) String photo,
			RedirectAttributes redirectAttrs, HttpSession session) throws ParseException {
		
		Message msg = new Message();

		if(changed.equals("name")){
			if (name.equals("")) {
				msg.setIsSuccess(0);
				msg.setMessage("Name cannot be empty!");
				redirectAttrs.addFlashAttribute("message", msg);
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
				redirectAttrs.addFlashAttribute("message", msg);
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
				redirectAttrs.addFlashAttribute("message", msg);
				return "redirect:/user/homesettings";
			}
			if(userService.getUserDao().getUserByEmail(email) != null){
				msg.setIsSuccess(0);
				msg.setMessage("This email address is already registered.");
				redirectAttrs.addFlashAttribute("message", msg);
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
				redirectAttrs.addFlashAttribute("message", msg);
				return "redirect:/user/homesettings";
			}

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			User u = (User) session.getAttribute("user");
			u.setPassword(encoder.encode(password));
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setMessage("You've successfully changed your password!");
			
		}else if (changed.equals("birthday")){
			if(Integer.valueOf(year) == 0 || Integer.valueOf(month) == 0 || Integer.valueOf(day) == 0){
				msg.setIsSuccess(0);
				msg.setMessage("You haven't selected all birthday fields!");
				redirectAttrs.addFlashAttribute("message", msg);
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
			
		}else if (changed.equals("photo")){
			
			if (photo.equals("")) {
				msg.setIsSuccess(0);
				msg.setMessage("Photo uploading haven't finished!");
				redirectAttrs.addFlashAttribute("message", msg);
				return "redirect:/user/homesettings";
			}
			
			User u = (User) session.getAttribute("user");
			u.setPhoto(photo);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setMessage("You've successfully changed your photo!");
			
		}else{
			msg.setIsSuccess(0);
			msg.setMessage("A problem has occurred when attempting to change your " + changed);	
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
			@RequestParam(value = "photo", required = false) String photo,
			@RequestParam(value = "user_id", required = true) Long user_id) throws ParseException {
		
		Message msg = new Message();
		User u = userService.getUserDao().getUserById(user_id);

		if(changed.equals("name")){
			if (name.equals("")) {
				msg.setIsSuccess(0);
				msg.setMessage("Name cannot be empty!");
				return msg;
			}

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
			
			if(userService.getUserDao().getUserByEmail(email) != null){
				msg.setIsSuccess(0);
				msg.setMessage("This email address is already registered.");
				return msg;
			}
					
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
			u.setPassword(encoder.encode(password));
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setData(u);
			msg.setMessage("You've successfully changed your password!");
			
		}else if (changed.equals("birthday")){
			if(Integer.valueOf(year) == 0 || Integer.valueOf(month) == 0 || Integer.valueOf(day) == 0){
				msg.setIsSuccess(0);
				msg.setMessage("You haven't selected all birthday fields!");
				return msg;
			}
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date birthdate = formatter.parse(year + "-" + month + "-" + day);
			java.sql.Date birthday = new java.sql.Date(birthdate.getTime());
							
			u.setBirthday(birthday);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setData(u);
			msg.setMessage("You've successfully changed your birthday to " + u.getBirthday());
			
		}else if (changed.equals("gender")){
			u.setGender(gender);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setData(u);
			msg.setMessage("You've successfully changed your gender!");
			
		}else if (changed.equals("photo")){
			
			if (photo.equals("")) {
				msg.setIsSuccess(0);
				msg.setMessage("Photo uploading haven't finished!");
				return msg;
			}
			
			u.setPhoto(photo);
			userService.getUserDao().updateUser(u);
			msg.setIsSuccess(1);
			msg.setData(u);
			msg.setMessage("You've successfully changed your photo!");
			
		}else{
			msg.setIsSuccess(0);
			msg.setMessage("A problem has occurred when attempting to change your " + changed);	
		}
		
		return msg;	
	}
	
	@RequestMapping(value = "/checkPassword")
	@ResponseBody
	public boolean checkPassword(
			@RequestParam(value = "password", required = true) String password,
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
	
	@RequestMapping(value = "/checkPasswordREST")
	@ResponseBody
	public boolean checkPassword(
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "user_id", required = true) Long user_id){
				
		if (password.equals("")) {
			return false;
		}
		User u = userService.getUserDao().getUserById(user_id);
		String realPassword = u.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(encoder.matches(password, realPassword)){
			return true;
		}else{
			return false;
		}	
	}
		
	@RequestMapping(value = "/followUser")
	public String followUser(
			@RequestParam(value = "follower_id", required = true) Long follower_id,
			@RequestParam(value = "followed_id", required = true) Long followed_id,
			@RequestParam(value = "value", required = true) int value
			) {

		int isFollowable = userService.getUserDao().getPrivacyOptionValue(followed_id, "followable");
		if(value == 0){
			userService.unfollow(follower_id, followed_id);		
		}
		else{
			if(isFollowable == 1)
				userService.addFollower(follower_id, followed_id);
			else
				userService.addFollowRequest(follower_id, followed_id);
		}
		return "profile";
	}
	
	@RequestMapping(value = "/getFollowRequests")
	public String getFollowRequests(
			@RequestParam(value = "user_id", required = true) Long user_id
			) {

		String answer = "";
		User[] users = userService.getUserDao().getFollowRequests(user_id);
		
		if(users == null){
			return answer;
		}
		answer = "user/profile";
		for(int i=0; i<users.length; i++){
			answer += "|" + users[i].getName() + " " + users[i].getSurname() + ">" + users[i].getId();
		}
		return answer;
	}
	
	@RequestMapping(value = "/answerFollowRequest")
	public String answerFollowRequest(
			@RequestParam(value = "follower_id", required = true) Long follower_id,
			@RequestParam(value = "followed_id", required = true) Long followed_id,
			@RequestParam(value = "value", required = true) int value
			) {

		if(value == 1){	//accept request
			userService.addFollower(follower_id, followed_id);
		}
		userService.deleteFollowRequest(follower_id, followed_id);
		return "profile";
	}
	
	@RequestMapping(value = "/getUsersRecipes")
	@ResponseBody
	public String[][] getUsersRecipes(
			@RequestParam(value = "userId", required = true) Long userId){
				
		if (userId == null) {
			return null;
		}
		
		int[] recipeIds = userService.getUserDao().getRecipes(userId);
		String[][] response = new String[recipeIds.length][3];
		for(int i=0; i<recipeIds.length; i++){
			response[i][0] = recipeIds[i] + "";
			response[i][1] = recipeService.getRecipe(recipeIds[i]).getName();
			String[] urls = recipeService.getRecipeAllPhotoUrl(recipeIds[i]);
			response[i][2] = urls[0];
		}
		return response;
	}
	
	@RequestMapping(value = "/getUsersEatenRecipes")
	@ResponseBody
	public String[][] getUsersEatenRecipes(
			@RequestParam(value = "userId", required = true) Long userId){
				
		if (userId == null) {
			return null;
		}
		
		int[] recipeIds = userService.getUserDao().getEatenRecipes(userId);
		String[][] response = new String[recipeIds.length][3];
		for(int i=0; i<recipeIds.length; i++){
			response[i][0] = recipeIds[i] + "";
			response[i][1] = recipeService.getRecipe(recipeIds[i]).getName();
			String[] urls = recipeService.getRecipeAllPhotoUrl(recipeIds[i]);
			response[i][2] = urls[0];
		}
		return response;
	}
	
	@RequestMapping(value = "/getSharedRecipes")
	@ResponseBody
	public String[][] getSharedRecipes(
			@RequestParam(value = "userId", required = true) Long userId){
				
		if (userId == null) {
			return null;
		}		
		int[] recipeIds = recipeService.getSharedRecipes(userId);
		String[][] response = new String[recipeIds.length][3];
		for(int i=0; i<recipeIds.length; i++){
			response[i][0] = recipeIds[i] + "";
			response[i][1] = recipeService.getRecipe(recipeIds[i]).getName();
			String[] urls = recipeService.getRecipeAllPhotoUrl(recipeIds[i]);
			response[i][2] = urls[0];
		}
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/foodSelectionREST")
	public FoodSelection[] getFoodSelections(
			@RequestParam(value = "user_id", required = true) Long user_id
			) {
		
		User u = (User) userService.getUserDao().getUserById(user_id);
		return userService.getFoodSelection(u);
	}
	
	@ResponseBody
	@RequestMapping(value = "/foodIntoleranceREST")
	public FoodSelection[] getFoodIntolerances(
			@RequestParam(value = "user_id", required = true) Long user_id
			) {
		
		User u = (User) userService.getUserDao().getUserById(user_id);
		return userService.getFoodIntolerances(u);
	}
	
	@ResponseBody
	@RequestMapping(value = "/healthConditionREST")
	public FoodSelection[] getHealthConditions(
			@RequestParam(value = "user_id", required = true) Long user_id
			) {
		
		User u = (User) userService.getUserDao().getUserById(user_id);
		return userService.getHealthCondition(u);
	}
	
	@ResponseBody
	@RequestMapping(value = "/unpreferREST")
	public String[] getUnpreferred(
			@RequestParam(value = "user_id", required = true) Long user_id
			) {
		
		User u = (User) userService.getUserDao().getUserById(user_id);
		return userService.getUnpreferredForUser(u);
	}
	
	@ResponseBody
	@RequestMapping(value = "/preferencesREST")
	public Message addFoodPreferencesREST(
			@RequestParam(value = "FoodSelection[]",required = false) String foodSelectionz,
			@RequestParam(value = "OtherPreferences[]", required = false) String unpreferredz,
			@RequestParam(value = "user_id", required = true) Long user_id
			){

		//Convert from JSON String
		Gson gson = new Gson();
		String[] foodSelection = gson.fromJson(foodSelectionz, String[].class);
		String[] unpreferred = gson.fromJson(unpreferredz, String[].class);
		
		User u = (User) userService.getUserDao().getUserById(user_id);
		userService.addFoodSelection(u, foodSelection);
		userService.addUnpreferredFood(u, unpreferred);
		
		return new Message(1, null, "Your selections are successfully added to the system.");
	}
	
	@ResponseBody
	@RequestMapping(value = "/isFollowerREST")
	public boolean isFollower(
			@RequestParam(value = "visiting_user_id", required = true) Long visiting_user_id,
			@RequestParam(value = "visited_user_id", required = true) Long visited_user_id
			) {
		
		return userService.isFollower(visiting_user_id, visited_user_id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getFollowerListREST")
	public User[] getFollowerList(
			@RequestParam(value = "user_id", required = true) Long user_id
			) {
		
		return userService.getFollowerList(user_id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getFollowingListREST")
	public User[] getFollowingList(
			@RequestParam(value = "user_id", required = true) Long user_id
			) {
		
		return userService.getFollowingList(user_id);
	}

	@ResponseBody
	@RequestMapping(value = "/userREST/{visited_user_id}")
	public SuperUser viewUserREST(@PathVariable Long visited_user_id){
		
		SuperUser su = new SuperUser();
		
		User visited_user = userService.getUserDao().getUserById(visited_user_id);
		su.setUser(visited_user);
		
		User[] followerList = userService.getFollowerList(visited_user_id);
		su.setFollowerList(followerList);
		
		User[] followingList = userService.getFollowingList(visited_user_id);
		su.setFollowingList(followingList);		
		
		int[] ownedRecipeIds = userService.getUserDao().getRecipes(visited_user_id);
		int recipeCount = ownedRecipeIds.length;
		Recipe[] ownedRecipes = new Recipe[recipeCount];
		String[] ownedRecipePictures = new String[recipeCount];
		
		for(int i=0; i<recipeCount; i++){
			ownedRecipes[i] = recipeService.getRecipe(ownedRecipeIds[i]);
			String[] urls = recipeService.getRecipeAllPhotoUrl(ownedRecipeIds[i]);
			ownedRecipePictures[i] = urls[0];
		}
		
		su.setOwnedRecipes(ownedRecipes);
		su.setOwnedRecipePictures(ownedRecipePictures);

		int[] sharedRecipeIds = recipeService.getSharedRecipes(visited_user_id);
		recipeCount = sharedRecipeIds.length;
		Recipe[] sharedRecipes = new Recipe[recipeCount];
		String[] sharedRecipePictures = new String[recipeCount];
		
		for(int i=0; i<recipeCount; i++){
			sharedRecipes[i] = recipeService.getRecipe(sharedRecipeIds[i]);
			String[] urls = recipeService.getRecipeAllPhotoUrl(sharedRecipeIds[i]);
			sharedRecipePictures[i] = urls[0];
		}
		
		su.setSharedRecipes(sharedRecipes);
		su.setSharedRecipePictures(sharedRecipePictures);
		
		return su;
	}
	
	@RequestMapping(value = "/privacy", method = RequestMethod.GET)
	public String viewPrivacySettings(Model model, HttpSession session) {
		Object logged = session.getAttribute("isLogged");
		boolean isLogged = logged == null ? false : (Boolean) logged;
		if (isLogged) {
			User u = (User) session.getAttribute("user");
			model.addAttribute("user", u);
			PrivacyOptions privOptions = userService.getUserDao().getPrivacyOptions(u.getId());
			model.addAttribute("privOptions", privOptions);
			return "privacy";
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/privacy", method = RequestMethod.POST)
	public String updatePrivacySettings(
			@RequestParam(value = "followable", required = true) int followable,
			@RequestParam(value = "visible_hc", required = true) int visible_hc,
			@RequestParam(value = "visible_fi", required = true) int visible_fi,
			@RequestParam(value = "visible_np", required = true) int visible_np,
			RedirectAttributes redirectAttrs, HttpSession session) {
		User u = (User) session.getAttribute("user");
		long user_id = u.getId();
		userService.getUserDao().updatePrivacyOption(user_id, "followable", followable);
		userService.getUserDao().updatePrivacyOption(user_id, "visible_health_condition", visible_hc);
		userService.getUserDao().updatePrivacyOption(user_id, "visible_food_intolerance", visible_fi);
		userService.getUserDao().updatePrivacyOption(user_id, "visible_not_pref", visible_np);		
		redirectAttrs.addFlashAttribute("message", new Message(1, null, "Your privacy settings are successfully updated."));
		return "redirect:/success";
	}
}
