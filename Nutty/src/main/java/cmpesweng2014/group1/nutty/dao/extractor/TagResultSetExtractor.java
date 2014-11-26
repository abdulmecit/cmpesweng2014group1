package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.Tag;

public class TagResultSetExtractor implements ResultSetExtractor<Tag> {
	@Override
	public Tag extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Tag tag= new Tag();
		tag.setTag_id(rs.getInt(1));
		tag.setTag_name(rs.getString(2));
		
		return tag;
	}
}
