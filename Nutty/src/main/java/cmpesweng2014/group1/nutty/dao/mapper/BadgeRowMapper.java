package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.BadgeResultSetExtractor;
import cmpesweng2014.group1.nutty.model.Badge;


public class BadgeRowMapper implements RowMapper<Badge> {
	@Override
	public Badge mapRow(ResultSet rs, int rowNum) throws SQLException {
		BadgeResultSetExtractor ext = new BadgeResultSetExtractor();
		return ext.extractData(rs);
	}
}
