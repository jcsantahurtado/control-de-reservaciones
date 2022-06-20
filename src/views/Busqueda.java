package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

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

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;

	private DefaultTableModel modelo; // Juan agregó
	private HuespedController huespedController;

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
		configurarContenido();
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

		JButton btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/lupa2.png")));
		btnBuscar.setBounds(815, 75, 54, 41);
		contentPane.add(btnBuscar);

		JButton btnEditar = new JButton("");

		btnEditar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				editar();

			}
		});

		btnEditar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/editar-texto.png")));
		btnEditar.setBackground(SystemColor.menu);
		btnEditar.setBounds(587, 416, 54, 41);
		contentPane.add(btnEditar);

		JLabel lblNewLabel_4 = new JLabel("Sistema de Búsqueda");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 24));
		lblNewLabel_4.setBounds(155, 42, 301, 42);
		contentPane.add(lblNewLabel_4);

		JButton btnSalir = new JButton("");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
		});
		btnSalir.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/cerrar-sesion 32-px.png")));
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setBackground(Color.WHITE);
		btnSalir.setBounds(815, 416, 54, 41);
		contentPane.add(btnSalir);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBounds(10, 127, 874, 265);
		contentPane.add(panel);

		tbHuespedes = new JTable();
		JScrollPane scrollPane = new JScrollPane(tbHuespedes);

		modelo = (DefaultTableModel) tbHuespedes.getModel();
		modelo.addColumn("Identificador");
		modelo.addColumn("Nombre");
		modelo.addColumn("Apellido");
		modelo.addColumn("Fecha de Nacimiento");
		modelo.addColumn("Nacionalidad");
		modelo.addColumn("Teléfono");

		scrollPane.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/persona.png")), scrollPane, null);

		cargarTablaHuespedes();

		JTable tbReservas = new JTable();
		tbReservas.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/calendario.png")), tbReservas,
				null);

		JButton btnEliminar = new JButton("");

		btnEliminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				eliminar();
				limpiarTabla();
				cargarTablaHuespedes();

			}
		});

		btnEliminar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/deletar.png")));
		btnEliminar.setBackground(SystemColor.menu);
		btnEliminar.setBounds(651, 416, 54, 41);
		contentPane.add(btnEliminar);

		JButton btnCancelar = new JButton("");

		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cancelar();

			}
		});

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

		tbHuespedes.clearSelection();

	}

	protected void editar() { // Carga los datos en la view RegistroHUesped

		if (!tieneFilaElegida()) {

			JOptionPane.showMessageDialog(this, "Por favor, elije un item.");
			return;

		}

		Optional.ofNullable(modelo.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))

				.ifPresentOrElse(fila -> {

					String id = (String) modelo.getValueAt(tbHuespedes.getSelectedRow(), 0);
					String nombre = (String) modelo.getValueAt(tbHuespedes.getSelectedRow(), 1);
					String apellido = (String) modelo.getValueAt(tbHuespedes.getSelectedRow(), 2);
					String fechaNacimiento = (String) modelo.getValueAt(tbHuespedes.getSelectedRow(), 3);
					String nacionalidad = (String) modelo.getValueAt(tbHuespedes.getSelectedRow(), 4);
					String telefono = (String) modelo.getValueAt(tbHuespedes.getSelectedRow(), 5);

					Date date = null;

					try {
						date = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					RegistroHuesped registroHuesped = new RegistroHuesped();
					registroHuesped.setTituloFormulario("Edición de Huésped");
					registroHuesped.setTxtNombre(nombre);
					registroHuesped.setTxtApellido(apellido);
					registroHuesped.setTxtFechaN(date);
					registroHuesped.setTxtNacionalidad(nacionalidad);
					registroHuesped.setTxtTelefono(telefono);
					registroHuesped.setIdBtnGuardar(id);
					registroHuesped.setIdBtnCancelar(id);
					registroHuesped.setVisible(true);
					dispose();

				}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item."));

	}

	protected void eliminar() {

		if (!tieneFilaElegida()) {

			JOptionPane.showMessageDialog(this, "Por favor, elije un item.");
			return;

		}

		Optional.ofNullable(modelo.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))

				.ifPresentOrElse(fila -> {

					Integer id = Integer.parseInt(modelo.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());

					int cantidadEliminada;

					try {
						cantidadEliminada = this.huespedController.eliminar(id);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}

					modelo.removeRow(tbHuespedes.getSelectedRow());

					JOptionPane.showMessageDialog(this, cantidadEliminada + " item eliminado con éxito!");

				}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item."));

	}

	private void limpiarTabla() {
		modelo.getDataVector().clear();
	}

	private boolean tieneFilaElegida() {
		return tbHuespedes.getSelectedRowCount() != 0 || tbHuespedes.getSelectedColumnCount() != 0;
	}

	private void cargarTablaHuespedes() {

		try {

			var huespedes = this.huespedController.listar();

			huespedes.forEach(huesped -> modelo
					.addRow(new Object[] { huesped.get("id"), huesped.get("nombre"), huesped.get("apellido"),
							huesped.get("fecha_nacimiento"), huesped.get("nacionalidad"), huesped.get("telefono") }));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
