package cmpesweng2014.group1.nutty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.FoodSelectionRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.IngredientRowMapper;
import cmpesweng2014.group1.nutty.model.FoodSelection;
import cmpesweng2014.group1.nutty.model.Ingredient;

@Component
public class FoodSelectionDao extends PcDao {
	public int getFoodSelectionIdByName(String foodSelection){
		List<FoodSelection> foodSelections = this.getTemplate().query(
				"SELECT * FROM FoodSelection WHERE fs_name = ? ",
				new Object[] { foodSelection }, new FoodSelectionRowMapper());

		if (foodSelections.isEmpty()) {
			return 0;
		} else {
			return foodSelections.get(0).getFsId();
		}
	}
	public void addFoodSelection(final int fs_id, final long user_id){
		final String query = "INSERT INTO HasSelection (user_id, fs_id) VALUES (?,?)";

		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, user_id);
				ps.setInt(2, fs_id);
				return ps;
			}
		}, gkh);
	}
	
	public void addUnpreferredFood(final int ing_id, final long user_id){
		final String query = "INSERT INTO Unprefer (user_id, ing_id) VALUES (?,?)";

		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, user_id);
				ps.setInt(2, ing_id);
				return ps;
			}
		}, gkh);
	}
	
	public FoodSelection[] getFoodSelectionForUser(long user_id, String type){
		List<FoodSelection> fsList = this.getTemplate().query(
				"SELECT fs_id, fs_name, type FROM FoodSelection a, HasSelection b WHERE "
				+ " user_id= ? AND a.fs_id=b.fs_id AND a.type="+type,
				new Object[] { user_id }, new FoodSelectionRowMapper());

		if (fsList.isEmpty()) {
			return null;
		} else {
			FoodSelection[] foodSelections = fsList.toArray(new FoodSelection[fsList.size()]);
			return foodSelections;
		}
	}
	
	public Ingredient[] getUnpreferredFoodForUser(long user_id){
		List<Ingredient> ingList = this.getTemplate().query(
				"SELECT Shrt_Desc as ing_name, Energ_Kcal as calorie, NDB_No as id "
				+ "FROM ingredients a, Unprefer b  WHERE "
				+ " user_id= ? AND a.NDB_No=b.ing_id",
				new Object[] { user_id }, new IngredientRowMapper());
		if (ingList.isEmpty()) {
			return null;
		} else {
			Ingredient[] ingredients = ingList.toArray(new Ingredient[ingList.size()]);
			return ingredients;
		}
	}

}
