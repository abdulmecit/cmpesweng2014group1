package cmpesweng2014.group1.nutty.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.CommentRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.RecipeRowMapper;
import cmpesweng2014.group1.nutty.model.Comment;
import cmpesweng2014.group1.nutty.model.Event;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.User;

@Component
public class EventDao extends PcDao {
	
	@Autowired
	RecipeDao recipeDao;
	
	@Autowired
	UserDao userDao;
	
	public List<Event> getRecentCommentEventsOfUser(long now, long user_id){
		
		Timestamp right_now = new Timestamp(now);
		Timestamp last_week = new Timestamp(now - (604800000));
		
		User u = userDao.getUserById(user_id);
					
		List<Comment> recentComments = this.getTemplate().query(
				"SELECT * FROM Comment WHERE user_id = ? AND date BETWEEN ? AND ?",
				new Object[] { user_id, last_week, right_now}, new CommentRowMapper());
		
		List<Event> recentCommentEvents = new ArrayList<Event>(recentComments.size());
		
		for(Comment c : recentComments){
			Recipe r = recipeDao.getRecipeById(c.getRecipe_id());
			String photo = recipeDao.getAllPhotoUrl(c.getRecipe_id())[0];
			recentCommentEvents.add(new Event(u, "add_comment", r, photo, c.getTimestamp()));
		}
	
		return recentCommentEvents;
	}
	
	public List<Event> getRecentRecipeEventsOfUser(long now, long user_id){
		
		Timestamp right_now = new Timestamp(now);
		Timestamp last_week = new Timestamp(now - (604800000));
		
		User u = userDao.getUserById(user_id);
					
		List<Recipe> recentRecipes = this.getTemplate().query(
				"SELECT b.* FROM OwnsRecipe a, Recipe b WHERE a.user_id = ? AND a.recipe_id = b.recipe_id "
				+ "AND b.last_updated BETWEEN ? AND ?",
				new Object[] { user_id, last_week, right_now}, new RecipeRowMapper());
		
		List<Event> recentRecipeEvents = new ArrayList<Event>(recentRecipes.size());
		
		for(Recipe r : recentRecipes){
			String photo = recipeDao.getAllPhotoUrl(r.getRecipe_id())[0];

			if(!r.getCreatedDate().equals(r.getUpdatedDate()))
				recentRecipeEvents.add(new Event(u, "edit_recipe", r, photo, r.getUpdatedDate()));
			else{
				if(recipeDao.getParent(r) == null){
					recentRecipeEvents.add(new Event(u, "add_recipe", r, photo, r.getUpdatedDate()));
				}
				else{
					recentRecipeEvents.add(new Event(u, "derive_recipe", r, photo, r.getUpdatedDate()));
				}
			}
		}
		return recentRecipeEvents;
	}
	
}