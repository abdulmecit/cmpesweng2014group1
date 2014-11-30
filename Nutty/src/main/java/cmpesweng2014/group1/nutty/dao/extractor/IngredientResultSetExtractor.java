package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.Ingredient;

public class IngredientResultSetExtractor implements ResultSetExtractor<Ingredient>{
	@Override
	public Ingredient extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Ingredient ingredient = new Ingredient();
		ingredient.setIng_name(rs.getString(1));
		ingredient.setCalorie(rs.getInt(2));
		ingredient.setId(rs.getInt(3));	
		return ingredient;
	}
}
