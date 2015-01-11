package cmpesweng2014.group1.nutty.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * 
	 * @param recipe_id
	 * @param user_id
	 * @return true if the user has already eaten this recipe
	 */ 
	public boolean isEaten(int recipe_id, long user_id){
		if((recipeDao.getRatesForUser("eats", recipe_id, user_id))!=0)
			return true;
		else
			return false;
	}	
	
	/**
	 * 
	 * @param recipe_id
	 * @param user_id
	 * @return true if user can eat (no selection/preference) false if user should not eat this
	 */
	public boolean canEat(int recipe_id, long user_id){
		//return true if user has selection for this recipe
		IngredientAmount[] ingredientAmounts = ingredientAmountDao.allIngredientAmounts(recipe_id);
		//if true not eat.
		boolean hasSelection=foodSelectionDao.hasSelection(ingredientAmounts,user_id);
		return (!hasSelection);
	}
	
	private Map<Long, Recipe[]> recommendedRecipeMap = new HashMap<Long, Recipe[]>();
	/**
	 * 
	 * @param user_id
	 * @param isFast whether the recommendations will be recalculated
	 * @return a recipe list of max size 7 to recommend user
	 * @throws Exception
	 */
	public Recipe[] getRecommendation(long user_id, boolean isFast) throws Exception{
		List<Recipe> recList;
		if(!isFast){
			recList = recipeDao.calculateRecommendation(user_id);
		
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
			if(finalList.size() == 0){
				//no appropriate recipe found, return random recipes
				recommendedRecipeMap.put(user_id, recipeService.getRecipeDao().getRandomRecipes());
			}
			else if(finalList.size() > 7){
				Collections.shuffle(finalList);
				recommendedRecipeMap.put(user_id, (finalList.subList(0, 7)).toArray(new Recipe[7]));
			}
			else{
				recommendedRecipeMap.put(user_id, finalList.toArray(new Recipe[finalList.size()]));
			}
		}
		return recommendedRecipeMap.get(user_id);
	}
}
