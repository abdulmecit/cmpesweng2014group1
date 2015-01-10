package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.NutritionInfo;
import cmpesweng2014.group1.nutty.model.Recipe;

public class RecipeResultSetExtractor implements ResultSetExtractor<Recipe> {

	@Override
	public Recipe extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Recipe recipe = new Recipe();
		recipe.setRecipe_id(rs.getInt(1));
		recipe.setName(rs.getString(2));
		recipe.setDescription(rs.getString(3));
		recipe.setPortion(rs.getInt(4));
		recipe.setCreatedDate(rs.getTimestamp(5));
		recipe.setUpdatedDate(rs.getTimestamp(6));
		
		NutritionInfo nutrition_info = new NutritionInfo();
		nutrition_info.setTotal_calorie(rs.getDouble(7));
		nutrition_info.setTotal_fat(rs.getDouble(8));
		nutrition_info.setTotal_protein(rs.getDouble(9));
		nutrition_info.setTotal_carbohydrate(rs.getDouble(10));

		recipe.setNutrition_info(nutrition_info);	
		return recipe;
	}
}
