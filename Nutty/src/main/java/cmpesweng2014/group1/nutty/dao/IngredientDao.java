package cmpesweng2014.group1.nutty.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.IngredientRowMapper;
import cmpesweng2014.group1.nutty.model.Ingredient;

@Component
public class IngredientDao extends PcDao{

	public int getCalorieById(int id){
		List<Ingredient> ingredients = this.getTemplate().query(
				 
				"SELECT Shrt_Desc as ing_name, Energ_Kcal as calorie, NDB_No as id FROM ingredients WHERE NDB_No= ? ",
				new Object[] { id }, new IngredientRowMapper());

		if (ingredients.isEmpty()) {
			return 0;
		} else {
			return ingredients.get(0).getCalorie();
		}
		
	}
	public int getIdByName(String name){
		List<Ingredient> ingredients = this.getTemplate().query(
				"SELECT Shrt_Desc as ing_name, Energ_Kcal as calorie, NDB_No as id FROM ingredients WHERE Shrt_Desc = ? ",
				new Object[] { name }, new IngredientRowMapper());

		if (ingredients.isEmpty()) {
			return 0;
		} else {
			return ingredients.get(0).getId();
		}
	}
	
	public Ingredient[] allIngredients(){
		List<Ingredient> ingredientList = this.getTemplate().query(
				"SELECT Shrt_Desc as ing_name, Energ_Kcal as calorie, NDB_No as id FROM ingredients",
				new Object[] {}, new IngredientRowMapper());
	
		if (ingredientList.isEmpty()) {
			return null;
		}
		else{
			Ingredient[] ingredients = ingredientList.toArray(new Ingredient[ingredientList.size()]);
			return ingredients;
		}
	}
	public Ingredient[] someIngredients(String filter) {
		List<Ingredient> ingredientList = this.getTemplate().query(
				"SELECT Shrt_Desc as ing_name, Energ_Kcal as calorie, NDB_No as id FROM ingredients WHERE Shrt_Desc LIKE " + filter,
				new Object[] {}, new IngredientRowMapper());
	
		if (ingredientList.isEmpty()) {
			return null;
		}
		else{
			Ingredient[] ingredients = ingredientList.toArray(new Ingredient[ingredientList.size()]);
			return ingredients;
		}
	}
}
