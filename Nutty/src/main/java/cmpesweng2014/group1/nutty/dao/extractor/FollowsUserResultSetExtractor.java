package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.FollowsUser;


public class FollowsUserResultSetExtractor implements ResultSetExtractor<FollowsUser> {
	@Override
	public FollowsUser extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		FollowsUser followsUser = new FollowsUser();
		followsUser.setFollower_id(rs.getLong(1));
		followsUser.setFollowed_id(rs.getLong(2));
		followsUser.setDate(rs.getTimestamp(3));
		return followsUser;
	}
}