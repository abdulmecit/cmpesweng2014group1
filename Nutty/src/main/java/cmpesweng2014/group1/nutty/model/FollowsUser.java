package cmpesweng2014.group1.nutty.model;

import java.sql.Timestamp;

public class FollowsUser{
	private Long follower_id;
	private Long followed_id;
	private Timestamp date;
	
	public FollowsUser() {
		super();
	}

	public Long getFollower_id() {
		return follower_id;
	}

	public void setFollower_id(Long follower_id) {
		this.follower_id = follower_id;
	}

	public Long getFollowed_id() {
		return followed_id;
	}

	public void setFollowed_id(Long followed_id) {
		this.followed_id = followed_id;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
}
