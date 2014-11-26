package cmpesweng2014.group1.nutty.model;

public class SuperRecipe{
	
	private Recipe recipe;
	private IngredientAmount[] ingredientAmounts;
	private Comment[] comments;
	private EatLikeRate[] eLikeRs;
	private String photoUrl;
	private int owner_id;
	private int parent_recipe_id;
	private int child_recipe_id;
	
	public SuperRecipe() {
		super();
	}

	public SuperRecipe(Recipe recipe, IngredientAmount[] ingredientAmounts, Comment[] comments, EatLikeRate[] eLikeRs, 
			String photoUrl, int owner_id, int parent_recipe_id, int child_recipe_id){
		this.setRecipe(recipe);
		this.setIngredientAmounts(ingredientAmounts);
		this.setComments(comments);
		this.seteLikeRs(eLikeRs);
		this.setPhotoUrl(photoUrl);
		this.setOwner_id(owner_id);
		this.setParent_recipe_id(parent_recipe_id);
		this.setChild_recipe_id(child_recipe_id);
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public IngredientAmount[] getIngredientAmounts() {
		return ingredientAmounts;
	}

	public void setIngredientAmounts(IngredientAmount[] ingredientAmounts) {
		this.ingredientAmounts = ingredientAmounts;
	}

	public Comment[] getComments() {
		return comments;
	}

	public void setComments(Comment[] comments) {
		this.comments = comments;
	}

	public EatLikeRate[] geteLikeRs() {
		return eLikeRs;
	}

	public void seteLikeRs(EatLikeRate[] eLikeRs) {
		this.eLikeRs = eLikeRs;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}

	public int getParent_recipe_id() {
		return parent_recipe_id;
	}

	public void setParent_recipe_id(int parent_recipe_id) {
		this.parent_recipe_id = parent_recipe_id;
	}

	public int getChild_recipe_id() {
		return child_recipe_id;
	}

	public void setChild_recipe_id(int child_recipe_id) {
		this.child_recipe_id = child_recipe_id;
	}
	
}