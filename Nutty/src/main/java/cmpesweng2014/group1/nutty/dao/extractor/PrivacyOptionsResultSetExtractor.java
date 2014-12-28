package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.PrivacyOptions;


public class PrivacyOptionsResultSetExtractor implements ResultSetExtractor<PrivacyOptions>{
	@Override
	public PrivacyOptions extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		PrivacyOptions po = new PrivacyOptions();
		po.setUser_id(rs.getLong(1));
		po.setFollowable(rs.getInt(2));
		po.setVisible_health_condition(rs.getInt(3));
		po.setVisible_food_intolerance(rs.getInt(4));
		po.setVisible_not_pref(rs.getInt(5));
		return po;
	}
}
