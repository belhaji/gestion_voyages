package forms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AdminForm() {
		addWindowListener(new WindowListener() {

			@Override
			public void windowClosing(WindowEvent e) {
				new LoginForm().setVisible(true);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
		});
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 711, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnGestionDesUtilisateurs = new JButton("Gestion des utilisateurs");
		btnGestionDesUtilisateurs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EmployeeManagerForm().setVisible(true);
				dispose();
			}
		});
		btnGestionDesUtilisateurs.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnGestionDesUtilisateurs.setBounds(44, 87, 259, 82);
		contentPane.add(btnGestionDesUtilisateurs);

		JButton btnGestionDesPilotes = new JButton("Gestion des Pilotes");
		btnGestionDesPilotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PiloteManagerForm().setVisible(true);
				dispose();
			}
		});
		
		btnGestionDesPilotes.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnGestionDesPilotes.setBounds(44, 196, 259, 82);
		contentPane.add(btnGestionDesPilotes);

		JButton btnGestionDesAvions = new JButton("Gestion des avions");
		btnGestionDesAvions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AvionManagerForm().setVisible(true);
				dispose();
			}
		});
		btnGestionDesAvions.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnGestionDesAvions.setBounds(401, 196, 259, 82);
		contentPane.add(btnGestionDesAvions);

		JButton btnGestionDesLignes = new JButton("Gestion des lignes");
		btnGestionDesLignes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LigneManagerForm().setVisible(true);
				dispose();
			}
		});
		btnGestionDesLignes.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnGestionDesLignes.setBounds(401, 87, 259, 82);
		contentPane.add(btnGestionDesLignes);

		JLabel lblGreetingMessage = new JLabel("Espace d'administration ");
		lblGreetingMessage.setFont(new Font("Menlo", Font.PLAIN, 16));
		lblGreetingMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblGreetingMessage.setBounds(44, 6, 616, 42);
		contentPane.add(lblGreetingMessage);
	}
}
