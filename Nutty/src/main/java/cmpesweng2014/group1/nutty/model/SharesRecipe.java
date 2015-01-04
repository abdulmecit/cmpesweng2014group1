package cmpesweng2014.group1.nutty.model;

import java.sql.Timestamp;

public class SharesRecipe {
	private Long user_id;
	private int recipe_id;
	private Timestamp date;
	
	public SharesRecipe() {
		super();
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public int getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
}
