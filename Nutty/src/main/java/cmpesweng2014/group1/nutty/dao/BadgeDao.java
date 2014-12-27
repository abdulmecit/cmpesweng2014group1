package cmpesweng2014.group1.nutty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.BadgeRowMapper;
import cmpesweng2014.group1.nutty.model.Badge;

@Component
public class BadgeDao extends PcDao {

	public Long addBadge(final String name, final double min_score, final double max_score){
		final String query = "INSERT INTO Badge (name, min_score, max_score) VALUES (?,?,?)";

		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, name);
				ps.setDouble(2, min_score);
				ps.setDouble(3, max_score);
				
				return ps;
			}
		}, gkh);

		Long newItemId = gkh.getKey().longValue();

		return newItemId;
	}
	
	public void updateBadge(final Badge b){
		final String query = "UPDATE Badge SET name=?, min_value=?, max_value=? WHERE badge_id=?";

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, b.getName());
				ps.setDouble(2, b.getMin_score());
				ps.setDouble(3, b.getMax_score());
				ps.setLong(4, b.getBadge_id());
			
				return ps;
			}
		});
	}
	
	public Badge getBadgeById(int badge_id){
		List<Badge> badges = this.getTemplate().query(
				"SELECT * FROM Badge WHERE badge_id = ? ",
				new Object[] { badge_id }, new BadgeRowMapper());

		if (badges.isEmpty()) {
			return null;
		} else {
			return badges.get(0);
		}
	}
	public Badge getBadgeByScore(double score){
		
		List<Badge> badges = this.getTemplate().query(
				"SELECT * FROM Badge",
				new Object[] {}, new BadgeRowMapper());

		if (badges.isEmpty()) {
			return null;
		} else {
			for(int i=0;i<badges.size();i++){
				
				if(badges.get(i).getMin_score()<=score &&badges.get(i).getMax_score()>score){
					
					return badges.get(i);
				}
				
			}
			return null;
		}
	}
	public void updateUserBadge(final Long user_id,final int badge_id){
		
		int badgeId = this.getTemplate().queryForObject(
				"SELECT badge_id FROM UserBadge WHERE user_id=?",
				new Object[] {user_id}, Integer.class);
		

		if (badgeId==0) {
			final String query = "INSERT INTO UserBadge (user_id, badge_id) VALUES (?,?)";
			KeyHolder gkh = new GeneratedKeyHolder();

			this.getTemplate().update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query,
							Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, user_id);
					ps.setInt(2, badge_id);
					return ps;
				}
			}, gkh);
		} else {
			
			final String query = "UPDATE UserBadge SET badge_id=? WHERE user_id=?";
			KeyHolder gkh = new GeneratedKeyHolder();
			this.getTemplate().update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query,
							Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, badge_id);
					ps.setLong(2, user_id);
					return ps;
				}
			}, gkh);
		}	
	}
}
