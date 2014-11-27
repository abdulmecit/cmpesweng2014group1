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
		this.setComment_id(comment_id);
		this.setText(text);	
	}
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
}
