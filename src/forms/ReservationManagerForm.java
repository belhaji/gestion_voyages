package forms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xpath.internal.compiler.Keywords;

import db.ConnectionManager;
import javafx.scene.control.ComboBox;
import models.Avion;
import models.Client;
import models.Employe;
import models.Ligne;
import models.Pilote;
import models.Reservation;
import models.Role;
import service.AvionManager;
import service.ClientManager;
import service.EmployeManager;
import service.LigneManager;
import service.PiloteManager;
import service.ReservationManager;
import service.RoleManager;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import java.sql.SQLException;
import java.util.Date;
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
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class ReservationManagerForm extends JFrame {
	class TableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String[] columns = { "ID", "TYPE", "CLASS", "CLIENT", "LIGNE" };
		private List<Reservation> reservations;

		public TableModel() {
			try {
				reservations = reservationManager.findAll();
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
			return reservations.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object value = null;
			switch (columnIndex) {
			case 0:
				value = reservations.get(rowIndex).getId();
				break;
			case 1:
				value = reservations.get(rowIndex).getType();
				break;
			case 2:
				value = reservations.get(rowIndex).getClasse();
				break;
			case 3:
				value = reservations.get(rowIndex).getClient().getNom();
				break;
			case 4:
				value = reservations.get(rowIndex).getLigne();
				break;
			default:
				break;
			}
			return value;
		}

		public List<Reservation> getReservations() {
			return reservations;
		}

		public void setReservations(List<Reservation> reservations) {
			this.reservations = reservations;
		}

	}

	class BoxModelClient implements ComboBoxModel<Client> {
		private List<Client> clients;
		private Client selected;

		public List<Client> getClients() {
			return clients;
		}

		public void setClients(List<Client> clients) {
			this.clients = clients;
		}

		public BoxModelClient() {
			try {
				clients = clientManager.findAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public int getSize() {
			return clients.size();
		}

		@Override
		public Client getElementAt(int index) {
			return clients.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {

		}

		@Override
		public void removeListDataListener(ListDataListener l) {

		}

		@Override
		public void setSelectedItem(Object anItem) {
			selected = (Client) anItem;
		}

		@Override
		public Object getSelectedItem() {
			return selected;
		}
	}


	class BoxModelLigne implements ComboBoxModel<Ligne> {
		private List<Ligne> lignes;
		private Ligne selected;

		public List<Ligne> getLignes() {
			return lignes;
		}

		public void setLignes(List<Ligne> lignes) {
			this.lignes = lignes;
		}

		public BoxModelLigne() {
			try {
				lignes = ligneManager.findAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public int getSize() {
			return lignes.size();
		}

		@Override
		public Ligne getElementAt(int index) {
			return lignes.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {

		}

		@Override
		public void removeListDataListener(ListDataListener l) {

		}

		@Override
		public void setSelectedItem(Object anItem) {
			selected = (Ligne) anItem;
		}

		@Override
		public Object getSelectedItem() {
			return selected;
		}
	}
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ClientManager clientManager = new ClientManager(ConnectionManager.getConnection());
	private LigneManager ligneManager = new LigneManager(ConnectionManager.getConnection());
	private ReservationManager reservationManager = new ReservationManager(ConnectionManager.getConnection(),
			clientManager, ligneManager);

	private JComboBox cbxLigne;
	private JComboBox cbxType;
	private JComboBox cbxClient;
	private JComboBox cbxClasse;
	private BoxModelClient boxModelClient;
	private BoxModelLigne boxModelLigne;
	private JTable table;
	private TableModel tableModel = null;
	private JTextField txtRecherche;

	/**
	 * Create the frame.
	 */

	public ReservationManagerForm() {
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				new UserForm().setVisible(true);
			}
		});

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 871, 524);
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
				Reservation reservation = tableModel.getReservations().get(row);
				System.out.println(reservation.getClient());
				cbxClient.setSelectedItem(reservation.getClient());
				cbxLigne.setSelectedItem(reservation.getLigne());
				cbxType.setSelectedItem(reservation.getType());
				cbxClasse.setSelectedItem(reservation.getClasse());
				cbxClient.repaint();
				cbxLigne.repaint();
			}
		});

		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					Reservation reservation = new Reservation((Client) cbxClient.getSelectedItem(),(Ligne) cbxLigne.getSelectedItem());
					reservation.setDate(new Date());
					reservation.setClasse(cbxClasse.getSelectedItem().toString());
					reservation.setType(cbxType.getSelectedItem().toString());
					reservationManager.add(reservation);
 					tableModel.getReservations().add(reservationManager.findLast());
					JOptionPane.showMessageDialog(ReservationManagerForm.this, "La reservation a été ajouter");
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(ReservationManagerForm.this, "Erreur de saisie");
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
					JOptionPane.showMessageDialog(ReservationManagerForm.this, "Veuillez selectionner une ligne ");
				} else {
					try {
						Reservation reservation = tableModel.getReservations().get(row);
						reservation.setClasse(cbxClasse.getSelectedItem().toString());
						reservation.setType(cbxType.getSelectedItem().toString());
						reservation.setClient((Client) cbxClient.getSelectedItem());
						reservation.setLigne((Ligne) cbxLigne.getSelectedItem());
						reservationManager.update(reservation.getId(), reservation);
						JOptionPane.showMessageDialog(ReservationManagerForm.this, "la reservation a été modifier");
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

		JLabel lblNom = new JLabel("Type");
		lblNom.setBounds(18, 373, 61, 16);
		contentPane.add(lblNom);

		JLabel lblPrenom = new JLabel("Class");
		lblPrenom.setBounds(18, 401, 117, 16);
		contentPane.add(lblPrenom);

		JLabel lblEmail = new JLabel("Client");
		lblEmail.setBounds(18, 429, 61, 16);
		contentPane.add(lblEmail);

		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(ReservationManagerForm.this, "Veuillez selectionner une ligne ");
				} else {
					Reservation reservation = tableModel.getReservations().get(row);
					try {
						reservationManager.delete(reservation.getId());
						tableModel.getReservations().remove(reservation);
						JOptionPane.showMessageDialog(ReservationManagerForm.this, "La reservation a été supprimer");
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

		cbxClient = new JComboBox();
		cbxClient.setBounds(91, 425, 134, 27);
		boxModelClient = new BoxModelClient();
		cbxClient.setModel(boxModelClient);
		cbxClient.setSelectedIndex(0);
		contentPane.add(cbxClient);

		cbxType = new JComboBox();
		cbxType.setModel(new DefaultComboBoxModel(new String[] { "aller", "aller_retour" }));
		cbxType.setBounds(91, 369, 134, 27);
		contentPane.add(cbxType);

		cbxClasse = new JComboBox();
		cbxClasse.setModel(new DefaultComboBoxModel(new String[] { "normale", "business" }));
		cbxClasse.setBounds(91, 397, 134, 27);
		contentPane.add(cbxClasse);

		JLabel lblLigne = new JLabel("Ligne");
		lblLigne.setBounds(18, 457, 61, 16);
		contentPane.add(lblLigne);

		cbxLigne = new JComboBox();
		cbxLigne.setBounds(91, 453, 134, 27);
		boxModelLigne = new BoxModelLigne();
		cbxLigne.setModel(boxModelLigne);
		cbxLigne.setSelectedIndex(0);
		contentPane.add(cbxLigne);
		txtRecherche = new JTextField();
		txtRecherche.setBounds(675, 331, 175, 28);
		contentPane.add(txtRecherche);
		txtRecherche.setColumns(10);
		
		JButton btnRechercher = new JButton("Rechercher");
		btnRechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyword = txtRecherche.getText();
				List<Reservation> reservations;
				try {
					reservations = reservationManager.rechercher(keyword);
					tableModel.getReservations().clear();
					tableModel.getReservations().addAll(reservations);
					((AbstractTableModel) table.getModel()).fireTableDataChanged();
					table.repaint();				
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnRechercher.setBounds(546, 332, 117, 29);
		contentPane.add(btnRechercher);

	}
}
