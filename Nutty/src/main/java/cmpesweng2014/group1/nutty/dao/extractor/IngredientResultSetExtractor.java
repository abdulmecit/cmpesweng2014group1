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
		ingredient.setId(rs.getInt(1));
		ingredient.setName(rs.getString(2));
		ingredient.setCalorie(rs.getInt(4));	
		return ingredient;
	}
}
