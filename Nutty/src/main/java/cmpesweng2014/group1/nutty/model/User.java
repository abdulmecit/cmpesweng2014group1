package cmpesweng2014.group1.nutty.model;

import java.util.Date;

public class User implements Comparable<User>{
	private Long id;
	private String email;
	private String password;
	private String name;
	private String surname;
	private Date birthday;
	private Integer gender;
	private int isBanned;
	private String photo;
	
	public User() {
		super();
	}

	public User(Long id, String email, String password, String name,
			String surname, Date birthday, Integer gender) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.gender = gender;
		this.isBanned = 0;
		this.photo = null;
	}
	
	public User(Long id, String email, String password, String name,
			String surname, Date birthday, Integer gender, int isBanned, String photo) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.gender = gender;
		this.isBanned = isBanned;
		this.photo = photo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
	
	public int getIsBanned() {
		return isBanned;
	}

	public void setIsBanned(int isBanned) {
		this.isBanned = isBanned;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public int compareTo(User other) {
		return this.id.compareTo(other.id);
	}
}
