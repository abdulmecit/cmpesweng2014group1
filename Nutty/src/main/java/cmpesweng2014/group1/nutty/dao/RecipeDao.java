package cmpesweng2014.group1.nutty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import cmpesweng2014.group1.nutty.dao.mapper.RecipeRowMapper;
import cmpesweng2014.group1.nutty.model.Recipe;

public class RecipeDao extends PcDao{
	
	public int createRecipe(final String name, final String description,
			final int portion, final double total_calorie) {
		final String query = "INSERT INTO Recipe (name, description, portion, created, last_updated, total_calorie) VALUES (?,?,?,?,?,?)";

		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, name);
				ps.setString(2, description);
				ps.setInt(3, portion);
				ps.setDate(4,null);
				ps.setDate(5,null);
				ps.setDouble(6, total_calorie);
				return ps;
			}
		}, gkh);

		int newItemId = gkh.getKey().intValue();

		return newItemId;
	}
	
	public Recipe getRecipeById(int id) {
		
		List<Recipe> recipes = this.getTemplate().query(
				"SELECT * FROM Recipe WHERE recipe_id = ? ",
				new Object[] { id }, new RecipeRowMapper());

		if (recipes.isEmpty()) {
			return null;
		} else {
			return recipes.get(0);
		}
	}
	
	//can be changed, assumed gram value is given by the user and database has value for 100 gram
	public double calculateTotalCalorie(int[] ingredient_calories, double[] amounts){
		double total=0;
		for(int k=0; k<ingredient_calories.length; k++){
			total+=ingredient_calories[k]/100*amounts[k];
		}		
		return total;
	}
	
	public void addIngredient(final int ingredient_id, final int recipe_id, final double amount){
		final String query = "INSERT INTO HasIngredient (recipe_id, ing_id, amount) VALUES (?,?,?)";
		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, recipe_id);
				ps.setInt(2, ingredient_id);
				ps.setDouble(3, amount);
				return ps;
			}
		}, gkh);
	}

	public void addOwner(final int recipe_id, final Long user_id) {
		final String query = "INSERT INTO OwnsRecipe (user_id, recipe_id) VALUES (?,?)";
		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, user_id);
				ps.setInt(2, recipe_id);
				return ps;
			}
		}, gkh);	
	}
}
