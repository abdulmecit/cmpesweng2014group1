package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.OwnsRecipeResultSetExtractor;
import cmpesweng2014.group1.nutty.model.OwnsRecipe;


public class OwnsRecipeRowMapper implements RowMapper<OwnsRecipe> {
	@Override
	public OwnsRecipe mapRow(ResultSet rs, int rowNum) throws SQLException {
		OwnsRecipeResultSetExtractor ext = new OwnsRecipeResultSetExtractor();
		return ext.extractData(rs);
	}
}
