package forms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import db.ConnectionManager;
import models.Ligne;
import service.LigneManager;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class LigneManagerForm extends JFrame {
	class TableModel extends AbstractTableModel {
		private String[] columns = { "ID", "AIROPORT D'ALLER", "AIROPORT D'ARRIVER", "PRIX NORMALE", "PRIX CLASS"};
		private List<Ligne> lignes;

		public TableModel() {
			try {
				lignes = new LigneManager(ConnectionManager.getConnection()).findAll();
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

			return lignes.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object value = null;
			switch (columnIndex) {
			case 0:
				value = lignes.get(rowIndex).getId();
				break;
			case 1:
				value = lignes.get(rowIndex).getAiroportAller();
				break;
			case 2:
				value = lignes.get(rowIndex).getAiroportArriver();
				break;
			case 3:
				value = lignes.get(rowIndex).getPrixNormale();
				break;
			case 4:
				value = lignes.get(rowIndex).getPrixClass();
				break;
			default:
				break;
			}
			return value;
		}

		public List<Ligne> getLignes() {
			return lignes;
		}

		public void setLignes(List<Ligne> lignes) {
			this.lignes = lignes;
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LigneManager ligneManager= new LigneManager(ConnectionManager.getConnection());
	private JTable table;
	private TableModel tableModel = null;
	private JTextField txtPrixNormal;
	private JTextField txtPrixClass;
	private JTextField txtAiroAller;

	private JTextField txtAiroArriver;

	/**
	 * Create the frame.
	 */
	
	public LigneManagerForm() {
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
		setBounds(100, 100, 871, 532);
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
				Ligne ligne = tableModel.getLignes().get(row);
				txtAiroAller.setText(ligne.getAiroportAller());
				txtAiroArriver.setText(ligne.getAiroportArriver());
				txtPrixNormal.setText(Double.toString(ligne.getPrixNormale()));
				txtPrixClass.setText(Double.toString(ligne.getPrixClass()));
			}
		});

		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Ligne ligne = new Ligne();
					ligne.setAiroportAller(txtAiroAller.getText());
					ligne.setAiroportArriver(txtAiroArriver.getText());
					ligne.setPrixNormale(Double.parseDouble(txtPrixNormal.getText()));
					ligne.setPrixClass(Double.parseDouble(txtPrixClass.getText()));
					ligneManager.add(ligne);
					tableModel.getLignes().add(ligneManager.findLast());
					JOptionPane.showMessageDialog(LigneManagerForm.this, "La ligne a été ajouter");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}catch (Exception e2) {
					JOptionPane.showMessageDialog(LigneManagerForm.this, "Erreur de saisie");
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
					JOptionPane.showMessageDialog(LigneManagerForm.this, "Veuillez selectionner une ligne ");
				} else {
					try {
						Ligne ligne = tableModel.getLignes().get(row);
						ligne.setAiroportAller(txtAiroAller.getText());
						ligne.setAiroportArriver(txtAiroArriver.getText());
						ligne.setPrixNormale(Double.parseDouble(txtPrixNormal.getText()));
						ligne.setPrixClass(Double.parseDouble(txtPrixClass.getText()));
						ligneManager.update(ligne.getId(), ligne);
						JOptionPane.showMessageDialog(LigneManagerForm.this, "La ligne a été modifier");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}catch (Exception e2) {
						JOptionPane.showMessageDialog(LigneManagerForm.this, "Erreur de saisie");
					}
					((AbstractTableModel) table.getModel()).fireTableDataChanged();
					table.repaint();
				}

			}
		});
		btnModifier.setBounds(135, 332, 117, 29);
		contentPane.add(btnModifier);

		JLabel lblAiroAller = new JLabel("Airoport Aller");
		lblAiroAller.setBounds(18, 379, 122, 16);
		contentPane.add(lblAiroAller);

		JLabel lblAiroArriver = new JLabel("Airoport Arriver");
		lblAiroArriver.setBounds(18, 407, 122, 16);
		contentPane.add(lblAiroArriver);

		JLabel lblEmail = new JLabel("Prix Normale");
		lblEmail.setBounds(18, 435, 122, 16);
		contentPane.add(lblEmail);

		JLabel lblLogin = new JLabel("Prix Class");
		lblLogin.setBounds(18, 463, 122, 16);
		contentPane.add(lblLogin);

		txtAiroAller = new JTextField();
		txtAiroAller.setBounds(156, 373, 225, 28);
		contentPane.add(txtAiroAller);
		txtAiroAller.setColumns(10);

		txtAiroArriver = new JTextField();
		txtAiroArriver.setBounds(156, 401, 225, 28);
		contentPane.add(txtAiroArriver);
		txtAiroArriver.setColumns(10);

		txtPrixNormal = new JTextField();
		txtPrixNormal.setBounds(156, 429, 225, 28);
		contentPane.add(txtPrixNormal);
		txtPrixNormal.setColumns(10);

		txtPrixClass = new JTextField();
		txtPrixClass.setBounds(156, 457, 225, 28);
		contentPane.add(txtPrixClass);
		txtPrixClass.setColumns(10);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row < 0) {
					JOptionPane.showMessageDialog(LigneManagerForm.this, "Veuillez selectionner une ligne ");
				}else {
					Ligne ligne = tableModel.getLignes().get(row);
					try {
						ligneManager.delete(ligne.getId());
						tableModel.getLignes().remove(ligne);
						JOptionPane.showMessageDialog(LigneManagerForm.this, "La ligne a été supprimer");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					((AbstractTableModel) table.getModel()).fireTableDataChanged();
					table.repaint();
				}
			}
		});
		btnSupprimer.setBounds(264, 331, 117, 29);
		contentPane.add(btnSupprimer);
	}
}
