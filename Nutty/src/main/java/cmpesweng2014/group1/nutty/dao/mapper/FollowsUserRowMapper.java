package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.FollowsUserResultSetExtractor;
import cmpesweng2014.group1.nutty.model.FollowsUser;


public class FollowsUserRowMapper implements RowMapper<FollowsUser> {
	@Override
	public FollowsUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		FollowsUserResultSetExtractor ext = new FollowsUserResultSetExtractor();
		return ext.extractData(rs);
	}
}
