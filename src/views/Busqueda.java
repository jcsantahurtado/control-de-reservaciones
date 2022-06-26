package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.HuespedController;
import controller.ReservaController;
import model.Huesped;
import model.Reserva;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;

	private JTabbedPane panel;

	private DefaultTableModel modeloTbHuespedes; // Juan agregó
	private DefaultTableModel modeloTbReservas;

	private HuespedController huespedController;
	private ReservaController reservaController;

	private JButton btnBuscar;
	private JButton btnEditar;
	private JButton btnCancelar;
	private JButton btnEliminar;
	private JButton btnSalir;

	private List<Huesped> huespedes;
	private List<Reserva> reservas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {

		this.huespedController = new HuespedController();
		this.reservaController = new ReservaController();

		configurarContenido();
		configurarAccionesDelUsuario();
	}

	private void configurarAccionesDelUsuario() {

		txtBuscar.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				buscar();
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		btnEditar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});

		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eliminar();
				limpiarTabla();
				cargarTablaHuespedes("");
				cargarTablaReservas("");
			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});

		btnSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
		});

	}

	protected void buscar() {

		Integer index = panel.getSelectedIndex();
		String busqueda;
		busqueda = txtBuscar.getText();

		switch (index) {
		case 0:

			limpiarTabla();
			cargarTablaHuespedes(busqueda);
			tbHuespedes.revalidate();
			break;

		case 1:
			limpiarTablaReservas();
			cargarTablaReservas(busqueda);
			tbReservas.revalidate();
			break;

		default:
			busqueda = "";
			break;
		}

	}

	private void configurarContenido() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 516);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(647, 85, 158, 31);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		btnBuscar = new JButton("");
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/lupa2.png")));
		btnBuscar.setBounds(815, 75, 54, 41);
		contentPane.add(btnBuscar);

		btnEditar = new JButton("");
		btnEditar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/editar-texto.png")));
		btnEditar.setBackground(SystemColor.menu);
		btnEditar.setBounds(587, 416, 54, 41);
		contentPane.add(btnEditar);

		JLabel lblNewLabel_4 = new JLabel("Sistema de Búsqueda");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 24));
		lblNewLabel_4.setBounds(155, 42, 301, 42);
		contentPane.add(lblNewLabel_4);

		btnSalir = new JButton("");
		btnSalir.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/cerrar-sesion 32-px.png")));
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setBackground(Color.WHITE);
		btnSalir.setBounds(815, 416, 54, 41);
		contentPane.add(btnSalir);

		panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBounds(10, 127, 874, 265);
		contentPane.add(panel);

		tbHuespedes = new JTable();
		JScrollPane scrollPane1 = new JScrollPane(tbHuespedes);

		modeloTbHuespedes = (DefaultTableModel) tbHuespedes.getModel();
		modeloTbHuespedes.addColumn("Identificador");
		modeloTbHuespedes.addColumn("Nombre");
		modeloTbHuespedes.addColumn("Apellido");
		modeloTbHuespedes.addColumn("Fecha de Nacimiento");
		modeloTbHuespedes.addColumn("Nacionalidad");
		modeloTbHuespedes.addColumn("Teléfono");
		modeloTbHuespedes.addColumn("Id Reserva");

		scrollPane1.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/persona.png")), scrollPane1,
				null);

		cargarTablaHuespedes("");

		tbReservas = new JTable();
		JScrollPane scrollPane2 = new JScrollPane(tbReservas);

		modeloTbReservas = (DefaultTableModel) tbReservas.getModel();
		modeloTbReservas.addColumn("Identificador");
		modeloTbReservas.addColumn("Fecha Entrada");
		modeloTbReservas.addColumn("Fecha Salida");
		modeloTbReservas.addColumn("Valor");
		modeloTbReservas.addColumn("Forma de Pago");

		scrollPane2.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/calendario.png")), scrollPane2,
				null);

		cargarTablaReservas("");

		btnEliminar = new JButton("");
		btnEliminar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/deletar.png")));
		btnEliminar.setBackground(SystemColor.menu);
		btnEliminar.setBounds(651, 416, 54, 41);
		contentPane.add(btnEliminar);

		btnCancelar = new JButton("");
		btnCancelar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/cancelar.png")));
		btnCancelar.setBackground(SystemColor.menu);
		btnCancelar.setBounds(713, 416, 54, 41);
		contentPane.add(btnCancelar);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(25, 10, 104, 107);
		contentPane.add(lblNewLabel_2);
		setResizable(false);
	}

	protected void cancelar() {

		MenuUsuario menuUsuario = new MenuUsuario();
		menuUsuario.setVisible(true);
		dispose();

	}

	protected void editar() { // Carga los datos en la view RegistroHUesped

		Integer index = panel.getSelectedIndex();

		switch (index) {
		case 0:
			if (!tieneFilaElegida(tbHuespedes)) {

				JOptionPane.showMessageDialog(this, "Por favor, elije un item.");
				return;

			}

			Optional.ofNullable(
					modeloTbHuespedes.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))

					.ifPresentOrElse(fila -> {

						Integer id = (Integer) modeloTbHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 0);
						String nombre = (String) modeloTbHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 1);
						String apellido = (String) modeloTbHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 2);
						Date fechaNacimiento = (Date) modeloTbHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 3);
						String nacionalidad = (String) modeloTbHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 4);
						String telefono = (String) modeloTbHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 5);
						Integer idReserva = (Integer) modeloTbHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 6);

						Huesped huesped = new Huesped(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono,
								idReserva);

						RegistroHuesped registroHuesped = new RegistroHuesped();
						registroHuesped.configurarDatosHuespedEnFormulario(huesped);
						registroHuesped.setVisible(true);
						dispose();

					}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item."));

			break;

		case 1:
			if (!tieneFilaElegida(tbReservas)) {

				JOptionPane.showMessageDialog(this, "Por favor, elije un item.");
				return;

			}

			Optional.ofNullable(
					modeloTbReservas.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))

					.ifPresentOrElse(fila -> {

						Integer id = (Integer) modeloTbReservas.getValueAt(tbReservas.getSelectedRow(), 0);
						Date fechaEntrada = (Date) modeloTbReservas.getValueAt(tbReservas.getSelectedRow(), 1);
						Date fechaSalida = (Date) modeloTbReservas.getValueAt(tbReservas.getSelectedRow(), 2);
						Double valor = (Double) modeloTbReservas.getValueAt(tbReservas.getSelectedRow(), 3);
						String formaPago = (String) modeloTbReservas.getValueAt(tbReservas.getSelectedRow(), 4);

						Reserva reserva = new Reserva(id, fechaEntrada, fechaSalida, valor, formaPago);

						Reservas reservas = new Reservas();
						reservas.configurarDatosReservaEnFormulario(reserva);
						reservas.setVisible(true);
						dispose();

					}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item."));

		default:
			break;
		}

	}

	protected void eliminar() {

		Integer index = panel.getSelectedIndex();

		switch (index) {
		case 0:
			if (!tieneFilaElegida(tbHuespedes)) {

				JOptionPane.showMessageDialog(this, "Por favor, elije un item.");
				return;

			}

			Optional.ofNullable(
					modeloTbHuespedes.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))

					.ifPresentOrElse(fila -> {

						Integer id = Integer
								.parseInt(modeloTbHuespedes.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());

						int cantidadEliminada;

						cantidadEliminada = this.huespedController.eliminar(id);

						modeloTbHuespedes.removeRow(tbHuespedes.getSelectedRow());

						JOptionPane.showMessageDialog(this, cantidadEliminada + " item eliminado con éxito!");

					}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item."));

			break;

		case 1:

			System.out.println("Reservas");

			if (!tieneFilaElegida(tbReservas)) {

				JOptionPane.showMessageDialog(this, "Por favor, elije un item.");
				return;

			}

			Optional.ofNullable(
					modeloTbReservas.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))

					.ifPresentOrElse(fila -> {

						Integer id = Integer
								.parseInt(modeloTbReservas.getValueAt(tbReservas.getSelectedRow(), 0).toString());

						int cantidadEliminada;

						cantidadEliminada = this.reservaController.eliminar(id);

						modeloTbReservas.removeRow(tbReservas.getSelectedRow());

						JOptionPane.showMessageDialog(this, cantidadEliminada + " item eliminado con éxito!");

					}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item."));

			break;

		default:
			break;
		}

	}

	private void limpiarTabla() {
		modeloTbHuespedes.getDataVector().clear();
	}

	private void limpiarTablaReservas() {
		modeloTbReservas.getDataVector().clear();
	}

	private boolean tieneFilaElegida(JTable tbSeleccionada) {
		return tbSeleccionada.getSelectedRowCount() != 0 || tbSeleccionada.getSelectedColumnCount() != 0;
	}

	private void cargarTablaHuespedes(String busqueda) {

		huespedes = this.huespedController.listar();

		List<Huesped> collect = huespedes.stream().filter(huesped -> huesped.getApellido().contains(busqueda))
				.collect(Collectors.toList());

		System.out.println(collect);

		collect.forEach(huesped -> modeloTbHuespedes.addRow(new Object[] { huesped.getId(), huesped.getNombre(),
				huesped.getApellido(), huesped.getFechaNacimiento(), huesped.getNacionalidad(), huesped.getTelefono(),
				huesped.getIdReserva() }));

	}

	private void cargarTablaReservas(String busqueda) {

		reservas = this.reservaController.listar();

		List<Reserva> collect = reservas.stream().filter(reserva -> reserva.getFormaPago().contains(busqueda))
				.collect(Collectors.toList());

		collect.forEach(reserva -> modeloTbReservas.addRow(new Object[] { reserva.getId(), reserva.getFechaEntrada(),
				reserva.getFechaSalida(), reserva.getValor(), reserva.getFormaPago() }));

	}

}
