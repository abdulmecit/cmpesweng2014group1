package cmpesweng2014.group1.nutty.model;

import java.sql.Timestamp;

public class UserBadge {
	private int badge_id;
	private Long user_id;
	private Timestamp date;
	
	public UserBadge() {
		super();
	}

	public int getBadge_id() {
		return badge_id;
	}

	public void setBadge_id(int badge_id) {
		this.badge_id = badge_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
}
