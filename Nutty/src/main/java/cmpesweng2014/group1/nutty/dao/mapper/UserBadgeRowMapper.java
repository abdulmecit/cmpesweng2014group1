package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.UserBadgeResultSetExtractor;
import cmpesweng2014.group1.nutty.model.UserBadge;


public class UserBadgeRowMapper implements RowMapper<UserBadge> {
	@Override
	public UserBadge mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserBadgeResultSetExtractor ext = new UserBadgeResultSetExtractor();
		return ext.extractData(rs);
	}
}
