package cmpesweng2014.group1.nutty.model;

public class NutritionInfo {
	private double total_calorie;
	private double total_fat;
	private double total_protein;
	private double total_carbohydrate;
	
	public NutritionInfo() {
		super();
	}

	public NutritionInfo(double total_calorie,
			double total_fat, double total_protein, double total_carbohydrate) {
		super();
		this.total_calorie = total_calorie;
		this.total_fat = total_fat;
		this.total_protein = total_protein;
		this.total_carbohydrate = total_carbohydrate;
	}

	public double getTotal_calorie() {
		return total_calorie;
	}

	public void setTotal_calorie(double total_calorie) {
		this.total_calorie = total_calorie;
	}

	public double getTotal_fat() {
		return total_fat;
	}

	public void setTotal_fat(double total_fat) {
		this.total_fat = total_fat;
	}

	public double getTotal_protein() {
		return total_protein;
	}

	public void setTotal_protein(double total_protein) {
		this.total_protein = total_protein;
	}

	public double getTotal_carbohydrate() {
		return total_carbohydrate;
	}

	public void setTotal_carbohydrate(double total_carbohydrate) {
		this.total_carbohydrate = total_carbohydrate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(total_calorie);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(total_carbohydrate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(total_fat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(total_protein);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NutritionInfo other = (NutritionInfo) obj;
		if (Double.doubleToLongBits(total_calorie) != Double
				.doubleToLongBits(other.total_calorie))
			return false;
		if (Double.doubleToLongBits(total_carbohydrate) != Double
				.doubleToLongBits(other.total_carbohydrate))
			return false;
		if (Double.doubleToLongBits(total_fat) != Double
				.doubleToLongBits(other.total_fat))
			return false;
		if (Double.doubleToLongBits(total_protein) != Double
				.doubleToLongBits(other.total_protein))
			return false;
		return true;
	}
	
}
