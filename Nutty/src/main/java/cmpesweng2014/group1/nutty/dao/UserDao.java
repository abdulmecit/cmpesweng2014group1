package cmpesweng2014.group1.nutty.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.UserRowMapper;
import cmpesweng2014.group1.nutty.model.User;

@Component
public class UserDao extends PcDao {

	public Long createUser(final String email, final String password,
			final String name, final String surname, final Date birthday, final int gender) {
		final String query = "INSERT INTO User (email, password, name, surname, birthday, gender, isBanned) VALUES (?,?,?,?,?,?,?)";

		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, email);
				ps.setString(2, password);
				ps.setString(3, name);
				ps.setString(4, surname);
				ps.setDate(5, birthday);
				ps.setInt(6,gender);
				ps.setInt(7, 0);

				return ps;
			}
		}, gkh);

		Long newItemId = gkh.getKey().longValue();

		return newItemId;
	}

	public User getUserByEmail(String email) {
		List<User> users = this.getTemplate().query(
				"SELECT * FROM User WHERE email = ? ",
				new Object[] { email }, new UserRowMapper());

		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}
	
	public User getUserById(Long id) {
		
		List<User> users = this.getTemplate().query(
				"SELECT * FROM User WHERE user_id = ? ",
				new Object[] { id }, new UserRowMapper());

		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}
}
