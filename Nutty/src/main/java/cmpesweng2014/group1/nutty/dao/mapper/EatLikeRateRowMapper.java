package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.EatLikeRateResultSetExtractor;
import cmpesweng2014.group1.nutty.model.EatLikeRate;

public class EatLikeRateRowMapper implements RowMapper<EatLikeRate>{
	@Override
	public EatLikeRate mapRow(ResultSet rs, int rowNum) throws SQLException {
		EatLikeRateResultSetExtractor ext = new EatLikeRateResultSetExtractor();
		return ext.extractData(rs);
	}
}
