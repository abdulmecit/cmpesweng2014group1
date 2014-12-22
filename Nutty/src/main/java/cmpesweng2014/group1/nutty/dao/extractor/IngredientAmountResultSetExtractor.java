package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.IngredientAmount;


public class IngredientAmountResultSetExtractor implements ResultSetExtractor<IngredientAmount>{

	public IngredientAmount extractData(ResultSet rs) throws SQLException, DataAccessException {
		IngredientAmount ingredientAmount = new IngredientAmount();
		ingredientAmount.setRecipe_id(rs.getInt(1));
		ingredientAmount.setIng_id(rs.getInt(2));
		ingredientAmount.setIng_name(rs.getString(3));
		ingredientAmount.setAmount(rs.getDouble(4));
		ingredientAmount.setMeas_type(rs.getString(5));
		return ingredientAmount;
	}
}
