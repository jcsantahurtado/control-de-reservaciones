package views;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.UserController;
import model.User;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtContrasena;

	private JButton btnLogin, btnCancelar;

	private UserController userController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {

		this.userController = new UserController();

		configurarContenido();
		configurarAccionesDelUsuario();
	}

	private void configurarAccionesDelUsuario() {

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				iniciarSesion();

			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				cancelar();

			}
		});

	}

	protected void cancelar() {

		MenuPrincipal menuPrincipal = new MenuPrincipal();
		menuPrincipal.setVisible(true);
		dispose();

	}

	protected void iniciarSesion() {

		if (txtUsuario.getText().isBlank() || String.valueOf(txtContrasena.getPassword()).isBlank()) {

			JOptionPane.showMessageDialog(this, "Todos los campos son requeridos.");
			return;

		}

		var user = new User(txtUsuario.getText(), String.valueOf(txtContrasena.getPassword()));

		this.userController.login(user);

		if (user.getId() != null) {

			MenuUsuario usuario = new MenuUsuario();
			usuario.setVisible(true);
			dispose();

		} else {

			JOptionPane.showMessageDialog(this, "Usurio y/o contraseña incorrectos.", "Error",
					JOptionPane.ERROR_MESSAGE);

			JOptionPane.showMessageDialog(this,

					"Intenta con: "

							+ System.lineSeparator()

							+ "Usuario = admin"

							+ System.lineSeparator()

							+ "Contraseña = 1234");
		}

	}

	private void configurarContenido() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/imagenes/perfil-del-usuario.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 538);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("Iniciar Sesión");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/imagenes/hotel.png")));
		lblNewLabel.setBounds(-53, 0, 422, 499);
		contentPane.add(lblNewLabel);

		txtUsuario = new JTextField();
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(409, 181, 234, 33);
		contentPane.add(txtUsuario);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
		lblUsuario.setBounds(409, 156, 57, 14);
		contentPane.add(lblUsuario);

		txtContrasena = new JPasswordField();
		txtContrasena.setBounds(409, 261, 234, 33);
		contentPane.add(txtContrasena);

		JLabel lblContrasena = new JLabel("Contraseña");
		lblContrasena.setFont(new Font("Arial", Font.PLAIN, 14));
		lblContrasena.setBounds(409, 236, 133, 14);
		contentPane.add(lblContrasena);

		btnLogin = new JButton("Login");
		btnLogin.setIcon(new ImageIcon(Login.class.getResource("/imagenes/perfil-del-usuario.png")));
		btnLogin.setBounds(409, 322, 103, 33);
		contentPane.add(btnLogin);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(Login.class.getResource("/imagenes/cerrar-24px.png")));
		btnCancelar.setBounds(540, 322, 103, 33);
		contentPane.add(btnCancelar);

		JLabel lblIniciarSesion = new JLabel("Iniciar Sesión");
		lblIniciarSesion.setIcon(new ImageIcon("/imagenes/Ha-100px.png"));
		lblIniciarSesion.setBounds(470, 30, 103, 94);
		contentPane.add(lblIniciarSesion);
	}
}
