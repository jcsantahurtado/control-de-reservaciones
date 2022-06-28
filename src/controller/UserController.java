package controller;

import dao.UserDAO;
import factory.ConnectionFactory;
import model.User;

public class UserController {

	private UserDAO userDAO;

	public UserController() {
		var factory = new ConnectionFactory();
		this.userDAO = new UserDAO(factory.recuperaConexion());
	}

	public void login(User user) {
		userDAO.login(user);
	}

}
