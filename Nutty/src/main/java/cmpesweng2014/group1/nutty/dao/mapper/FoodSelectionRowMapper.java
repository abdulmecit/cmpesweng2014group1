package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.FoodSelectionResultSetExtractor;
import cmpesweng2014.group1.nutty.model.FoodSelection;


public class FoodSelectionRowMapper implements RowMapper<FoodSelection>{

	@Override
	public FoodSelection mapRow(ResultSet rs, int rowNum) throws SQLException {
		FoodSelectionResultSetExtractor ext = new FoodSelectionResultSetExtractor();
		return ext.extractData(rs);
	}
}
