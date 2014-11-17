package cmpesweng2014.group1.nutty.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.UserDao;
import cmpesweng2014.group1.nutty.model.User;

@Component
public class UserService {

	@Autowired
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public Long createUser(String email, String password, String name,
			String surname, Date birthday, int gender) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return userDao.createUser(email, encoder.encode(password), 
				name, surname, birthday, gender);
	}
	
	public User canLogin(String email, String password) {
		User u = userDao.getUserByEmail(email);
		if (u == null) { // no user
			return null;
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode(password));
		if(	encoder.matches(password, u.getPassword())){
			return u;
		}else{
			return null;
		}
	}
}
