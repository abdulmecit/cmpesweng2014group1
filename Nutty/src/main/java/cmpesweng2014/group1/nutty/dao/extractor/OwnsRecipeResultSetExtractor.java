package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import cmpesweng2014.group1.nutty.model.OwnsRecipe;


public class OwnsRecipeResultSetExtractor implements ResultSetExtractor<OwnsRecipe> {
	@Override
	public OwnsRecipe extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		OwnsRecipe ownsRecipe = new OwnsRecipe();
		ownsRecipe.setUser_id(rs.getLong(1));
		ownsRecipe.setRecipe_id(rs.getInt(2));
		return ownsRecipe;
	}
}
