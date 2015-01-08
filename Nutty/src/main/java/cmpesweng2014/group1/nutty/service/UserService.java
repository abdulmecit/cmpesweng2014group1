package cmpesweng2014.group1.nutty.service;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.FoodSelectionDao;
import cmpesweng2014.group1.nutty.dao.IngredientDao;
import cmpesweng2014.group1.nutty.dao.UserDao;
import cmpesweng2014.group1.nutty.dao.BadgeDao;
import cmpesweng2014.group1.nutty.model.Badge;
import cmpesweng2014.group1.nutty.model.FoodSelection;
import cmpesweng2014.group1.nutty.model.Message;
import cmpesweng2014.group1.nutty.model.User;
import cmpesweng2014.group1.nutty.model.UserRecipeScore;

@Component
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private FoodSelectionDao foodSelectionDao;
	@Autowired
	private IngredientDao ingredientDao;
	@Autowired
	private BadgeDao badgeDao;


	/**
	 * gets the userDao 
	 * 
	 * @return UserDao
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * setter for userDao
	 * 
	 * @param userDao
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/**
	 * gets the foodSelectionDao 
	 * 
	 * @return FoodSelectionDao
	 */
	public FoodSelectionDao getFoodSelectionDao() {
		return foodSelectionDao;
	}

	/**
	 * setter for foodSelectionDao
	 * 
	 * @param foodSelectionDao
	 */
	public void setFoodSelectionDao(FoodSelectionDao foodSelectionDao) {
		this.foodSelectionDao = foodSelectionDao;
	}

	/**
	 * 
	 * @param email String, user's email address
	 * @param password String, user's password
	 * @param name String, user's first name
	 * @param surname String, user's surname
	 * @param birthday Date, user's birthday
	 * @param gender int, user's gender. 0 for male, 1 for female
	 * @param photo String, the url of user's profile picture
	 * @return if the user created successfully or not
	 */
	
	public Message createUser(String email, String password, String name,
			String surname, Date birthday, Integer gender, String photo) {
		if(userDao.getUserByEmail(email) != null)
			return new Message(0, null, "This email address is already registered.");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//Create user
		Long user_id = userDao.createUser(email, encoder.encode(password), name, surname, birthday, gender, photo);
		//Add default privacy options
		userDao.addPrivacyOptions(user_id);
		User u = userDao.getUserById(user_id);
		return new Message(1, u, "Signup is successful.");
	}
	
	/**
	 * 
	 * @param email user's email
	 * @param password user's password, without encode
	 * @return if the user is allowed returns the User, otherwise null
	 */
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
	
	/**
	 * to use forgot password
	 * 
	 * @param token randomly generated token
	 * @param user_id the id of the user
	 * @return if the user is allowed to change password
	 */
	public boolean tokensDoMatch(String token, long user_id) {
		String hashedToken = userDao.getTokenById(user_id);
		if (hashedToken == null) { // no token
			return false;
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if(	encoder.matches(token, hashedToken)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * add food intolerance and health condition
	 * it is the same for both
	 * 
	 * @param user the user which owns the given food selection
	 * @param foodSelectionNames names for user's food selections
	 */
	public void addFoodSelection(User user, String[] foodSelectionNames){
		foodSelectionDao.deleteAllFoodSelection(user.getId());
		if(foodSelectionNames == null)
			return;	
		int[] fs_ids=new int[foodSelectionNames.length] ;
		int fs_id;
		for(int i=0; i<foodSelectionNames.length; i++){
			fs_id=foodSelectionDao.getFoodSelectionIdByName(foodSelectionNames[i]);
			fs_ids[i]=fs_id;
			foodSelectionDao.addFoodSelection(fs_id,user.getId());
		}		
	}
	 
	/**
	 * add unpreferred food
	 * 
	 * @param user user the user which owns the given food selection
	 * @param ing_grp_names names of unpreferred ingredients
	 */
	public void addUnpreferredFood(User user, String[] ing_grp_names){
		foodSelectionDao.deleteAllUnpreferredFood(user.getId());
		if(ing_grp_names == null)
			return;	
		for(int i=0; i<ing_grp_names.length; i++){
			foodSelectionDao.addUnpreferredFood(user.getId(), ing_grp_names[i]);
		}		
	}	
 
	/**
	 * 
	 * @param user the user
	 * @return food intolerances for the given user as an array of foodSelection objects
	 */
	public FoodSelection[] getFoodIntolerances(User user){
		return foodSelectionDao.getFoodSelectionForUser(user.getId(), "food_intolerance");
	} 
	/**
	 * 
	 * @param user the user
	 * @return health conditions for the given user as an array of foodSelection objects
	 */
	public FoodSelection[] getHealthCondition(User user){
		return foodSelectionDao.getFoodSelectionForUser(user.getId(), "health_condition");
	}
	
	/**
	 * 
	 * @param user the user
	 * @return food intolerances and health conditions combined
	 */
	public FoodSelection[] getFoodSelection(User user){
		return foodSelectionDao.getFoodSelectionForUser(user.getId());
	}
 
	/**
	 * 
	 * @param user the user
	 * @return unpreferred foods for the given user
	 */
	public String[] getUnpreferredForUser(User user){
		return foodSelectionDao.getUnpreferredFoodForUser(user.getId());
	}
	/**
	 * to add a follow request from a follower to followed
	 * 
	 * @param follower_id the id of the user who wants to follow
	 * @param followed_id the id of the user whom the follower wants to follow
	 */
	public void addFollowRequest(long follower_id, long followed_id){
		userDao.addFollowRequest(follower_id, followed_id);
	}
	/**
	 * To delete the follow request from db
	 * 
	 * @param follower_id id of the follow request sender
	 * @param followed_id id of the one who has a follow request
	 */
	public void deleteFollowRequest(long follower_id, long followed_id){
		userDao.deleteFollowRequest(follower_id, followed_id);
	}
	
	/**
	 * to add follower
	 * 
	 * @param follower_id the id of the user who wants to follow
	 * @param followed_id the id of the user whom the follower wants to follow
	 */
	public void addFollower(long follower_id, long followed_id){
		userDao.addFollower(follower_id, followed_id);
	}
	
	/**
	 * to delete the follower
	 * 
	 * @param follower_id user id of the follower
	 * @param followed_id user id of the followed
	 */
	public void unfollow(long follower_id, long followed_id){
		userDao.unfollow(follower_id, followed_id);
	}
 
	/**
	 * 
	 * @param user_id the id of the user
	 * @return followers as user objects
	 */
	public User[] getFollowerList(long user_id){
		return userDao.getFollowers(user_id);
	}
 
	/**
	 * 
	 * @param user_id the id of the user
	 * @return following list as user objects
	 */
	public User[] getFollowingList(long user_id){
		return userDao.getFollowings(user_id);
	}
	
	/**
	 * 
	 * @param user_id the id of the user
	 * @return number of followers
	 */
	public int getNumberOfFollowers(long user_id){
		if(getFollowerList(user_id)==null){
			return 0;
		}
		else{
			return getFollowerList(user_id).length;
		}
	}
 
	/**
	 * 
	 * @param user_id the id of the user
	 * @return the number of following
	 */
	public int getNumberOfFollowing(long user_id){
		if(getFollowingList(user_id)==null){
			return 0;
		}
		else{
			return getFollowingList(user_id).length;
		}
	}
	/**
	 * determines the follow status of an user against the other
	 * 
	 * @param follower_id id of the user which we want to know the follow status from
 	 * @param followed_id id of the user which we want to know the follow status to
	 * @return a string value true for following, false for not following, request for there is a live request
	 */
	public String getFollowStatus(long follower_id, long followed_id){
		if(isFollower(follower_id, followed_id))
			return "true";
		else{
			if(hasFollowRequest(follower_id, followed_id))
				return "request";
			else
				return "false";
		}
	}
	
	/**
	 * 
	 * @param follower_id id of the follower
	 * @param followed_id id of the followed
	 * @return if user with follower_id is a follower of the user with followed_id
	 */
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
 
	/**
	 * 
	 * @param follower_id id of the follower
	 * @param followed_id id of the followed
	 * @return if user with follower_id has sent a follow request to the user with followed_id
	 */
	public boolean hasFollowRequest(long follower_id, long followed_id){
		User[] followRequesterList = getUserDao().getFollowRequests(followed_id);
		if(followRequesterList == null){
			return false;
		}
		Arrays.sort(followRequesterList);
		int result = Arrays.binarySearch(followRequesterList, new User(follower_id, null, null, null, null, null, 0));
		if (result >= 0)
			return true;
		return false;
	}	
	
	/**
	 * 
	 * @param user_id the id of the user
	 * @return badge of the given user
	 */
	public Badge getBadge(long user_id){
		List<UserRecipeScore> scoreList = userDao.getUserRecipeScore(user_id);
		if(scoreList == null){
			Badge b = badgeDao.getBadgeById(1);
			return b;
			
		}else{
			UserRecipeScore [] scoreArray = scoreList.toArray(new UserRecipeScore[scoreList.size()]);
			double score = 0;
			for(int i=0;i<scoreList.size();i++){
				score += scoreArray[i].getScore();
			}
			Badge badge = badgeDao.getBadgeByScore(score);
			badgeDao.updateUserBadge(user_id,badge.getBadge_id());
			return badge;
		}
	}
	/**
	 * 
	 * @param user_id the id of the user
	 * @return the score of the given user
	 */
	public int getScore(long user_id){
		int score = 0;
		List<UserRecipeScore> scoreList = userDao.getUserRecipeScore(user_id);
		if(scoreList == null){
			return score;
			
		}else{
			UserRecipeScore [] scoreArray = scoreList.toArray(new UserRecipeScore[scoreList.size()]);
			
			for(int i=0;i<scoreList.size();i++){
				score += scoreArray[i].getScore();
			}
			return score;
		}
	}
}
