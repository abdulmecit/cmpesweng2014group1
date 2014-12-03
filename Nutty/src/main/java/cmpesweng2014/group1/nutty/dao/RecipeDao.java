package cmpesweng2014.group1.nutty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.EatLikeRateRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.RecipeRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.TagRowMapper;
import cmpesweng2014.group1.nutty.model.EatLikeRate;
import cmpesweng2014.group1.nutty.model.Recipe;
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
		int rate=this.getTemplate().queryForObject(
				"SELECT "+property+" FROM EatLikeRate WHERE recipe_id = ? AND user_id = ?", 
				new Object[] {recipe_id,user_id}, Integer.class);
		return rate;		
	}
	
	public int getVoterCountForRate(String property, int recipe_id){
		int voterCount=this.getTemplate().queryForObject(
				"SELECT COUNT(*) FROM EatLikeRate WHERE recipe_id = ? AND "+property+"> 0", 
				new Object[] {recipe_id}, Integer.class);
		return voterCount;
				
	}
	
	public String getPhotoUrl(int recipe_id){
		
		String photoUrl=this.getTemplate().queryForObject(
				"SELECT url FROM RecipePhoto a, Photo b WHERE a.photo_id = b.photo_id and recipe_id = ?", 
				new Object[] {recipe_id}, String.class);
		return photoUrl;
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
	
	
}
