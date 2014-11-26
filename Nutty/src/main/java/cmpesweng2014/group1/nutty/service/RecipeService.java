package cmpesweng2014.group1.nutty.service;


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

	public RecipeDao getRecipeDao() {
		return recipeDao;
	}

	public void setRecipeDao(RecipeDao recipeDao) {
		this.recipeDao = recipeDao;
	}
	//returns created recipe object 
	//give the user also to the function
	public Recipe createRecipe(String name, String description,
			int portion, int photo_id,String[] ingredients, double[] amounts, User user) {
		
		int[] ingredient_ids=new int[ingredients.length] ;
		int[] ingredient_calories=new int[ingredients.length]  ;
		int ing_id;		
		//get an array of ingredient id s for the given ingredient names
		//get an array of calories for the given ingredients
		for(int i=0; i<ingredients.length; i++){
			ing_id=ingredientDao.getIdByName(ingredients[i]);
			ingredient_ids[i]=ing_id;
			ingredient_calories[i]=ingredientDao.getCalorieById(ing_id);
		}		
		//get total calorie value for the recipe
		//for now assumed given amount is in gram and the database calorie has value for 100 gram
		double total_calorie=recipeDao.calculateTotalCalorie(ingredient_calories, amounts);		
		//create new recipe
		int recipe_id=recipeDao.createRecipe(name, description, portion, total_calorie);		
		//ingredients are added to HasIngredient table
		for(int i=0; i<ingredients.length; i++){
			recipeDao.addIngredient(ingredient_ids[i], recipe_id, amounts[i]);
		}		
		//Added to OwnsRecipe table
		recipeDao.addOwner(recipe_id,user.getId());			
		return recipeDao.getRecipeById(recipe_id);		
	}	
	
	//likes becomes 1 in the table
	public void likeRecipe(User user, Recipe recipe){
		int like=1;
		recipeDao.evaluateRecipe("likes",like,user,recipe);
	}	
	//dislike means getting like back, likes becomes 0 in the table
	public void dislikeRecipe(User user, Recipe recipe){
		int like=0;
		recipeDao.evaluateRecipe("likes",like,user,recipe);
	}
	//eats becomes 1 in the table
	public void eatRecipe(User user, Recipe recipe){
		int eat=1;
		recipeDao.evaluateRecipe("eats",eat,user,recipe);
	}		
	//getting eat back, eats becomes 0 in the table
	public void notEatRecipe(User user, Recipe recipe){
		int eat=0;
		recipeDao.evaluateRecipe("eats",eat,user,recipe);
	}
	//sets health rate
	public void setHealthRateRecipe(int score,User user, Recipe recipe){
		recipeDao.evaluateRecipe("health_rate",score,user,recipe);
	}
	//sets cost rate
	public void setCostRateRecipe(int score,User user, Recipe recipe){
		recipeDao.evaluateRecipe("cost_rate",score,user,recipe);
	}
	//sets taste rate
	public void setTasteRateRecipe(int score,User user, Recipe recipe){
		recipeDao.evaluateRecipe("taste_rate",score,user,recipe);
	}
	//sets ease rate
	public void setEaseRateRecipe(int score,User user, Recipe recipe){
		recipeDao.evaluateRecipe("ease_rate",score,user,recipe);
	}
	//returns created Comment
	public Comment commentRecipe(String text, User user, Recipe recipe){
		int comment_id=commentDao.createComment(text,user.getId(), recipe.getRecipe_id());
		return commentDao.getCommentById(comment_id);
	}	
	//returns recipe object to view its properties
	public Recipe getRecipe(int recipe_id){
		return recipeDao.getRecipeById(recipe_id);
	}
	
	//returns all ingredients and their amounts of a recipe
	public IngredientAmount[] getIngredientAmounts(int recipe_id){
		return ingredientAmountDao.allIngredientAmounts(recipe_id);
	}
	
	//returns all ingredients in the database
	public Ingredient[] getAllIngredients(){
		return ingredientDao.allIngredients();
	}
	
	//returns all comments of a recipe
	public Comment[] getComments(int recipe_id){
		return commentDao.allComments(recipe_id);
	}
	//returns number of likes for this recipe
	public int numberOfLikes(int recipe_id){
		return recipeDao.getEatLikeStatistic("likes",recipe_id);
	}
	//returns number of eats for this recipe
	public int numberOfEats(int recipe_id){
		return recipeDao.getEatLikeStatistic("eats",recipe_id);
	}
	//returns average health rating for this recipe
	public double avgOfHealthRates(int recipe_id){
		return recipeDao.getAvgRateStatistic("health_rate", recipe_id);
	}
	//returns average cost rating for this recipe
	public double avgOfCostRates(int recipe_id){
		return recipeDao.getAvgRateStatistic("cost_rate", recipe_id);
	}
	//returns average taste rating for this recipe
	public double avgOfTasteRates(int recipe_id){
		return recipeDao.getAvgRateStatistic("taste_rate", recipe_id);
	}
	//returns average ease rating for this recipe
	public double avgOfEaseRates(int recipe_id){
		return recipeDao.getAvgRateStatistic("ease_rate", recipe_id);
	}
	//returns url of the recipe photo
	public String getRecipePhotoUrl(int recipe_id){
		return recipeDao.getPhotoUrl(recipe_id);
	}
	public Long getRecipeOwnerId(int recipe_id){
		return recipeDao.getOwnerId(recipe_id);
	}
	//creates and returns the derived recipe
	public Recipe deriveRecipe(String name, String description,
			int portion, int photo_id,String[] ingredients, double[] amounts, User user, 
			Recipe originalRecipe){
		Recipe createdRecipe=createRecipe(name, description,
				portion, photo_id,ingredients,amounts,user);
		recipeDao.addDerivedFrom(originalRecipe, createdRecipe);
		return createdRecipe;
	}	
	//return derived recipe list from the given recipe
	public Recipe[] getDerivedRecipes(Recipe originalRecipe){
		return recipeDao.getAllDerivations(originalRecipe);
	}
	//returns the parent recipe which the given recipe is derived from
	public Recipe getParentRecipe(Recipe recipe){
		return recipeDao.getParent(recipe);
	}
	//user likes the comment
	public void likeComment(Comment comment, User user){
		commentDao.likeComment(comment, user);
	}
	//get likes of a comment
	public int numberOfLikesOfAComment(Comment comment){
		return commentDao.numberOfLikes(comment);
	}
	
}
