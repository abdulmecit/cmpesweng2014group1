package cmpesweng2014.group1.nutty.model;

public class Ingredient {
	private int id;
	private String ing_name;
	private int calorie;
	private double fat;
	private double protein;
	private double carbohydrate;
	
	public Ingredient() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIng_name() {
		return ing_name;
	}

	public void setIng_name(String ing_name) {
		this.ing_name = ing_name;
	}

	public int getCalorie() {
		return calorie;
	}

	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getCarbohydrate() {
		return carbohydrate;
	}

	public void setCarbohydrate(double carbohydrate) {
		this.carbohydrate = carbohydrate;
	}
	
}
