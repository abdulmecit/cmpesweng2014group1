package cmpesweng2014.group1.nutty.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.CommentDao;
import cmpesweng2014.group1.nutty.dao.IngredientAmountDao;
import cmpesweng2014.group1.nutty.dao.IngredientDao;
import cmpesweng2014.group1.nutty.dao.RecipeDao;
import cmpesweng2014.group1.nutty.model.Comment;
import cmpesweng2014.group1.nutty.model.Ingredient;
import cmpesweng2014.group1.nutty.model.IngredientAmount;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.Tag;
import cmpesweng2014.group1.nutty.model.User;

@Component
public class RecipeService {
	
	@Autowired
	private RecipeDao recipeDao;
	@Autowired
	private IngredientDao ingredientDao;
	@Autowired
	private IngredientAmountDao ingredientAmountDao;
	@Autowired
	private CommentDao commentDao;

	/**
	 * gets the recipeDao 
	 * 
	 * @return RecipeDao
	 */
	public RecipeDao getRecipeDao() {
		return recipeDao;
	}
	
	/**
	 * setter for recipeDao
	 * 
	 * @param recipeDao
	 */
	public void setRecipeDao(RecipeDao recipeDao) {
		this.recipeDao = recipeDao;
	}
	/**
	 * gets the ingredientDao 
	 * 
	 * @return IngredientDao
	 */
	public IngredientDao getIngredientDao() {
		return ingredientDao;
	}
	/**
	 * setter for ingredientDao
	 * 
	 * @param ingredientDao
	 */
	public void setIngredientDao(IngredientDao ingredientDao) {
		this.ingredientDao = ingredientDao;
	}
	/**
	 * gets the ingredientAmountDao 
	 * 
	 * @return IngredientAmountDao
	 */
	public IngredientAmountDao getIngredientAmountDao() {
		return ingredientAmountDao;
	}
	/**
	 * setter for ingredientAmountDao
	 * 
	 * @param ingredientAmountDao
	 */
	public void setIngredientAmountDao(IngredientAmountDao ingredientAmountDao) {
		this.ingredientAmountDao = ingredientAmountDao;
	}
	/**
	 * gets the commentDao 
	 * 
	 * @return commentDao
	 */
	public CommentDao getCommentDao() {
		return commentDao;
	}
	/**
	 * setter for commentDao
	 * 
	 * @param commentDao
	 */
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	/**
	 * 
	 * Creates new recipe.
	 * @param name String, name of the recipe
	 * @param description String, directions of the recipe
	 * @param portion integer, portion value of the recipe
	 * @param photo_urls String array, photo urls of the recipe
	 * @param ingredients String array, ingredient names for the recipe
	 * @param amounts String array, amount of the ingredients
	 * @param parsedAmounts double array, amounts of the ingredients in double format
	 * @param meas-types String array, units of the amounts
	 * @param user User, the user which adds the recipe
	 * @param tags String array, tags of the recipe
	 * @return Recipe object that is created now.
	 * */
	public Recipe createRecipe(String name, String description,
			int portion, String[] photo_urls, String[] ingredients, String[] amounts, double[] parsedAmounts, String[] meas_types, User user, String[] tags) {
		
		int[] ingredient_ids=new int[ingredients.length];
		int[] ingredient_calories=new int[ingredients.length];
		int ing_id;		
		//get an array of ingredient id s for the given ingredient names
		//get an array of calories for the given ingredients
		for(int i=0; i<ingredients.length; i++){
			ing_id=ingredientDao.getIdByName(ingredients[i]);
			ingredient_ids[i]=ing_id;
			ingredient_calories[i]=ingredientDao.getCalorieById(ing_id);
		}		
		//get total calorie value for the recipe
		double total_calorie = calculateTotalCalorie(ingredient_ids, ingredient_calories, parsedAmounts, meas_types);		
		
		//create new recipe
		int recipe_id=recipeDao.createRecipe(name, description, portion, total_calorie);
		//add tags
		for(int i=0; i<tags.length; i++){
			recipeDao.addTag(recipe_id, tags[i]);
		}		
		//ingredients are added to HasIngredient table
		for(int i=0; i<ingredients.length; i++){
			recipeDao.addIngredient(ingredient_ids[i], recipe_id, amounts[i], meas_types[i]);
		}		
		//Added to OwnsRecipe table
		recipeDao.addOwner(recipe_id, user.getId());			
		
		//Add photoUrl
		for(int i=0; i<photo_urls.length;i++){
			if(photo_urls[i] != "")
				recipeDao.addPhotoUrl(photo_urls[i], recipe_id);
		}
		
		return recipeDao.getRecipeById(recipe_id);	
	}	
	
	/** 
	 * Calculates the calorie of the recipe.
	 * @param ingredient_ids integer array, id s of the ingredients of this recipe
	 * @param ingredient_calories integer array, calorie values of each ingredient
	 * @param parsedAmounts double array, amounts of the ingredients in double format
	 * @param meas-types String array, units of the amounts
	 * @return the total calorie of the recipe
	 * */
	//calculates total calorie of a recipe
	public double calculateTotalCalorie(int ingredient_ids[], int[] ingredient_calories, double[] parsedAmounts, String[] meas_types){
		double total=0;
		for(int i=0; i<ingredient_calories.length; i++){
			if(meas_types[i].equals("gr")){
				total += (ingredient_calories[i]/100.0) * parsedAmounts[i];
			}
			else{
				double weight = recipeDao.getWeightByMeasType(ingredient_ids[i], meas_types[i]);
				total += (ingredient_calories[i]/100.0) * weight * parsedAmounts[i];
			}
		}		
		return new BigDecimal(String.valueOf(total)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * parses amount string to get the double value
	 * @param input String
	 * @return double amount
	 */
	public double parseAmount(String input){
		String s = input.replace(',', '.');
		if(s.contains("/")){
			String[] temp = s.split("/");
			if(temp.length != 2)
				return 0.0;
			int numerator, denominator;
			try{
				numerator = Integer.valueOf(temp[0]);
				denominator =  Integer.valueOf(temp[1]);
			}
			catch(NumberFormatException e){
				return 0.0;
			}
			if(denominator == 0)
				return 0.0;
			return (double) numerator / denominator;
		}
		double amount;
		try{
			amount = Double.valueOf(s);
		}
		catch(NumberFormatException e){
			return 0.0;
		}
		return amount;
	}
	/**
	 * returns created Comment
	 * @param text String, content of the comment
	 * @param user User, who adds the comment
	 * @param recipe Recipe, which recipe comment belongs to
	 * @return Comment object created now
	 */
	public Comment commentRecipe(String text, User user, Recipe recipe){
		int comment_id=commentDao.createComment(text,user.getId(), recipe.getRecipe_id());
		return commentDao.getCommentById(comment_id);
	}	
	/**
	 * returns recipe object to view its properties
	 * @param recipe_id integer, id of the recipe to return
	 * @return Recipe object
	 */
	public Recipe getRecipe(int recipe_id){
		return recipeDao.getRecipeById(recipe_id);
	}
	/**
	 * returns all ingredients and their amounts of a recipe
	 * @param recipe_id integer, id of the recipe to return information
	 * @return IngredientAmount array
	 */
	public IngredientAmount[] getIngredientAmounts(int recipe_id){
		return ingredientAmountDao.allIngredientAmounts(recipe_id);
	}
	/**
	 * returns all ingredients in the database
	 * @return ingredients in the database
	 */
	public Ingredient[] getAllIngredients(){
		return ingredientDao.allIngredients();
	}
	/**
	 * returns some ingredients according to a filter from the database
	 * @param filter String, a filter for searching ingredient
	 * @return Ingredient array
	 */
	public Ingredient[] getSomeIngredients(String filter){
		return ingredientDao.someIngredients(filter);
	}
	/**
	 * returns all comments of a recipe
	 * @param recipe_id integer, which recipe's comments will be returned
	 * @return Comment object array
	 */
	public Comment[] getComments(int recipe_id){
		return commentDao.allComments(recipe_id);
	}
	/**
	 * returns number of likes for this recipe
	 * @param recipe_id
	 * @return integer
	 */
	public int numberOfLikes(int recipe_id){
		return recipeDao.getEatLikeStatistic("likes",recipe_id);
	}
	/**
	 * returns number of eats for this recipe
	 * @param recipe_id
	 * @return number of likes integer
	 */
	public int numberOfEats(int recipe_id){
		return recipeDao.getEatLikeStatistic("eats",recipe_id);
	}
	/**
	 * returns average health rating for this recipe
	 * @param recipe_id
	 * @return health rate value for the recipe
	 */
	public double avgOfHealthRates(int recipe_id){
		return recipeDao.getAvgRateStatistic("health_rate", recipe_id);
	}
	/**
	 * returns average cost rating for this recipe
	 * @param recipe_id
	 * @return
	 */
	public double avgOfCostRates(int recipe_id){
		return recipeDao.getAvgRateStatistic("cost_rate", recipe_id);
	}
	/**
	 * returns average taste rating for this recipe
	 * @param recipe_id
	 * @return
	 */
	public double avgOfTasteRates(int recipe_id){
		return recipeDao.getAvgRateStatistic("taste_rate", recipe_id);
	}
	/**
	 * returns average ease rating for this recipe
	 * @param recipe_id
	 * @return
	 */
	public double avgOfEaseRates(int recipe_id){
		return recipeDao.getAvgRateStatistic("ease_rate", recipe_id);
	}
	/**
	 * returns total average rating for this recipe
	 * @param recipe_id
	 * @return
	 */
	public double avgOfAllRates(int recipe_id){
		double total=recipeDao.getAvgRateStatistic("ease_rate", recipe_id)+
				recipeDao.getAvgRateStatistic("taste_rate", recipe_id)+
				recipeDao.getAvgRateStatistic("cost_rate", recipe_id)+
				recipeDao.getAvgRateStatistic("health_rate", recipe_id);
		return total/4;
	}
	/**
	 * return voter count for health rate
	 * @param recipe_id
	 * @return
	 */
	public int voterCountHealth(int recipe_id){
		return recipeDao.getVoterCountForRate("health_rate", recipe_id);
	}
	/**
	 * return voter count for cost rate
	 * @param recipe_id
	 * @return
	 */
	public int voterCountCost(int recipe_id){
		return recipeDao.getVoterCountForRate("cost_rate", recipe_id);
	}
	/**
	 * return voter count for taste rate
	 * @param recipe_id
	 * @return
	 */
	public int voterCountTaste(int recipe_id){
		return recipeDao.getVoterCountForRate("taste_rate", recipe_id);
	}
	/**
	 * return voter count for ease rate
	 * @param recipe_id
	 * @return
	 */
	public int voterCountEase(int recipe_id){
		return recipeDao.getVoterCountForRate("ease_rate", recipe_id);
	}
	/**
	 * returns url of the recipe photo
	 * @param recipe_id
	 * @return
	 */
	public String[] getRecipeAllPhotoUrl(int recipe_id){
		return recipeDao.getAllPhotoUrl(recipe_id);
	}
	/**
	 * Given recipe id, it returns the owner id
	 * @param recipe_id
	 * @return owner id
	 */
	public Long getRecipeOwnerId(int recipe_id){
		return recipeDao.getOwnerId(recipe_id);
	}
	/**
	 * creates and returns the derived recipe, very similar to create recipe
	 * @param name
	 * @param description
	 * @param portion
	 * @param photo_url
	 * @param ingredients
	 * @param amounts
	 * @param parsedAmounts
	 * @param meas_types
	 * @param user
	 * @param originalRecipe
	 * @param tags
	 * @return created Recipe object
	 */
	//
	public Recipe deriveRecipe(String name, String description,
			int portion, String[] photo_url, String[] ingredients, String[] amounts, double[] parsedAmounts, String[] meas_types, User user, 
			Recipe originalRecipe, String[] tags){
		Recipe createdRecipe=createRecipe(name, description,
				portion, photo_url,ingredients,amounts, parsedAmounts, meas_types, user, tags);
		recipeDao.addDerivedFrom(originalRecipe, createdRecipe);
		return createdRecipe;
	}	
	/**
	 * return derived recipe list from the given recipe
	 * @param originalRecipe, recipe to get the derivations
	 * @return Recipe array, derivations
	 */
	public Recipe[] getDerivedRecipes(Recipe originalRecipe){
		return recipeDao.getAllDerivations(originalRecipe);
	}
	/**
	 * returns the parent recipe which the given recipe is derived from
	 * @param recipe
	 * @return Recipe object, parent recipe
	 */
	public Recipe getParentRecipe(Recipe recipe){
		return recipeDao.getParent(recipe);
	}
	/**
	 * user likes the comment
	 * @param comment, which will be liked
	 * @param user, who likes the recipe
	 */
	public void likeComment(Comment comment, User user){
		commentDao.likeComment(comment, user);
	}
	/**
	 * user gets back the like of a comment
	 * @param comment, which will not be liked any more
	 * @param user, who likes the recipe
	 */
	public void getBackLikeOfAComment(Comment comment, User user){
		commentDao.getBackLikeOfComment(comment, user);
	}	
	/**
	 * get likes of a comment
	 * @param comment
	 * @return number of likes
	 */
	public int numberOfLikesOfAComment(Comment comment){
		return commentDao.numberOfLikes(comment);
	}
	/**
	 * get users who like a particular comment
	 * @param comment, get likers of it
	 * @return User objects
	 */
	public User[] usersWhoLike(Comment comment){
		return commentDao.usersWhoLikeThisComment(comment);
	}
	/**
	 * get tags of a recipe
	 * @param recipe_id, to return tags
	 * @return Tag objects
	 */
	public Tag[] getAllTags(int recipe_id){
		return recipeDao.getAllTags(recipe_id);
	}
	/**
	 * get health rate of the given user for the given recipe
	 * @param recipe_id
	 * @param user_id
	 * @return rate
	 */
	public int getHealthRateForUser(int recipe_id, long user_id){
		return recipeDao.getRatesForUser("health_rate", recipe_id, user_id);
	}
	/**
	 * get taste rate of the given user for the given recipe
	 * @param recipe_id
	 * @param user_id
	 * @return rate
	 */
	public int getTasteRateForUser(int recipe_id, long user_id){
		return recipeDao.getRatesForUser("taste_rate", recipe_id, user_id);
	}
	/**
	 * get cost rate of the given user for the given recipe
	 * @param recipe_id
	 * @param user_id
	 * @return rate
	 */
	public int getCostRateForUser(int recipe_id, long user_id){
		return recipeDao.getRatesForUser("cost_rate", recipe_id, user_id);
	}
	/**
	 * get ease rate of the given user for the given recipe
	 * @param recipe_id
	 * @param user_id
	 * @return
	 */
	public int getEaseRateForUser(int recipe_id, long user_id){
		return recipeDao.getRatesForUser("ease_rate", recipe_id, user_id);
	}
	/**
	 * get like status of the given user for the given recipe
	 * @param recipe_id
	 * @param user_id
	 * @return
	 */
	public int getLikeForUser(int recipe_id, long user_id){
			return recipeDao.getRatesForUser("likes", recipe_id, user_id);
	}
	/**
	 * get eaten status of the given user for the given recipe
	 * @param recipe_id
	 * @param user_id
	 * @return
	 */
	public int getEatenForUser(int recipe_id, long user_id){
		return recipeDao.getRatesForUser("eats", recipe_id, user_id);
	}
	/**
	 * share recipe
	 * @param user_id
	 * @param recipe_id
	 */
	public void shareRecipe(long user_id, int recipe_id){
		recipeDao.shareRecipe(user_id, recipe_id);
	}	
	/**
	 * return shared recipes for the given user
	 * @param user_id
	 * @return
	 */
	public int[] getSharedRecipes(long user_id){
		return recipeDao.getSharedRecipes(user_id);
	}
	/**
	 * return true if shared, false not
	 * @param user_id
	 * @param recipe_id
	 * @return
	 */
	public int isShared(long user_id, int recipe_id){
		return recipeDao.isShared(user_id, recipe_id);
	}
	/**
	 * get back sharing recipe
	 * @param user_id
	 * @param recipe_id
	 */
	public void cancelShare(long user_id, int recipe_id){
		recipeDao.cancelShare(user_id, recipe_id);
	}
	/**
	 * Find intersection of two recipe arrays
	 * @param recipes1
	 * @param recipes2
	 * @return recipe array
	 */
	public Recipe[] findIntersection(Recipe[] recipes1, Recipe[] recipes2){
		if(recipes1 == null || recipes2 == null)
			return null;
		
		Set<Recipe> recipeSet = new LinkedHashSet<Recipe>(Arrays.asList(recipes1));
		Set<Recipe> intersection = new LinkedHashSet<Recipe>();

		for(int i=0; i<recipes2.length; i++){
			if(recipeSet.contains(recipes2[i]))
				intersection.add(recipes2[i]);
		}
		return intersection.toArray(new Recipe[intersection.size()]);
	}
	/**
	 * Edits the recipe
	 * @param recipe_id, recipe to edit
	 * @param name
	 * @param description
	 * @param portion
	 * @param photo_url
	 * @param ingredients
	 * @param amounts
	 * @param parsedAmounts
	 * @param meas_types
	 * @param tags
	 * @return edited recipe object
	 */
	public Recipe editRecipe(int recipe_id,String name, String description,int portion, String[] photo_url, String[] ingredients, String[] amounts, double[] parsedAmounts, String[] meas_types, String[] tags){
		int[] ingredient_ids=new int[ingredients.length];
		int[] ingredient_calories=new int[ingredients.length];
		int ing_id;		
		//get an array of ingredient id s for the given ingredient names
		//get an array of calories for the given ingredients
		for(int i=0; i<ingredients.length; i++){
			ing_id=ingredientDao.getIdByName(ingredients[i]);
			ingredient_ids[i]=ing_id;
			ingredient_calories[i]=ingredientDao.getCalorieById(ing_id);
		}		
		//get total calorie value for the recipe
		double total_calorie = calculateTotalCalorie(ingredient_ids, ingredient_calories, parsedAmounts, meas_types);		
		
		//edit recipe table
		recipeDao.editRecipeTable(recipe_id, name, description, portion, total_calorie);
		//delete previous tags
		recipeDao.deleteTags(recipe_id);
		//add tags
		for(int i=0; i<tags.length; i++){
			recipeDao.addTag(recipe_id, tags[i]);
		}		
		//delete previous ingredients
		recipeDao.deleteIngredients(recipe_id);
		//ingredients are added to HasIngredient table
		for(int i=0; i<ingredients.length; i++){
			recipeDao.addIngredient(ingredient_ids[i], recipe_id, amounts[i], meas_types[i]);
		}
		//delete photo
		recipeDao.deleteRecipePhoto(recipe_id);
		//Add photoUrl
		if(photo_url != null)
			for(int i=0;i<photo_url.length;i++)
					if(photo_url[i] != "")
						recipeDao.addPhotoUrl(photo_url[i], recipe_id);
		
		return recipeDao.getRecipeById(recipe_id);
	}
	
	public static Recipe[] all_recipes = null;
	/**
	 * Returns all the recipes in the database.
	 * @return
	 */
	public Recipe[] getAllRecipes(){
		if(all_recipes == null)
			all_recipes = recipeDao.getAllRecipes();
		return all_recipes;
	}
}
