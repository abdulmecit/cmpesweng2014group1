package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.CommentResultSetExtractor;
import cmpesweng2014.group1.nutty.model.Comment;


public class CommentRowMapper implements RowMapper<Comment> {
	@Override
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
		CommentResultSetExtractor ext = new CommentResultSetExtractor();
		return ext.extractData(rs);
	}
}
