package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.RecipeResultSetExtractor;
import cmpesweng2014.group1.nutty.model.Recipe;

public class RecipeRowMapper implements RowMapper<Recipe> {

	@Override
	public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException {
		RecipeResultSetExtractor ext = new RecipeResultSetExtractor();
		return ext.extractData(rs);
	}
}
