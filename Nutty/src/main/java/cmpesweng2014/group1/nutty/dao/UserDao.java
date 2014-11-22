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
	
	public void updateUser(User userModel){
		final Long id = userModel.getId();
		User userDB = this.getUserById(id);
		String newString = null;
		Date newDate = null;
		Integer newInt = (Integer) null;
		String column = "";
		int type = 0; //string = 0, Date = 1, Int = 2
		if(!userDB.getName().equals(userModel.getName())){
			column = "name";
			newString = userModel.getName();
		}else if(!userDB.getSurname().equals(userModel.getSurname())){
			column = "surname";
			newString = userModel.getSurname();
		}else if(!userDB.getEmail().equals(userModel.getEmail())){
			column = "email";
			newString = userModel.getEmail();
		}else if(!userDB.getPassword().equals(userModel.getPassword())){
			column = "password";
			newString = userModel.getPassword();
		}else if(!userDB.getBirthday().equals(userModel.getBirthday())){
			column = "birthday";
			newDate = (Date) userModel.getBirthday();
			type = 1;
		}else if(userDB.getGender() != userModel.getGender()){
			column = "gender";
			newInt = userModel.getGender();
			type = 2;
		}else if(userDB.getIsBanned() != userModel.getIsBanned()){
			column = "isBanned";
			newInt = userModel.getIsBanned();
			type = 2;
		}
		final String finalString = newString;
		final Date finalDate = newDate;
		final Integer finalInt = newInt;
		final Integer finalType = type;
		if(column == ""){
			//error message
		}else{
			final String query = "UPDATE User SET " + column + "=? WHERE user_id=?";
			KeyHolder gkh = new GeneratedKeyHolder();
			this.getTemplate().update(new PreparedStatementCreator() {
	
				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query,
							Statement.RETURN_GENERATED_KEYS);
					if(finalType == 0){
						ps.setString(1, finalString);
						ps.setLong(2, id);
					}else if(finalType == 1){
						ps.setDate(1, finalDate);
						ps.setLong(2, id);
					}else if(finalType == 2){
						ps.setInt(1, finalInt);
						ps.setLong(2, id);
					}
					return ps;
				}
			}, gkh);
		}
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
