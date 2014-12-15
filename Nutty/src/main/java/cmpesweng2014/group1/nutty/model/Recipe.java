package cmpesweng2014.group1.nutty.model;

import java.util.Date;

public class Recipe implements Comparable<Recipe>{

	private int recipe_id;
	private String name;
	private String description;
	private int portion;
	private Date updatedDate;
	private Date createdDate;
	private double total_calorie;
	
	public Recipe() {
		super();
	}
	
	public Recipe(int recipe_id, String name, String description, int portion,
			      Date date,double total_calorie) {
		super();
		this.setRecipe_id(recipe_id);
		this.name = name;
		this.description = description;
		this.portion = portion;
		this.createdDate = date;
		this.setTotal_calorie(total_calorie);		
	}
	
	public int getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(int recipe_id) {
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
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date date) {
		this.createdDate = date;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date date) {
		this.updatedDate = date;
	}

	public double getTotal_calorie() {
		return total_calorie;
	}

	public void setTotal_calorie(double total_calorie) {
		this.total_calorie = total_calorie;
	}

	@Override
	public int compareTo(Recipe other) {
		return (int)(this.recipe_id - other.recipe_id);
	}
}
