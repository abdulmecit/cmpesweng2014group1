package cmpesweng2014.group1.nutty.model;

public class SuperUser{
	
	private User user;
	private User[] followerList;
	private User[] followingList;
	private Recipe[] ownedRecipes;
	private String[] ownedRecipePictures;
	private Recipe[] sharedRecipes;
	private String[] sharedRecipePictures;
	
	public SuperUser() {
		super();
	}

	public SuperUser(User user, User[] followerList, User[] followingList,
			Recipe[] ownedRecipes, String[] ownedRecipePictures,
			Recipe[] sharedRecipes, String[] sharedRecipePictures) {
		super();
		this.user = user;
		this.followerList = followerList;
		this.followingList = followingList;
		this.ownedRecipes = ownedRecipes;
		this.ownedRecipePictures = ownedRecipePictures;
		this.sharedRecipes = sharedRecipes;
		this.sharedRecipePictures = sharedRecipePictures;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User[] getFollowerList() {
		return followerList;
	}

	public void setFollowerList(User[] followerList) {
		this.followerList = followerList;
	}

	public User[] getFollowingList() {
		return followingList;
	}

	public void setFollowingList(User[] followingList) {
		this.followingList = followingList;
	}

	public Recipe[] getOwnedRecipes() {
		return ownedRecipes;
	}

	public void setOwnedRecipes(Recipe[] ownedRecipes) {
		this.ownedRecipes = ownedRecipes;
	}

	public String[] getOwnedRecipePictures() {
		return ownedRecipePictures;
	}

	public void setOwnedRecipePictures(String[] ownedRecipePictures) {
		this.ownedRecipePictures = ownedRecipePictures;
	}

	public Recipe[] getSharedRecipes() {
		return sharedRecipes;
	}

	public void setSharedRecipes(Recipe[] sharedRecipes) {
		this.sharedRecipes = sharedRecipes;
	}

	public String[] getSharedRecipePictures() {
		return sharedRecipePictures;
	}

	public void setSharedRecipePictures(String[] sharedRecipePictures) {
		this.sharedRecipePictures = sharedRecipePictures;
	}
}
