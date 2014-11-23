package cmpesweng2014.group1.nutty.model;

public class EatLikeRate {
	private int user_id;
	private int recipe_id;
	private int eats;
	private int likes;
	private int health_rate;
	private int cost_rate;
	private int taste_rate;
	private int ease_rate;
	
	public EatLikeRate() {
		super();
	}
	public int getUserId() {
		return user_id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	public int getRecipeId() {
		return recipe_id;
	}
	public void setRecipeId(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public int getEats() {
		return eats;
	}
	public void setEats(int eats) {
		this.eats = eats;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes =likes;
	}
	public int getHealthRate() {
		return health_rate;
	}
	public void setHealthRate(int health_rate) {
		this.health_rate = health_rate;
	}
	public int getCostRate() {
		return cost_rate;
	}
	public void setCostRate(int cost_rate) {
		this.cost_rate = cost_rate;
	}
	public int getTasteRate() {
		return taste_rate;
	}
	public void setTasteRate(int taste_rate) {
		this.taste_rate = taste_rate;
	}
	public int getEaseRate() {
		return ease_rate;
	}
	public void setEaseRate(int ease_rate) {
		this.ease_rate = ease_rate;
	}
}
