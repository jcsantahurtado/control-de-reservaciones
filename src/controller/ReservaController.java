package controller;

import java.util.List;

import dao.ReservaDAO;
import factory.ConnectionFactory;
import model.Reserva;

public class ReservaController {

	private ReservaDAO reservaDAO;

	public ReservaController() {
		var factory = new ConnectionFactory();
		this.reservaDAO = new ReservaDAO(factory.recuperaConexion());
	}

	public List<Reserva> listar() {
		return reservaDAO.listar();
	}

	public void guardar(Reserva reserva) {
		reservaDAO.guardar(reserva);
	}

	public void actualizar(Reserva reserva) {
		reservaDAO.actualiar(reserva);
	}

	public int eliminar(Integer id) {
		return reservaDAO.eliminar(id);
	}

}
