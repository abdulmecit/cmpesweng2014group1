package cmpesweng2014.group1.nutty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.EatLikeRateRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.RecipeRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.SharesRecipeRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.TagRowMapper;
import cmpesweng2014.group1.nutty.model.EatLikeRate;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.RecipeTag;
import cmpesweng2014.group1.nutty.model.SharesRecipe;
import cmpesweng2014.group1.nutty.model.Tag;
import cmpesweng2014.group1.nutty.model.User;

@Component
public class RecipeDao extends PcDao{
	
	public int createRecipe(final String name, final String description,
			final int portion, final double total_calorie) {
		final String query = "INSERT INTO Recipe (name, description, portion, created, last_updated, total_calorie) VALUES (?,?,?,?,?,?)";

		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, name);
				ps.setString(2, description);
				ps.setInt(3, portion);
				ps.setDate(4,null);
				ps.setDate(5,null);
				ps.setDouble(6, total_calorie);
				return ps;
			}
		}, gkh);

		int newItemId = gkh.getKey().intValue();

		return newItemId;
	}
	
	public Recipe getRecipeById(int id) {		
		List<Recipe> recipes = this.getTemplate().query(
				"SELECT * FROM Recipe WHERE recipe_id = ? ",
				new Object[] { id }, new RecipeRowMapper());

		if (recipes.isEmpty()) {
			return null;
		} else {
			return recipes.get(0);
		}
	}
	
	//can be changed, assumed gram value is given by the user and database has value for 100 gram
	public double calculateTotalCalorie(int[] ingredient_calories, double[] amounts){
		double total=0;
		for(int k=0; k<ingredient_calories.length; k++){
			total+=ingredient_calories[k]/100*amounts[k];
		}		
		return total;
	}
	
	public void addIngredient(final int ingredient_id, final int recipe_id, final double amount){
		final String query = "INSERT INTO HasIngredient (recipe_id, ing_id, amount) VALUES (?,?,?)";
		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, recipe_id);
				ps.setInt(2, ingredient_id);
				ps.setDouble(3, amount);
				return ps;
			}
		}, gkh);
	}

	public void addOwner(final int recipe_id, final Long user_id) {
		final String query = "INSERT INTO OwnsRecipe (user_id, recipe_id) VALUES (?,?)";
		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, user_id);
				ps.setInt(2, recipe_id);
				return ps;
			}
		}, gkh);	
	}
	
	public void addPhotoUrl(final String photoUrl, final int recipe_id) {
		final String query = "INSERT INTO Photo (url) VALUES (?)";
		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, photoUrl);
				return ps;
			}
		}, gkh);	
		
		final int photo_id = gkh.getKey().intValue();
		
		final String query2 = "INSERT INTO RecipePhoto (recipe_id, photo_id) VALUES (?,?)";

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query2);
				ps.setInt(1, recipe_id);
				ps.setInt(2, photo_id);
				return ps;
			}
		});	
	}

	public void evaluateRecipe(final String column,final int value,final User user, final Recipe recipe){
		if (emptyCheckUserRecipeRelation(user,recipe)) {
			//add this recipe user relation to the table with eats value		
			final String query = "INSERT INTO EatLikeRate (user_id, recipe_id,"+column+") VALUES (?,?,?)";
			KeyHolder gkh = new GeneratedKeyHolder();

			this.getTemplate().update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query,
							Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, user.getId());
					ps.setInt(2, recipe.getRecipe_id());
					ps.setInt(3, value);
					return ps;
				}
			}, gkh);	
		} else {
			//update eats value
			final String query = "UPDATE EatLikeRate SET "+column+"=? WHERE user_id=? AND recipe_id=?";
			KeyHolder gkh = new GeneratedKeyHolder();
			this.getTemplate().update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query,
							Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, value);
					ps.setLong(2, user.getId());
					ps.setInt(3, recipe.getRecipe_id());
					return ps;
				}
			}, gkh);	
		}	
	}
	
	public boolean emptyCheckUserRecipeRelation(User user, Recipe recipe){
		List<EatLikeRate> eLikeR = this.getTemplate().query(
				"SELECT * FROM EatLikeRate WHERE recipe_id =? AND user_id=?",
				new Object[] { recipe.getRecipe_id(), user.getId()  }, new EatLikeRateRowMapper());
	
		if (eLikeR.isEmpty()) {
			return true;
		}
		else{
			return false;
		}
	}
	
	//this method returns the number of likes, eats.
	//property is set to likes or eats
	public int getEatLikeStatistic(String property,int recipe_id){
		int value=1;		
		//not sure about this
		int total=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM EatLikeRate WHERE recipe_id = ? AND "+property+"= ?", 
				new Object[] {recipe_id, value}, Integer.class);
		return total;
	}
	
	public double getAvgRateStatistic(String property,int recipe_id){
		//not sure about this
		int voterCount=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM EatLikeRate WHERE recipe_id = ? AND "+property+"> 0", 
				new Object[] {recipe_id}, Integer.class);

		if(voterCount == 0){
			return 0.0;
		}
		else{			
			int total=this.getTemplate().queryForObject(
					"SELECT SUM(" + property + ") FROM EatLikeRate WHERE recipe_id = ? AND "+property+"> 0", 
					new Object[] {recipe_id}, Integer.class);
			
			return (double)total / voterCount;
		}
	}

	public int getRatesForUser(String property,int recipe_id,long user_id){
		int count=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM EatLikeRate WHERE recipe_id = ? AND user_id = ? AND "+property+"> 0", 
				new Object[] {recipe_id,user_id}, Integer.class);
		if(count==0){
			return 0;
		}
		else{
			int rate=this.getTemplate().queryForObject(
					"SELECT "+property+" FROM EatLikeRate WHERE recipe_id = ? AND user_id = ?", 
					new Object[] {recipe_id,user_id}, Integer.class);		
			return rate;
		}
	}
	
	public int getVoterCountForRate(String property, int recipe_id){
		int voterCount=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM EatLikeRate WHERE recipe_id = ? AND "+property+"> 0", 
				new Object[] {recipe_id}, Integer.class);
		return voterCount;
				
	}
	
	public String getPhotoUrl(int recipe_id){
		int count=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM RecipePhoto a, Photo b WHERE a.photo_id = b.photo_id and recipe_id = ?",  
				new Object[] {recipe_id}, Integer.class);
		if(count==0){
			return "http://i.imgur.com/opd2vBI.png";
		}
		else{
			String photoUrl=this.getTemplate().queryForObject(
				"SELECT url FROM RecipePhoto a, Photo b WHERE a.photo_id = b.photo_id and recipe_id = ?", 
				new Object[] {recipe_id}, String.class);
			return photoUrl;
		}
	}
	
	public Long getOwnerId(int recipe_id){
		
		Long id=this.getTemplate().queryForObject(
				"SELECT user_id FROM OwnsRecipe WHERE recipe_id = ?", 
				new Object[] {recipe_id}, Long.class);
		return id;
	}
	
	public void addDerivedFrom(final Recipe original, final Recipe derived){
		final String query = "INSERT INTO Derived (parent_recipe_id, child_recipe_id) VALUES (?,?)";
		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, original.getRecipe_id());
				ps.setInt(2, derived.getRecipe_id());
				return ps;
			}
		}, gkh);
	}

	public Recipe[] getAllDerivations(Recipe originalRecipe) {
		List<Recipe> recipeList = this.getTemplate().query(
				"SELECT recipe_id, name, description, portion, created,"
				+ "last_updated,total_calorie  FROM Recipe, Derived WHERE recipe_id = child_recipe_id "
				+ "AND parent_recipe_id = ? ",
				new Object[] { originalRecipe.getRecipe_id() }, new RecipeRowMapper());

		if (recipeList.isEmpty()) {
			return null;
		} else {
			Recipe[] recipes = recipeList.toArray(new Recipe[recipeList.size()]);
			return recipes;
		}
		
	}

	public Recipe getParent(Recipe recipe) {
		List<Recipe> recipes = this.getTemplate().query(
				"SELECT recipe_id, name, description, portion, created,"
				+ "last_updated,total_calorie  FROM Recipe, Derived WHERE recipe_id = parent_recipe_id "
				+ "AND child_recipe_id = ? ",
				new Object[] { recipe.getRecipe_id() }, new RecipeRowMapper());

		if (recipes.isEmpty()) {
			return null;
		} else {
			return recipes.get(0);
		}
	}

	public void addTag(final int recipe_id, final String tag) {
		
		List<Tag> tagList = this.getTemplate().query(
				"SELECT tag_id, tag_name FROM Tag WHERE tag_name = ?",
				new Object[] { tag }, new TagRowMapper());

		final int tag_id;
		
		if (!tagList.isEmpty()) {	//tag is already in database, use its id
			tag_id = tagList.get(0).getTag_id();
		} 
		else {			
			final String query = "INSERT INTO Tag (tag_name) VALUES (?)";
	
			KeyHolder gkh = new GeneratedKeyHolder();
	
			this.getTemplate().update(new PreparedStatementCreator() {
	
				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, tag);
					return ps;
				}
			}, gkh);
	
			tag_id = gkh.getKey().intValue();
		}
		
		final String query2 = "INSERT INTO HasTag (recipe_id, tag_id) VALUES (?,?)";

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query2);
				ps.setInt(1, recipe_id);
				ps.setInt(2, tag_id);
				return ps;
			}
		});		
	}

	public Tag[] getAllTags(int recipe_id) {
		List<Tag> tagList = this.getTemplate().query(
				"SELECT a.tag_id,tag_name FROM HasTag a, Tag b WHERE recipe_id =? AND b.tag_id = a.tag_id",
				new Object[] { recipe_id  }, new TagRowMapper());
	
		if (tagList.isEmpty()) {
			return null;
		}
		else{
			Tag[] tags = tagList.toArray(new Tag[tagList.size()]);
			return tags;
		}
	}
	
	public List<Recipe> searchRecipeByName(String search){
		String words[] = search.split(" ");
		String query = "SELECT * FROM Recipe WHERE";
		for(int i=0; i<words.length; i++){
			query += " name LIKE '%" + words[i] + "%' ";
			if(i<words.length-1){
				query += "AND";
			}
		}
		List<Recipe> recipes = this.getTemplate().query(
				query,
				new RecipeRowMapper());

		if (recipes.isEmpty()) {
			return null;
		} else {
			return recipes;
		}
	}
	public void shareRecipe(final long user_id, final int recipe_id){
		final String query = "INSERT INTO SharesRecipe (user_id, recipe_id) VALUES (?,?)";

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setLong(1, user_id);
				ps.setInt(2, recipe_id);

				return ps;
			}
		});
	}
	
	public int[] getSharedRecipes(long user_id){
		
		List<SharesRecipe> recipes = this.getTemplate().query(
					"SELECT * FROM SharesRecipe WHERE user_id = ?",
					new Object[] { user_id }, new SharesRecipeRowMapper());
					
		int recipeIds[] = new int[recipes.size()];
		for(int i=0; i<recipes.size(); i++){
			recipeIds[i] = recipes.get(i).getRecipe_id();
		}
		return recipeIds;
	}
	public int isShared(long user_id, int recipe_id){
		int count=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM SharesRecipe WHERE recipe_id = ? AND user_id = ?", 
				new Object[] {recipe_id, user_id}, Integer.class);
		if(count==0){
			return 0;
		}
		else{
			return 1;
		}
	}
	public void cancelShare(long user_id, int recipe_id){
		this.getTemplate().update("DELETE FROM SharesRecipe WHERE user_id = ? AND recipe_id = ?",
				new Object[] { user_id, recipe_id});
	}
	
	public Recipe[] mustHaveIngredient(String ing_name){
		List<Recipe> recList = this.getTemplate().query(
				"SELECT a.recipe_id, a.name, a.description, a.portion, a.created,"
						+ "a.last_updated,a.total_calorie  FROM Recipe a, HasIngredient b,"
						+ "ingredients c WHERE c.Shrt_Desc=? AND b.ing_id=c.NDB_No AND"
						+ "a.recipe_id=b.recipe_id ",
						new Object[] { ing_name }, new RecipeRowMapper());
		if (recList.isEmpty()) {
			return null;
		} else {
			Recipe[] recipes = recList.toArray(new Recipe[recList.size()]);
			return recipes;		
		}
	}
	
	public Recipe[] searchByAllTags(String[] tags, int relatedTagsIndex){		
		List<RecipeTag> recTags = new ArrayList<RecipeTag>();
		
		//recipe id - value mapping
		//value will be increased by one for found original tags, by 0.5 for found related tags
		Map<Integer, Double> recValue = new HashMap<Integer, Double>();
		//deal with original tags
		for(int i=0; i<relatedTagsIndex;i++){
			Recipe[] foundRecipes=searchRecipesForATag(tags[i]);
			for(int k=0; k<foundRecipes.length;k++){
				int recipe_id=foundRecipes[k].getRecipe_id();
				Double value = recValue.get(recipe_id);
				//if this recipe was added before, increase the value
				if (value != null) {
					value=value+1;
					recValue.put(recipe_id, value);
				}
				//if this recipe wasn't added.
				else{
					recValue.put(recipe_id, 1.0);
				}
			}
		}
		//deal with related tags
		for(int i=relatedTagsIndex; i<tags.length;i++){
			Recipe[] foundRecipes2=searchRecipesForATag(tags[i]);
			for(int k=0; k<foundRecipes2.length;k++){
				int recipe_id=foundRecipes2[k].getRecipe_id();
				Double value2 = recValue.get(recipe_id);
				//if this recipe was added before, increase the value
				if (value2 != null) {
					value2=value2+0.5;
					recValue.put(recipe_id, value2);
				}
				//if this recipe wasn't added.
				else{
					recValue.put(recipe_id, 0.5);
				}
			}
		}
		//now store all the information in RecipeTag object, to do sorting according to
		//value/total number of tags ratio
		for(Integer key : recValue.keySet()) {
            Double value = recValue.get(key);
            RecipeTag recTag=new RecipeTag();
            recTag.setRecipe_id(key);
            recTag.setValueOfFound(value);
            recTag.setNumberOfTags(getNumberOfTags(key));
            recTag.setRatio(value/getNumberOfTags(key));
            recTags.add(recTag);
        }
		Collections.sort(recTags);
		Recipe[] rec=new Recipe[recTags.size()];
		for(int i=0; i<recTags.size();i++){
			rec[i]= getRecipeById(recTags.get(i).getRecipe_id());
		}
		return rec;		
	}
	
	//return recipes for the given tag
	public Recipe[] searchRecipesForATag(String tag){
		List<Recipe> recList = this.getTemplate().query(
				"SELECT a.recipe_id, a.name, a.description, a.portion, a.created,"
						+ "a.last_updated,a.total_calorie  FROM Recipe a, HasTag b,"
						+ "Tag c WHERE c.tag_name=? AND b.tag_id=c.tag_id AND"
						+ "a.recipe_id=b.recipe_id ",
						new Object[] { tag }, new RecipeRowMapper());
		if (recList.isEmpty()) {
			return null;
		} else {
			Recipe[] recipes = recList.toArray(new Recipe[recList.size()]);
			return recipes;		
		}
	}
	//return number of tags for a given recipe
	public int getNumberOfTags(int recipe_id){
		int count=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM HasTag WHERE recipe_id = ?", 
				new Object[] {recipe_id}, Integer.class);
		return count;
	}
}
