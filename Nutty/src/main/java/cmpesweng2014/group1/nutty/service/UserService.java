package cmpesweng2014.group1.nutty.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.FoodSelectionDao;
import cmpesweng2014.group1.nutty.dao.UserDao;
import cmpesweng2014.group1.nutty.model.User;

@Component
public class UserService {

	@Autowired
	private UserDao userDao;
	private FoodSelectionDao foodSelectionDao;

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
	
	//not complete yet
	public void addFoodIntolerance(User user, String[] FoodSelectionNames){
		int[] fs_ids=new int[FoodSelectionNames.length] ;
		for(int i=0; i<FoodSelectionNames.length; i++){
			fs_ids[i]=foodSelectionDao.getFoodSelectionIdByName(FoodSelectionNames[i]);
		}
		userDao.addFoodIntolerance(user, fs_ids);
	}
}
