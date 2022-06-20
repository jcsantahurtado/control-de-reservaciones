package controller;

import java.sql.Connection;
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

		Connection con = new ConnectionFactory().recuperaConexion();

		Statement statement = con.createStatement();

		statement.execute("SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono FROM huesped");

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

		con.close();

		return resultado;
	}

	public void guardar(Map<String, String> huesped) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement createStatement = con.createStatement();
		createStatement.execute("INSERT INTO huesped ("

				+ "nombre, "

				+ "apellido, "

				+ "fecha_nacimiento, "

				+ "nacionalidad, "

				+ "telefono) "

				+ "VALUES("

				+ "'" + huesped.get("nombre") + "', "

				+ "'" + huesped.get("apellido") + "', "

				+ "'" + huesped.get("fecha_nacimiento") + "', "

				+ "'" + huesped.get("nacionalidad") + "', "

				+ "'" + huesped.get("telefono") + "')", Statement.RETURN_GENERATED_KEYS);

		ResultSet generatedKeys = createStatement.getGeneratedKeys();

		while (generatedKeys.next()) {

			System.out.println(String.format("Fue ingresado el huesped con ID %d", generatedKeys.getInt(1)));

		}
	}

	public int eliminar(Integer id) throws SQLException {

		Connection con = new ConnectionFactory().recuperaConexion();

		Statement statement = con.createStatement();

		statement.execute("DELETE FROM huesped WHERE id = " + id);

		return statement.getUpdateCount();

	}

	public void actualizar(HashMap<String, String> huesped) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();

		Statement statement = con.createStatement();

		statement.execute("UPDATE huesped SET "

				+ "nombre = '" + huesped.get("nombre") + "', "

				+ "apellido = '" + huesped.get("apellido") + "', "

				+ "fecha_nacimiento = '" + huesped.get("fecha_nacimiento") + "', "

				+ "nacionalidad = '" + huesped.get("nacionalidad") + "', "

				+ "telefono = '" + huesped.get("telefono") + "'"

				+ "WHERE id =" + huesped.get("id") + "");

	}

}
