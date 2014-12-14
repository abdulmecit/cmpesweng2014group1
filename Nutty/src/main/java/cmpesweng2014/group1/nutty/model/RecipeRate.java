package cmpesweng2014.group1.nutty.model;

public class RecipeRate implements Comparable<RecipeRate>{
	private int recipe_id;
	private double rate;
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public int compareTo(RecipeRate compareRecipe) {
		 
		double rate = compareRecipe.getRate(); 
		//descending order
		return (int)(rate - this.rate);
 
	}	
	
}
