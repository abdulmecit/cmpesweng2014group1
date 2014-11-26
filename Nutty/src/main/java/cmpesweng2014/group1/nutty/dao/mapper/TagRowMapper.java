package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.TagResultSetExtractor;
import cmpesweng2014.group1.nutty.model.Tag;


public class TagRowMapper implements RowMapper<Tag>{

	@Override
	public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
		TagResultSetExtractor ext = new TagResultSetExtractor();
		return ext.extractData(rs);
	}
}
