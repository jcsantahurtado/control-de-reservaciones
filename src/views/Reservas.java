package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import controller.ReservaController;
import model.Reserva;

@SuppressWarnings("serial")
public class Reservas extends JFrame {

	private JPanel contentPane;

	private JDateChooser txtFechaE;
	private JDateChooser txtFechaS;
	private JTextField txtValor;
	private JComboBox<String> txtFormaPago;

	private JButton btnReservar;

	private ReservaController reservaController;

	private Reserva reserva;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reservas frame = new Reservas();
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
	public Reservas() {

		this.reservaController = new ReservaController();

		configurarFormularioReserva();
		configurarAccionesDelUsuario();

	}

	private void configurarAccionesDelUsuario() {

		txtFechaE.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				if (txtFechaE.getDate() != null) {
					txtFechaS.setMinSelectableDate(txtFechaE.getDate());
				}

				calcularValor();

			}
		});

		txtFechaS.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				calcularValor();

			}
		});

		btnReservar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (reserva != null) {

					actualizar(reserva.getId());

				} else {

					Reserva reserva = guardar();

					RegistroHuesped registroHuesped = new RegistroHuesped();
					registroHuesped.configurarDatosReserva(reserva);
					registroHuesped.setVisible(true);
					dispose();

				}

			}
		});
	}

	protected void actualizar(Integer id) {

		var reserva = new Reserva(id, new Date(txtFechaE.getDate().getTime()), new Date(txtFechaS.getDate().getTime()),
				Double.parseDouble(txtValor.getText()), String.valueOf(txtFormaPago.getSelectedItem()));

		this.reservaController.actualizar(reserva);

		JOptionPane.showMessageDialog(this, "Actualizado con Exito.");

		Busqueda busqueda = new Busqueda();
		busqueda.setVisible(true);
		dispose();

	}

	protected void calcularValor() {

		if (txtFechaE.getDate() != null && txtFechaS.getDate() != null) {

			Date fechaEntrada = new Date(txtFechaE.getDate().getTime());
			Date fechaSalida = new Date(txtFechaS.getDate().getTime());

			double valorTotal, valorDiario;
			valorDiario = 95000;
			int milisecondsByDay = 86400000;
			int dias = (int) ((fechaSalida.getTime() - fechaEntrada.getTime()) / milisecondsByDay);

			valorTotal = dias * valorDiario;

			txtValor.setText("" + valorTotal);

		}

	}

	protected Reserva guardar() {

		if (txtFechaE.getDate() == null || txtFechaS.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Todos los campos son requeridos.");
			return null;
		}

		Date fechaEntrada = new Date(txtFechaE.getDate().getTime());
		Date fechaSalida = new Date(txtFechaS.getDate().getTime());

		var reserva = new Reserva(fechaEntrada, fechaSalida, Double.parseDouble(txtValor.getText()),
				txtFormaPago.getSelectedItem().toString());

		this.reservaController.guardar(reserva);

		return reserva;
	}

	private void configurarFormularioReserva() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Reservas.class.getResource("/imagenes/calendario.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 540);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(245, 245, 245));
		panel.setBounds(0, 0, 900, 502);
		contentPane.add(panel);
		panel.setLayout(null);

		txtFechaE = new JDateChooser();
		txtFechaE.setMinSelectableDate(new java.util.Date());
		txtFechaE.setBounds(88, 166, 235, 33);
		panel.add(txtFechaE);

		JLabel lblFechaEntrada = new JLabel("Fecha de Check In");
		lblFechaEntrada.setBounds(88, 142, 133, 14);
		lblFechaEntrada.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lblFechaEntrada);

		JLabel lblFechaSalida = new JLabel("Fecha de Check Out");
		lblFechaSalida.setBounds(88, 210, 133, 14);
		lblFechaSalida.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lblFechaSalida);

		txtFechaS = new JDateChooser();
		txtFechaS.setMinSelectableDate(new java.util.Date());
		txtFechaS.setBounds(88, 234, 235, 33);
		txtFechaS.getCalendarButton().setBackground(Color.WHITE);
		panel.add(txtFechaS);

		txtValor = new JTextField();
		txtValor.setBounds(88, 303, 235, 33);
		txtValor.setEnabled(false);
		panel.add(txtValor);
		txtValor.setColumns(10);

		JLabel lblValorReserva = new JLabel("Valor de la Reserva");
		lblValorReserva.setBounds(88, 278, 133, 14);
		lblValorReserva.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lblValorReserva);

		txtFormaPago = new JComboBox<>();
		txtFormaPago.setBounds(88, 373, 235, 33);
		txtFormaPago.setFont(new Font("Arial", Font.PLAIN, 14));
		txtFormaPago.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Tarjeta de Crédito", "Tarjeta de Débito", "Dinero en efectivo" }));
		panel.add(txtFormaPago);

		JLabel lblFormaPago = new JLabel("Forma de pago");
		lblFormaPago.setBounds(88, 347, 133, 24);
		lblFormaPago.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lblFormaPago);

		JLabel lblTitulo = new JLabel("Sistema de Reservas");
		lblTitulo.setBounds(77, 93, 257, 42);
		lblTitulo.setForeground(new Color(65, 105, 225));
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(lblTitulo);

		btnReservar = new JButton("Continuar");

		btnReservar.setForeground(Color.WHITE);
		btnReservar.setBounds(183, 436, 140, 33);
		btnReservar.setIcon(new ImageIcon(Reservas.class.getResource("/imagenes/calendario.png")));
		btnReservar.setBackground(new Color(65, 105, 225));
		btnReservar.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(btnReservar);

		JPanel panelBackground = new JPanel();
		panelBackground.setBackground(Color.WHITE);
		panelBackground.setBounds(399, 0, 491, 502);
		panel.add(panelBackground);
		panelBackground.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, -16, 500, 539);
		panelBackground.add(lblNewLabel);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon(Reservas.class.getResource("/imagenes/reservas-img-2.png")));

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Reservas.class.getResource("/imagenes/Ha-100px.png")));
		lblLogo.setBounds(15, 6, 104, 107);
		panel.add(lblLogo);
	}

	@SuppressWarnings("unused")
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public void configurarDatosReservaEnFormulario(Reserva reserva) {

		txtFechaE.setDate(reserva.getFechaEntrada());
		txtFechaS.setDate(reserva.getFechaSalida());
		txtValor.setText(reserva.getValor().toString());
		txtFormaPago.setSelectedItem(reserva.getFormaPago());

		btnReservar.setText("Actualizar");

		this.reserva = reserva;

	}
}
