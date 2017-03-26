package forms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import db.ConnectionManager;
import javafx.scene.control.ComboBox;
import models.Avion;
import models.Employe;
import models.Ligne;
import models.Pilote;
import models.Role;
import service.AvionManager;
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
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class AvionManagerForm extends JFrame {
	class TableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String[] columns = { "ID", "NOM", "NOMBRE DE PLACES", "PILOTE" };
		private List<Avion> avions;

		public TableModel() {
			try {
				avions = avionManager.findAll();
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
			return avions.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object value = null;
			switch (columnIndex) {
			case 0:
				value = avions.get(rowIndex).getId();
				break;
			case 1:
				value = avions.get(rowIndex).getNom();
				break;
			case 2:
				value = avions.get(rowIndex).getNbPlace();
				break;
			case 3:
				value = avions.get(rowIndex).getPilote().getNom();
				break;
			default:
				break;
			}
			return value;
		}

		public List<Avion> getAvions() {
			return avions;
		}

		public void setAvions(List<Avion> avions) {
			this.avions = avions;
		}

	}
	class BoxModel implements ComboBoxModel<Pilote> {
		private List<Pilote> pilotes;
		private Pilote selected;
		
		public List<Pilote> getPilotes() {
			return pilotes;
		}
		public void setPilotes(List<Pilote> pilotes) {
			this.pilotes = pilotes;
		}
		public BoxModel() {
			try {
				pilotes = piloteManager.findAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		@Override
		public int getSize() {
			return pilotes.size();
		}

		@Override
		public Pilote getElementAt(int index) {
			return pilotes.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			
		}

		@Override
		public void setSelectedItem(Object anItem) {
			selected = (Pilote) anItem;
		}

		@Override
		public Object getSelectedItem() {
			return selected;
		}
	}

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PiloteManager piloteManager = new PiloteManager(ConnectionManager.getConnection());
	private AvionManager avionManager = new AvionManager(piloteManager, ConnectionManager.getConnection());

	private JComboBox piloteComboBox;
	private BoxModel boxModel;
	private JTable table;
	private TableModel tableModel = null;
	private JTextField txtNom;

	private JTextField txtNbPlace;

	/**
	 * Create the frame.
	 */

	public AvionManagerForm() {
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
				Avion avion = tableModel.getAvions().get(row);
				txtNom.setText(avion.getNom());
				txtNbPlace.setText("" + avion.getNbPlace());

			}
		});

		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					Avion avion= new Avion((Pilote) piloteComboBox.getSelectedItem());
					avion.setNom(txtNom.getText());
					avion.setNbPlace(Integer.parseInt(txtNbPlace.getText()));
					avionManager.add(avion);
					tableModel.getAvions().add(avionManager.findLast());
					JOptionPane.showMessageDialog(AvionManagerForm.this, "L'avion a été ajouter");
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(AvionManagerForm.this, "Erreur de saisie");
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
					JOptionPane.showMessageDialog(AvionManagerForm.this, "Veuillez selectionner une ligne ");
				} else {
					try {
						Avion avion = tableModel.getAvions().get(row);
						avion.setNom(txtNom.getText());
						avion.setNbPlace(Integer.parseInt(txtNbPlace.getText()));
						avion.setPilote((Pilote) piloteComboBox.getSelectedItem());
						avionManager.update(avion.getId(), avion);
						JOptionPane.showMessageDialog(AvionManagerForm.this, "le pilote a été modifier");
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

		JLabel lblPrenom = new JLabel("Nombre de Places");
		lblPrenom.setBounds(18, 401, 117, 16);
		contentPane.add(lblPrenom);

		JLabel lblEmail = new JLabel("Pilote");
		lblEmail.setBounds(18, 429, 61, 16);
		contentPane.add(lblEmail);

		txtNom = new JTextField();
		txtNom.setBounds(184, 373, 134, 28);
		contentPane.add(txtNom);
		txtNom.setColumns(10);

		txtNbPlace = new JTextField();
		txtNbPlace.setBounds(184, 401, 134, 28);
		contentPane.add(txtNbPlace);
		txtNbPlace.setColumns(10);

		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(AvionManagerForm.this, "Veuillez selectionner une ligne ");
				} else {
					Avion avion = tableModel.getAvions().get(row);
					try {
						avionManager.delete(avion.getId());
						tableModel.getAvions().remove(avion);
						JOptionPane.showMessageDialog(AvionManagerForm.this, "L'avion a été supprimer");
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

		piloteComboBox = new JComboBox();
		piloteComboBox.setBounds(184, 425, 134, 27);
		boxModel = new BoxModel();
		piloteComboBox.setModel(boxModel);
		contentPane.add(piloteComboBox);
				
	}
}
