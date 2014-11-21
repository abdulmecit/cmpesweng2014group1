package cmpesweng2014.group1.nutty.model;

public class Comment {
	private int comment_id;
	private String text;
	private int user_id;
	private int recipe_id;
	
	public Comment() {
		super();
	}
	public Comment(int comment_id, String text){
		super();
		this.comment_id=comment_id;
		this.text=text;	
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public int getCommentId() {
		return comment_id;
	}

	public void setCommentId(int comment_id) {
		this.comment_id = comment_id;
	}
	public int getUserId() {
		return user_id;
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	public int getRecipeId() {
		return recipe_id;
	}

	public void setRecipeId(int recipe_id) {
		this.recipe_id = recipe_id;
	}
}
