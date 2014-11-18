package cmpesweng2014.group1.nutty.service;


import org.springframework.beans.factory.annotation.Autowired;

import cmpesweng2014.group1.nutty.dao.IngredientDao;
import cmpesweng2014.group1.nutty.dao.RecipeDao;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.User;

public class RecipeService {
	@Autowired
	private RecipeDao recipeDao;
	private IngredientDao ingredientDao;

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
		int recipe_id=recipeDao.createRecipe(name, description, 
				portion, total_calorie);		

		//ingredients are added to HasIngredient table
		for(int i=0; i<ingredients.length; i++){
			recipeDao.addIngredient(ingredient_ids[i], recipe_id, amounts[i]);
		}
		
		//Added to OwnsRecipe table
		recipeDao.addOwner(recipe_id,user.getId());			
		return recipeDao.getRecipeById(recipe_id);
		
	}
	
}
