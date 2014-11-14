package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.UserResultSetExtractor;
import cmpesweng2014.group1.nutty.model.User;


public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserResultSetExtractor ext = new UserResultSetExtractor();
		return ext.extractData(rs);
	}

}
