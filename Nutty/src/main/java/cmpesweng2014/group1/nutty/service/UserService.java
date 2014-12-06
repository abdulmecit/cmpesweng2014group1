package cmpesweng2014.group1.nutty.service;

import java.sql.Date;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.FoodSelectionDao;
import cmpesweng2014.group1.nutty.dao.IngredientDao;
import cmpesweng2014.group1.nutty.dao.UserDao;
import cmpesweng2014.group1.nutty.model.FoodSelection;
import cmpesweng2014.group1.nutty.model.Ingredient;
import cmpesweng2014.group1.nutty.model.User;

@Component
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private FoodSelectionDao foodSelectionDao;
	@Autowired
	private IngredientDao ingredientDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User createUser(String email, String password, String name,
			String surname, Date birthday, int gender) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return userDao.getUserById(userDao.createUser(email, encoder.encode(password), 
				name, surname, birthday, gender));
	}
	
	public User canLogin(String email, String password) {
		User u = userDao.getUserByEmail(email);
		if (u == null) { // no user
			return null;
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(	encoder.matches(password, u.getPassword())){
			return u;
		}else{
			return null;
		}
	}
	
	//add food intolerance and health condition
	//it is the same to add food intolerance and health condition 
	public void addFoodSelectionAndHealth(User user, String[] FoodSelectionNames){
		int[] fs_ids=new int[FoodSelectionNames.length] ;
		int fs_id;
		for(int i=0; i<FoodSelectionNames.length; i++){
			fs_id=foodSelectionDao.getFoodSelectionIdByName(FoodSelectionNames[i]);
			fs_ids[i]=fs_id;
			foodSelectionDao.updateFoodSelection(fs_id,user.getId());
		}		
	}
	//add unpreferred food 
	public void addUnpreferredFood(User user, String[] foodNames){
		int ing_id;
		for(int i=0; i<foodNames.length; i++){
			ing_id=ingredientDao.getIdByName(foodNames[i]);
			foodSelectionDao.updateUnpreferredFood(ing_id, user.getId());
		}		
	}	
	//returns food intolerances for the given user as an array of foodSelection objects
	public FoodSelection[] getFoodIntolerances(User user){
		return foodSelectionDao.getFoodSelectionForUser(user.getId(), "food_intolerance");
	}
	//returns health conditions for the given user as an array of foodSelection objects
	public FoodSelection[] getHealthCondition(User user){
		return foodSelectionDao.getFoodSelectionForUser(user.getId(), "health_condition");
	}
	//returns unpreferred foods for the given user
	public Ingredient[] getUnpreferredForUser(User user){
		return foodSelectionDao.getUnpreferredFoodForUser(user.getId());
	}
	//add follower
	//for now no private users, directly added to follower list
	public void addFollower(long follower_id, long followed_id){
		userDao.addFollower(follower_id, followed_id);
	}
	//unfollow
	//deletes the row. not sure if the query will be problematic or not?
	public void unfollow(long follower_id, long followed_id){
		userDao.unfollow(follower_id, followed_id);
	}
	//returns followers as user objects
	public User[] getFollowerList(long user_id){
		return userDao.getFollowers(user_id);
	}
	//returns following list as user objects
	public User[] getFollowingList(long user_id){
		return userDao.getFollowings(user_id);
	}
	//returns number of followers
	public int getNumberOfFollowers(long user_id){
		if(getFollowerList(user_id)==null){
			return 0;
		}
		else{
			return getFollowerList(user_id).length;
		}
	}
	//returns number of following
	public int getNumberOfFollowing(long user_id){
		if(getFollowingList(user_id)==null){
			return 0;
		}
		else{
			return getFollowingList(user_id).length;
		}
	}
	//determines if 
	public boolean isFollower(long follower_id, long followed_id){
		User[] followerList = getFollowerList(followed_id);
		if(followerList == null){
			return false;
		}
		Arrays.sort(followerList);
		int result = Arrays.binarySearch(followerList, new User(follower_id, null, null, null, null, null, 0));
		if (result >= 0)
			return true;
		return false;
	}
	
}
