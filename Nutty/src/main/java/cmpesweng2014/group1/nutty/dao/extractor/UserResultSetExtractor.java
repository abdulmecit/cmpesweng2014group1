package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.User;

public class UserResultSetExtractor implements ResultSetExtractor<User> {

	@Override
	public User extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		User user = new User();
		user.setId(rs.getLong(1));
		user.setEmail(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setName(rs.getString(4));
		user.setSurname(rs.getString(5));
		user.setBirthday(rs.getDate(6));
		user.setGender(rs.getInt(7));
		user.setIsBanned(rs.getInt(8));
		user.setPhoto(rs.getString(9));
		return user;
	}

}
