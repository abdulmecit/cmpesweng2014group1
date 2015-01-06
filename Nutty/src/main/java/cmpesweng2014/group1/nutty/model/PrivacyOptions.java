package cmpesweng2014.group1.nutty.model;

public class PrivacyOptions {
	private long user_id;
	private int followable;
	private int visible_health_condition;
	private int visible_food_intolerance;
	private int visible_not_pref;
	private int visible_activities;
	
	public PrivacyOptions() {
		super();
	}
	
	public PrivacyOptions(long user_id, int followable,
			int visible_health_condition, int visible_food_intolerance,
			int visible_not_pref, int visible_activities) {
		super();
		this.user_id = user_id;
		this.followable = followable;
		this.visible_health_condition = visible_health_condition;
		this.visible_food_intolerance = visible_food_intolerance;
		this.visible_not_pref = visible_not_pref;
		this.visible_activities = visible_activities;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public int getFollowable() {
		return followable;
	}

	public void setFollowable(int followable) {
		this.followable = followable;
	}

	public int getVisible_health_condition() {
		return visible_health_condition;
	}

	public void setVisible_health_condition(int visible_health_condition) {
		this.visible_health_condition = visible_health_condition;
	}

	public int getVisible_food_intolerance() {
		return visible_food_intolerance;
	}

	public void setVisible_food_intolerance(int visible_food_intolerance) {
		this.visible_food_intolerance = visible_food_intolerance;
	}

	public int getVisible_not_pref() {
		return visible_not_pref;
	}

	public void setVisible_not_pref(int visible_not_pref) {
		this.visible_not_pref = visible_not_pref;
	}
	
	public int getVisible_activities() {
		return visible_activities;
	}

	public void setVisible_activities(int visible_activities) {
		this.visible_activities = visible_activities;
	}
}
