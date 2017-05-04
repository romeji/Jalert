import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FormProfilType extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private Groupe GroupeSelected = new Groupe(0, "", 0, "");
	JList<String> list_2 = null ;
	
	public FormProfilType() throws ClassNotFoundException, SQLException {
		
		setModal(true);
		setResizable(false);
		setBounds(new Rectangle(400, 400, 1000, 400));
		setPreferredSize(new Dimension(800, 400));
		getContentPane().setBackground(new Color(112, 128, 144));
		getContentPane().setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setForeground(SystemColor.textHighlight);
		splitPane.setBackground(SystemColor.textHighlight);
		getContentPane().add(splitPane);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(112, 128, 144));
		splitPane.setLeftComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblGroupe = new JLabel("Groupes");
		lblGroupe.setOpaque(true);
		lblGroupe.setFont(new Font("Verdana", Font.BOLD, 16));
		lblGroupe.setBackground(new Color(30, 144, 255));
		lblGroupe.setHorizontalAlignment(SwingConstants.CENTER);
		lblGroupe.setHorizontalTextPosition(SwingConstants.CENTER);
		lblGroupe.setPreferredSize(new Dimension(120, 40));
		lblGroupe.setMinimumSize(new Dimension(35, 30));
		lblGroupe.setMaximumSize(new Dimension(35, 30));
		panel_2.add(lblGroupe, BorderLayout.NORTH);
		
		
		this.list_2 = new JList<String> (FormMain.getModelListGroupe());

		list_2.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		list_2.setBackground(new Color(211, 211, 211));
		
		/**
		 * Evènement lors du clic sur un groupe
		 */
		panel_2.add(list_2, BorderLayout.CENTER);
		list_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if(list_2.getSelectedValue() != null){
					Hashtable<String,Long> listeGroupe = SQLiteJDBC.getlistgroupe(FormMain.AssoGroupe);
					Long GroupeID = listeGroupe.get(list_2.getSelectedValue());
					
					GroupeSelected = FormMain.AssoGroupe.get(GroupeID);
					System.out.println("Identifiant du groupe selectionné = " + GroupeSelected.getIdentifiant());
					System.out.println("Nom du groupe selectionné = " + GroupeSelected.getNom());
				}
			}
		});
		new JList<Object>();
			
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(112, 128, 144));
		splitPane.setRightComponent(panel_3);
		
		JLabel lblCreationDeSemaine = new JLabel("Cr\u00E9ation d'une semaine Type");
		lblCreationDeSemaine.setOpaque(true);
		lblCreationDeSemaine.setFont(new Font("Verdana", Font.BOLD, 16));
		lblCreationDeSemaine.setPreferredSize(new Dimension(125, 40));
		lblCreationDeSemaine.setBackground(new Color(30, 144, 255));
		lblCreationDeSemaine.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_4.setVerifyInputWhenFocusTarget(false);
		panel_4.setBackground(new Color(112, 128, 144));
		
		JButton btnNewButton_1 = new JButton("Sauvegarde\r\n");
		btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnNewButton_1.setPreferredSize(new Dimension(150, 30));
		
		/**
		 * Evènement lors du clic sur le bouton Sauvegarde
		 */
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Profil NewProfil;
				if(FormMain.textNomProfil.getText().equals("") || String.valueOf(FormMain.textCouleurProfil.getBackground().getRGB()).equals("")){
					JOptionPane.showMessageDialog(null, "Insertion Impossible vérifiez vos informations");	
				}
				else{
					int vartemp =0;
					for(int i = 0 ; i < FormMain.getModeListProfil().getSize(); i++){
						if(FormMain.getModeListProfil().get(i).equals(FormMain.textNomProfil.getText())){
							vartemp ++;
						}
					}
					if(vartemp > 0){
						System.out.println("Le nom de profil existe déjà");
						JOptionPane.showMessageDialog(null, "Le nom de profil existe déjà");
					}
					else{
						try {
							String sql = String.format(ReqSQLite.INSERTION_PROFIL.getRequeteSQL(), FormMain.textNomProfil.getText(),FormMain.textCouleurProfil.getBackground().getRGB()); //("SELECT id_semaine FROM SEMAINE WHERE date_lundi = '%s';" ),
							SQLiteJDBC.ExecuteRequete(sql); 
							
							NewProfil = new Profil(Profil.RecupereIdProfil(),FormMain.textNomProfil.getText(),String.valueOf(FormMain.textCouleurProfil.getBackground().getRGB()));
							
							SemaineType NewSemaine = new SemaineType(SemaineType.RecupereIdSemaineType(),NewProfil,false);	
							NewSemaine.InsertionSemaineJour(NewProfil);
							
							long id_semaine = SemaineType.RecupereIdSemaineType();
							NewSemaine.setId_semaine(id_semaine);
					
							NewProfil.addListeSemainesType(NewSemaine);
						

							System.out.println(NewProfil.getListeSemaineType().get(0).getId_semaine() + "id_semaine qui est dans le profil");
							
							for(int i=0;i<table.getRowCount();i++)
							{
								String jour = (String) table.getValueAt(i, 0) ;	
								
								int zero = 		Integer.valueOf(table.getValueAt(i, 1).toString());
								int un = 		Integer.valueOf(table.getValueAt(i, 2).toString());
								int deux =  	Integer.valueOf(table.getValueAt(i, 3).toString());
								int trois = 	Integer.valueOf(table.getValueAt(i, 4).toString());
								int quatre =	Integer.valueOf(table.getValueAt(i, 5).toString());
								int cinq = 		Integer.valueOf(table.getValueAt(i, 6).toString());
								int six = 		Integer.valueOf(table.getValueAt(i, 7).toString());
								int sept =  	Integer.valueOf(table.getValueAt(i, 8).toString());
								int huit = 		Integer.valueOf(table.getValueAt(i, 9).toString());
								int neuf = 		Integer.valueOf(table.getValueAt(i, 10).toString());
								int dix =  		Integer.valueOf(table.getValueAt(i, 11).toString());
								int onze =  	Integer.valueOf(table.getValueAt(i, 12).toString());
								int douze =    	Integer.valueOf(table.getValueAt(i, 13).toString());
								int treize = 	Integer.valueOf(table.getValueAt(i, 14).toString());
								int quatorze =  Integer.valueOf(table.getValueAt(i, 15).toString());
								int quinze =	Integer.valueOf(table.getValueAt(i, 16).toString());
								int seize = 	Integer.valueOf(table.getValueAt(i, 17).toString());
								int dixsept =  	Integer.valueOf(table.getValueAt(i, 18).toString());
								int dixhuit =  	Integer.valueOf(table.getValueAt(i, 19).toString());
								int dixneuf =   Integer.valueOf(table.getValueAt(i, 20).toString());
								int vingt =   	Integer.valueOf(table.getValueAt(i, 21).toString());
								int vingtun =   Integer.valueOf(table.getValueAt(i, 22).toString());
								int vingtdeux = Integer.valueOf(table.getValueAt(i, 23).toString());
								int vingttrois =Integer.valueOf(table.getValueAt(i, 24).toString());
			
								String sql3 = String.format(ReqSQLite.MODIFICATION_JOURTYPE.getRequeteSQL(),zero,un,deux,trois,quatre,cinq,six,sept,huit,neuf,dix,onze,douze,treize,quatorze,quinze,seize,dixsept,dixhuit,dixneuf,vingt,vingtun,vingtdeux,vingttrois,NewProfil.getIdentifiant(),jour); 			
								System.out.println(sql3);
								SQLiteJDBC.ExecuteRequete(sql3); 
								System.out.println("requête effectuée");
						}
						
						FormMain.AssoProfilId.put(NewProfil.getIdentifiant(), NewProfil);
						
						FormMain.getModeListProfil().addElement(FormMain.textNomProfil.getText());
						FormProfilType.this.dispose();
					
					
					} catch (ClassNotFoundException | SQLException e) {
						e.printStackTrace();
						FormProfilType.this.dispose();
					}
					}		
				}
			}
		});
		
		panel_4.add(btnNewButton_1);
		SpringLayout sl_panel_3 = new SpringLayout();
		sl_panel_3.putConstraint(SpringLayout.WEST, panel_4, 0, SpringLayout.WEST, lblCreationDeSemaine);
		sl_panel_3.putConstraint(SpringLayout.EAST, panel_4, 0, SpringLayout.EAST, panel_3);
		sl_panel_3.putConstraint(SpringLayout.WEST, lblCreationDeSemaine, 0, SpringLayout.WEST, panel_3);
		sl_panel_3.putConstraint(SpringLayout.EAST, lblCreationDeSemaine, 1, SpringLayout.EAST, panel_3);
		sl_panel_3.putConstraint(SpringLayout.NORTH, lblCreationDeSemaine, 0, SpringLayout.NORTH, panel_3);
		panel_3.setLayout(sl_panel_3);
		panel_3.add(lblCreationDeSemaine);
		panel_3.add(panel_4);
		
		JScrollPane scrollPane = new JScrollPane();
		sl_panel_3.putConstraint(SpringLayout.NORTH, panel_4, 6, SpringLayout.SOUTH, scrollPane);
		scrollPane.setFont(new Font("Tahoma", Font.PLAIN, 10));
		sl_panel_3.putConstraint(SpringLayout.NORTH, scrollPane, 18, SpringLayout.SOUTH, lblCreationDeSemaine);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, scrollPane, -51, SpringLayout.SOUTH, panel_3);
		sl_panel_3.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, panel_3);
		sl_panel_3.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, panel_3);
		panel_3.add(scrollPane);
		
		table = new JTable();
		table.setEnabled(false);
		table.setSurrendersFocusOnKeystroke(true);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setRowMargin(-2);
		table.setRowHeight(15);
		table.setMaximumSize(new Dimension(15, 0));
		table.setMinimumSize(new Dimension(15, 0));
		table.setPreferredScrollableViewportSize(new Dimension(450, 500));
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/**
		 * Evènement lors du clic sur un champ de la table 
		 */
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				super.mouseClicked(arg0);

				int column = table.columnAtPoint(arg0.getPoint());
				int row = table.rowAtPoint(arg0.getPoint());
				
				if (arg0.getButton() == MouseEvent.BUTTON3  && column >= 1){
					table.setValueAt('0', row, column);
				}
				else if (GroupeSelected != null && column >= 1){
					table.setValueAt(GroupeSelected.getIdentifiant(), row, column);
				}
			}
		});
	
		/**
		 * Appel de la méthode de rendu du tableau pour changer la couleur de la cellule 
		 */
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
	                 Object value, boolean isSelected, boolean hasFocus,
	                 int row, int column) {
	             super.getTableCellRendererComponent(table, value, isSelected,
	                     hasFocus, row, column);
	             		if (column>=1  && GroupeSelected != null) {
			            	 if(table.getValueAt(row, column).toString() != null){
			            		 int id = Integer.parseInt(table.getValueAt(row, column).toString());
				            	 if (id != 0){
										long truc = (long)id ;
										Groupe group = FormMain.AssoGroupe.get(truc);
										String color = group.getCouleur() ;
										int couleur = Integer.parseInt(color);					
										setBackground(new Color(couleur));
									}
									else{
										setBackground(Color.WHITE);
									}	
			            	 }
			             }
			          table.repaint();
	             	return this ;
	            }
	         });
		
		/**
		 * Modèle de la table par défaut
		 */
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"LUNDI", new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0)},
				{"MARDI", new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0)},
				{"MERCREDI", new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0)},
				{"JEUDI", new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0)},
				{"VENDREDI", new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0)},
				{"SAMEDI", new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0)},
				{"DIMANCHE", new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0), new Integer(0)},
			},
			new String[] {
				"JOURS", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"
			}
		));
		
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		for(int x = 1; x<=24; x++){
			table.getColumnModel().getColumn(x).setPreferredWidth(17);
		}
		
		table.setRowHeight(0,31);
		table.setRowHeight(1,31);
		table.setRowHeight(2,31);
		table.setRowHeight(3,31);
		table.setRowHeight(4,31);
		table.setRowHeight(5,31);
		table.setRowHeight(6,31);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(112, 128, 144));
		
		JLabel lblNewLabel = new JLabel("Groupe et Utilisateur");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(112, 128, 144));
		
	}
}
