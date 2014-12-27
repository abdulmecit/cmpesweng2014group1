package cmpesweng2014.group1.nutty.model;

public class Badge {
	private int badge_id;
	private String name;
	private double min_score;
	private double max_score;
		
	public Badge() {
		super();
	}
	public Badge(int badge_id, String name){
		super();
		this.setBadge_id(badge_id);
		this.setName(name);	
	}
	public int getBadge_id() {
		return badge_id;
	}
	public void setBadge_id(int badge_id) {
		this.badge_id = badge_id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMin_score() {
		return min_score;
	}
	public void setMin_score(double min_score) {
		this.min_score = min_score;
	}
	public double getMax_score() {
		return max_score;
	}
	public void setMax_score(double max_score) {
		this.max_score = max_score;
	}
}
