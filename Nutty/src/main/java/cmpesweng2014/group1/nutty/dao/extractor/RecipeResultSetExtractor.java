package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

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
		recipe.setCreatedDate(rs.getDate(5));
		recipe.setUpdatedDate(rs.getDate(6));
		recipe.setTotal_calorie(rs.getDouble(7));	
		return recipe;
	}
}
