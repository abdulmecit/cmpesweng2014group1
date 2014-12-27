package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.Badge;


public class BadgeResultSetExtractor implements ResultSetExtractor<Badge> {
	@Override
	public Badge extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Badge badge = new Badge();
		badge.setBadge_id(rs.getInt(1));
		badge.setName(rs.getString(2));
		badge.setMin_score(rs.getInt(3));
		badge.setMax_score(rs.getInt(4));
		return badge;
	}
}
