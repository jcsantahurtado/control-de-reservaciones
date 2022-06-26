package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Reserva;

public class ReservaDAO {

	final private Connection con;

	public ReservaDAO(Connection con) {
		this.con = con;
	}

	public List<Reserva> listar() {

		List<Reserva> resultado = new ArrayList<>();

		try {

			final PreparedStatement statement = con
					.prepareStatement("SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reserva");

			try (statement) {

				statement.execute();

				final ResultSet resultSet = statement.getResultSet();

				while (resultSet.next()) {

					Reserva fila = new Reserva(resultSet.getInt("id"), resultSet.getDate("fecha_entrada"),
							resultSet.getDate("fecha_salida"), resultSet.getDouble("valor"),
							resultSet.getString("forma_pago"));

					resultado.add(fila);

				}

				return resultado;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void guardar(Reserva reserva) {

		try {

			final PreparedStatement statement = con.prepareStatement(

					"INSERT INTO reserva (fecha_entrada, fecha_salida, valor, forma_pago) "

							+ "VALUES (?, ?, ?, ?)",

					Statement.RETURN_GENERATED_KEYS);

			try (statement) {

				ejecutarRegistro(reserva, statement);

			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void ejecutarRegistro(Reserva reserva, PreparedStatement statement) throws SQLException {

		statement.setDate(1, reserva.getFechaEntrada());
		statement.setDate(2, reserva.getFechaSalida());
		statement.setDouble(3, reserva.getValor());
		statement.setString(4, reserva.getFormaPago());

		statement.execute();

		ResultSet generatedKeys = statement.getGeneratedKeys();

		while (generatedKeys.next()) {

			reserva.setId(generatedKeys.getInt(1));

			System.out.println(String.format("Fue ingresada la reserva %d", reserva.getId()));

		}

	}

	public void actualiar(Reserva reserva) {

		try {

			final PreparedStatement statement = con.prepareStatement("UPDATE reserva SET "

					+ "fecha_entrada = ?"

					+ ", fecha_salida = ?"

					+ ", valor = ?"

					+ ", forma_pago = ?"

					+ "WHERE id = ?");

			try (statement) {

				statement.setDate(1, reserva.getFechaEntrada());
				statement.setDate(2, reserva.getFechaSalida());
				statement.setDouble(3, reserva.getValor());
				statement.setString(4, reserva.getFormaPago());
				statement.setInt(5, reserva.getId());

				statement.execute();

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public int eliminar(Integer id) {

		try {

			final PreparedStatement statement = con.prepareStatement("DELETE FROM reserva WHERE id = ?");
			System.out.println("---");

			try (statement) {

				statement.setInt(1, id);

				statement.execute();

				return statement.getUpdateCount();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
