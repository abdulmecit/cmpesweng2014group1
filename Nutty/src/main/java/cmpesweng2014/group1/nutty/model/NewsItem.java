package cmpesweng2014.group1.nutty.model;

import java.util.Date;

public class NewsItem {
	private long user_id;
	private String action;
	private Object target;
	private Date time;
	
	public NewsItem() {
		super();
	}

	public NewsItem(long user_id, String action, Object target, Date time) {
		super();
		this.user_id = user_id;
		this.action = action;
		this.target = target;
		this.time = time;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	public int compareTo(NewsItem other) {
		return this.time.compareTo(other.time);
	}
}
