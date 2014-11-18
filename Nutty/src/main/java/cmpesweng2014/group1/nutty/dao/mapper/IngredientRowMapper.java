package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.IngredientResultSetExtractor;
import cmpesweng2014.group1.nutty.model.Ingredient;

public class IngredientRowMapper implements RowMapper<Ingredient>{
	@Override
	public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
		IngredientResultSetExtractor ext = new IngredientResultSetExtractor();
		return ext.extractData(rs);
	}
}
