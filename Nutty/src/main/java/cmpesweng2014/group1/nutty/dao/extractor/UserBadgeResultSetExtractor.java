package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.UserBadge;


public class UserBadgeResultSetExtractor implements ResultSetExtractor<UserBadge> {
	@Override
	public UserBadge extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		UserBadge userBadge = new UserBadge();
		userBadge.setBadge_id(rs.getInt(1));
		userBadge.setUser_id(rs.getLong(2));
		userBadge.setDate(rs.getTimestamp(3));
		return userBadge;
	}
}