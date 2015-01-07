package cmpesweng2014.group1.nutty.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.EatLikeRateRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.RecipeRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.SharesRecipeRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.TagRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.UserRecipeScoreRowMapper;
import cmpesweng2014.group1.nutty.model.Comment;
import cmpesweng2014.group1.nutty.model.EatLikeRate;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.SharesRecipe;
import cmpesweng2014.group1.nutty.model.Tag;
import cmpesweng2014.group1.nutty.model.User;
import cmpesweng2014.group1.nutty.model.UserRecipeScore;

@Component
public class RecipeDao extends PcDao{
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private IngredientDao ingredientDao;
	
	/**
	 * add recipe to the Recipe table
	 * @param name String, recipe name
	 * @param description String, directions
	 * @param portion integer, portion value of the recipe
	 * @param total_calorie, calorie of the recipe
	 * @return
	 */
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
	/**
	 * Returns the recipe for the given id
	 * @param id
	 * @return Recipe
	 */
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
	/**
	 * Get gram values for the given ingredient id for the given measurement type
	 * @param ing_id
	 * @param meas_type
	 * @return gram value
	 */
	public double getWeightByMeasType(int ing_id, String meas_type){
		double weight = this.getTemplate().queryForObject(
				"SELECT Gm_Wgt FROM IngredientWeight WHERE NDB_No = ? and Msre_Desc = ?", 
				new Object[] {ing_id, meas_type}, Double.class);
		return weight;
	}
	/**
	 * Returns measurement types for the given ingredient
	 * @param ing_id
	 * @return units
	 */
	public String[] getMeasTypesByIngId(int ing_id) {
		List<String> measTypeList = this.getTemplate().queryForList(
				"SELECT Msre_Desc FROM IngredientWeight WHERE NDB_No = ?",
				new Object[] { ing_id  }, String.class);
	
		if (measTypeList.isEmpty()) {
			return null;
		}
		else{
			return measTypeList.toArray(new String[measTypeList.size()]);
		}
	}
	/**
	 * Returns units for given ingredient
	 * @param ing_name
	 * @return units
	 */
	public String[] getMeasTypesByIngName(String ing_name) {
		int ing_id = ingredientDao.getIdByName(ing_name);
		if(ing_id == 0)
			return null;
		return getMeasTypesByIngId(ing_id);
	}
	/**
	 * Adding ingredient recipe relation to the database
	 * @param ingredient_id
	 * @param recipe_id
	 * @param amount
	 * @param meas_type
	 */
	public void addIngredient(final int ingredient_id, final int recipe_id, final String amount, final String meas_type){
		final String query = "INSERT INTO HasIngredient (recipe_id, ing_id, amount, meas_type) VALUES (?,?,?,?)";
		KeyHolder gkh = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, recipe_id);
				ps.setInt(2, ingredient_id);
				ps.setString(3, amount);
				ps.setString(4, meas_type);
				return ps;
			}
		}, gkh);
	}
	/**
	 * adding owner of the recipe to the database
	 * @param recipe_id, added recipe
	 * @param user_id, owner
	 */
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
		final double score=2.0;
		final String query2 = "INSERT INTO UserRecipeScore (user_id, recipe_id, add_score) VALUES (?,?,?)";
		KeyHolder gkh2 = new GeneratedKeyHolder();

		this.getTemplate().update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query2,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, user_id);
				ps.setInt(2, recipe_id);
				ps.setDouble(3, score); //2 points for adding this recipe
				return ps;
			}
		}, gkh2);
	}
	/**
	 * adding photo url for the given recipe
	 * @param photoUrl
	 * @param recipe_id
	 */
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
	/**
	 * adding like, eat, rate to the recipes
	 * @param column, determines whether it is like,rate,eat
	 * @param value, evaluation value
	 * @param user
	 * @param recipe
	 */
	public void evaluateRecipe(final String column,final int value,final User user, final Recipe recipe){
		
		String dateColumn;
		if(column.equals("eats"))
			dateColumn = "eat_date";
		else if(column.equals("likes"))
			dateColumn = "like_date";
		else
			dateColumn = "rate_date";
				
		if (emptyCheckUserRecipeRelation(user,recipe)) {
			//add this recipe user relation to the table with eats value
			final String query = "INSERT INTO EatLikeRate (user_id, recipe_id,"+column+","+dateColumn+") VALUES (?,?,?,?)";
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
					ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
					return ps;
				}
			}, gkh);
		} else {

		//update eats value
			final String query = "UPDATE EatLikeRate SET "+column+"=?, "+dateColumn+"=? WHERE user_id=? AND recipe_id=?";
			KeyHolder gkh = new GeneratedKeyHolder();
			this.getTemplate().update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query,
							Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, value);
					ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
					ps.setLong(3, user.getId());
					ps.setInt(4, recipe.getRecipe_id());
					return ps;
				}
			}, gkh);
		}
		//score part
		String newColumn=column+"_score";
		double score=0;
		if(column.equals("likes")){
			score=2.5;
		}
		else if(column.equals("eats")){
			score=2;
		}
		else{
			if(value<3){
				score=0;
			}
			else{
				score=value/2.0;
			}
		}
		//it this user- recipe relation does not exist in UserRecipeScore table,
		//insert this.
		final double insertScore=score;
		if(emptyCheckUserRecipeScore(user,recipe)){
			final String query2 = "INSERT INTO UserRecipeScore (user_id, recipe_id, "+newColumn+") VALUES (?,?,?)";
			KeyHolder gkh2 = new GeneratedKeyHolder();

			this.getTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query2,
							Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, user.getId());
					ps.setInt(2, recipe.getRecipe_id());
					ps.setDouble(3, insertScore);
					return ps;
				}
			}, gkh2);
		}
		else{
			final String query2 = "UPDATE UserRecipeScore SET "+newColumn+"=? WHERE user_id=? AND recipe_id=?";
			KeyHolder gkh = new GeneratedKeyHolder();
			this.getTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query2,
							Statement.RETURN_GENERATED_KEYS);
					ps.setDouble(1, insertScore);
					ps.setLong(2, user.getId());
					ps.setInt(3, recipe.getRecipe_id());
					return ps;
				}
			}, gkh);
		}
	}
	/**
	 * if a recipe user relation for eat like rate exists or not
	 * @param user
	 * @param recipe
	 * @return true if no relation 
	 */
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
	/**
	 * this method returns the number of likes, eats.
	 * property is set to likes or eats
	 * @param property
	 * @param recipe_id
	 * @return
	 */
	public int getEatLikeStatistic(String property,int recipe_id){
		int value=1;		
		//not sure about this
		int total=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM EatLikeRate WHERE recipe_id = ? AND "+property+"= ?", 
				new Object[] {recipe_id, value}, Integer.class);
		return total;
	}
	/**
	 * get statistics for the given recipe
	 * @param property can be rate, like, eat
	 * @param recipe_id
	 * @return value of the column
	 */
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
	/**
	 * get rates for the given user for the given recipe
	 * @param property is eat, like, rate
	 * @param recipe_id
	 * @param user_id
	 * @return value 
	 */
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
	/**
	 * get how many users voted for the like/eat/rate value of the given recipe
	 * @param property
	 * @param recipe_id
	 * @return count number
	 */
	public int getVoterCountForRate(String property, int recipe_id){
		int voterCount=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM EatLikeRate WHERE recipe_id = ? AND "+property+"> 0", 
				new Object[] {recipe_id}, Integer.class);
		return voterCount;
				
	}
	/**
	 * get all photo url s for the given recipe
	 * @param recipe_id
	 * @return
	 */
	public String[] getAllPhotoUrl(int recipe_id){
		int count=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM RecipePhoto a, Photo b WHERE a.photo_id = b.photo_id and recipe_id = ?",  
				new Object[] {recipe_id}, Integer.class);
		if(count==0){
			String[] defaultUrl = {"http://i.imgur.com/opd2vBI.png"};
			return defaultUrl;
		}
		else{
			List<String> photoUrlList=this.getTemplate().queryForList(
				"SELECT url FROM RecipePhoto a, Photo b WHERE a.photo_id = b.photo_id and recipe_id = ?", 
				new Object[] {recipe_id}, String.class);
			if (photoUrlList.isEmpty()) {
				return null;
			}
			else{
				String[] urls = photoUrlList.toArray(new String[photoUrlList.size()]);
				return urls;
			}
		}
	}
	/**
	 * get the owner of the recipe
	 * @param recipe_id
	 * @return user_id
	 */
	public Long getOwnerId(int recipe_id){
		
		Long id=this.getTemplate().queryForObject(
				"SELECT user_id FROM OwnsRecipe WHERE recipe_id = ?", 
				new Object[] {recipe_id}, Long.class);
		return id;
	}
	/**
	 * add derived form to the Recipe table
	 * @param original recipe which is derived from
	 * @param derived recipe
	 */
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
	/**
	 * returns the all recipes derived from the given recipe
	 * @param originalRecipe
	 * @return Recipe array
	 */
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
	/**
	 * return the parent recipe of the given recipe
	 * @param recipe
	 * @return Recipe object
	 */
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
	/**
	 * add tags for the recipe to the Tag table
	 * @param recipe_id
	 * @param tag
	 */
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
	/**
	 * get all tags for a given recipe
	 * @param recipe_id
	 * @return Tag array
	 */
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
	/**
	 * search for recipes with the given query
	 * @param search, filter for search
	 * @return Recipe list
	 */
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
	/**
	 * user shares the recipe
	 * @param user_id
	 * @param recipe_id
	 */
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
		//score part
		//it this user- recipe relation does not exist in UserRecipeScore table,
		//insert this.
		final double insertScore=2.0;
		if(emptyCheckUserRecipeScore(userDao.getUserById(user_id),getRecipeById(recipe_id))){
			final String query2 = "INSERT INTO UserRecipeScore (user_id, recipe_id, share_score) VALUES (?,?,?)";
			KeyHolder gkh2 = new GeneratedKeyHolder();
			this.getTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query2,
							Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, user_id);
					ps.setInt(2, recipe_id);
					ps.setDouble(3, insertScore);
					return ps;
				}
			}, gkh2);
		}
		else{
			final String query2 = "UPDATE UserRecipeScore SET share_score=? WHERE user_id=? AND recipe_id=?";
			KeyHolder gkh = new GeneratedKeyHolder();
				this.getTemplate().update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(
							Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(query2,
								Statement.RETURN_GENERATED_KEYS);
						ps.setDouble(1, insertScore);
						ps.setLong(2, user_id);
						ps.setInt(3, recipe_id);
						return ps;
					}
				}, gkh);
		}		
		
	}
	/**
	 * get shared recipes for the given user
	 * @param user_id
	 * @return recipe ids
	 */
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
	/**
	 * checks whether the user has shared this recipe or not
	 * @param user_id
	 * @param recipe_id
	 * @return 0, if it is not shared
	 */
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
	/**
	 * cancel sharing
	 * @param user_id
	 * @param recipe_id
	 */
	public void cancelShare(long user_id, int recipe_id){
		this.getTemplate().update("DELETE FROM SharesRecipe WHERE user_id = ? AND recipe_id = ?",
				new Object[] { user_id, recipe_id});
	}
	/**
	 * get the recipes that includes the given ingredient
	 * @param ing_name
	 * @return recipe arrays
	 */
	public Recipe[] mustHaveIngredient(String ing_name){
		List<Recipe> recList = this.getTemplate().query(
				"SELECT a.recipe_id, a.name, a.description, a.portion, a.created, "
						+ "a.last_updated,a.total_calorie  FROM Recipe a, HasIngredient b, "
						+ "ingredients c WHERE c.Shrt_Desc LIKE ? AND b.ing_id=c.NDB_No AND "
						+ "a.recipe_id=b.recipe_id ",new Object[] { "%" + ing_name.toUpperCase() + "%" }, new RecipeRowMapper());
		if (recList.isEmpty()) {
			return null;
		} else {
			Recipe[] recipes = recList.toArray(new Recipe[recList.size()]);
			return recipes;		
		}
	}
	/**
	 * get the recipes between the calorie intervals
	 * @param upperlimit
	 * @param lowerlimit
	 * @return recipe array
	 */
	public Recipe[] caloriesBetween(double upperlimit, double lowerlimit){
		List<Recipe> recList = this.getTemplate().query(
				"SELECT * FROM Recipe WHERE total_calorie > ? AND total_calorie < ? ",
				new Object[] { lowerlimit, upperlimit }, new RecipeRowMapper());
		if (recList.isEmpty()) {
			return null;
		} else {
			Recipe[] recipes = recList.toArray(new Recipe[recList.size()]);
			return recipes;		
		}
	}
	/**
	 * return recipes for the given tag
	 * @param tag
	 * @return recipe array
	 */
	public Recipe[] searchRecipesForATag(String tag){
		List<Recipe> recList = this.getTemplate().query(
				"SELECT a.recipe_id, a.name, a.description, a.portion, a.created, "
						+ "a.last_updated,a.total_calorie  FROM Recipe a, HasTag b, "
						+ "Tag c WHERE c.tag_name=? AND b.tag_id=c.tag_id AND "
						+ "a.recipe_id=b.recipe_id ",
						new Object[] { tag }, new RecipeRowMapper());
		if (recList.isEmpty()) {
			return new Recipe[0];
		} else {
			Recipe[] recipes = recList.toArray(new Recipe[recList.size()]);
			return recipes;		
		}
	}
	/**
	 * return number of tags for a given recipe
	 * @param recipe_id
	 * @return number of tags
	 */
	public int getNumberOfTags(int recipe_id){
		int count=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM HasTag WHERE recipe_id = ?", 
				new Object[] {recipe_id}, Integer.class);
		return count;
	}
	/**
	 * empty check for user recipe score relation
	 * if it is true, we need to insert 
	 * if it is false, update this relations score
	 * @param user
	 * @param recipe
	 * @return 0 if no relation
	 */
	public boolean emptyCheckUserRecipeScore(User user, Recipe recipe){
		int count = this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM UserRecipeScore WHERE recipe_id =? AND user_id=?",
				new Object[] { recipe.getRecipe_id(), user.getId()  },  Integer.class);
		if (count==0) {
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * get all recipes in the database
	 * @return Recipe array
	 */
	public Recipe[] getAllRecipes(){
		List<Recipe> recList = this.getTemplate().query(
				"SELECT * FROM Recipe",
				new Object[] {}, new RecipeRowMapper());
		if (recList.isEmpty()) {
			return null;
		} else {
			Recipe[] recipes = recList.toArray(new Recipe[recList.size()]);
			return recipes;
		}
	}
	/**
	 * get UserRecipeScore objects for the given user
	 * @param user_id
	 * @return list of UserRecipeScore objects
	 */
	public List<UserRecipeScore> getUserRecipeScore(long user_id){
		
		List<UserRecipeScore> scoreList = this.getTemplate().query(
				"SELECT user_id,recipe_id, "
						+ "COALESCE(eats_score,0)+COALESCE(health_rate_score,0) + "
						+ "COALESCE(add_score,0)+COALESCE(likes_score,0)+COALESCE(share_score,0)+"
						+ "COALESCE(ease_rate_score,0)+COALESCE(taste_rate_score,0)+"
						+ "COALESCE(comment_score,0) +COALESCE(cost_rate_score,0) "
						+ "AS 'score' FROM UserRecipeScore WHERE user_id =?",
						new Object[] { user_id }, new UserRecipeScoreRowMapper());

		
		if (scoreList.isEmpty()) {
			return null;
		}else{
			
			return scoreList;
		}
	}
	/**
	 * returns recommended recipes for the given user
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public List<Recipe> calculateRecommendation(long user_id) throws Exception{
		List<UserRecipeScore> scoreList = this.getTemplate().query(
				"SELECT user_id,recipe_id, "
						+ "COALESCE(eats_score,0)+COALESCE(health_rate_score,0) + "
						+ "COALESCE(add_score,0)+COALESCE(likes_score,0)+COALESCE(share_score,0)+"
						+ "COALESCE(ease_rate_score,0)+COALESCE(taste_rate_score,0)+"
						+ "COALESCE(comment_score,0) +COALESCE(cost_rate_score,0) "
						+ "AS 'score' FROM UserRecipeScore",
						new Object[] {}, new UserRecipeScoreRowMapper());

		boolean userScoresFound=false; //we need to know if this user has any score
		String dataToWrite="";
		if (scoreList.isEmpty()) {
			return null;
		} else {
			for(int i=0; i<scoreList.size();i++){
				if(scoreList.get(i).getUser_id()==user_id){
					userScoresFound=true;
				}
				dataToWrite+=scoreList.get(i).getUser_id()+","+scoreList.get(i).getRecipe_id()+","+scoreList.get(i).getScore()+"\n";
			}
			if(!userScoresFound){
				return null;
			}
			else{
				File f = new File("score.csv");
				f.createNewFile();
				OutputStream out = new FileOutputStream(f);
				out.write(dataToWrite.getBytes());

				DataModel model = new FileDataModel(f);
				UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
				UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
				UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
				List<RecommendedItem> recommendations = recommender.recommend(user_id, 7);
				List<Recipe> recipes=new ArrayList<Recipe>();
				out.close();
				//File f2 = new File("results.txt");
				//OutputStream out2 = new FileOutputStream(f2);
				if(recommendations.size()==0){
					return null;
				}
				else{
					for (RecommendedItem recommendation : recommendations) {
						int recipe_id=(int)recommendation.getItemID();
						Recipe recipe=getRecipeById(recipe_id);
						recipes.add(recipe);
					}
					return recipes;
				}

			}
		}
	}
	/**
	 * given user reports the given recipe
	 * @param recipe_id
	 * @param user_id
	 */
	public void reportRecipe(final int recipe_id, final Long user_id) {
		final String query = "INSERT INTO ReportsRecipe (user_id, recipe_id) VALUES (?,?)";
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
	/**
	 * get how many reports a recipe has
	 * @param recipe_id
	 * @return number of reports
	 */
	public int numberOfReportsOfRecipe(int recipe_id){
		int count = this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM ReportsRecipe WHERE recipe_id =?",
				new Object[] { recipe_id},  Integer.class);
		return count;		
	}
	/**
	 * delete reports of a recipe
	 * @param recipe_id
	 */
	public void deleteAllReportsOfRecipe(int recipe_id){
		this.getTemplate().update("DELETE FROM ReportsRecipe WHERE recipe_id = ?",
				new Object[] {recipe_id});
	}
	/**
	 * deletes report of a user for a specific recipe
	 * @param recipe_id
	 * @param user_id
	 */
	public void deleteReportOfAUserForRecipe(int recipe_id, Long user_id){
		this.getTemplate().update("DELETE FROM ReportsRecipe WHERE recipe_id = ? AND user_id = ?",
				new Object[] {recipe_id,user_id});
	}
	/**
	 * get the information if the user has reported this recipe or not
	 * @param recipe_id
	 * @param user_id
	 * @return 1 if the user has reported this recipe, else 0
	 */ 
	public int hasReportedRecipe(int recipe_id,Long user_id){
		int count = this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM ReportsRecipe WHERE recipe_id =? AND user_id =?",
				new Object[] { recipe_id, user_id},  Integer.class);
		if(count==0){
			return 0;
		}
		else{
			return 1;
		}
	}
	/**
	 * recipe delete
	 * @param recipe_id
	 */
	public void deleteRecipe(int recipe_id){
		//update Derived table
		//check whether it has parent or not
		final Recipe parent=getParent(getRecipeById(recipe_id));
		if (parent!=null){ //if it has a parent
			final Recipe[] children=getAllDerivations(getRecipeById(recipe_id));
			if(children!=null){ //if it has children
				for(int i=0;i<children.length;i++){
					addDerivedFrom(parent, children[i]);
				}
			}
		}
		this.getTemplate().update("DELETE FROM Derived WHERE parent_recipe_id = ?",
				new Object[] {recipe_id});
		this.getTemplate().update("DELETE FROM Derived WHERE child_recipe_id = ?",
				new Object[] {recipe_id});	
		//delete from Recipe table
		this.getTemplate().update("DELETE FROM Recipe WHERE recipe_id = ?",
				new Object[] {recipe_id});
		//delete from OwnsRecipe table
		this.getTemplate().update("DELETE FROM OwnsRecipe WHERE recipe_id = ?",
				new Object[] {recipe_id});
		//delete all comments of this recipe
		Comment[] comments= commentDao.allComments(recipe_id);
		if(comments!=null){
			for(int i=0; i<comments.length;i++) {
				//delete the comment and all relations of it
				commentDao.deleteComment(comments[i].getComment_id());
			}
		}	
		//delete from HasIngredient table
		deleteIngredients(recipe_id);
		//delete from HasTag table
		deleteTags(recipe_id);
		//delete from EatLikeRate table
		this.getTemplate().update("DELETE FROM EatLikeRate WHERE recipe_id = ?",
				new Object[] {recipe_id});
		//delete from RecipePhoto table
		deleteRecipePhoto(recipe_id);
		//delete reports of this recipe
		deleteAllReportsOfRecipe(recipe_id);
		//delete from SharesRecipe table
		this.getTemplate().update("DELETE FROM SharesRecipe WHERE recipe_id = ?",
				new Object[] {recipe_id});
		//delete from UserRecipeScore table
		this.getTemplate().update("DELETE FROM UserRecipeScore WHERE recipe_id = ?",
				new Object[] {recipe_id});
	}
	/**
	 * editing the recipe
	 * @param recipe_id, which recipe will be edited
	 * @param name
	 * @param description
	 * @param portion
	 * @param total_calorie
	 */
	public void editRecipeTable(final int recipe_id,final String name, final String description,
			final int portion, final double total_calorie){
		final String query = "UPDATE Recipe SET name=?, description=?, portion=?, total_calorie=? "
				+ "WHERE recipe_id= ?";

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
				ps.setDouble(4,total_calorie);
				ps.setInt(5, recipe_id);
				return ps;
			}
		}, gkh);		
	}
	/**
	 * deletes tag recipe relations for the given recipe 
	 * @param recipe_id
	 */
	public void deleteTags(int recipe_id){
		this.getTemplate().update("DELETE FROM HasTag WHERE recipe_id = ?",
				new Object[] {recipe_id});
	}
	/**
	 * deletes ingredient recipe relation for the given recipe
	 * @param recipe_id
	 */
	public void deleteIngredients(int recipe_id){
		this.getTemplate().update("DELETE FROM HasIngredient WHERE recipe_id = ?",
				new Object[] {recipe_id});
	}
	/**
	 * deletes photo recipe relation for the given recipe
	 * @param recipe_id
	 */
	public void deleteRecipePhoto(int recipe_id){
		this.getTemplate().update("DELETE FROM RecipePhoto WHERE recipe_id = ?",
				new Object[] {recipe_id});
	}
	/**
	 * returns the recipe id-number of reports mapping for all reported recipes
	 * @return
	 */
	public Map<Recipe,Integer> getReportedRecipes(){
		Map<Recipe, Integer> recipeReportMap = new HashMap<Recipe, Integer>();
		Recipe[] recipes = getAllRecipes();
		if(recipes != null){
			for(int i=0; i<recipes.length;i++){
				int id=recipes[i].getRecipe_id();
				int count=numberOfReportsOfRecipe(id);
				if(count>0){
					recipeReportMap.put( getRecipeById(id), count);
				}
			}
		}
		return recipeReportMap;
	}
	
	
}
