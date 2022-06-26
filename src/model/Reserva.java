package model;

import java.sql.Date;

public class Reserva {

	private Integer id;
	private Date fechaEntrada;
	private Date fechaSalida;
	private Double valor;
	private String formaPago;

	public Reserva(Date fechaEntrada, Date fechaSalida, Double valor, String formaPago) {
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.formaPago = formaPago;
	}

	public Reserva(Integer id, Date fechaEntrada, Date fechaSalida, Double valor, String formaPago) {
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.formaPago = formaPago;
	}

	public Integer getId() {
		return this.id;
	}

	public Date getFechaEntrada() {
		return this.fechaEntrada;
	}

	public Date getFechaSalida() {
		return this.fechaSalida;
	}

	public Double getValor() {
		return this.valor;
	}

	public String getFormaPago() {
		return this.formaPago;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("{ id: %d, fechaEntrada: %s, fechaSalida: %s, valor: %f formaPago: %s }", this.id,
				this.fechaEntrada, this.fechaSalida, this.valor, this.formaPago);
	}
}
