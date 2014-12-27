package cmpesweng2014.group1.nutty.model;

public class SuperRecipe{
	
	private Recipe recipe;
	private IngredientAmount[] ingredientAmounts;
	private Comment[] comments;
	private String[] commenterNames;
	private long[][] commentLikerIds;
	private String[][] commentLikerNames;
	private int noOfLikes;
	private int noOfEats;
	private double avgHealthRate;
	private double avgCostRate;
	private double avgTasteRate;
	private double avgEaseRate;
	private String[] photoUrl;
	private String owner;
	private Recipe parent;
	private Recipe[] children;
	private Tag[] tags;
	
	public SuperRecipe() {
		super();
	}

	public SuperRecipe(Recipe recipe, IngredientAmount[] ingredientAmounts,
			Comment[] comments, String[] commenterNames, long[][] commentLikerIds, String[][] commentLikerNames, int noOfLikes,
			int noOfEats, double avgHealthRate, double avgCostRate,
			double avgTasteRate, double avgEaseRate, String[] photoUrl,
			String owner, Recipe parent, Recipe[] children, Tag[] tags) {
		super();
		this.recipe = recipe;
		this.ingredientAmounts = ingredientAmounts;
		this.comments = comments;
		this.commenterNames = commenterNames;
		this.commentLikerIds = commentLikerIds;
		this.commentLikerNames = commentLikerNames;
		this.noOfLikes = noOfLikes;
		this.noOfEats = noOfEats;
		this.avgHealthRate = avgHealthRate;
		this.avgCostRate = avgCostRate;
		this.avgTasteRate = avgTasteRate;
		this.avgEaseRate = avgEaseRate;
		this.photoUrl = photoUrl;
		this.owner = owner;
		this.parent = parent;
		this.children = children;
		this.tags = tags;
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

	public String[] getCommenterNames() {
		return commenterNames;
	}

	public void setCommenterNames(String[] commenterNames) {
		this.commenterNames = commenterNames;
	}

	public long[][] getCommentLikerIds() {
		return commentLikerIds;
	}

	public void setCommentLikerIds(long[][] commentLikerIds) {
		this.commentLikerIds = commentLikerIds;
	}

	public String[][] getCommentLikerNames() {
		return commentLikerNames;
	}

	public void setCommentLikerNames(String[][] commentLikerNames) {
		this.commentLikerNames = commentLikerNames;
	}

	public int getNoOfLikes() {
		return noOfLikes;
	}

	public void setNoOfLikes(int noOfLikes) {
		this.noOfLikes = noOfLikes;
	}

	public int getNoOfEats() {
		return noOfEats;
	}

	public void setNoOfEats(int noOfEats) {
		this.noOfEats = noOfEats;
	}

	public double getAvgHealthRate() {
		return avgHealthRate;
	}

	public void setAvgHealthRate(double avgHealthRate) {
		this.avgHealthRate = avgHealthRate;
	}

	public double getAvgCostRate() {
		return avgCostRate;
	}

	public void setAvgCostRate(double avgCostRate) {
		this.avgCostRate = avgCostRate;
	}

	public double getAvgTasteRate() {
		return avgTasteRate;
	}

	public void setAvgTasteRate(double avgTasteRate) {
		this.avgTasteRate = avgTasteRate;
	}

	public double getAvgEaseRate() {
		return avgEaseRate;
	}

	public void setAvgEaseRate(double avgEaseRate) {
		this.avgEaseRate = avgEaseRate;
	}

	public String[] getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String[] photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Recipe getParent() {
		return parent;
	}

	public void setParent(Recipe parent) {
		this.parent = parent;
	}

	public Recipe[] getChildren() {
		return children;
	}

	public void setChildren(Recipe[] children) {
		this.children = children;
	}

	public Tag[] getTags() {
		return tags;
	}

	public void setTags(Tag[] tags) {
		this.tags = tags;
	}
}
