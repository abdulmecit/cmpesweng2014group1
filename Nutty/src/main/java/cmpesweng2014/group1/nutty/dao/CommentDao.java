package cmpesweng2014.group1.nutty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import cmpesweng2014.group1.nutty.dao.mapper.CommentRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.EatLikeRateRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.UserRowMapper;
import cmpesweng2014.group1.nutty.model.Comment;
import cmpesweng2014.group1.nutty.model.EatLikeRate;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.User;

public class CommentDao extends PcDao {

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
	
	
	public Comment[] allComments(int recipe_id){
		List<Comment> commentList = this.getTemplate().query(
				"SELECT * FROM Comment WHERE recipe_id =?",
				new Object[] { recipe_id  }, new CommentRowMapper());
	
		if (commentList.isEmpty()) {
			return null;
		}
		else{
			Comment[] comments = commentList.toArray(new Comment[commentList.size()]);
			return comments;
		}
	}
}
