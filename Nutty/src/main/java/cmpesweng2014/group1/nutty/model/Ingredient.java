package cmpesweng2014.group1.nutty.model;


public class Ingredient {
	private int id;
	private String name;
	private int calorie;
	
	public Ingredient() {
		super();
	}
	public Ingredient(String name) {
		super();
		this.name = name;
	}
	public int getCalorie() {
		return calorie;
	}
	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
