package cmpesweng2014.group1.nutty.model;

import java.sql.Timestamp;

public class Event implements Comparable<Event>{
	private User user;
	private int is_act_allowed;
	private String action;
	private Object target;
	private String target_photo_url;
	private Timestamp timestamp;
	
	public Event() {
		super();
	}

	public Event(User user, int  is_act_allowed, String action, Object target, String target_photo_url, Timestamp timestamp) {
		super();
		this.user = user;
		this.setIs_act_allowed(is_act_allowed);
		this.action = action;
		this.target = target;
		this.setTarget_photo_url(target_photo_url);
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getIs_act_allowed() {
		return is_act_allowed;
	}

	public void setIs_act_allowed(int is_act_allowed) {
		this.is_act_allowed = is_act_allowed;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getTarget_photo_url() {
		return target_photo_url;
	}

	public void setTarget_photo_url(String target_photo_url) {
		this.target_photo_url = target_photo_url;
	}

	public int compareTo(Event other) {
		return this.timestamp.compareTo(other.timestamp);
	}
}
