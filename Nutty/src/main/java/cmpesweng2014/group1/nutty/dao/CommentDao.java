package cmpesweng2014.group1.nutty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.CommentRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.UserRowMapper;
import cmpesweng2014.group1.nutty.model.Comment;
import cmpesweng2014.group1.nutty.model.User;

@Component
public class CommentDao extends PcDao {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RecipeDao recipeDao;
	
	public int createComment(final String text, final long user_id, final int recipe_id){
		final String query = "INSERT INTO Comment (text, user_id, recipe_id) VALUES (?,?,?)";
		KeyHolder gkh = new GeneratedKeyHolder();
		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, text);
				ps.setLong(2, user_id);
				ps.setInt(3, recipe_id);
				return ps;
			}
		}, gkh);

		int newItemId = gkh.getKey().intValue();
		
		//score part
		//it this user- recipe relation does not exist in UserRecipeScore table,
		//insert this.
		final double insertScore=1.0;
		if(recipeDao.emptyCheckUserRecipeScore(userDao.getUserById(user_id),recipeDao.getRecipeById(recipe_id))){
				final String query2 = "INSERT INTO UserRecipeScore (user_id, recipe_id, comment_score) VALUES (?,?,?)";
				KeyHolder gkh2 = new GeneratedKeyHolder();
				this.getTemplate().update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(
							Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(query2,
								Statement.RETURN_GENERATED_KEYS);
						ps.setLong(1, user_id);
						ps.setInt(2, recipe_id);
						ps.setDouble(3, insertScore);
						return ps;
					}
				}, gkh2);
		}
		else{
			final String query2 = "UPDATE UserRecipeScore SET comment_score=? WHERE user_id=? AND recipe_id=?";
			KeyHolder gkh2 = new GeneratedKeyHolder();
				this.getTemplate().update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(
							Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(query2,
								Statement.RETURN_GENERATED_KEYS);
						ps.setDouble(1, insertScore);
						ps.setLong(2, user_id);
						ps.setInt(3, recipe_id);
						return ps;
					}
				}, gkh2);
		}		
		return newItemId;		
	}
	public Comment getCommentById(int comment_id){
		List<Comment> comments = this.getTemplate().query(
				"SELECT * FROM Comment WHERE comment_id = ? ",
				new Object[] { comment_id }, new CommentRowMapper());

		if (comments.isEmpty()) {
			return null;
		} else {
			return comments.get(0);
		}
	}
	
	public Comment[] allComments(int recipeId){
		List<Comment> commentList = this.getTemplate().query(
				"SELECT * FROM Comment WHERE recipe_id =?",
				new Object[] { recipeId  }, new CommentRowMapper());
	
		if (commentList.isEmpty()) {
			return null;
		}
		else{
			Comment[] comments = commentList.toArray(new Comment[commentList.size()]);
			return comments;
		}
	}
	public void likeComment(Comment comment, User user){
		final String query = "INSERT INTO LikesComment (user_id, comment_id) VALUES (?,?)";
		this.getTemplate().update(query, new Object[] { 
				user.getId(), comment.getComment_id()});
	}
	public int numberOfLikes(Comment comment) {
		int total=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM LikesComment WHERE comment_id = ?", 
				new Object[] {comment.getComment_id()}, Integer.class);
		return total;
	}
	
	public User[] usersWhoLikeThisComment(Comment comment) {
		List<User> userList = this.getTemplate().query(
				"SELECT a.* FROM User a, LikesComment b WHERE a.user_id = b.user_id AND b.comment_id =?",
				new Object[] { comment.getComment_id() }, new UserRowMapper());
	
		if (userList.isEmpty()) {
			return null;
		}
		else{
			User[] users = userList.toArray(new User[userList.size()]);
			return users;
		}
	}
	
	public void getBackLikeOfComment(Comment comment, User user){
		this.getTemplate().update("DELETE FROM LikesComment WHERE user_id = ? AND comment_id = ?", 
				new Object[] { user.getId(), comment.getComment_id()});
	}
}
