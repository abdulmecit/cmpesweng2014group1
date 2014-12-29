package cmpesweng2014.group1.nutty.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.EatLikeRateRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.OwnsRecipeRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.PrivacyOptionsRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.UserRecipeScoreRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.UserRowMapper;
import cmpesweng2014.group1.nutty.model.EatLikeRate;
import cmpesweng2014.group1.nutty.model.OwnsRecipe;
import cmpesweng2014.group1.nutty.model.PrivacyOptions;
import cmpesweng2014.group1.nutty.model.User;
import cmpesweng2014.group1.nutty.model.UserRecipeScore;

@Component
public class UserDao extends PcDao {

	public Long createUser(final String email, final String password,
			final String name, final String surname, final Date birthday, final Integer gender,final String photo) {
		final String query = "INSERT INTO User (email, password, name, surname, birthday, gender, isBanned, photo) VALUES (?,?,?,?,?,?,?,?)";

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
				if(gender == null)
					ps.setNull(6, java.sql.Types.TINYINT);
				else
					ps.setInt(6, gender);
				ps.setInt(7, 0);
				ps.setString(8, photo);
				return ps;
			}
		}, gkh);

		Long newItemId = gkh.getKey().longValue();

		return newItemId;
	}
	
	public void updateUser(final User u){
		final String query = "UPDATE User SET name=?, surname=?, email=?, password=?, birthday=?, gender=?, isBanned=?, photo=? WHERE user_id=?";

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
				if(u.getGender() == null)
					ps.setNull(6, java.sql.Types.TINYINT);
				else
					ps.setInt(6,u.getGender());
				ps.setInt(7, u.getIsBanned());
				ps.setString(8, u.getPhoto());
				ps.setLong(9, u.getId());
				

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
	public int[] getEatenRecipes(Long user_id){
		int eats = 1;
		List<EatLikeRate> recipes = this.getTemplate().query(
				"SELECT * FROM EatLikeRate WHERE user_id = ? and eats=?",
				new Object[] { user_id, eats }, new EatLikeRateRowMapper());

		int recipeIds[] = new int[recipes.size()];
		for(int i=0; i<recipes.size(); i++){
			recipeIds[i] = recipes.get(i).getRecipeId();
		}
		return recipeIds;
	}	
	
	public void addFollowRequest(final long follower_id, final long followed_id){
		final String query = "INSERT INTO FollowRequests (follower_id, followed_id) VALUES (?,?)";
		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setLong(1, follower_id);
				ps.setLong(2, followed_id);

				return ps;
			}
		});
	}
	
	public void deleteFollowRequest(final long follower_id, final long followed_id){
		this.getTemplate().update("DELETE FROM FollowRequests WHERE follower_id = ? AND followed_id=?", 
				new Object[] { follower_id, followed_id });
	}
	
	public void addFollower(final long follower_id, final long followed_id){
		final String query = "INSERT INTO Follows (follower_id, followed_id) VALUES (?,?)";

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setLong(1, follower_id);
				ps.setLong(2, followed_id);

				return ps;
			}
		});
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
	
	
	public List<UserRecipeScore> getUserRecipeScore(long user_id){
		
		List<UserRecipeScore> scoreList = this.getTemplate().query(
				"SELECT user_id,recipe_id, "
						+ "COALESCE(eats_score,0)+COALESCE(health_rate_score,0) + "
						+ "COALESCE(add_score,0)+COALESCE(likes_score,0)+COALESCE(share_score,0)+"
						+ "COALESCE(ease_rate_score,0)+COALESCE(taste_rate_score,0)+"
						+ "COALESCE(comment_score,0) +COALESCE(cost_rate_score,0) "
						+ "AS 'score' FROM UserRecipeScore WHERE user_id =?",
						new Object[] { user_id }, new UserRecipeScoreRowMapper());

		
		if (scoreList.isEmpty()) {
			return null;
		}else{
			
			return scoreList;
		}
	}
	
/*	
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
*/	
	public List<User> searchUserByNameSurname(String search){
		String query = "SELECT a.* FROM User a, ( SELECT user_id, concat(name, ' ', surname) as fullName FROM `User` ) b WHERE a.user_id = b.user_id AND b.fullName LIKE ?";
		List<User> users = this.getTemplate().query(query,new Object[] { "%" + search + "%"  }, new UserRowMapper());
		
		if (users.isEmpty()) {
			return null;
		} else {
			return users;
		}
	}
	
	public void addPrivacyOptions(final long user_id){
		
		if(emptyCheckPrivacy(user_id)) {
			final String query = "INSERT INTO PrivacyOption (user_id, followable, visible_health_condition,"
					+ " visible_food_intolerance, visible_not_pref) VALUES (?,?,?,?,?)";
	
			this.getTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setLong(1, user_id);
					ps.setInt(2, 1);
					ps.setInt(3, 1);
					ps.setInt(4, 1);
					ps.setInt(5, 1);
					return ps;
				}
			});
		}
	}
	
	public void updatePrivacyOption(final long user_id, final String column, final int value){
		
		if(!emptyCheckPrivacy(user_id)) {
			final String query = "UPDATE PrivacyOption SET "+column+"=? WHERE user_id=?";
			this.getTemplate().update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setInt(1, value);
					ps.setLong(2, user_id);
					return ps;
				}
			});
		}
	}
	
	public PrivacyOptions getPrivacyOptions(long user_id) {		
		List<PrivacyOptions> privOptions = this.getTemplate().query(
				"SELECT * FROM PrivacyOption WHERE user_id = ? ",
				new Object[] { user_id }, new PrivacyOptionsRowMapper());

		if (privOptions.isEmpty()) {
			return null;
		} else {
			return privOptions.get(0);
		}
	}
	
	public Integer getPrivacyOptionValue(long user_id, final String column) {		
		Integer po;
		try{
			po = this.getTemplate().queryForObject(
				"SELECT * FROM PrivacyOption WHERE user_id = ? AND " + column + " = ?",
				new Object[] { user_id }, Integer.class);
		}
		catch(DataAccessException e){
			po = null;
		}
		return po;
	}
	
	public boolean emptyCheckPrivacy(long user_id){
		int count = this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM PrivacyOption WHERE user_id=?",
				new Object[] { user_id  },  Integer.class);
		if (count == 0) {
			return true;
		}
		else{
			return false;
		}
	}
	
}
