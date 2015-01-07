package cmpesweng2014.group1.nutty.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.mapper.CommentRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.EatLikeRateRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.FollowsUserRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.RecipeRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.SharesRecipeRowMapper;
import cmpesweng2014.group1.nutty.dao.mapper.UserBadgeRowMapper;
import cmpesweng2014.group1.nutty.model.Badge;
import cmpesweng2014.group1.nutty.model.Comment;
import cmpesweng2014.group1.nutty.model.EatLikeRate;
import cmpesweng2014.group1.nutty.model.Event;
import cmpesweng2014.group1.nutty.model.FollowsUser;
import cmpesweng2014.group1.nutty.model.Recipe;
import cmpesweng2014.group1.nutty.model.SharesRecipe;
import cmpesweng2014.group1.nutty.model.User;
import cmpesweng2014.group1.nutty.model.UserBadge;

@Component
public class EventDao extends PcDao {
	
	@Autowired
	RecipeDao recipeDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	BadgeDao badgeDao;
	/**
	 * get recent comment event of user
	 * @param now, todays date
	 * @param dayCount, how many days will be looked
	 * @param user_id
	 * @return
	 */
	public List<Event> getRecentCommentEventsOfUser(long now, int dayCount, long user_id){
		
		Timestamp right_now = new Timestamp(now);
		Timestamp past = new Timestamp(now - (dayCount * 86400000));
		
		User u = userDao.getUserById(user_id);
					
		List<Comment> recentComments = this.getTemplate().query(
				"SELECT * FROM Comment WHERE user_id = ? AND date BETWEEN ? AND ?",
				new Object[] { user_id, past, right_now }, new CommentRowMapper());
		
		List<Event> recentCommentEvents = new ArrayList<Event>(recentComments.size());
		
		for(Comment c : recentComments){
			Recipe r = recipeDao.getRecipeById(c.getRecipe_id());
			String photo = recipeDao.getAllPhotoUrl(c.getRecipe_id())[0];
			recentCommentEvents.add(new Event(u, "add_comment", r, photo, c.getTimestamp()));
		}
	
		return recentCommentEvents;
	}
	/**
	 * get recent recipe event of user
	 * @param now, today
	 * @param dayCount, how many days will be looked
	 * @param user_id
	 * @return
	 */
	public List<Event> getRecentRecipeEventsOfUser(long now, int dayCount, long user_id){
		
		Timestamp right_now = new Timestamp(now);
		Timestamp past = new Timestamp(now - (dayCount * 86400000));
		
		User u = userDao.getUserById(user_id);
					
		List<Recipe> recentRecipes = this.getTemplate().query(
				"SELECT b.* FROM OwnsRecipe a, Recipe b WHERE a.user_id = ? AND a.recipe_id = b.recipe_id "
				+ "AND b.last_updated BETWEEN ? AND ?",
				new Object[] { user_id, past, right_now }, new RecipeRowMapper());
		
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
	/**
	 * get recent share events of the user
	 * @param now
	 * @param dayCount, how many days will be looked
	 * @param user_id
	 * @return
	 */
	public List<Event> getRecentShareEventsOfUser(long now, int dayCount, long user_id){
		
		Timestamp right_now = new Timestamp(now);
		Timestamp past = new Timestamp(now - (dayCount * 86400000));
		
		User u = userDao.getUserById(user_id);
					
		List<SharesRecipe> recentShares = this.getTemplate().query(
				"SELECT * FROM SharesRecipe WHERE user_id = ? AND date BETWEEN ? AND ?",
				new Object[] { user_id, past, right_now }, new SharesRecipeRowMapper());
		
		List<Event> recentShareEvents = new ArrayList<Event>(recentShares.size());
		
		for(SharesRecipe s : recentShares){
			Recipe r = recipeDao.getRecipeById(s.getRecipe_id());
			String photo = recipeDao.getAllPhotoUrl(s.getRecipe_id())[0];
			recentShareEvents.add(new Event(u, "share_recipe", r, photo, s.getDate()));
		}
	
		return recentShareEvents;
	}
	/**
	 * get eat events of the user
	 * @param now
	 * @param dayCount, how many days will be looked
	 * @param user_id
	 * @return
	 */
	public List<Event> getRecentEatEventsOfUser(long now, int dayCount, long user_id){
		
		Timestamp right_now = new Timestamp(now);
		Timestamp past = new Timestamp(now - (dayCount * 86400000));
		
		User u = userDao.getUserById(user_id);
					
		List<EatLikeRate> recentEats = this.getTemplate().query(
				"SELECT * FROM EatLikeRate WHERE user_id = ? AND eat_date BETWEEN ? AND ?",
				new Object[] { user_id, past, right_now }, new EatLikeRateRowMapper());
		
		List<Event> recentEatEvents = new ArrayList<Event>(recentEats.size());
		
		for(EatLikeRate elr : recentEats){
			Recipe r = recipeDao.getRecipeById(elr.getRecipeId());
			String photo = recipeDao.getAllPhotoUrl(elr.getRecipeId())[0];
			if(elr.getEats() == 1)
				recentEatEvents.add(new Event(u, "eat_recipe", r, photo, elr.getEat_date()));
			/*
			else
				recentEatEvents.add(new Event(u, "not_eat_recipe", r, photo, elr.getEat_date()));
			*/
		}
	
		return recentEatEvents;
	}
	/**
	 * get recent like events of user
	 * @param now
	 * @param dayCount
	 * @param user_id
	 * @return
	 */
	public List<Event> getRecentLikeEventsOfUser(long now, int dayCount, long user_id){
		
		Timestamp right_now = new Timestamp(now);
		Timestamp past = new Timestamp(now - (dayCount * 86400000));
		
		User u = userDao.getUserById(user_id);
					
		List<EatLikeRate> recentLikes = this.getTemplate().query(
				"SELECT * FROM EatLikeRate WHERE user_id = ? AND like_date BETWEEN ? AND ?",
				new Object[] { user_id, past, right_now }, new EatLikeRateRowMapper());
		
		List<Event> recentLikeEvents = new ArrayList<Event>(recentLikes.size());
		
		for(EatLikeRate elr : recentLikes){
			Recipe r = recipeDao.getRecipeById(elr.getRecipeId());
			String photo = recipeDao.getAllPhotoUrl(elr.getRecipeId())[0];
			if(elr.getLikes() == 1)
				recentLikeEvents.add(new Event(u, "like_recipe", r, photo, elr.getLike_date()));
			/*
			else
				recentLikeEvents.add(new Event(u, "not_like_recipe", r, photo, elr.getLike_date()));
			*/
		}
	
		return recentLikeEvents;
	}
	/**
	 * get rate events of user
	 * @param now
	 * @param dayCount
	 * @param user_id
	 * @return
	 */
	public List<Event> getRecentRateEventsOfUser(long now, int dayCount, long user_id){
		
		Timestamp right_now = new Timestamp(now);
		Timestamp past = new Timestamp(now - (dayCount * 86400000));
		
		User u = userDao.getUserById(user_id);
					
		List<EatLikeRate> recentRates = this.getTemplate().query(
				"SELECT * FROM EatLikeRate WHERE user_id = ? AND rate_date BETWEEN ? AND ?",
				new Object[] { user_id, past, right_now }, new EatLikeRateRowMapper());
		
		List<Event> recentRateEvents = new ArrayList<Event>(recentRates.size());
		
		for(EatLikeRate elr : recentRates){
			Recipe r = recipeDao.getRecipeById(elr.getRecipeId());
			String photo = recipeDao.getAllPhotoUrl(elr.getRecipeId())[0];
			recentRateEvents.add(new Event(u, "rate_recipe", r, photo, elr.getRate_date()));
		}
	
		return recentRateEvents;
	}
	/**
	 * get recent follow event of the user
	 * @param now
	 * @param dayCount
	 * @param user_id
	 * @return
	 */
	public List<Event> getRecentFollowEventsOfUser(long now, int dayCount, long user_id){
		
		Timestamp right_now = new Timestamp(now);
		Timestamp past = new Timestamp(now - (dayCount * 86400000));
		
		User u = userDao.getUserById(user_id);
					
		List<FollowsUser> recentFollows = this.getTemplate().query(
				"SELECT * FROM Follows WHERE follower_id = ? OR followed_id = ? AND date BETWEEN ? AND ?",
				new Object[] { user_id, user_id, past, right_now }, new FollowsUserRowMapper());
		
		List<Event> recentFollowEvents = new ArrayList<Event>(recentFollows.size());
		
		for(FollowsUser f : recentFollows){
			if(f.getFollowed_id() == user_id){
				User target = userDao.getUserById(f.getFollower_id());
				recentFollowEvents.add(new Event(u, "get_followed", target, target.getPhoto(), f.getDate()));
			}
			else{
				User target = userDao.getUserById(f.getFollowed_id());
				recentFollowEvents.add(new Event(u, "follow_user", target, target.getPhoto(), f.getDate()));
			}
		}
	
		return recentFollowEvents;
	}
	/**
	 * get recent badge events of user
	 * @param now
	 * @param dayCount
	 * @param user_id
	 * @return
	 */
	public List<Event> getRecentBadgeEventsOfUser(long now, int dayCount, long user_id){
		
		Timestamp right_now = new Timestamp(now);
		Timestamp past = new Timestamp(now - (dayCount * 86400000));
		
		User u = userDao.getUserById(user_id);
					
		List<UserBadge> recentBadges = this.getTemplate().query(
				"SELECT * FROM UserBadge WHERE user_id = ? AND date BETWEEN ? AND ?",
				new Object[] { user_id, past, right_now }, new UserBadgeRowMapper());
		
		List<Event> recentBadgeEvents = new ArrayList<Event>(recentBadges.size());
		
		for(UserBadge b : recentBadges){

			Badge target = badgeDao.getBadgeById(b.getBadge_id());
			String photo = "http://ak2.polyvoreimg.com/cgi/img-thing/size/l/tid/91774.jpg";
			recentBadgeEvents.add(new Event(u, "earn_badge", target, photo, b.getDate()));
		}
	
		return recentBadgeEvents;
	}
}