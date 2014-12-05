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

import cmpesweng2014.group1.nutty.dao.mapper.OwnsRecipeRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.RecipeRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.UserRowMapper;
import cmpesweng2014.group1.nutty.model.OwnsRecipe;
import cmpesweng2014.group1.nutty.model.Recipe;
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
	
	public void updateUser(final User u){
		final String query = "UPDATE User SET name=?, surname=?, email=?, password=?, birthday=?, gender=?, isBanned=? WHERE user_id=?";

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, u.getName());
				ps.setString(2, u.getSurname());
				ps.setString(3, u.getEmail());
				ps.setString(4, u.getPassword());
				ps.setDate(5, (Date) u.getBirthday());
				ps.setInt(6,u.getGender());
				ps.setInt(7, u.getIsBanned());
				ps.setLong(8, u.getId());

				return ps;
			}
		});
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
	public int[] getRecipes(Long id){
		List<OwnsRecipe> recipes = this.getTemplate().query(
				"SELECT * FROM OwnsRecipe WHERE user_id = ?",
				new Object[] { id }, new OwnsRecipeRowMapper());

		int recipeIds[] = new int[recipes.size()];
		for(int i=0; i<recipes.size(); i++){
			recipeIds[i] = recipes.get(i).getRecipe_id();
		}
		return recipeIds;
	}	
	public void addFollower(final long follower_id, final long followed_id){
		final String query = "INSERT INTO Follows (follower_id, followed_id) VALUES (?,?)";

		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, follower_id);
				ps.setLong(2, followed_id);

				return ps;
			}
		}, gkh);
	}	
	public void unfollow(final long follower_id, final long followed_id){
		this.getTemplate().update("DELETE FROM Follows WHERE follower_id = ? AND followed_id=?", 
				new Object[] { follower_id, followed_id });
	}
	public User[] getFollowers(long user_id){
		List<User> followerList = this.getTemplate().query(
				"SELECT user_id, email, password, name, surname, "
				+ "birthday, gender, isBanned, photo FROM User, Follows "
				+ "WHERE followed_id =? AND user_id=follower_id",
				new Object[] { user_id  }, new UserRowMapper());
	
		if (followerList.isEmpty()) {
			return null;
		}
		else{
			User[] users = followerList.toArray(new User[followerList.size()]);
			return users;
		}
	}
	public User[] getFollowings(long user_id){
		List<User> followingList = this.getTemplate().query(
				"SELECT user_id, email, password, name, surname, "
				+ "birthday, gender, isBanned, photo FROM User, Follows "
				+ "WHERE follower_id =? AND user_id=followed_id",
				new Object[] { user_id  }, new UserRowMapper());
	
		if (followingList.isEmpty()) {
			return null;
		}
		else{
			User[] users = followingList.toArray(new User[followingList.size()]);
			return users;
		}
	}
	public List<User> searchUserByNameSurname(String search){
		String words[] = search.split(" ");
		String query = "SELECT * FROM User WHERE ";
		for(int i=0; i<words.length; i++){
			query += " name LIKE '%" + words[i] + "%' ";
			if(i<words.length-1){
				query += "OR";
			}
		}	
		query+="UNION SELECT * FROM User WHERE";
		for(int i=0; i<words.length; i++){
			query += " surname LIKE '%" + words[i] + "%' ";
			if(i<words.length-1){
				query += "OR";
			}
		}	
		List<User> users = this.getTemplate().query(
				query,
				new UserRowMapper());
		
		if (users.isEmpty()) {
			return null;
		} else {
			return users;
		}
	}
}
