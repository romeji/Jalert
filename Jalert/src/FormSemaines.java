
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormSemaines extends JDialog {
	public FormMain main;
	private static final long serialVersionUID = 1L;
	private JTable table;
	private Profil ProfilSelected = new Profil(0, "", "");
	
	public static DefaultTableModel modeListSemaine ;
	
	@SuppressWarnings("rawtypes")
	private JList ListeProfil;
	
	private JButton btnretour;
	@SuppressWarnings("deprecation")
	public FormSemaines(FormMain main) throws ClassNotFoundException, SQLException {
		this.main = main;
		setModal(true);
		setSize(new Dimension(859, 436));
		setResizable(false);
		setBounds(new Rectangle(400, 400, 859, 440));
		setPreferredSize(new Dimension(480, 160));	
		getContentPane().setBackground(new Color(112, 128, 144));
		setLocationRelativeTo(null);
		String sql1 = String.format(ReqSQLite.AFFICHER_SEMAINE.getRequeteSQL());
		System.out.println(sql1);
	
	
		
		JSplitPane splitPane = new JSplitPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(splitPane, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(24, Short.MAX_VALUE))
		);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(119, 136, 153));
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblGroupe = new JLabel("Profils");
		lblGroupe.setBackground(new Color(30, 144, 255));
		lblGroupe.setOpaque(true);
		lblGroupe.setFont(new Font("Verdana", Font.BOLD, 14));
		lblGroupe.setSize(new Dimension(150, 150));
		lblGroupe.setMinimumSize(new Dimension(150, 100));
		lblGroupe.setMaximumSize(new Dimension(141, 100));
		lblGroupe.setHorizontalAlignment(SwingConstants.CENTER);
		lblGroupe.setPreferredSize(new Dimension(200, 60));
		panel.add(lblGroupe, BorderLayout.NORTH);
		
	
	
			@SuppressWarnings({ "rawtypes", "unchecked" })
			JList jList = new JList(FormMain.getModeListProfil());
			ListeProfil = jList;
			ListeProfil.setFont(new Font("Verdana", Font.BOLD, 13));
			
			/**
			 * Evènement lors du clic récupère l'id du profil
			 */
			ListeProfil.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					
					Hashtable<String,Long> Profil = SQLiteJDBC.getlistprofil(FormMain.AssoProfilId);
					Long ProfilID = null ;
					
						ProfilID = Profil.get(ListeProfil.getSelectedValue());
						ProfilSelected = FormMain.AssoProfilId.get(ProfilID);
						System.out.println("Identifiant du profil selectionné = " + ProfilSelected.getIdentifiant());
						System.out.println("Nom du profil selectionné = " + ProfilSelected.getNom());
					}
		
			});
			
			
			ListeProfil.setBackground(new Color(211, 211, 211));
			ListeProfil.setDragEnabled(true);
			panel.add(ListeProfil, BorderLayout.CENTER);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(119, 136, 153));
		splitPane.setRightComponent(panel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, 200));
		
		ResultSet rs = SQLiteJDBC.ExecuteRequeteSelect(sql1);
		 table = new JTable(modeListSemaine);
		 table.setSelectionForeground(Color.BLACK);
		 	
		 table.setModel(DbUtils.resultSetToTableModel(rs));
			 DefaultTableModel modeListSemaine = (DefaultTableModel) table.getModel();

			
		/**
		 * Lors de la pression du clic gauche sur la table on place l'id du profil sélectionné 
		 */
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				super.mouseClicked(arg0);

				int column = table.columnAtPoint(arg0.getPoint());
				int row = table.rowAtPoint(arg0.getPoint());
				if (arg0.getButton() == MouseEvent.BUTTON3  && column == 2){
					table.setValueAt('0', row, column);
				}
				else if (ProfilSelected != null && column == 2){
					table.setValueAt(ProfilSelected.getIdentifiant(), row, column);
				}
			}
		});
		
		/**
		 * Evènement qui met dans la table la couleur de l'id du profil
		 */
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
	         public Component getTableCellRendererComponent(JTable table,
	                 Object value, boolean isSelected, boolean hasFocus,int row, int column) {
	             super.getTableCellRendererComponent(table, value, isSelected,
	                     hasFocus, row, column);
	             		if (column==2  && ProfilSelected != null) {       	
			            	 if(table.getValueAt(row, column).toString() != null){		
			            		 int id = Integer.parseInt(table.getValueAt(row, column).toString());
				            	 if (id != 0){
										long truc = (long)id ;
										Profil profil = FormMain.AssoProfilId.get(truc);
										String color = profil.getCouleur() ;
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
		 * Recupère la date de la semaine affichée dans la table et la convertie
		 */
		for(int i = 0; i< table.getRowCount(); i++){
			try {
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
				String date1 =  (String) table.getValueAt(i, 1);
				Date date2;
				date2 = new Date(format2.parse(date1).getTime());
				table.setValueAt(date2.toLocaleString().split("00:00:00")[0], i, 1);
			
			} catch (ParseException e) {
			
				e.printStackTrace();
			}
			
		}
		
		table.getColumn("id_semaine").setMinWidth(0);
		table.getColumn("id_semaine").setMaxWidth(0);
		
		scrollPane.setViewportView(table);
		
		/**
		 * Evènement =>  Supprime la semaine lors du clic 
		 */
		JButton btnNewButton_1 = new JButton("-");
		btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
				
			public void actionPerformed(ActionEvent arg0) {
				String[] buttons = { "Oui", "Annuler" };
				 int rc = JOptionPane.showOptionDialog(null, "Voulez-vous vraiment supprimer cette semaine ?", "Confirmation",
					        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[0]);
				 if( rc == 0 ){
					 int x = table.getSelectedRow();
					 if(x == -1){
						 JOptionPane.showMessageDialog(null,"Sélectionner une semaine");
					 }
					 else {
						 
						 table.getValueAt(x,1).toString();
						 
						 String dateparse = Semaine.dateLitToIso(table.getValueAt(x,1).toString());		
							SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
							Date tempDate2;
							String Dates = null;
							
							try {
								tempDate2 = new Date(format2.parse(dateparse).getTime());
								Dates = format2.format(tempDate2);
								String annee = Dates.split("-")[0];						
								String mois = Dates.split("-")[1];
								String jour = Dates.split("-")[2];
								OutilsDate.DateSemaine(tempDate2);
								String date = OutilsDate.DateSemaine(tempDate2) + " - "+ jour+"-"+mois+"-"+annee ;
								System.out.println(date);
								
								main.comboBox.removeItem(date);
								
							} catch (ParseException e2) {
								
								e2.printStackTrace();
							}

							System.out.println(Dates);
						
							try {
								Semaine.SupressionDeSemaineJour(Dates);
							} catch (ClassNotFoundException | SQLException e1) {
								
								e1.printStackTrace();
							}
							System.out.println(Dates);
						
						 String sql = String.format(ReqSQLite.SUPRIMER_SEMAINE_DATE.getRequeteSQL(),Dates);
						 JOptionPane.showMessageDialog(null,"Suppression réussie");
						 
						 try {
							 SQLiteJDBC.ExecuteRequete(sql);
							 modeListSemaine.removeRow(x);
				
						}catch (ClassNotFoundException | SQLException e) {
							e.printStackTrace();
						}
						 
						 System.out.println(x);
					 }	 
				}
					 
			}
		});
		btnNewButton_1.setPreferredSize(new Dimension(28, 28));
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 60));
		panel_2.setMinimumSize(new Dimension(10, 60));
		panel_2.setBackground(new Color(30, 144, 255));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(119, 136, 153));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(27)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGap(15))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
					.addContainerGap())
				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(0)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
					.addGap(38)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(226))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(0))
		);
		
		JLabel lblSemainesDastreintesProgramm = new JLabel("Semaines d'astreintes programm\u00E9es");
		lblSemainesDastreintesProgramm.setIgnoreRepaint(true);
		lblSemainesDastreintesProgramm.setAutoscrolls(true);
		lblSemainesDastreintesProgramm.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblSemainesDastreintesProgramm.setSize(new Dimension(160, 150));
		lblSemainesDastreintesProgramm.setPreferredSize(new Dimension(420, 60));
		lblSemainesDastreintesProgramm.setOpaque(true);
		lblSemainesDastreintesProgramm.setMinimumSize(new Dimension(175, 120));
		lblSemainesDastreintesProgramm.setMaximumSize(new Dimension(160, 120));
		lblSemainesDastreintesProgramm.setHorizontalAlignment(SwingConstants.CENTER);
		lblSemainesDastreintesProgramm.setFont(new Font("Verdana", Font.BOLD, 20));
		lblSemainesDastreintesProgramm.setBackground(new Color(30, 144, 255));
		panel_2.add(lblSemainesDastreintesProgramm);
		
		btnretour = new JButton("Retour au planning");
		btnretour.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnretour.setPreferredSize(new Dimension(150, 30));
		btnretour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				FormSemaines.this.dispose();
			}
		});
		btnretour.setHorizontalTextPosition(SwingConstants.CENTER);
		panel_3.add(btnretour);
		
		JButton btnSauvegarde = new JButton("Sauvegarder");
		btnSauvegarde.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnSauvegarde.setPreferredSize(new Dimension(150, 30));
		/**
		 * Sauvegarde les modifications apportées à la semaine(ajout d'un profil)
		 */
		btnSauvegarde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<table.getRowCount();i++)
				{
						String date[] = {"LUNDI","MARDI","MERCREDI","JEUDI","VENDREDI","SAMEDI","DIMANCHE"};
						String id_semaine =  "0" ;
						String joursActuelle = "0" ;
						
						ArrayList<Date> tempJours;
							
						try {
									id_semaine = table.getValueAt(i,0).toString();
									joursActuelle = table.getValueAt(i,1).toString();
									String id_profil = table.getValueAt(i,2).toString();										
									String dateparse = Semaine.dateLitToIso(joursActuelle);		
									SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
									Date tempDate2 = new Date(format2.parse(dateparse).getTime());
			
									tempJours = OutilsDate.getDatesFromWeekNumber(OutilsDate.getAnnee(tempDate2),OutilsDate.DateSemaine(tempDate2));	
									
									String sql = String.format(ReqSQLite.INSERTION_PROFIL_DANS_SEMAINE.getRequeteSQL(),id_profil,id_semaine); //UPDATE SEMAINE SET id_profil = '%s' WHERE id_semaine = '%s
									SQLiteJDBC.ExecuteRequete(sql);
									System.out.println(sql);
									
									int zero = Integer.parseInt(id_profil);
									
									if(zero != 0) {
										for(int x = 0; x < 7; x++) {
											System.out.println(table.getValueAt(i,2) + " table.getvalueat");
											String sql1 = String.format(ReqSQLite.RECUPERE_IDSEMAINETYPE.getRequeteSQL(),id_profil); 
											ResultSet IdsemaineType = SQLiteJDBC.ExecuteRequeteSelect(sql1);
	
											long id_semaineType = IdsemaineType.getLong("id_semaine");
											String sql2 = String.format(ReqSQLite.MAJ_TABLE_JOUR.getRequeteSQL(),id_semaineType,date[x] ,id_semaineType,date[x] ,id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaineType,date[x],id_semaine,tempJours.get(x)); 
											System.out.println(sql2);
											SQLiteJDBC.ExecuteRequete(sql2);
										}
										JOptionPane.showMessageDialog(null,"Le profil" + id_profil + " a été appliqué à la semaine." );
									}
									if(zero == 0) {
										for(int x = 0; x <= 6; x++) {								
											String sql2 = String.format(ReqSQLite.DEFAULT_TABLE_JOUR.getRequeteSQL(),id_semaine,tempJours.get(x)); 
											SQLiteJDBC.ExecuteRequete(sql2);
											System.out.println("0 partout" + sql2);							
										}
										JOptionPane.showMessageDialog(null,"Semaine ré-initialisée !");
									}
									
							
						} catch (ParseException | ClassNotFoundException | SQLException e) {
							e.printStackTrace();
						}
					
				}
			}
		});
		panel_3.add(btnSauvegarde);
		btnSauvegarde = new JButton("Sauvegarder\r\n");
		panel_1.setLayout(gl_panel_1);
		getContentPane().setLayout(groupLayout);
		
		
	}
}
