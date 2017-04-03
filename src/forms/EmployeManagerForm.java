package forms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import db.ConnectionManager;
import models.Employe;
import models.Role;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class EmployeManagerForm extends JFrame {
	class TableModel extends AbstractTableModel {
		private String[] columns = { "ID", "NOM", "PRENOM", "EMAIL", "LOGIN", "PASSWORD", "ROLE", "ACTIVE" };
		private List<Employe> employes;

		public TableModel() {
			try {
				employes = new EmployeManager(ConnectionManager.getConnection()).findAll();
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

		public List<Employe> getEmployes() {
			return employes;
		}

		@Override
		public int getRowCount() {

			return employes.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object value = null;
			switch (columnIndex) {
			case 0:
				value = employes.get(rowIndex).getId();
				break;
			case 1:
				value = employes.get(rowIndex).getNom();
				break;
			case 2:
				value = employes.get(rowIndex).getPrenom();
				break;
			case 3:
				value = employes.get(rowIndex).getEmail();
				break;
			case 4:
				value = employes.get(rowIndex).getLogin();
				break;
			case 5:
				value = employes.get(rowIndex).getPassword();
				break;
			case 6:
				value = employes.get(rowIndex).getRole().getNom();
				break;
			case 7:
				value = employes.get(rowIndex).getActive();
				break;
			default:
				break;
			}
			return value;
		}

		public void setEmployes(List<Employe> employes) {
			this.employes = employes;
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox checkBoxActive;
	private JPanel contentPane;
	private EmployeManager employeManager = new EmployeManager(ConnectionManager.getConnection());
	private JTable table;
	private TableModel tableModel = null;
	private JTextField txtEmail;
	private JTextField txtLogin;
	private JTextField txtNom;
	private JTextField txtPassword;
	private JComboBox roleComboBox;

	private JTextField txtPrenom;

	/**
	 * Create the frame.
	 */
	
	public EmployeManagerForm() {
		this.addWindowListener(new WindowListener() {
			
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
			
			@Override
			public void windowClosing(WindowEvent e) {
				new AdminForm().setVisible(true);
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 871, 617);
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
				Employe employe = tableModel.getEmployes().get(row);
				txtNom.setText(employe.getNom());
				txtPrenom.setText(employe.getPrenom());
				txtEmail.setText(employe.getEmail());
				txtLogin.setText(employe.getLogin());
				txtPassword.setText(employe.getPassword());
				checkBoxActive.setSelected(employe.getActive() == 1);
				roleComboBox.setSelectedItem(employe.getRole().getNom());

			}
		});

		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Role role = new RoleManager(ConnectionManager.getConnection())
							.findByName(roleComboBox.getSelectedItem().toString());
					Employe employe = new Employe(role);
					employe.setNom(txtNom.getText());
					employe.setPrenom(txtPrenom.getText());
					employe.setEmail(txtEmail.getText());
					employe.setLogin(txtLogin.getText());
					employe.setPassword(txtPassword.getText());
					employe.setActive(checkBoxActive.isSelected() ? 1 : 0);
					employeManager.add(employe);
					tableModel.getEmployes().add(employeManager.findByLogin(employe.getLogin()));
					JOptionPane.showMessageDialog(EmployeManagerForm.this, "l'employee a été ajouter");
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
					JOptionPane.showMessageDialog(EmployeManagerForm.this, "Veuillez selectionner une ligne ");
				} else {
					try {
						Employe employe = tableModel.getEmployes().get(row);
						employe.setNom(txtNom.getText());
						employe.setPrenom(txtPrenom.getText());
						employe.setEmail(txtEmail.getText());
						employe.setLogin(txtLogin.getText());
						employe.setPassword(txtPassword.getText());
						employe.setActive(checkBoxActive.isSelected() ? 1 : 0);
						Role role = new RoleManager(ConnectionManager.getConnection())
								.findByName(roleComboBox.getSelectedItem().toString());
						employe.setRole(role);
						employeManager.update(employe.getId(), employe);
						JOptionPane.showMessageDialog(EmployeManagerForm.this, "l'employee a été modifier");
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
		lblNom.setBounds(18, 373, 61, 16);
		contentPane.add(lblNom);

		JLabel lblPrenom = new JLabel("Prenom");
		lblPrenom.setBounds(18, 401, 61, 16);
		contentPane.add(lblPrenom);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(18, 429, 61, 16);
		contentPane.add(lblEmail);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(18, 457, 61, 16);
		contentPane.add(lblLogin);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(18, 485, 61, 16);
		contentPane.add(lblPassword);

		JLabel lblActive = new JLabel("Active");
		lblActive.setBounds(18, 513, 61, 16);
		contentPane.add(lblActive);

		txtNom = new JTextField();
		txtNom.setBounds(118, 367, 134, 28);
		contentPane.add(txtNom);
		txtNom.setColumns(10);

		txtPrenom = new JTextField();
		txtPrenom.setBounds(118, 395, 134, 28);
		contentPane.add(txtPrenom);
		txtPrenom.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBounds(118, 423, 134, 28);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		txtLogin = new JTextField();
		txtLogin.setBounds(118, 451, 134, 28);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		txtPassword = new JTextField();
		txtPassword.setBounds(118, 479, 134, 28);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);

		checkBoxActive = new JCheckBox("");
		checkBoxActive.setBounds(118, 509, 128, 23);
		contentPane.add(checkBoxActive);

		roleComboBox = new JComboBox<Object>();
		roleComboBox.setModel(new DefaultComboBoxModel<Object>(new String[] { "admin", "user" }));
		roleComboBox.setBounds(118, 544, 134, 27);
		contentPane.add(roleComboBox);

		JLabel lblRole = new JLabel("Role");
		lblRole.setBounds(18, 548, 61, 16);
		contentPane.add(lblRole);
	}
}
