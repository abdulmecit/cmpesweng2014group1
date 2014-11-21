package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import cmpesweng2014.group1.nutty.model.Comment;


public class CommentResultSetExtractor implements ResultSetExtractor<Comment> {
	@Override
	public Comment extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Comment comment = new Comment();
		comment.setCommentId(rs.getInt(1));
		comment.setText(rs.getString(2));
		comment.setUserId(rs.getInt(3));
		comment.setRecipeId(rs.getInt(4));
		return comment;
	}
}
