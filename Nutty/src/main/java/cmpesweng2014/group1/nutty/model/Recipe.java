package cmpesweng2014.group1.nutty.model;

import java.sql.Timestamp;

public class Recipe implements Comparable<Recipe>{

	private int recipe_id;
	private String name;
	private String description;
	private int portion;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	private double total_calorie;
	
	public Recipe() {
		super();
	}
	
	public Recipe(int recipe_id, String name, String description, int portion,
			Timestamp createdDate, double total_calorie) {
		super();
		this.setRecipe_id(recipe_id);
		this.name = name;
		this.description = description;
		this.portion = portion;
		this.createdDate = createdDate;
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
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp date) {
		this.createdDate = date;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp date) {
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + portion;
		result = prime * result + recipe_id;
		long temp;
		temp = Double.doubleToLongBits(total_calorie);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((updatedDate == null) ? 0 : updatedDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (portion != other.portion)
			return false;
		if (recipe_id != other.recipe_id)
			return false;
		if (Double.doubleToLongBits(total_calorie) != Double
				.doubleToLongBits(other.total_calorie))
			return false;
		if (updatedDate == null) {
			if (other.updatedDate != null)
				return false;
		} else if (!updatedDate.equals(other.updatedDate))
			return false;
		return true;
	}
	
}
