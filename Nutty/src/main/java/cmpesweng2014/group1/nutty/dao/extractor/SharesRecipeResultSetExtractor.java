package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.SharesRecipe;


public class SharesRecipeResultSetExtractor implements ResultSetExtractor<SharesRecipe> {
	@Override
	public SharesRecipe extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		SharesRecipe sharesRecipe = new SharesRecipe();
		sharesRecipe.setUser_id(rs.getLong(1));
		sharesRecipe.setRecipe_id(rs.getInt(2));
		return sharesRecipe;
	}
}