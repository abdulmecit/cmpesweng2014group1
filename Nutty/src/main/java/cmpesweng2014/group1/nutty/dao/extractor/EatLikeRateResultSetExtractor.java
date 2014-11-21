package cmpesweng2014.group1.nutty.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import cmpesweng2014.group1.nutty.model.EatLikeRate;


public class EatLikeRateResultSetExtractor implements ResultSetExtractor<EatLikeRate>{
	@Override
	public EatLikeRate extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		EatLikeRate eLikeR = new EatLikeRate();
		eLikeR.setUserId(rs.getInt(1));
		eLikeR.setRecipeId(rs.getInt(2));
		eLikeR.setEats(rs.getInt(3));
		eLikeR.setLikes(rs.getInt(4));
		eLikeR.setHealthRate(rs.getInt(5));
		eLikeR.setCostRate(rs.getInt(6));
		eLikeR.setTasteRate(rs.getInt(7));
		eLikeR.setEaseRate(rs.getInt(8));
		return eLikeR;
	}

}
