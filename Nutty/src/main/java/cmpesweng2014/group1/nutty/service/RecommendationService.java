package cmpesweng2014.group1.nutty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.FoodSelectionDao;
import cmpesweng2014.group1.nutty.dao.IngredientAmountDao;
import cmpesweng2014.group1.nutty.dao.IngredientDao;
import cmpesweng2014.group1.nutty.dao.RecipeDao;
import cmpesweng2014.group1.nutty.model.IngredientAmount;

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
}
