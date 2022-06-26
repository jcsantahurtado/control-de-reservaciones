package controller;

import java.util.List;

import dao.HuespedDAO;
import factory.ConnectionFactory;
import model.Huesped;

public class HuespedController {

	private HuespedDAO huespedDAO;

	public HuespedController() {
		this.huespedDAO = new HuespedDAO(new ConnectionFactory().recuperaConexion());
	}

	public List<Huesped> listar() {

		return huespedDAO.listar();

	}

	public void guardar(Huesped huesped) {

		huespedDAO.guardar(huesped);

	}

	public int eliminar(Integer id) {

		return huespedDAO.eliminar(id);

	}

	public void actualizar(Huesped huesped) {

		huespedDAO.actualiar(huesped);

	}

}
