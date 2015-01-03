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
		comment.setComment_id(rs.getInt(1));
		comment.setText(rs.getString(2));
		comment.setUser_id(rs.getInt(3));
		comment.setRecipe_id(rs.getInt(4));
		comment.setTimestamp(rs.getTimestamp(5));
		return comment;
	}
}
