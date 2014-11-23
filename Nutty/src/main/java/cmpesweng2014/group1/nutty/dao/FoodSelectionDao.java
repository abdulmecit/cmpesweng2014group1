package cmpesweng2014.group1.nutty.dao;

import java.util.List;

import cmpesweng2014.group1.nutty.dao.mapper.FoodSelectionRowMapper;
import cmpesweng2014.group1.nutty.model.FoodSelection;

public class FoodSelectionDao extends PcDao {
	public int getFoodSelectionIdByName(String foodSelection){
		List<FoodSelection> foodSelections = this.getTemplate().query(
				"SELECT * FROM FoodSelection WHERE fs_name = ? ",
				new Object[] { foodSelection }, new FoodSelectionRowMapper());

		if (foodSelections.isEmpty()) {
			return 0;
		} else {
			return foodSelections.get(0).getFsId();
		}
	}

}
