package forms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import db.ConnectionManager;
import models.Employe;
import service.AuthManager;
import service.EmployeManager;
import utils.Roles;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class LoginForm extends JFrame {

	private JPanel contentPane;
	private JTextField loginTextField;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public LoginForm() {
		setType(Type.POPUP);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 453, 206);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection connection = ConnectionManager.getConnection();
				EmployeManager employeManager = new EmployeManager(connection);
				AuthManager authManager = new AuthManager(employeManager);
				String login = loginTextField.getText();
				String password = passwordField.getText();
				if (authManager.login(login, password) == true) {
					try {
						Employe employe = employeManager.findByLogin(login);
						if (employe.getRole().getNom().equals(Roles.ADMIN)) {
							AdminForm adminForm = new AdminForm();
							adminForm.setVisible(true);
						}else {
							UserForm userForm = new UserForm();
							userForm.setVisible(true);
						}
						dispose();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(LoginForm.this,
								"Erreur de connection avec la base de données",
							    "Error",
							    JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} 
				}else {
					JOptionPane.showMessageDialog(LoginForm.this,
							"Login ou mot de passe incorrect !",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnLogin.setBounds(182, 135, 117, 29);
		contentPane.add(btnLogin);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(LoginForm.this, "Voulez-vous Quitter ?","",JOptionPane.OK_CANCEL_OPTION);
				if (response == JOptionPane.OK_OPTION) {
					LoginForm.this.dispose();
					System.exit(0);
				}
			}
		});
		btnCancel.setBounds(314, 135, 117, 29);
		contentPane.add(btnCancel);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(64, 50, 61, 16);
		contentPane.add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(64, 97, 61, 16);
		contentPane.add(lblPassword);
		
		loginTextField = new JTextField();
		loginTextField.setBounds(182, 44, 224, 28);
		contentPane.add(loginTextField);
		loginTextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(182, 91, 224, 28);
		contentPane.add(passwordField);
	}
}
