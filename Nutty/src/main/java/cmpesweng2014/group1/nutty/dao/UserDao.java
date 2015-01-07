package cmpesweng2014.group1.nutty.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import cmpesweng2014.group1.nutty.model.Comment;
import cmpesweng2014.group1.nutty.model.EatLikeRate;
import cmpesweng2014.group1.nutty.model.OwnsRecipe;
import cmpesweng2014.group1.nutty.model.PrivacyOptions;
import cmpesweng2014.group1.nutty.model.User;
import cmpesweng2014.group1.nutty.model.UserRecipeScore;

@Component
public class UserDao extends PcDao {

	@Autowired
	private RecipeDao recipeDao;
	@Autowired
	private CommentDao commentDao;
	/**
	 * Creates new user.
	 * @param email String, email address of the user
	 * @param password String, password provided by the user
	 * @param name String, name of the user
	 * @param surname String, surname of the user
	 * @param birthday Date
	 * @param gender integer, 1 is it is female, 0 is male
	 * @param photo
	 * @return created user object
	 */
	public Long createUser(final String email, final String password,
			final String name, final String surname, final Date birthday, final Integer gender,final String photo) {
		final String query = "INSERT INTO User (email, password, name, surname, birthday, gender, isBanned, photo) VALUES (?,?,?,?,?,?,?,?)";

		final String defaultPhoto = "http://i.imgur.com/YyzTO03.jpg";
		
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
				if(photo.equals("")){
					ps.setString(8, defaultPhoto);
				}else{
					ps.setString(8, photo);
				}
				return ps;
			}
		}, gkh);

		Long newItemId = gkh.getKey().longValue();

		return newItemId;
	}
	/**
	 * Updates user information
	 * @param u User
	 */
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
	/**
	 * Get user object by providing email 
	 * @param email
	 * @return User object
	 */
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
	/**
	 * Returns user object with the given id
	 * @param id
	 * @return User object
	 */
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
	/**
	 * Return recipes of the given user
	 * @param id
	 * @return recipe ids as an array
	 */
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
	/**
	 * Return which recipes were eaten by this user
	 * @param user_id
	 * @return recipe id s as an array 
	 */
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
	/**
	 * Get token id for the given user_id
	 * @param user_id
	 * @return token_id String
	 */
	public String getTokenById(long user_id){	
		String token_id;
		try{
			token_id = this.getTemplate().queryForObject(
				"SELECT token_id FROM PassResetRequests WHERE user_id = ?",
				new Object[] { user_id }, String.class);
		}
		catch(DataAccessException e){
			token_id = null;
		}
		return token_id;
	}
	/**
	 * Get time value for the reset password request
	 * @param user_id
	 * @return time value
	 */
	public Timestamp getTimestampById(Long user_id){	
		Timestamp timestamp;
		try{
			timestamp = this.getTemplate().queryForObject(
				"SELECT date FROM PassResetRequests WHERE user_id = ?",
				new Object[] { user_id }, Timestamp.class);
		}
		catch(DataAccessException e){
			timestamp = null;
		}
		return timestamp;
	}
	/**
	 * adds password reset request to the database
	 * @param token_id
	 * @param user_id
	 */
	public void addPasswordResetRequest(final String token_id, final long user_id){
		// if there are old password reset requests delete them
		if(!emptyCheckPassResetRequest(user_id)){
			deletePasswordResetRequest(user_id);
		}
		final String query = "INSERT INTO PassResetRequests (token_id, user_id) VALUES (?,?)";
		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, token_id);
				ps.setLong(2, user_id);
				return ps;
			}
		});
	}
	/**
	 * deletes password reset request for the given user
	 * @param user_id 
	 */
	public void deletePasswordResetRequest(final Long user_id){
		this.getTemplate().update("DELETE FROM PassResetRequests WHERE user_id = ?", 
				new Object[] { user_id});
	}
	/**
	 * adding follow request
	 * @param follower_id, who will follow
	 * @param followed_id, who will be followed
	 */
	public void addFollowRequest(final long follower_id, final long followed_id){
		final String query = "INSERT INTO `FollowRequests` (follower_id, followed_id) VALUES (?,?)";
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
	/**
	 * deletes follow request
	 * @param follower_id, who wanted to follow
	 * @param followed_id, who will be followed
	 */
	public void deleteFollowRequest(final long follower_id, final long followed_id){
		this.getTemplate().update("DELETE FROM FollowRequests WHERE follower_id = ? AND followed_id=?", 
				new Object[] { follower_id, followed_id });
	}
	/**
	 * returns users who requested to follow the given user
	 * @param user_id, owner of the requests
	 * @return user objects
	 */
	public User[] getFollowRequests(long user_id){
		List<User> followRequesterList = this.getTemplate().query(
				"SELECT user_id, email, password, name, surname, "
				+ "birthday, gender, isBanned, photo FROM User, FollowRequests "
				+ "WHERE followed_id =? AND user_id=follower_id",
				new Object[] { user_id  }, new UserRowMapper());
	
		if (followRequesterList.isEmpty()) {
			return null;
		}
		else{
			User[] followRequesters = followRequesterList.toArray(new User[followRequesterList.size()]);
			return followRequesters;
		}
	}
	/**
	 * add follower
	 * @param follower_id, who is following
	 * @param followed_id, who will be followed
	 */
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
	/**
	 * cancel following
	 * @param follower_id
	 * @param followed_id
	 */
	public void unfollow(final long follower_id, final long followed_id){
		this.getTemplate().update("DELETE FROM Follows WHERE follower_id = ? AND followed_id=?", 
				new Object[] { follower_id, followed_id });
	}
	/**
	 * returns the list of followers 
	 * @param user_id, get followers of this recipe
	 * @return user objects
	 */
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
	/**
	 * returns following list of the user
	 * @param user_id
	 * @return user objects
	 */
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
	/**
	 * returns userRecipeScore objects for the given user
	 * @param user_id
	 * @return
	 */
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
	/**
	 * searches for users with the given string
	 * @param search String, query
	 * @return user list
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
	/**
	 * Adds privacy options for the given user
	 * @param user_id
	 */
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
	/**
	 * updates privacy options for the given user
	 * @param user_id
	 * @param column String, which option will be updated
	 * @param value integer, new value for that column
	 */
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
	/**
	 * returns privacy options for the given user
	 * @param user_id
	 * @return PrivacyOptions object
	 */
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
	/**
	 * get privacy option value for a given column
	 * @param user_id
	 * @param column
	 * @return
	 */
	public Integer getPrivacyOptionValue(long user_id, final String column) {		
		Integer po;
		try{
			po = this.getTemplate().queryForObject(
				"SELECT " + column + " FROM PrivacyOption WHERE user_id = ?",
				new Object[] { user_id }, Integer.class);
		}
		catch(DataAccessException e){
			po = null;
		}
		return po;
	}
	/**
	 * checks whether privacy options exist for this user or not
	 * @param user_id
	 * @return true if no previous privacy option
	 */
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
	/**
	 * Checks whether there exists a password reset request for that user
	 * @param user_id
	 * @return true if there is no request
	 */
	public boolean emptyCheckPassResetRequest(long user_id){
		int count = this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM PassResetRequests WHERE user_id=?",
				new Object[] { user_id  },  Integer.class);
		if (count == 0) {
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * Deletes the account of the given user
	 * @param user_id
	 */
	public void deactivateAccount(long user_id){
		//delete comments of this user
		Comment[] comments=commentDao.commentsOfUser(user_id);
		if(comments!=null){
			for(int i=0;i<comments.length;i++){
				commentDao.deleteComment(comments[i].getComment_id());
			}
		}
		//delete from EatLikeRate table
		this.getTemplate().update("DELETE FROM EatLikeRate WHERE user_id = ?",
				new Object[] {user_id});
		//delete from followRequests table
		this.getTemplate().update("DELETE FROM FollowRequests WHERE follower_id = ? OR followed_id = ?",
				new Object[] {user_id, user_id});
		//delete from follows table
		this.getTemplate().update("DELETE FROM Follows WHERE follower_id = ? OR followed_id = ?",
				new Object[] {user_id, user_id});
		//delete from HasSelection table
		this.getTemplate().update("DELETE FROM HasSelection WHERE user_id = ?",
				new Object[] {user_id});
		//delete from LikesComment table
		this.getTemplate().update("DELETE FROM LikesComment WHERE user_id = ?",
				new Object[] {user_id});
		//delete recipes of that user
		int[] recipes=getRecipes(user_id);
		if(recipes!=null){
			for(int i=0; i<recipes.length;i++){
				recipeDao.deleteRecipe(i);
			}
		}
		//delete privacy options
		this.getTemplate().update("DELETE FROM PrivacyOption WHERE user_id = ?",
				new Object[] {user_id});
		//delete from ReportComment table
		this.getTemplate().update("DELETE FROM ReportComment WHERE user_id = ?",
				new Object[] {user_id});
		//delete from ReportsRecipe table
		this.getTemplate().update("DELETE FROM ReportsRecipe WHERE user_id = ?",
				new Object[] {user_id});
		//delete from SharesRecipe table
		this.getTemplate().update("DELETE FROM SharesRecipe WHERE user_id = ?",
				new Object[] {user_id});
		//delete from Unprefer table
		this.getTemplate().update("DELETE FROM Unprefer WHERE user_id = ?",
				new Object[] {user_id});
		//delete from User table
		this.getTemplate().update("DELETE FROM User WHERE user_id = ?",
				new Object[] {user_id});
		//delete from UserBadge table
		this.getTemplate().update("DELETE FROM UserBadge WHERE user_id = ?",
				new Object[] {user_id});
		//delete from UserRecipeScore table
		this.getTemplate().update("DELETE FROM UserRecipeScore WHERE user_id = ?",
				new Object[] {user_id});
		this.getTemplate().update("DELETE FROM PassResetRequests WHERE user_id = ?",
				new Object[] {user_id});

		}

}
