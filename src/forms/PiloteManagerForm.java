package forms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import db.ConnectionManager;
import models.Employe;
import models.Ligne;
import models.Pilote;
import models.Role;
import service.EmployeManager;
import service.PiloteManager;
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

public class PiloteManagerForm extends JFrame {
	class TableModel extends AbstractTableModel {
		private String[] columns = { "ID", "NOM", "PRENOM", "MATRICULE" };
		private List<Pilote> pilotes;

		public TableModel() {
			try {
				pilotes = new PiloteManager(ConnectionManager.getConnection()).findAll();
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

		@Override
		public int getRowCount() {

			return pilotes.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object value = null;
			switch (columnIndex) {
			case 0:
				value = pilotes.get(rowIndex).getId();
				break;
			case 1:
				value = pilotes.get(rowIndex).getNom();
				break;
			case 2:
				value = pilotes.get(rowIndex).getPrenom();
				break;
			case 3:
				value = pilotes.get(rowIndex).getMatricule();
				break;
			default:
				break;
			}
			return value;
		}

		public List<Pilote> getPilotes() {
			return pilotes;
		}

		public void setPilotes(List<Pilote> pilotes) {
			this.pilotes = pilotes;
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PiloteManager piloteManager = new PiloteManager(ConnectionManager.getConnection());
	private JTable table;
	private TableModel tableModel = null;
	private JTextField txtMatricule;
	private JTextField txtNom;

	private JTextField txtPrenom;

	/**
	 * Create the frame.
	 */

	public PiloteManagerForm() {
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
		setBounds(100, 100, 871, 490);
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
				Pilote pilote = tableModel.getPilotes().get(row);
				txtNom.setText(pilote.getNom());
				txtPrenom.setText(pilote.getPrenom());
				txtMatricule.setText(pilote.getMatricule());
			}
		});

		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					Pilote pilote = new Pilote();
					pilote.setNom(txtNom.getText());
					pilote.setPrenom(txtPrenom.getText());
					pilote.setMatricule(txtMatricule.getText());
					piloteManager.add(pilote);
					tableModel.getPilotes().add(piloteManager.findLast());
					JOptionPane.showMessageDialog(PiloteManagerForm.this, "l'employee a été ajouter");
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
					JOptionPane.showMessageDialog(PiloteManagerForm.this, "Veuillez selectionner une ligne ");
				} else {
					try {
						Pilote pilote = tableModel.getPilotes().get(row);
						pilote.setNom(txtNom.getText());
						pilote.setPrenom(txtPrenom.getText());
						pilote.setMatricule(txtMatricule.getText());
						piloteManager.update(pilote.getId(), pilote);
						JOptionPane.showMessageDialog(PiloteManagerForm.this, "le pilote a été modifier");
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

		JLabel lblEmail = new JLabel("Matricule");
		lblEmail.setBounds(18, 429, 61, 16);
		contentPane.add(lblEmail);

		txtNom = new JTextField();
		txtNom.setBounds(118, 367, 134, 28);
		contentPane.add(txtNom);
		txtNom.setColumns(10);

		txtPrenom = new JTextField();
		txtPrenom.setBounds(118, 395, 134, 28);
		contentPane.add(txtPrenom);
		txtPrenom.setColumns(10);

		txtMatricule = new JTextField();
		txtMatricule.setBounds(118, 423, 134, 28);
		contentPane.add(txtMatricule);
		txtMatricule.setColumns(10);

		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(PiloteManagerForm.this, "Veuillez selectionner une ligne ");
				} else {
					Pilote pilote = tableModel.getPilotes().get(row);
					try {
						piloteManager.delete(pilote.getId());
						tableModel.getPilotes().remove(pilote);
						JOptionPane.showMessageDialog(PiloteManagerForm.this, "Le pilote a été supprimer");
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
	}
}
