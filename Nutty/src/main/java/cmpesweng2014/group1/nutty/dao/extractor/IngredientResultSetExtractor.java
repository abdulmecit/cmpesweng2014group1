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
		ingredient.setIng_name(rs.getString(2));
		ingredient.setCalorie(rs.getInt(3));
		ingredient.setFat(Double.valueOf((rs.getString(4)).replace(",",".")));
		ingredient.setProtein(Double.valueOf((rs.getString(5)).replace(",",".")));
		ingredient.setCarbohydrate(Double.valueOf((rs.getString(6)).replace(",",".")));
		return ingredient;
	}
}
