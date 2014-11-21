package cmpesweng2014.group1.nutty.model;

import java.util.Date;

public class Recipe {

	private int recipe_id;
	private String name;
	private String description;
	private int portion;
	private Date date;
	private double total_calorie;
	
	public Recipe() {
		super();
	}
	
	public Recipe(int recipe_id, String name, String description, int portion,
			      Date date,double total_calorie) {
		super();
		this.recipe_id = recipe_id;
		this.name = name;
		this.description = description;
		this.portion = portion;
		this.date = date;
		this.total_calorie = total_calorie;		
	}
	
	public int getRecipeId() {
		return recipe_id;
	}

	public void setRecipeId(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public int getPortion() {
		return portion;
	}
	public void setPortion(int portion) {
		this.portion = portion;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public double getTotalCalorie() {
		return total_calorie;
	}
	public void setTotalCalorie(double total_calorie) {
		this.total_calorie=total_calorie;
	}
	
}