package model;

public class Huesped {

	private Integer id;
	private String nombre;
	private String apellido;
	private java.sql.Date fechaNacimiento;
	private String nacionalidad;
	private String telefono;
	private Integer idReserva;

	public Huesped(String nombre, String apellido, java.sql.Date fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva) {

		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;

	}

	public Huesped(Integer id, String nombre, String apellido, java.sql.Date fechaNacimiento, String nacionalidad,
			String telefono, Integer idReserva) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getApellido() {
		return this.apellido;
	}

	public java.sql.Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public String getNacionalidad() {
		return this.nacionalidad;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public Integer getIdReserva() {
		return this.idReserva;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format(

				"{ id: %d, nombre: %s, apellido: %s, fecha_nacimiento: %s, nacionalidad: %s, telefono: %s }",

				this.id, this.nombre, this.apellido, this.fechaNacimiento, this.nacionalidad, this.telefono);
	}

}
