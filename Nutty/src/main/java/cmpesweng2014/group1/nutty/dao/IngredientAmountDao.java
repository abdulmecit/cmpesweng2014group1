package cmpesweng2014.group1.nutty.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.IngredientAmountRowMapper;
import cmpesweng2014.group1.nutty.model.IngredientAmount;

@Component
public class IngredientAmountDao extends PcDao{

	public IngredientAmount[] allIngredientAmounts(int recipe_id){
		List<IngredientAmount> ingredientAmountList = this.getTemplate().query(
				"SELECT recipe_id, ing_id, Shrt_Desc as ing_name, amount, meas_type FROM HasIngredient, ingredients WHERE ing_id = NDB_No and recipe_id =?",
				new Object[] { recipe_id  }, new IngredientAmountRowMapper());
	
		if (ingredientAmountList.isEmpty()) {
			return null;
		}
		else{
			IngredientAmount[] ingredientAmounts = ingredientAmountList.toArray(new IngredientAmount[ingredientAmountList.size()]);
			return ingredientAmounts;
		}
	}
}
