package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.UserRecipeScoreResultSetExtractor;
import cmpesweng2014.group1.nutty.model.UserRecipeScore;

public class UserRecipeScoreRowMapper implements RowMapper<UserRecipeScore> {

	@Override
	public UserRecipeScore mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserRecipeScoreResultSetExtractor ext = new UserRecipeScoreResultSetExtractor();
		return ext.extractData(rs);
	}

}
