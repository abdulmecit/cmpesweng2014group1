package cmpesweng2014.group1.nutty.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.IngredientRowMapper;
import cmpesweng2014.group1.nutty.model.Ingredient;

@Component
public class IngredientDao extends PcDao{
	/**
	 * get the ingredient for the given id
	 * @param id
	 * @return ingredient
	 */
	public Ingredient getIngredientById(int id){
		List<Ingredient> ingredients = this.getTemplate().query(
				 
				"SELECT NDB_No as id, Shrt_Desc as ing_name, Energ_Kcal as calorie, Lipid_Tot_g as fat,"
				+ " Protein_g as protein, Carbohydrt_g as carbohydrate FROM ingredients WHERE NDB_No= ? ",
				new Object[] { id }, new IngredientRowMapper());

		if (ingredients.isEmpty()) {
			return null;
		} else {
			return ingredients.get(0);
		}
		
	}
	/**
	 * get the ingredient for the given name
	 * @param name
	 * @return ingredient
	 */
	public Ingredient getIngredientByName(String name){
		List<Ingredient> ingredients = this.getTemplate().query(
				"SELECT NDB_No as id, Shrt_Desc as ing_name, Energ_Kcal as calorie, Lipid_Tot_g as fat,"
				+ " Protein_g as protein, Carbohydrt_g as carbohydrate FROM ingredients WHERE Shrt_Desc = ? ",
				new Object[] { name }, new IngredientRowMapper());

		if (ingredients.isEmpty()) {
			return null;
		} else {
			return ingredients.get(0);
		}
	}
	/**
	 * get all the ingredients in the database for autocomplete
	 * @return the list of ingredients
	 */
	public Ingredient[] allIngredients(){
		List<Ingredient> ingredientList = this.getTemplate().query(
				"SELECT NDB_No as id, Shrt_Desc as ing_name, Energ_Kcal as calorie, Lipid_Tot_g as fat,"
				+ " Protein_g as protein, Carbohydrt_g as carbohydrate FROM ingredients",
				new Object[] {}, new IngredientRowMapper());
	
		if (ingredientList.isEmpty()) {
			return null;
		}
		else{
			Ingredient[] ingredients = ingredientList.toArray(new Ingredient[ingredientList.size()]);
			return ingredients;
		}
	}
	/**
	 * get the ingredients with the given filter
	 * @param filter
	 * @return
	 */
	public Ingredient[] someIngredients(String filter) {
		List<Ingredient> ingredientList = this.getTemplate().query(
				"SELECT NDB_No as id, Shrt_Desc as ing_name, Energ_Kcal as calorie, Lipid_Tot_g as fat,"
				+ " Protein_g as protein, Carbohydrt_g as carbohydrate FROM ingredients WHERE Shrt_Desc LIKE ?",
				new Object[] {"%" + filter.toUpperCase() + "%"}, new IngredientRowMapper());
	
		if (ingredientList.isEmpty()) {
			return null;
		}
		else{
			Ingredient[] ingredients = ingredientList.toArray(new Ingredient[ingredientList.size()]);
			return ingredients;
		}
	}
}
