package forms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import db.ConnectionManager;
import models.Client;
import models.Employe;
import models.Pilote;
import models.Role;
import service.ClientManager;
import service.EmployeManager;
import service.RoleManager;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ClientManagerForm extends JFrame {
	class TableModel extends AbstractTableModel {
		private String[] columns = { "ID", "NOM", "PRENOM", "EMAIL", "ADRESSE", "CIN", "NUMERO DE PASPORT" };
		private List<Client> clients;

		public TableModel() {
			try {
				clients = new ClientManager(ConnectionManager.getConnection()).findAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public String getColumnName(int column) {
			return columns[column];
		}

		public List<Client> getClients() {
			return clients;
		}

		public void setClients(List<Client> clients) {
			this.clients = clients;
		}

		@Override
		public int getRowCount() {

			return clients.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object value = null;
			switch (columnIndex) {
			case 0:
				value = clients.get(rowIndex).getId();
				break;
			case 1:
				value = clients.get(rowIndex).getNom();
				break;
			case 2:
				value = clients.get(rowIndex).getPrenom();
				break;
			case 3:
				value = clients.get(rowIndex).getEmail();
				break;
			case 4:
				value = clients.get(rowIndex).getAdresse();
				break;
			case 5:
				value = clients.get(rowIndex).getCin();
				break;
			case 6:
				value = clients.get(rowIndex).getNumPassport();
				break;
			default:
				break;
			}
			return value;
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ClientManager clientManager = new ClientManager(ConnectionManager.getConnection());
	private JTable table;
	private TableModel tableModel = null;
	private JTextField txtEmail;
	private JTextField txtCin;
	private JTextField txtNom;
	private JTextField txtNumPass;

	private JTextField txtPrenom;
	private JTextField txtAdresse;

	/**
	 * Create the frame.
	 */

	public ClientManagerForm() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new UserForm().setVisible(true);
			}
		});

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 871, 605);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		tableModel = new TableModel();

		JPanel tablePanel = new JPanel();
		tablePanel.setBorder(null);
		tablePanel.setBounds(6, 6, 859, 313);
		contentPane.add(tablePanel);
		tablePanel.setLayout(new BorderLayout(0, 0));
		table = new JTable(tableModel);
		table.setFillsViewportHeight(true);
		tablePanel.add(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int row = table.getSelectedRow();
				if (row < 0) {
					return;
				}
				Client client = tableModel.getClients().get(row);
				txtNom.setText(client.getNom());
				txtPrenom.setText(client.getPrenom());
				txtEmail.setText(client.getEmail());
				txtCin.setText(client.getCin());
				txtNumPass.setText(client.getNumPassport());
				txtAdresse.setText(client.getAdresse());
			}
		});

		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Client client = new Client();
					client.setNom(txtNom.getText());
					client.setPrenom(txtPrenom.getText());
					client.setEmail(txtEmail.getText());
					client.setCin(txtCin.getText());
					client.setAdresse(txtAdresse.getText());
					client.setNumPassport(txtNumPass.getText());
					clientManager.add(client);
					tableModel.getClients().add(clientManager.findLast());
					JOptionPane.showMessageDialog(ClientManagerForm.this, "le client a été ajouter");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				((AbstractTableModel) table.getModel()).fireTableDataChanged();
				table.repaint();
			}
		});
		btnAjouter.setBounds(6, 332, 117, 29);
		contentPane.add(btnAjouter);

		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(ClientManagerForm.this, "Veuillez selectionner une ligne ");
				} else {
					try {
						Client client = tableModel.getClients().get(row);
						client.setNom(txtNom.getText());
						client.setPrenom(txtPrenom.getText());
						client.setEmail(txtEmail.getText());
						client.setCin(txtCin.getText());
						client.setNumPassport(txtNumPass.getText());
						client.setAdresse(txtAdresse.getText());
						clientManager.update(client.getId(), client);
						JOptionPane.showMessageDialog(ClientManagerForm.this, "le client a été modifier");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					((AbstractTableModel) table.getModel()).fireTableDataChanged();
					table.repaint();
				}

			}
		});
		btnModifier.setBounds(135, 332, 117, 29);
		contentPane.add(btnModifier);

		JLabel lblNom = new JLabel("Nom");
		lblNom.setBounds(16, 392, 61, 16);
		contentPane.add(lblNom);

		JLabel lblPrenom = new JLabel("Prenom");
		lblPrenom.setBounds(16, 420, 61, 16);
		contentPane.add(lblPrenom);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(16, 448, 61, 16);
		contentPane.add(lblEmail);

		JLabel lblLogin = new JLabel("CIN");
		lblLogin.setBounds(16, 502, 61, 16);
		contentPane.add(lblLogin);

		JLabel lblPassword = new JLabel("Numero de passport");
		lblPassword.setBounds(16, 530, 142, 16);
		contentPane.add(lblPassword);

		txtNom = new JTextField();
		txtNom.setBounds(170, 380, 134, 28);
		contentPane.add(txtNom);
		txtNom.setColumns(10);

		txtPrenom = new JTextField();
		txtPrenom.setBounds(170, 408, 134, 28);
		contentPane.add(txtPrenom);
		txtPrenom.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBounds(170, 436, 134, 28);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		txtCin = new JTextField();
		txtCin.setBounds(170, 490, 134, 28);
		contentPane.add(txtCin);
		txtCin.setColumns(10);

		txtNumPass = new JTextField();
		txtNumPass.setBounds(170, 518, 134, 28);
		contentPane.add(txtNumPass);
		txtNumPass.setColumns(10);

		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(ClientManagerForm.this, "Veuillez selectionner une ligne ");
				} else {
					Client client = tableModel.getClients().get(row);
					try {
						clientManager.delete(client.getId());
						tableModel.getClients().remove(client);
						JOptionPane.showMessageDialog(ClientManagerForm.this, "Le client a été supprimer");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					((AbstractTableModel) table.getModel()).fireTableDataChanged();
					table.repaint();
				}
			}
		});
		btnSupprimer.setBounds(264, 332, 117, 29);
		contentPane.add(btnSupprimer);

		JLabel lblAdresse = new JLabel("Adresse");
		lblAdresse.setBounds(16, 474, 61, 16);
		contentPane.add(lblAdresse);

		txtAdresse = new JTextField();
		txtAdresse.setColumns(10);
		txtAdresse.setBounds(170, 462, 134, 28);
		contentPane.add(txtAdresse);
	}
}
