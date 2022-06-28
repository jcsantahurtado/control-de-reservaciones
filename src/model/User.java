package model;

public class User {

	private Integer id;
	private String username;
	private String password;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUser() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("{ id: %d, username: %s, password: %s }", this.id, this.username, this.password);
	}

}
