package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;

public class UserDAO {

	final private Connection con;

	public UserDAO(Connection con) {
		this.con = con;
	}

	public User login(User user) {

		try {

			final PreparedStatement statement = con.prepareStatement(

					"SELECT id, username, password FROM user WHERE username = ? AND password = ?");

			try (statement) {

				statement.setString(1, user.getUser());
				statement.setString(2, user.getPassword());

				statement.execute();

				ResultSet resultSet = statement.getResultSet();

				while (resultSet.next()) {

					Integer id = resultSet.getInt("id");

					user.setId(id);

				}

				return user;

			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
