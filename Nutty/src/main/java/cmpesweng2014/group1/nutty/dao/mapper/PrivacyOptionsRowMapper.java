package cmpesweng2014.group1.nutty.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cmpesweng2014.group1.nutty.dao.extractor.PrivacyOptionsResultSetExtractor;
import cmpesweng2014.group1.nutty.model.PrivacyOptions;

public class PrivacyOptionsRowMapper implements RowMapper<PrivacyOptions>{
	@Override
	public PrivacyOptions mapRow(ResultSet rs, int rowNum) throws SQLException {
		PrivacyOptionsResultSetExtractor ext = new PrivacyOptionsResultSetExtractor();
		return ext.extractData(rs);
	}
}
