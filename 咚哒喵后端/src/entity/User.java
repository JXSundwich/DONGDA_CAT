package entity;

public class User {
	
	private int userID;
	private String openID;
	private String name;
	private boolean sex;
	private int age;
	private String avatarUrl;
	private int limit;

	public User() {
		// TODO 自动生成的构造函数存根
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String object) {
		this.openID = object;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getSex() {
		return sex;
	}

	public void setSex(boolean b) {
		this.sex = b;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
