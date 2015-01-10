package cmpesweng2014.group1.nutty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.FoodSelectionRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.IngredientRowMapper;
import cmpesweng2014.group1.nutty.model.FoodSelection;
import cmpesweng2014.group1.nutty.model.Ingredient;
import cmpesweng2014.group1.nutty.model.IngredientAmount;

@Component
public class FoodSelectionDao extends PcDao {
	
	/**
	 * get the id of the food selection
	 * @param foodSelection
	 * @return
	 */
	public int getFoodSelectionIdByName(String foodSelection){
		List<FoodSelection> foodSelections = this.getTemplate().query(
				"SELECT * FROM FoodSelection WHERE fs_name = ? ",
				new Object[] { foodSelection }, new FoodSelectionRowMapper());

		if (foodSelections.isEmpty()) {
			return 0;
		} else {
			return foodSelections.get(0).getFs_id();
		}
	}
	/**
	 * delete food selections for the given user
	 * @param user_id
	 */
	public void deleteAllFoodSelection(long user_id){
		this.getTemplate().update("DELETE FROM HasSelection WHERE user_id = ?", 
				new Object[] { user_id});
	}	
	/**
	 * add food selections for the user
	 * @param fs_id
	 * @param user_id
	 */
	public void addFoodSelection(final int fs_id, final long user_id){			
		final String query = "INSERT INTO HasSelection (user_id, fs_id) VALUES (?,?)";

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setLong(1, user_id);
				ps.setInt(2, fs_id);
				return ps;
			}
		});
	}
	/**
	 * delete all unpreferred food data for the user
	 * @param user_id
	 */
	public void deleteAllUnpreferredFood(long user_id){
		this.getTemplate().update("DELETE FROM Unprefer WHERE user_id = ?", 
				new Object[] { user_id});
	}
	/**
	 * add unpreferred food group for the user
	 * @param user_id
	 * @param ing_grp_name
	 */
	public void addUnpreferredFood(final long user_id, final String ing_grp_name){
		final String query = "INSERT INTO Unprefer (user_id, ing_grp_name) VALUES (?,?)";

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setLong(1, user_id);
				ps.setString(2, ing_grp_name);
				return ps;
			}
		});
	}
	/**
	 * returns food selections for the given user
	 * @param user_id
	 * @return
	 */
	public FoodSelection[] getFoodSelectionForUser(long user_id){
		List<FoodSelection> fsList = this.getTemplate().query(
				"SELECT a.fs_id, a.fs_name, a.type FROM FoodSelection a, HasSelection b WHERE "
				+ " user_id= ? AND a.fs_id=b.fs_id",
				new Object[] { user_id}, new FoodSelectionRowMapper());

		if (fsList.isEmpty()) {
			return null;
		} else {
			FoodSelection[] foodSelections = fsList.toArray(new FoodSelection[fsList.size()]);
			return foodSelections;
		}
	}
	/**
	 * get food selections for the user for the given type(food intolerance/health condition)
	 * @param user_id
	 * @param type
	 * @return
	 */
	public FoodSelection[] getFoodSelectionForUser(long user_id, String type){
		List<FoodSelection> fsList = this.getTemplate().query(
				"SELECT a.fs_id, a.fs_name, a.type FROM FoodSelection a, HasSelection b WHERE "
				+ " user_id= ? AND a.fs_id=b.fs_id AND a.type=?",
				new Object[] { user_id, type }, new FoodSelectionRowMapper());

		if (fsList.isEmpty()) {
			return null;
		} else {
			FoodSelection[] foodSelections = fsList.toArray(new FoodSelection[fsList.size()]);
			return foodSelections;
		}
	}
	/**
	 * get unpreferred food for the given user
	 * @param user_id
	 * @return
	 */
	public String[] getUnpreferredFoodForUser(long user_id){		
		List<String> unpreferred = this.getTemplate().queryForList(
				"SELECT ing_grp_name FROM Unprefer WHERE user_id = ?",
				new Object[] { user_id  }, String.class);
	
		if (unpreferred.isEmpty()) {
			return null;
		}
		else{
			return unpreferred.toArray(new String[unpreferred.size()]);
		}
	}
	/**
	 * Takes the ingredient array and determines if the user shouldn't eat this recipe or not
	 * @param ingredientAmounts
	 * @param user_id
	 * @return true if there is an ingredient in the given array, that the user should not eat
	 */
	public boolean hasSelection(IngredientAmount[] ingredientAmounts, long user_id) {
		List<Ingredient> ingredientList = getFoodSelectionIngredients(user_id);
		List<Ingredient> unpreferredList = getUnpreferredIngredients(user_id);
		if(unpreferredList != null)
			ingredientList.addAll(unpreferredList);
		//now we have all the ingredient list
		List<Integer> ingredientIds = new ArrayList<Integer>();
		if(ingredientList != null){
			for(int i=0; i<ingredientList.size();i++){
				ingredientIds.add(ingredientList.get(i).getId());
			}
		}	
		if(ingredientAmounts != null){
			for(int i=0; i<ingredientAmounts.length;i++){
				if(ingredientIds.contains(ingredientAmounts[i].getIng_id())){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * return the list of ingredients for the food intolerance and health condition of the user
	 * @param user_id
	 * @return
	 */
	public List<Ingredient> getFoodSelectionIngredients(long user_id){
		FoodSelection[] foodIntolerance=getFoodSelectionForUser(user_id, "food_intolerance");
		FoodSelection[] healthCondition=getFoodSelectionForUser(user_id, "health_condition");

		List<Ingredient> ingredientList = new ArrayList<Ingredient>();
		//all ingredients that the user should not eat
		if(foodIntolerance != null){
			for(int i=0; i<foodIntolerance.length; i++){
				List<Ingredient> ingList=getIngredientsForSelection(foodIntolerance[i]);
				if(ingList != null)
					ingredientList.addAll(ingList);
			}
		}
		if(healthCondition != null){
			for(int i=0; i<healthCondition.length; i++){
				List<Ingredient> ingList=getIngredientsForSelection(healthCondition[i]);
				if(ingList != null)
					ingredientList.addAll(ingList);
			}
		}
		return ingredientList;
	}
	/**
	 * returns the unpreferred ingredients for that user
	 * @param user_id
	 * @return
	 */
	public List<Ingredient> getUnpreferredIngredients(long user_id) {
		List<Ingredient> ingList = this.getTemplate().query(
				"SELECT NDB_No as id, Shrt_Desc as ing_name, Energ_Kcal as calorie, Lipid_Tot_g as fat,"
				+ " Protein_g as protein, Carbohydrt_g as carbohydrate FROM ingredients a, Unprefer b"
				+ " WHERE user_id= ? AND a.Shrt_Desc LIKE CONCAT(b.ing_grp_name,',%') ",
				new Object[] { user_id }, new IngredientRowMapper());
		if (ingList.isEmpty()) {
			return null;
		} else {
			return ingList;
		}
		
	}
	/**
	 * returns all ingredients for the food selection (should not eat)
	 * @param foodSelection
	 * @return
	 */
	public List<Ingredient> getIngredientsForSelection(FoodSelection foodSelection){
		List<Ingredient> ingList = this.getTemplate().query(
				"SELECT NDB_No as id, Shrt_Desc as ing_name, Energ_Kcal as calorie, Lipid_Tot_g as fat,"
				+ " Protein_g as protein, Carbohydrt_g as carbohydrate FROM ingredients a, `Should(Not)Eat` b"
				+ " WHERE b.avoid_suggest=1 AND b.fs_id= ? AND a.NDB_No=b.ing_id",
				new Object[] { foodSelection.getFs_id() }, new IngredientRowMapper());
		if (ingList.isEmpty()) {
			return null;
		} else {
			return ingList;
		}
	}
	
	

}
