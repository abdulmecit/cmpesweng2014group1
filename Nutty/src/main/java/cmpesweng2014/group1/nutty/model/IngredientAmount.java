package cmpesweng2014.group1.nutty.model;

public class IngredientAmount {
	private int recipe_id;
	private int ing_id;
	private String ing_name;
	private String amount;
	private String meas_type;
	
	public IngredientAmount() {
		super();
	}	

	public IngredientAmount(int recipe_id, int ing_id, String ing_name, String amount, String meas_type) {
		super();
		this.recipe_id = recipe_id;
		this.ing_id = ing_id;
		this.ing_name = ing_name;
		this.amount = amount;
		this.meas_type = meas_type;
	}

	public int getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}

	public int getIng_id() {
		return ing_id;
	}

	public void setIng_id(int ing_id) {
		this.ing_id = ing_id;
	}

	public String getIng_name() {
		return ing_name;
	}

	public void setIng_name(String ing_name) {
		this.ing_name = ing_name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMeas_type() {
		return meas_type;
	}

	public void setMeas_type(String meas_type) {
		this.meas_type = meas_type;
	}
	
}