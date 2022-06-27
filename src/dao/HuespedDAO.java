package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Huesped;

public class HuespedDAO {

	final private Connection con;

	public HuespedDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Huesped huesped) {

		try {

			final PreparedStatement statement = con.prepareStatement(

					"INSERT INTO huesped (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, reserva_id) "

							+ "VALUES (?, ?, ?, ?, ?, ?)",

					Statement.RETURN_GENERATED_KEYS);

			try (statement) {

				ejecutarRegistro(huesped, statement);

			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void ejecutarRegistro(Huesped huesped, PreparedStatement statement) throws SQLException {

		statement.setString(1, huesped.getNombre());
		statement.setString(2, huesped.getApellido());
		statement.setDate(3, huesped.getFechaNacimiento());
		statement.setString(4, huesped.getNacionalidad());
		statement.setString(5, huesped.getTelefono());
		statement.setInt(6, huesped.getIdReserva());

		statement.execute();

		ResultSet generatedKeys = statement.getGeneratedKeys();

		while (generatedKeys.next()) {

			huesped.setId(generatedKeys.getInt(1));

			System.out.println(String.format("Fue ingresado el huesped %s", huesped));

		}
	}

	public List<Huesped> listar() {

		List<Huesped> resultado = new ArrayList<>();

		try {

			var querySelect = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, reserva_id FROM huesped";

			final PreparedStatement statement = con.prepareStatement(querySelect);

			try (statement) {

				statement.execute();

				final ResultSet resultSet = statement.getResultSet();

				while (resultSet.next()) {

					Huesped fila = new Huesped(resultSet.getInt("id"), resultSet.getString("nombre"),
							resultSet.getString("apellido"), resultSet.getDate("fecha_nacimiento"),
							resultSet.getString("nacionalidad"), resultSet.getString("telefono"),
							resultSet.getInt("reserva_id"));

					resultado.add(fila);

				}

				return resultado;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int eliminar(Integer id) {

		System.out.println(id);

		try {

			final PreparedStatement statement = con.prepareStatement("DELETE FROM huesped WHERE id = ?");

			try (statement) {

				statement.setInt(1, id);

				statement.execute();

				return statement.getUpdateCount();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void actualiar(Huesped huesped) {

		try (con) {

			final PreparedStatement statement = con.prepareStatement("UPDATE huesped SET "

					+ "nombre = ?"

					+ ", apellido = ?"

					+ ", fecha_nacimiento = ?"

					+ ", nacionalidad = ?"

					+ ", telefono = ?"

					+ "WHERE id = ?");

			try (statement) {

				statement.setString(1, huesped.getNombre());
				statement.setString(2, huesped.getApellido());
				statement.setDate(3, huesped.getFechaNacimiento());
				statement.setString(4, huesped.getNacionalidad());
				statement.setString(5, huesped.getTelefono());
				statement.setInt(6, huesped.getId());

				statement.execute();

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
