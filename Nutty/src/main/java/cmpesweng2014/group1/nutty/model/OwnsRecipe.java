package cmpesweng2014.group1.nutty.model;

public class OwnsRecipe {
	private Long user_id;
	private int recipe_id;
	
	public OwnsRecipe() {
		super();
	}
	public OwnsRecipe(Long user_id, int recipe_id){
		super();
		this.setRecipe_id(recipe_id);
		this.setUser_id(user_id);
	}
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id2) {
		this.user_id = user_id2;
	}
}
