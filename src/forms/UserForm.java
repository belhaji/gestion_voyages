package forms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class UserForm extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public UserForm() {
		
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
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnGestionDesClients = new JButton("Gestion des clients");
		btnGestionDesClients.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnGestionDesClients.setBounds(88, 102, 272, 76);
		contentPane.add(btnGestionDesClients);
		
		JButton btnGestionDesReservations = new JButton("Gestion des reservations");
		btnGestionDesReservations.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGestionDesReservations.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnGestionDesReservations.setBounds(88, 210, 272, 76);
		contentPane.add(btnGestionDesReservations);
		
		JLabel lblGreetingMessage = new JLabel("Greeting Message");
		lblGreetingMessage.setFont(new Font("Menlo", Font.PLAIN, 16));
		lblGreetingMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblGreetingMessage.setBounds(88, 29, 272, 35);
		contentPane.add(lblGreetingMessage);
	}
}
