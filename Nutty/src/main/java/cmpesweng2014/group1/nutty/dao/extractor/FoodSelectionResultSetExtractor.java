package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.FoodSelection;


public class FoodSelectionResultSetExtractor implements ResultSetExtractor<FoodSelection> {
	@Override
	public FoodSelection extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		FoodSelection foodSelection = new FoodSelection();
		foodSelection.setFs_id(rs.getInt(1));
		foodSelection.setFs_name(rs.getString(2));
		foodSelection.setType(rs.getString(3));	
		return foodSelection;
	}
}
