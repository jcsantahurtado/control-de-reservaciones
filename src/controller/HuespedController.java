package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import factory.ConnectionFactory;

public class HuespedController {

	public List<Map<String, String>> listar() throws SQLException {

		final Connection con = new ConnectionFactory().recuperaConexion();

		try (con) {

			PreparedStatement statement = con.prepareStatement(
					"SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono FROM huesped");

			try (statement) {

				statement.execute();

				ResultSet resultSet = statement.getResultSet();

				List<Map<String, String>> resultado = new ArrayList<>();

				while (resultSet.next()) {

					Map<String, String> fila = new HashMap<>();
					fila.put("id", String.valueOf(resultSet.getInt("id")));
					fila.put("nombre", resultSet.getString("nombre"));
					fila.put("apellido", resultSet.getString("apellido"));
					fila.put("fecha_nacimiento", resultSet.getString("fecha_nacimiento"));
					fila.put("nacionalidad", resultSet.getString("nacionalidad"));
					fila.put("telefono", resultSet.getString("telefono"));

					resultado.add(fila);

				}

				return resultado;

			}
		}
	}

	public void guardar(Map<String, String> huesped) throws SQLException {

		final Connection con = new ConnectionFactory().recuperaConexion();

		try (con) {

			final PreparedStatement statement = con.prepareStatement(

					"INSERT INTO huesped (nombre, apellido, fecha_nacimiento, nacionalidad, telefono) "

							+ "VALUES (?, ?, ?, ?, ?)",

					Statement.RETURN_GENERATED_KEYS);

			try (statement) {

				statement.setString(1, huesped.get("nombre"));
				statement.setString(2, huesped.get("apellido"));
				statement.setString(3, huesped.get("fecha_nacimiento"));
				statement.setString(4, huesped.get("nacionalidad"));
				statement.setString(5, huesped.get("telefono"));

				statement.execute();

				ResultSet generatedKeys = statement.getGeneratedKeys();

				while (generatedKeys.next()) {

					System.out.println(String.format("Fue ingresado el huesped con ID %d", generatedKeys.getInt(1)));

				}

			}

		}

	}

	public int eliminar(Integer id) throws SQLException {

		final Connection con = new ConnectionFactory().recuperaConexion();

		try (con) {

			final PreparedStatement statement = con.prepareStatement("DELETE FROM huesped WHERE id = ?");

			try (statement) {

				statement.setInt(1, id);

				statement.execute();

				return statement.getUpdateCount();
			}
		}
	}

	public void actualizar(HashMap<String, String> huesped) throws SQLException {

		final Connection con = new ConnectionFactory().recuperaConexion();

		try (con) {

			final PreparedStatement statement = con.prepareStatement("UPDATE huesped SET "

					+ "nombre = ?"

					+ ", apellido = ?"

					+ ", fecha_nacimiento = ?"

					+ ", nacionalidad = ?"

					+ ", telefono = ?"

					+ "WHERE id = ?");

			try (statement) {

				statement.setString(1, huesped.get("nombre"));
				statement.setString(2, huesped.get("apellido"));
				statement.setString(3, huesped.get("fecha_nacimiento"));
				statement.setString(4, huesped.get("nacionalidad"));
				statement.setString(5, huesped.get("telefono"));
				statement.setInt(6, Integer.parseInt(huesped.get("id")));

				statement.execute();

			}
		}
	}
}
