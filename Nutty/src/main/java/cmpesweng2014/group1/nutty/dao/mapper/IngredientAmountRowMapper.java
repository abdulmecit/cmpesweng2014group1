package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.IngredientAmountResultSetExtractor;
import cmpesweng2014.group1.nutty.model.IngredientAmount;

public class IngredientAmountRowMapper implements RowMapper<IngredientAmount>{
	@Override
	public IngredientAmount mapRow(ResultSet rs, int rowNum) throws SQLException {
		IngredientAmountResultSetExtractor ext = new IngredientAmountResultSetExtractor();
		return ext.extractData(rs);
	}
}