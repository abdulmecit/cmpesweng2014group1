package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.SharesRecipeResultSetExtractor;
import cmpesweng2014.group1.nutty.model.SharesRecipe;


public class SharesRecipeRowMapper implements RowMapper<SharesRecipe> {
	@Override
	public SharesRecipe mapRow(ResultSet rs, int rowNum) throws SQLException {
		SharesRecipeResultSetExtractor ext = new SharesRecipeResultSetExtractor();
		return ext.extractData(rs);
	}
}
