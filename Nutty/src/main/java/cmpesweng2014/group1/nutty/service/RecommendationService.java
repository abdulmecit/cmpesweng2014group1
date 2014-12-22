package cmpesweng2014.group1.nutty.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.FoodSelectionDao;
import cmpesweng2014.group1.nutty.dao.IngredientAmountDao;
import cmpesweng2014.group1.nutty.dao.IngredientDao;
import cmpesweng2014.group1.nutty.dao.RecipeDao;
import cmpesweng2014.group1.nutty.model.IngredientAmount;
import cmpesweng2014.group1.nutty.model.Recipe;

@Component
public class RecommendationService {

	@Autowired
	private RecipeDao recipeDao;
	@Autowired
	private IngredientDao ingredientDao;
	@Autowired
	private FoodSelectionDao foodSelectionDao;
	@Autowired
	private IngredientAmountDao ingredientAmountDao;
	@Autowired
	private RecipeService recipeService;
	
	//return true if the user has already eaten this recipe
	public boolean isEaten(int recipe_id, long user_id){
		if((recipeDao.getRatesForUser("eats", recipe_id, user_id))!=0)
			return true;
		else
			return false;
	}	
	//returns true if user can eat (no selection/preference)
	//false if user should not eat this
	public boolean canEat(int recipe_id, long user_id){
		//return true if user has selection for this recipe
		IngredientAmount[] ingredientAmounts = ingredientAmountDao.allIngredientAmounts(recipe_id);
		//if true not eat.
		boolean hasSelection=foodSelectionDao.hasSelection(ingredientAmounts,user_id);
		return (!hasSelection);
	}
	
	public Recipe[] getRecommendation(long user_id) throws Exception{
		List<Recipe> recList=recipeDao.calculateRecommendation(user_id);
		Recipe[] rec;
		if(recList == null || recList.size()==0)
			rec = recipeService.getRecipeDao().getAllRecipes();
		else
			rec = recList.toArray(new Recipe[recList.size()]);
		List<Recipe> selList=new ArrayList<Recipe>();
		for(int i=0; i<rec.length;i++){
			if(canEat(rec[i].getRecipe_id(), user_id)){
				selList.add(rec[i]);
			}
		}
		List<Recipe> finalList = new ArrayList<Recipe>();
		for(int i=0; i<selList.size();i++){
			if(!isEaten(selList.get(i).getRecipe_id(), user_id)){
				finalList.add(selList.get(i));
			}
		}
		if(finalList.size()==0){
			//no recipe found, return without any filter
			return recipeService.getRecipeDao().getAllRecipes();
		}
		else{
			return finalList.toArray(new Recipe[finalList.size()]);
		}

	}	
}
