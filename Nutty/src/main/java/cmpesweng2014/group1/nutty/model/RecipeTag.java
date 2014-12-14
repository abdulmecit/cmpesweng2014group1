package cmpesweng2014.group1.nutty.model;

public class RecipeTag implements Comparable<RecipeTag> {
	private int recipe_id;
	private double ratio;
	private int numberOfTags;
	private double valueOfFound;
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	public int getNumberOfTags() {
		return numberOfTags;
	}
	public void setNumberOfTags(int numberOfTags) {
		this.numberOfTags = numberOfTags;
	}
	public int compareTo(RecipeTag compareRecipe) {		 
		double ratio = compareRecipe.getRatio(); 
		//descending order
		return (int)(ratio - this.ratio);
 
	}
	public double getValueOfFound() {
		return valueOfFound;
	}
	public void setValueOfFound(double valueOfFound) {
		this.valueOfFound = valueOfFound;
	}
		
	
}
