package cmpesweng2014.group1.nutty.model;

public class FoodSelection {
	private int fs_id;
	private String fs_name;
	private String type;
	
	public FoodSelection(){
		super();
	}
	public String getName() {
		return fs_name;
	}

	public void setName(String fs_name) {
		this.fs_name = fs_name;
	}
	public int getFsId() {
		return fs_id;
	}

	public void setFsId(int fs_id) {
		this.fs_id = fs_id;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type= type;
	}
	
}
	
