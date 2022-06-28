package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Panel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MenuPrincipal extends JFrame {

	private JPanel contentPane;

	private JButton btnLogin, btnSalir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
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
	public MenuPrincipal() {
		configurarContenidoMenu();
		configuarAccionesDelUsuario();
	}

	private void configuarAccionesDelUsuario() {

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.setVisible(true);
				dispose();
			}
		});

		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] opciones = { "Aceptar", "Cancelar" };
				int eleccion = JOptionPane.showOptionDialog(rootPane, "En realidad desea realizar cerrar la aplicacion",
						"Mensaje de Confirmacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						opciones, "Aceptar");
				if (eleccion == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

	}

	private void configurarContenidoMenu() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/imagenes/aH-40px.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 910, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		Panel panel = new Panel();
		panel.setBackground(new Color(245, 245, 245));
		panel.setBounds(0, 0, 894, 501);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(-49, 0, 746, 471);
		lblNewLabel.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/menu-img.png")));
		panel.add(lblNewLabel);

		btnLogin = new JButton("");
		btnLogin.setForeground(SystemColor.text);
		btnLogin.setBackground(Color.white);
		btnLogin.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/login.png")));
		btnLogin.setBounds(763, 241, 71, 73);
		panel.add(btnLogin);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setForeground(new Color(12, 138, 199));
		lblLogin.setFont(new Font("Arial", Font.BOLD, 16));
		lblLogin.setBounds(763, 213, 71, 24);
		panel.add(lblLogin);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/aH-150px.png")));
		lblNewLabel_2.setBounds(723, 33, 150, 156);
		panel.add(lblNewLabel_2);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(12, 138, 199));
		panel_1.setBounds(0, 471, 894, 30);
		panel.add(panel_1);

		JLabel lblFooter = new JLabel("Desarrollado por Juan Carlos © 2022");
		lblFooter.setForeground(new Color(240, 248, 255));
		lblFooter.setFont(new Font("Arial", Font.PLAIN, 13));
		panel_1.add(lblFooter);

		btnSalir = new JButton("");
		btnSalir.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imagenes/cerrar-sesion 32-px.png")));
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setBackground(Color.WHITE);
		btnSalir.setBounds(832, 420, 44, 39);
		panel.add(btnSalir);
	}
}