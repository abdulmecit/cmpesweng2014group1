package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.UserRecipeScore;

public class UserRecipeScoreResultSetExtractor implements ResultSetExtractor<UserRecipeScore> {

	@Override
	public UserRecipeScore extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		UserRecipeScore userRS = new UserRecipeScore();
		userRS.setUser_id(rs.getLong(1));
		userRS.setRecipe_id(rs.getInt(2));
		userRS.setScore(rs.getDouble(3));

		return userRS;
	}
}
