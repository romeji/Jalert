import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import net.proteanit.sql.DbUtils;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;
import java.awt.Cursor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.ScrollPaneConstants;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Frame;
import java.awt.CardLayout;

public class FormMain  {

	static FormMain main = null;
	
	public static JFrame frame;
	private JTextField UtilisateurFormMail;
	private JTextField UtilisateurFormCode;
	private JTextField UtilisateurFormCouleur;
	private JTextField UtilisateurFormNom;
	private JTextField UtilisateurFormPrenom;
	private JTextField UtilisateurFormTelephone;
	private JTextField TextFieldGroupeNom;
	private JTextField TextFieldGroupeCouleur;
	private JTextField TextFieldGroupeEdroit;
	static JTextField textCouleurProfil;
	static JTextField textNomProfil;
	private JTable table_1;
	private JTable table_2;
	private Groupe GroupeSelected = new Groupe(0, "", 0, "");
	private static SQLiteJDBC Con1 ;
	
	private JList<String> listUser;
	private JList<String> listProfil;
	private JList<Object> list_1 = new JList<Object>();
	private JScrollPane scrollPane_1;
	
	public JSplitPane splitPane_2 ;
	public JSplitPane splitPane_1 ;
	
	public JComboBox comboBox = new JComboBox(SQLiteJDBC.getListeSemaine());
	
	private static DefaultListModel<String> modelListGroupe;
	private static DefaultListModel<String> modellistUser;
	private static DefaultListModel<String> modeListProfil;
	ArrayList<String> ListePersonneDuGroupe = new ArrayList<String>();
	static Hashtable<Long,Utilisateur> AssoUtilisateur = new Hashtable<Long,Utilisateur>(); 
	static Hashtable<Long,Groupe> 	   AssoGroupe = new Hashtable<Long,Groupe>();	  
	static Hashtable<Long,Profil>	AssoProfilId = new Hashtable<Long,Profil>();
	
	public static DefaultListModel<String> getModeListProfil() {
		return modeListProfil;
	}
	public static DefaultListModel<String> getModelListGroupe() {
		return modelListGroupe;
	}
	public static Hashtable<Long, Groupe> getAssoGroupe() {
		return AssoGroupe;
	}

	JList<String> list_3 = null ;
	
	private JTable table_3;
	private JPanel panel_10;

	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {

		Con1 = new SQLiteJDBC();
		SQLiteJDBC.CreationDesTables();
		Con1.ChargementAssociations(AssoUtilisateur, AssoGroupe);
		Con1.ChargementUtilisateur(AssoUtilisateur,AssoGroupe);
		Con1.ChargementGroupe(AssoGroupe, AssoUtilisateur);
		Con1.ChargementDesProfils(AssoProfilId);
		Con1.ChargementDesSemainesEtJours(AssoProfilId);
		Con1.ChargementDesSemainesTypeEtJoursType(AssoProfilId);
		
		/**
		 * Génération de la fenêtre 
		 */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
					 main = new FormMain();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	

	/**
	 * Constructeur
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public FormMain() throws ClassNotFoundException, SQLException, ParseException {
		initialize();	
	}

	private void initialize() throws ClassNotFoundException, SQLException, ParseException{
		frame = new JFrame();
		frame.setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		frame.setSize(new Dimension(15, 15));
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\Documents\\Projet développement\\java\\workspace\\Jalert\\res\\ClipBoard-icon.png"));
		frame.getContentPane().setBackground(SystemColor.activeCaptionText);
		frame.setVisible(true);
		frame.setBackground(SystemColor.desktop);
		frame.setForeground(SystemColor.desktop);
		frame.setBounds(100, 100, 1275, 598);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setOpaque(true);
		tabbedPane.setFont(new Font("Dialog", Font.BOLD, 14));
		tabbedPane.setPreferredSize(new Dimension(10, 70));
		tabbedPane.setBackground(new Color(30, 144, 255));
		
		splitPane_1 = new JSplitPane();
		splitPane_1.setOpaque(false);
		splitPane_1.setForeground(SystemColor.desktop);
		splitPane_1.setBackground(SystemColor.activeCaptionText);
		tabbedPane.addTab("Groupe", null, splitPane_1, null);
		
		
		JPanel GroupePanelGroupe = new JPanel();
		GroupePanelGroupe.setBackground(new Color(119, 136, 153));
		splitPane_1.setLeftComponent(GroupePanelGroupe);
		GroupePanelGroupe.setLayout(new BoxLayout(GroupePanelGroupe, BoxLayout.X_AXIS));
		
		JPanel BoutonPanelGroupe = new JPanel();
		BoutonPanelGroupe.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		BoutonPanelGroupe.setBackground(new Color(119, 136, 153));
		BoutonPanelGroupe.setVerifyInputWhenFocusTarget(false);
		GroupePanelGroupe.add(BoutonPanelGroupe);
		BoutonPanelGroupe.setLayout(new BorderLayout(0, 0));
		
		JLabel labelGroupe = new JLabel("Groupes");
		labelGroupe.setBorder(new MatteBorder(0, 0, 1, 1, (Color) new Color(0, 0, 0)));
		labelGroupe.setOpaque(true);
		labelGroupe.setMaximumSize(new Dimension(56, 16));
		labelGroupe.setPreferredSize(new Dimension(50, 16));
		labelGroupe.setMinimumSize(new Dimension(140, 14));
		labelGroupe.setBackground(new Color(30, 144, 255));
		BoutonPanelGroupe.add(labelGroupe, BorderLayout.CENTER);
		labelGroupe.setHorizontalTextPosition(SwingConstants.CENTER);
		labelGroupe.setHorizontalAlignment(SwingConstants.CENTER);
		labelGroupe.setFont(new Font("Verdana", Font.BOLD, 16));
		labelGroupe.setAlignmentX(1.0f);
		
		JPanel panel_11 = new JPanel();
		panel_11.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_11.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		BoutonPanelGroupe.add(panel_11, BorderLayout.EAST);
		panel_11.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton_3 = new JButton("+");
		btnNewButton_3.setBounds(new Rectangle(2, 0, 2, 0));
		btnNewButton_3.setMaximumSize(new Dimension(341, 32));
		btnNewButton_3.setHorizontalAlignment(SwingConstants.LEADING);
		btnNewButton_3.setPreferredSize(new Dimension(42, 32));
		btnNewButton_3.addActionListener(new ActionListener() {
			
			/**
			 * Evènement lors de l'ajout d'un Groupe
			 */
			public void actionPerformed(ActionEvent e) {
					
				if(TextFieldGroupeNom.getText().equals("") || TextFieldGroupeEdroit.getText().equals("") || String.valueOf(TextFieldGroupeCouleur.getBackground().getRGB()).equals("")){
						JOptionPane.showMessageDialog(frame, "Insertion impossible vérifiez vos informations");
				}
				else {
					Groupe NewGroupe;
					try {
						int vartemp =0;
						for(int i = 0 ; i < modelListGroupe.getSize(); i++){
							if(modelListGroupe.get(i).equals(TextFieldGroupeNom.getText())){
								vartemp ++;
							}
						}
						if(vartemp > 0){
							System.out.println("Le nom de groupe existe déjà");
							JOptionPane.showMessageDialog(frame, "Le nom de groupe existe déjà");
						}
						else{
								String sql = String.format(ReqSQLite.INSERTION_GROUPE.getRequeteSQL(),TextFieldGroupeNom.getText(),Integer.parseInt(TextFieldGroupeEdroit.getText()),TextFieldGroupeCouleur.getBackground().getRGB()); // AFFICHER_INFO_DUN_GROUPE ("SELECT * FROM GROUPE WHERE nom = '%s'"),
								SQLiteJDBC.ExecuteRequete(sql);	
								NewGroupe = new Groupe(Groupe.RecupereIdGroupe(),TextFieldGroupeNom.getText(),Integer.parseInt(TextFieldGroupeEdroit.getText()),String.valueOf(TextFieldGroupeCouleur.getBackground().getRGB()));
								
								AssoGroupe.put(NewGroupe.getIdentifiant(), NewGroupe);
								
								System.out.print("identifiant : " +NewGroupe.getIdentifiant());
								System.out.print("Nom de groupe : " +NewGroupe.getNom());
								modelListGroupe.addElement(NewGroupe.getNom());
								JOptionPane.showMessageDialog(frame, "Groupe créé");
						}	
					} catch (NumberFormatException | ClassNotFoundException | SQLException e1) {
						JOptionPane.showMessageDialog(frame, "Insertion impossible vérifiez vos informations");
						
					}
					
				}
			}
		});
		btnNewButton_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		panel_11.add(btnNewButton_3, BorderLayout.WEST);
		
		JButton button_3 = new JButton("-");
		button_3.setPreferredSize(new Dimension(41, 33));
		panel_11.add(button_3, BorderLayout.NORTH);
		button_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button_3.setHorizontalTextPosition(SwingConstants.LEADING);
		
		JPanel panel_15 = new JPanel();
		panel_15.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		panel_15.setBackground(new Color(119, 136, 153));
		BoutonPanelGroupe.add(panel_15, BorderLayout.SOUTH);
		panel_15.setLayout(new BorderLayout(0, 0));
				
		modelListGroupe= new DefaultListModel<String>();
		this.list_3 = new JList<String>(modelListGroupe);
		list_3.setBounds(new Rectangle(2, 14, 2, 4));
		list_3.setVisibleRowCount(4);
		list_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_3.setSelectionBackground(Color.BLACK);
		list_3.setFont(new Font("Verdana", Font.BOLD, 13));
		for (String elem : SQLiteJDBC.getListeGroupe(AssoGroupe)){
			modelListGroupe.addElement(elem);
		}
		
		this.list_3.setBackground(new Color(211, 211, 211));
		this.list_3.setPreferredSize(new Dimension(150, 200));
		this.list_3.setSize(new Dimension(150, 210));
		panel_15.add(list_3, BorderLayout.NORTH);
		
		/**
		 * Evènement lors de la suppression d'un groupe
		 */
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list_3.getSelectedValue()!=null)
				{
					try {
						Hashtable<String,Long> listgroupe = SQLiteJDBC.getlistgroupe(FormMain.AssoGroupe);
						Long GroupeSelectionne = listgroupe.get(list_3.getSelectedValue());
						Groupe tempGroupe = AssoGroupe.get(GroupeSelectionne);
						
						 for(int i = 0; i<tempGroupe.listePersonnes.size();i++){
					    		String name = tempGroupe.listePersonnes.get(i).getPrenom()+" "+tempGroupe.listePersonnes.get(i).getNom(); 
					    		int index = ListePersonneDuGroupe.indexOf(name);
					    		ListePersonneDuGroupe.remove(index);
					    		list_1.setListData(ListePersonneDuGroupe.toArray());
					    }
		
						AssoGroupe.remove(GroupeSelectionne);
						String sql = String.format(ReqSQLite.SUPPRIMER_TABLE_APPARTIENT_GROUPE.getRequeteSQL(),GroupeSelectionne);
						SQLiteJDBC.ExecuteRequete(sql);
						sql = String.format(ReqSQLite.SUPRESSION_GROUPE.getRequeteSQL(),tempGroupe.getNom());
						SQLiteJDBC.ExecuteRequete(sql);
						modelListGroupe.removeElement(tempGroupe.getNom());
						
						
					} catch (ClassNotFoundException | SQLException e) {
						JOptionPane.showMessageDialog(frame, "Veuillez re-sélectionner le groupe.");
					}
					JOptionPane.showMessageDialog(frame, "Groupe supprimé.");
					
				}
				else{
					JOptionPane.showMessageDialog(frame, "Sélectionner un groupe.");
				}
				
			}
		});
		
		JPanel panel_16 = new JPanel();
		panel_15.add(panel_16, BorderLayout.EAST);
		panel_16.setLayout(new BorderLayout(0, 0));
		
	
		
		list_1.setSelectionBackground(Color.BLACK);
		list_1.setFocusTraversalKeysEnabled(false);
		list_1.setFocusable(false);
		list_1.setVerifyInputWhenFocusTarget(false);
		list_1.setRequestFocusEnabled(false);
		list_1.setFont(new Font("Verdana", Font.BOLD, 13));
		list_1.setBackground(new Color(211, 211, 211));
		list_1.setPreferredSize(new Dimension(200, 210));
		panel_15.add(list_1, BorderLayout.SOUTH);
		
		JLabel lblUtilisateur = new JLabel("Utilisateurs");
		lblUtilisateur.setAutoscrolls(true);
		lblUtilisateur.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		lblUtilisateur.setOpaque(true);
		panel_15.add(lblUtilisateur, BorderLayout.CENTER);
		lblUtilisateur.setFont(new Font("Verdana", Font.BOLD, 16));
		lblUtilisateur.setBackground(new Color(30, 144, 255));
		lblUtilisateur.setHorizontalAlignment(SwingConstants.CENTER);
		lblUtilisateur.setPreferredSize(new Dimension(20, 50));
		
		/**
		 * Evènement lors du clic sur le nom du groupe
		 */
		list_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (list_3.getSelectedValue()!=null){
	
					Hashtable<String,Long> listgroupe = SQLiteJDBC.getlistgroupe(FormMain.AssoGroupe);
					Long GroupeSelected = listgroupe.get(list_3.getSelectedValue());
					Groupe Groupetempe = AssoGroupe.get(GroupeSelected);
					TextFieldGroupeNom.setText(Groupetempe.getNom());
					TextFieldGroupeEdroit.setText(String.valueOf(Groupetempe.getEdroit()));
					int couleur = Integer.parseInt(Groupetempe.getCouleur());
					Color c = new Color(couleur);
							
					TextFieldGroupeCouleur.setBackground(c);
					TextFieldGroupeCouleur.getBackground().getRGB();
					Groupe Groupetemp;
					
					Groupetemp = FormMain.AssoGroupe.get(GroupeSelected);
					
					ListePersonneDuGroupe.clear();
				
					System.out.println("Identifiant = "+ Groupetemp.getIdentifiant());
					System.out.println("Nom = " + Groupetemp.getNom());
					
					 for(int i = 0; i<Groupetemp.listePersonnes.size();i++){
					        ListePersonneDuGroupe.add(Groupetemp.listePersonnes.get(i).getPrenom()+" "+Groupetemp.listePersonnes.get(i).getNom());      
					  }
					 list_1.setListData(ListePersonneDuGroupe.toArray());
				}
			}
		});	
		
		JPanel GlobalPanelGroupe = new JPanel();
		GlobalPanelGroupe.setBackground(SystemColor.inactiveCaption);
		splitPane_1.setRightComponent(GlobalPanelGroupe);
		GlobalPanelGroupe.setLayout(new BorderLayout(0, 0));
		
		JLabel lblFormulaireGroupe = new JLabel("Formulaire Groupes");
		lblFormulaireGroupe.setOpaque(true);
		lblFormulaireGroupe.setBackground(new Color(30, 144, 255));
		lblFormulaireGroupe.setPreferredSize(new Dimension(50, 50));
		lblFormulaireGroupe.setHorizontalAlignment(SwingConstants.CENTER);
		lblFormulaireGroupe.setFont(new Font("Verdana", Font.BOLD, 25));
		lblFormulaireGroupe.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		GlobalPanelGroupe.add(lblFormulaireGroupe, BorderLayout.NORTH);
		
		JPanel boutonSaveGroupe = new JPanel();
		boutonSaveGroupe.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boutonSaveGroupe.setBackground(new Color(119, 136, 153));
		GlobalPanelGroupe.add(boutonSaveGroupe, BorderLayout.SOUTH);
		
		JButton btnNewButton_5 = new JButton("Ajouter un utilisateur");
		btnNewButton_5.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnNewButton_5.setPreferredSize(new Dimension(165, 30));
		
		/**
		 * Evènement lors du clic sur le bouton liste des membres
		 */
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormUserGroupe UserGroupe;
				try {
					UserGroupe = new FormUserGroupe();
					UserGroupe.setVisible(true);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		boutonSaveGroupe.add(btnNewButton_5);
		
		JButton btnSave = new JButton("Modifier");
		btnSave.setFont(new Font("Verdana", Font.PLAIN, 11));
		boutonSaveGroupe.add(btnSave);
		
		/**
		 * Action lors du clic sur le bouton Sauvegarder
		 */
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list_3.getSelectedValue() != null){
					
					if(TextFieldGroupeNom.getText().equals("") || TextFieldGroupeEdroit.getText().equals("") || String.valueOf(TextFieldGroupeCouleur.getBackground().getRGB()).equals("")){
						JOptionPane.showMessageDialog(frame, "Insertion impossible vérifiez vos informations");
				}
				else {
						Hashtable<String,Long> listGroupe = SQLiteJDBC.getlistgroupe(AssoGroupe);				
						Long idSelected = listGroupe.get(list_3.getSelectedValue());			
						
						
						String sql = String.format(ReqSQLite.MODIFICATION_GROUPE.getRequeteSQL(),TextFieldGroupeNom.getText(),TextFieldGroupeEdroit.getText(),TextFieldGroupeCouleur.getBackground().getRGB(),idSelected);
						Groupe tempGroupe = null;
					
						try {
							System.out.println("identifiant du groupe = " + idSelected);
							System.out.println(sql);
							
							SQLiteJDBC.ExecuteRequete(sql);
							tempGroupe = new Groupe(idSelected,TextFieldGroupeNom.getText(),Integer.valueOf(TextFieldGroupeEdroit.getText()),String.valueOf(TextFieldGroupeCouleur.getBackground().getRGB()));
							modelListGroupe.set(list_3.getSelectedIndex(), tempGroupe.getNom());
							AssoGroupe.put(idSelected,tempGroupe);	
		
						} catch (NumberFormatException | ClassNotFoundException | SQLException e2) {
							JOptionPane.showMessageDialog(frame, "Problème de modification vérifiez vos données");
							e2.printStackTrace();
						}	
						JOptionPane.showMessageDialog(frame, "Modification réussie");
					}
				}
			}
		});
		btnSave.setPreferredSize(new Dimension(150, 30));
		btnSave.setAlignmentX(0.5f);
		
		JPanel panel_1 = new JPanel();
		GlobalPanelGroupe.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JPanel PanelDroitGroupe1 = new JPanel();
		PanelDroitGroupe1.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		PanelDroitGroupe1.setMinimumSize(new Dimension(145, 25));
		PanelDroitGroupe1.setBackground(new Color(119, 136, 153));
		panel_1.add(PanelDroitGroupe1);
		GridBagLayout gbl_PanelDroitGroupe1 = new GridBagLayout();
		gbl_PanelDroitGroupe1.columnWidths = new int[] {147, 297};
		gbl_PanelDroitGroupe1.rowHeights = new int[] {53, 112, 112, 113, 53};
		gbl_PanelDroitGroupe1.columnWeights = new double[]{1.0, 0.0};
		gbl_PanelDroitGroupe1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE, 0.0, 0.0};
		PanelDroitGroupe1.setLayout(gbl_PanelDroitGroupe1);
		
		JLabel lblEdroit = new JLabel("E-droit");
		lblEdroit.setFont(new Font("Verdana", Font.PLAIN, 18));
		GridBagConstraints gbc_lblEdroit = new GridBagConstraints();
		gbc_lblEdroit.anchor = GridBagConstraints.WEST;
		gbc_lblEdroit.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdroit.gridx = 0;
		gbc_lblEdroit.gridy = 1;
		PanelDroitGroupe1.add(lblEdroit, gbc_lblEdroit);
		
		TextFieldGroupeEdroit = new JTextField();
		TextFieldGroupeEdroit.setFont(new Font("Verdana", Font.PLAIN, 13));
		TextFieldGroupeEdroit.setPreferredSize(new Dimension(15, 20));
		TextFieldGroupeEdroit.setMinimumSize(new Dimension(145, 25));
		TextFieldGroupeEdroit.setColumns(12);
		GridBagConstraints gbc_TextFieldGroupeEdroit = new GridBagConstraints();
		gbc_TextFieldGroupeEdroit.anchor = GridBagConstraints.WEST;
		gbc_TextFieldGroupeEdroit.insets = new Insets(0, 0, 5, 0);
		gbc_TextFieldGroupeEdroit.gridx = 1;
		gbc_TextFieldGroupeEdroit.gridy = 1;
		PanelDroitGroupe1.add(TextFieldGroupeEdroit, gbc_TextFieldGroupeEdroit);
		
		JPanel PanelGaucheGroupe1 = new JPanel();
		PanelGaucheGroupe1.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(0, 0, 0)));
		PanelGaucheGroupe1.setBackground(new Color(119, 136, 153));
		GlobalPanelGroupe.add(PanelGaucheGroupe1, BorderLayout.CENTER);
		GridBagLayout gbl_PanelGaucheGroupe1 = new GridBagLayout();
		gbl_PanelGaucheGroupe1.columnWidths = new int[] {122, 133, 377};
		gbl_PanelGaucheGroupe1.rowHeights = new int[] {53, 112, 112, 112, 56, 0};
		gbl_PanelGaucheGroupe1.columnWeights = new double[]{0.0, 0.0, 1.0};
		gbl_PanelGaucheGroupe1.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		PanelGaucheGroupe1.setLayout(gbl_PanelGaucheGroupe1);
		
		JLabel label_1 = new JLabel("Nom");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.weighty = 1.0;
		gbc_label_1.weightx = 1.0;
		gbc_label_1.fill = GridBagConstraints.VERTICAL;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 1;
		PanelGaucheGroupe1.add(label_1, gbc_label_1);
		label_1.setFont(new Font("Verdana", Font.PLAIN, 18));
		
		TextFieldGroupeNom = new JTextField();
		TextFieldGroupeNom.setFont(new Font("Verdana", Font.PLAIN, 13));
		TextFieldGroupeNom.setMinimumSize(new Dimension(145, 25));
		TextFieldGroupeNom.setPreferredSize(new Dimension(15, 20));
		GridBagConstraints gbc_TextFieldGroupeNom = new GridBagConstraints();
		gbc_TextFieldGroupeNom.anchor = GridBagConstraints.WEST;
		gbc_TextFieldGroupeNom.weighty = 1.0;
		gbc_TextFieldGroupeNom.weightx = 1.0;
		gbc_TextFieldGroupeNom.insets = new Insets(0, 0, 5, 0);
		gbc_TextFieldGroupeNom.gridx = 2;
		gbc_TextFieldGroupeNom.gridy = 1;
		PanelGaucheGroupe1.add(TextFieldGroupeNom, gbc_TextFieldGroupeNom);
		TextFieldGroupeNom.setColumns(12);
		
		JLabel lblCouleur = new JLabel("Couleur");
		GridBagConstraints gbc_lblCouleur = new GridBagConstraints();
		gbc_lblCouleur.anchor = GridBagConstraints.WEST;
		gbc_lblCouleur.fill = GridBagConstraints.VERTICAL;
		gbc_lblCouleur.insets = new Insets(0, 0, 5, 5);
		gbc_lblCouleur.gridx = 1;
		gbc_lblCouleur.gridy = 2;
		PanelGaucheGroupe1.add(lblCouleur, gbc_lblCouleur);
		lblCouleur.setFont(new Font("Verdana", Font.PLAIN, 18));
		
		TextFieldGroupeCouleur = new JTextField();
		TextFieldGroupeCouleur.setFont(new Font("Verdana", Font.PLAIN, 11));
		
		/**
		 * Evènement lors du clic sur le TextField de la couleur
		 */
		TextFieldGroupeCouleur.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	
				Color c = JColorChooser.showDialog(null, "hello",Color.getHSBColor(255, 153, 153));
				if(c!=null || String.valueOf(c) != "null"){
					TextFieldGroupeCouleur.setForeground(c);
					TextFieldGroupeCouleur.setBackground(c);
				}
			}
		});

		TextFieldGroupeCouleur.setMinimumSize(new Dimension(145, 25));
		GridBagConstraints gbc_TextFieldGroupeCouleur = new GridBagConstraints();
		gbc_TextFieldGroupeCouleur.anchor = GridBagConstraints.WEST;
		gbc_TextFieldGroupeCouleur.insets = new Insets(0, 0, 5, 0);
		gbc_TextFieldGroupeCouleur.gridx = 2;
		gbc_TextFieldGroupeCouleur.gridy = 2;
		PanelGaucheGroupe1.add(TextFieldGroupeCouleur, gbc_TextFieldGroupeCouleur);
		TextFieldGroupeCouleur.setColumns(12);
		
		JLabel label_3 = new JLabel("");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.EAST;
		gbc_label_3.fill = GridBagConstraints.VERTICAL;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 3;
		PanelGaucheGroupe1.add(label_3, gbc_label_3);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JSplitPane splitPane = new JSplitPane();
		
		
		tabbedPane.addTab("Utilisateur", null, splitPane, null);
		
		JPanel UserPanelUser = new JPanel();
		splitPane.setLeftComponent(UserPanelUser);
		UserPanelUser.setLayout(new BoxLayout(UserPanelUser, BoxLayout.Y_AXIS));
		
		JPanel BoutonPanel = new JPanel();
		BoutonPanel.setBackground(new Color(119, 136, 153));
		BoutonPanel.setVerifyInputWhenFocusTarget(false);
		BoutonPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		UserPanelUser.add(BoutonPanel);
		
		BoutonPanel.setLayout(new BorderLayout(0, 0));
		modellistUser = new DefaultListModel<String>();
		this.listUser =  new JList<String>(modellistUser);
		listUser.setSelectionBackground(Color.BLACK);
		listUser.setFont(new Font("Verdana", Font.BOLD, 13));
		
		/**
		 * Création du DéfaultListModel à partir d'un tableau associatif  
		 */
		for (String elem : SQLiteJDBC.getListeUtilisateurs(AssoUtilisateur)){
			modellistUser.addElement(elem);
			System.out.println(modellistUser.indexOf(elem)); ;
		}
					
		/**
		 * Action lors du clic sur le nom d'un utilisateur
		 */
			listUser.addMouseListener(new MouseAdapter() {			
				public void mouseClicked(MouseEvent e) {
					if(listUser.getSelectedValue() != null){
						Hashtable<String,Long> listUtilisateur = SQLiteJDBC.getlistuser(FormMain.AssoUtilisateur);
						Object test = listUser.getSelectedValue() ;
						Long UtilisateurSelected = listUtilisateur.get(test);
						System.out.println("Identifiant = " + UtilisateurSelected);
						System.out.println("Nom utilisateur = " + test);
						
						Utilisateur Utilisateurtemp = AssoUtilisateur.get(UtilisateurSelected);
						
						UtilisateurFormNom.setText(Utilisateurtemp.getNom());
						UtilisateurFormPrenom.setText(Utilisateurtemp.getPrenom());
						UtilisateurFormTelephone.setText(String.valueOf(Utilisateurtemp.getTelephone()));
						UtilisateurFormMail.setText(Utilisateurtemp.getMail());
						UtilisateurFormCode.setText(Utilisateurtemp.getCode());
					
						int couleur = Integer.parseInt(Utilisateurtemp.getCouleur());
						Color c = new Color(couleur);
						UtilisateurFormCouleur.setBackground(c);	
					}	
				}
			});
			this.listUser.setValueIsAdjusting(true);
			this.listUser.setRequestFocusEnabled(false);
			this.listUser.setBackground(new Color(211, 211, 211));
			BoutonPanel.add(listUser, BorderLayout.SOUTH);
			this.listUser.setPreferredSize(new Dimension(200, 460));
		
			JLabel lblNewLabel = new JLabel("Utilisateurs");
			lblNewLabel.setBorder(new MatteBorder(0, 0, 1, 1, (Color) new Color(0, 0, 0)));
			lblNewLabel.setOpaque(true);
			lblNewLabel.setPreferredSize(new Dimension(410, 410));
			lblNewLabel.setBackground(new Color(30, 144, 255));
			lblNewLabel.setMinimumSize(new Dimension(140, 14));
			lblNewLabel.setMaximumSize(new Dimension(400, 14));
			lblNewLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
			lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			BoutonPanel.add(lblNewLabel, BorderLayout.CENTER);
			
			JPanel panel_12 = new JPanel();
			panel_12.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
			BoutonPanel.add(panel_12, BorderLayout.EAST);
			panel_12.setLayout(new BorderLayout(0, 0));
			
			
			
			/**
			 * Action lors du clic sur le bouton "-"
			 */
			JButton button = new JButton("-");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(listUser.getSelectedValue()!=null)
					{
						try {
							Hashtable<String,Long> listUtilisateur= SQLiteJDBC.getlistuser(FormMain.AssoUtilisateur);
							Long UserSelected = listUtilisateur.get(listUser.getSelectedValue());
							Utilisateur NewUtilisateur = FormMain.AssoUtilisateur.get(UserSelected);
							String NomUser = NewUtilisateur.getNom();
							String PrenomUser = NewUtilisateur.getPrenom() ;
							
							System.out.println("Identifiant de l'utilisateur = " + UserSelected);
							System.out.println("Nom Utilisateur = " + NomUser);
							System.out.println("Prenom Utilisateur = " + PrenomUser);
							
							AssoUtilisateur.remove(UserSelected);
							String sql = String.format(ReqSQLite.SUPPRIMER_TABLE_APPARTIENT_UTILISATEUR.getRequeteSQL(),UserSelected);
							SQLiteJDBC.ExecuteRequete(sql);
							sql = String.format(ReqSQLite.SUPPRESSION_UTILISATEUR.getRequeteSQL(),NomUser,PrenomUser);
							SQLiteJDBC.ExecuteRequete(sql);	
							modellistUser.removeElement(NomUser);
							
						} catch (ClassNotFoundException | SQLException e1) {	
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(frame, "Utilisateur supprimé.");
					}
					else{
						JOptionPane.showMessageDialog(frame, "Sélectionner un utilisateur");	
					}
				}
			});
			button.setPreferredSize(new Dimension(42, 18));
			panel_12.add(button, BorderLayout.EAST);
			button.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
			button.setAlignmentX(0.5f);
			
			/**
			 * Action lors du clic sur bouton "+"
			 */
			JButton btnNewButton_1 = new JButton("+");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Utilisateur NewUtilisateur = null ;
					if(UtilisateurFormNom.getText().equals("") || UtilisateurFormPrenom.getText().equals("") || UtilisateurFormTelephone.getText().equals("") || UtilisateurFormMail.getText().equals("") || UtilisateurFormCode.getText().equals("")|| String.valueOf(UtilisateurFormCouleur.getBackground().getRGB()).equals("")){
							JOptionPane.showMessageDialog(frame, "Insertion impossible vérifiez vos informations");	
					}
					else{
						int vartemp =0;
						for(int i = 0 ; i < modellistUser.getSize(); i++){
							if(modellistUser.get(i).equals(UtilisateurFormNom.getText())){
								vartemp ++;
							}
						}
						if(vartemp > 0){
							System.out.println("Le nom de groupe existe déjà");
							JOptionPane.showMessageDialog(frame, "Le nom de groupe existe déjà");
						}
						else{
							try {
								if((Pattern.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z0-9]+$", UtilisateurFormMail.getText()))){
									if((Pattern.matches("^[0-9]{10}+$", UtilisateurFormTelephone.getText()))){
										JOptionPane.showMessageDialog(null, "Email valide", "Good", JOptionPane.INFORMATION_MESSAGE);
										String sql = String.format(ReqSQLite.INSERTION_UTILISATEUR.getRequeteSQL(),UtilisateurFormNom.getText(),UtilisateurFormPrenom.getText(),Integer.parseInt(UtilisateurFormTelephone.getText()),UtilisateurFormMail.getText(),UtilisateurFormCode.getText(),UtilisateurFormCouleur.getBackground().getRGB());
										System.out.println(sql);
										SQLiteJDBC.ExecuteRequete(sql);
										
										NewUtilisateur = new Utilisateur(Utilisateur.RecupereIdUtilisateur(),UtilisateurFormNom.getText(),UtilisateurFormPrenom.getText(),Integer.parseInt(UtilisateurFormTelephone.getText()),UtilisateurFormMail.getText(),UtilisateurFormCode.getText(),String.valueOf(UtilisateurFormCouleur.getBackground().getRGB()));
										AssoUtilisateur.put(NewUtilisateur.getIdentifiant(),NewUtilisateur);
										modellistUser.addElement(NewUtilisateur.getNom());
										JOptionPane.showMessageDialog(frame, "Insertion effectuée");
									}else{
										JOptionPane.showMessageDialog(null, "Entrer un numéro de téléphone valide", "ERREUR", JOptionPane.ERROR_MESSAGE);
									}
								}else{
									JOptionPane.showMessageDialog(null, "Entrer une adresse email valide", "ERREUR", JOptionPane.ERROR_MESSAGE);	
								}	
							}catch (NumberFormatException | ClassNotFoundException | SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}			
			});
			
		btnNewButton_1.setPreferredSize(new Dimension(41, 32));
		panel_12.add(btnNewButton_1, BorderLayout.SOUTH);
		btnNewButton_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel GlobalPanelUser = new JPanel();
		GlobalPanelUser.setBackground(SystemColor.inactiveCaption);
		splitPane.setRightComponent(GlobalPanelUser);
		GlobalPanelUser.setLayout(new BorderLayout(0, 0));
		
		JLabel lblFormulaire = new JLabel("Formulaire Utilisateurs\r\n");
		lblFormulaire.setOpaque(true);
		lblFormulaire.setBackground(new Color(30, 144, 255));
		lblFormulaire.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblFormulaire.setFont(new Font("Verdana", Font.BOLD, 25));
		lblFormulaire.setHorizontalAlignment(SwingConstants.CENTER);
		lblFormulaire.setPreferredSize(new Dimension(50, 50));
		GlobalPanelUser.add(lblFormulaire, BorderLayout.NORTH);
		
		JPanel boutonSaveUtilisateur = new JPanel();
		boutonSaveUtilisateur.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
		boutonSaveUtilisateur.setBackground(new Color(119, 136, 153));
		GlobalPanelUser.add(boutonSaveUtilisateur, BorderLayout.SOUTH);
		
		JButton BOUTONUSER = new JButton("Modifier");
		/**
		 * Action lors du clic sur le bouton Sauvegarder
		 */
		BOUTONUSER.setFont(new Font("Verdana", Font.PLAIN, 11));
		BOUTONUSER.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(listUser.getSelectedValue() !=null){
					Hashtable<String,Long> listUtilisateur= SQLiteJDBC.getlistuser(FormMain.AssoUtilisateur);
					
					Long UserSelected = listUtilisateur.get(listUser.getSelectedValue());				
					String sql = String.format(ReqSQLite.MODIFICATION_UTILISATEUR.getRequeteSQL(),UtilisateurFormNom.getText(),UtilisateurFormPrenom.getText(),Integer.parseInt(UtilisateurFormTelephone.getText()),UtilisateurFormMail.getText(),UtilisateurFormCode.getText(),UtilisateurFormCouleur.getBackground().getRGB(),UserSelected);
					Utilisateur tempUser = null;
					
					try {
						System.out.println("Identifiant de l'utilisateur sélectionné = " +UserSelected);
						SQLiteJDBC.ExecuteRequete(sql);	
						tempUser = new Utilisateur(UserSelected,UtilisateurFormNom.getText(),UtilisateurFormPrenom.getText(),Integer.parseInt(UtilisateurFormTelephone.getText()),UtilisateurFormMail.getText(),UtilisateurFormCode.getText(), String.valueOf(UtilisateurFormCouleur.getBackground().getRGB()));			
						modellistUser.set(listUser.getSelectedIndex(), tempUser.getNom());
						AssoUtilisateur.put(UserSelected,tempUser);		
					
					} catch (NumberFormatException | ClassNotFoundException | SQLException e2) {
						JOptionPane.showMessageDialog(frame, "Problème de modification vérifiez vos données");
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(frame, "Modification réussie");
					}
				}	
		});
		
		BOUTONUSER.setPreferredSize(new Dimension(150, 30));
		boutonSaveUtilisateur.add(BOUTONUSER);
		BOUTONUSER.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel PanelInfoUser = new JPanel();
		GlobalPanelUser.add(PanelInfoUser, BorderLayout.EAST);
		PanelInfoUser.setLayout(new BoxLayout(PanelInfoUser, BoxLayout.X_AXIS));
		
		JPanel panelDroiteUser2 = new JPanel();
		panelDroiteUser2.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		panelDroiteUser2.setBackground(new Color(119, 136, 153));
		PanelInfoUser.add(panelDroiteUser2);
		GridBagLayout gbl_panelDroiteUser2 = new GridBagLayout();
		gbl_panelDroiteUser2.columnWidths = new int[] {138, 308};
		gbl_panelDroiteUser2.rowHeights = new int[]{51, 112, 112, 112, 55, 0};
		gbl_panelDroiteUser2.columnWeights = new double[]{0.0, 0.0};
		gbl_panelDroiteUser2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelDroiteUser2.setLayout(gbl_panelDroiteUser2);
		
		JLabel lblNewLabel_1 = new JLabel("Mail");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.weightx = 1.0;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panelDroiteUser2.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		UtilisateurFormMail = new JTextField();
		UtilisateurFormMail.setPreferredSize(new Dimension(145, 25));
		UtilisateurFormMail.setMinimumSize(new Dimension(145, 25));
		UtilisateurFormMail.setFont(new Font("Verdana", Font.PLAIN, 13));
		GridBagConstraints gbc_UtilisateurFormMail = new GridBagConstraints();
		gbc_UtilisateurFormMail.anchor = GridBagConstraints.WEST;
		gbc_UtilisateurFormMail.insets = new Insets(0, 0, 5, 0);
		gbc_UtilisateurFormMail.gridx = 1;
		gbc_UtilisateurFormMail.gridy = 1;
		panelDroiteUser2.add(UtilisateurFormMail, gbc_UtilisateurFormMail);
		UtilisateurFormMail.setColumns(12);
		
		JLabel lblNewLabel_2 = new JLabel("Code");
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.weightx = 1.0;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panelDroiteUser2.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		UtilisateurFormCode = new JTextField();
		UtilisateurFormCode.setPreferredSize(new Dimension(145, 25));
		UtilisateurFormCode.setMinimumSize(new Dimension(145, 25));
		UtilisateurFormCode.setFont(new Font("Verdana", Font.PLAIN, 13));
		GridBagConstraints gbc_UtilisateurFormCode = new GridBagConstraints();
		gbc_UtilisateurFormCode.anchor = GridBagConstraints.WEST;
		gbc_UtilisateurFormCode.insets = new Insets(0, 0, 5, 0);
		gbc_UtilisateurFormCode.gridx = 1;
		gbc_UtilisateurFormCode.gridy = 2;
		panelDroiteUser2.add(UtilisateurFormCode, gbc_UtilisateurFormCode);
		UtilisateurFormCode.setColumns(12);
		
		JLabel lblNewLabel_3 = new JLabel("Couleur");
		lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		panelDroiteUser2.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		UtilisateurFormCouleur = new JTextField();
		UtilisateurFormCouleur.setPreferredSize(new Dimension(145, 25));
		UtilisateurFormCouleur.setMinimumSize(new Dimension(145, 25));
		UtilisateurFormCouleur.setFont(new Font("Verdana", Font.PLAIN, 13));
		
		/**
		 * Evènement lors du clic sur le Textfield Couleur 
		 */
		UtilisateurFormCouleur.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				Color c = JColorChooser.showDialog(null, "hello",Color.getHSBColor(255, 153, 153));
				
				if(c!=null || String.valueOf(c) != "null"){
					UtilisateurFormCouleur.setForeground(c);
					UtilisateurFormCouleur.setBackground(c);
				}
			}
		});
		
		GridBagConstraints gbc_UtilisateurFormCouleur = new GridBagConstraints();
		gbc_UtilisateurFormCouleur.anchor = GridBagConstraints.WEST;
		gbc_UtilisateurFormCouleur.insets = new Insets(0, 0, 5, 0);
		gbc_UtilisateurFormCouleur.gridx = 1;
		gbc_UtilisateurFormCouleur.gridy = 3;
		panelDroiteUser2.add(UtilisateurFormCouleur, gbc_UtilisateurFormCouleur);
		UtilisateurFormCouleur.setColumns(12);
		
		JLabel lblNewLabel_4 = new JLabel(" ");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 1;
		panelDroiteUser2.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		JPanel panelGaucheUser2 = new JPanel();
		panelGaucheUser2.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(0, 0, 0)));
		panelGaucheUser2.setMinimumSize(new Dimension(10, 20));
		panelGaucheUser2.setBackground(new Color(119, 136, 153));
		GlobalPanelUser.add(panelGaucheUser2, BorderLayout.WEST);
		GridBagLayout gbl_panelGaucheUser2 = new GridBagLayout();
		gbl_panelGaucheUser2.columnWidths = new int[] {123, 138, 374};
		gbl_panelGaucheUser2.rowHeights = new int[]{52, 112, 110, 112, 55, 0};
		gbl_panelGaucheUser2.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panelGaucheUser2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelGaucheUser2.setLayout(gbl_panelGaucheUser2);
		
		JLabel lblUtilisateur_1 = new JLabel("Nom");
		lblUtilisateur_1.setHorizontalTextPosition(SwingConstants.LEADING);
		lblUtilisateur_1.setFont(new Font("Verdana", Font.PLAIN, 18));
		GridBagConstraints gbc_lblUtilisateur_1 = new GridBagConstraints();
		gbc_lblUtilisateur_1.anchor = GridBagConstraints.WEST;
		gbc_lblUtilisateur_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblUtilisateur_1.gridx = 1;
		gbc_lblUtilisateur_1.gridy = 1;
		panelGaucheUser2.add(lblUtilisateur_1, gbc_lblUtilisateur_1);
		
		UtilisateurFormNom = new JTextField();
		UtilisateurFormNom.setFont(new Font("Verdana", Font.PLAIN, 13));
		UtilisateurFormNom.setMinimumSize(new Dimension(145, 25));
		
		UtilisateurFormNom.setPreferredSize(new Dimension(15, 25));
		UtilisateurFormNom.setColumns(12);
		GridBagConstraints gbc_UtilisateurFormNom = new GridBagConstraints();
		gbc_UtilisateurFormNom.anchor = GridBagConstraints.WEST;
		gbc_UtilisateurFormNom.insets = new Insets(0, 0, 5, 0);
		gbc_UtilisateurFormNom.gridx = 2;
		gbc_UtilisateurFormNom.gridy = 1;
		panelGaucheUser2.add(UtilisateurFormNom, gbc_UtilisateurFormNom);
		
		JLabel lblNom = new JLabel("Prénom");
		lblNom.setFont(new Font("Verdana", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.WEST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 1;
		gbc_lblNom.gridy = 2;
		panelGaucheUser2.add(lblNom, gbc_lblNom);
		
		UtilisateurFormPrenom = new JTextField();
		UtilisateurFormPrenom.setPreferredSize(new Dimension(145, 25));
		UtilisateurFormPrenom.setFont(new Font("Verdana", Font.PLAIN, 13));
		UtilisateurFormPrenom.setHorizontalAlignment(SwingConstants.LEFT);
		UtilisateurFormPrenom.setMinimumSize(new Dimension(145, 25));
		UtilisateurFormPrenom.setColumns(12);
		GridBagConstraints gbc_UtilisateurFormPrenom = new GridBagConstraints();
		gbc_UtilisateurFormPrenom.anchor = GridBagConstraints.WEST;
		gbc_UtilisateurFormPrenom.insets = new Insets(0, 0, 5, 0);
		gbc_UtilisateurFormPrenom.gridx = 2;
		gbc_UtilisateurFormPrenom.gridy = 2;
		panelGaucheUser2.add(UtilisateurFormPrenom, gbc_UtilisateurFormPrenom);
		
		JLabel lblPrenom = new JLabel("T\u00E9l\u00E9phone");
		lblPrenom.setFont(new Font("Verdana", Font.PLAIN, 18));
		GridBagConstraints gbc_lblPrenom = new GridBagConstraints();
		gbc_lblPrenom.anchor = GridBagConstraints.WEST;
		gbc_lblPrenom.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrenom.gridx = 1;
		gbc_lblPrenom.gridy = 3;
		panelGaucheUser2.add(lblPrenom, gbc_lblPrenom);
		
		UtilisateurFormTelephone = new JTextField();
		UtilisateurFormTelephone.setPreferredSize(new Dimension(145, 25));
		UtilisateurFormTelephone.setFont(new Font("Verdana", Font.PLAIN, 13));
	
		UtilisateurFormTelephone.setMinimumSize(new Dimension(145, 25));
		UtilisateurFormTelephone.setColumns(12);
		GridBagConstraints gbc_UtilisateurFormTelephone = new GridBagConstraints();
		gbc_UtilisateurFormTelephone.anchor = GridBagConstraints.WEST;
		gbc_UtilisateurFormTelephone.insets = new Insets(0, 0, 5, 0);
		gbc_UtilisateurFormTelephone.gridx = 2;
		gbc_UtilisateurFormTelephone.gridy = 3;
		panelGaucheUser2.add(UtilisateurFormTelephone, gbc_UtilisateurFormTelephone);
		
		JLabel lblTlphone = new JLabel("");
		lblTlphone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_lblTlphone = new GridBagConstraints();
		gbc_lblTlphone.anchor = GridBagConstraints.EAST;
		gbc_lblTlphone.insets = new Insets(0, 0, 0, 5);
		gbc_lblTlphone.gridx = 1;
		gbc_lblTlphone.gridy = 4;
		panelGaucheUser2.add(lblTlphone, gbc_lblTlphone);
		
		splitPane_2 = new JSplitPane();		
		splitPane_2.setPreferredSize(new Dimension(140, 25));
		tabbedPane.addTab("Planning", null, splitPane_2, null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setAlignmentX(0.0f);
		panel_3.setAlignmentY(0.0f);
		panel_3.setBackground(SystemColor.inactiveCaption);
		splitPane_2.setRightComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_5.setPreferredSize(new Dimension(175, 30));
		panel_5.setBackground(new Color(119, 136, 153));
		panel_3.add(panel_5);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_4.setBackground(new Color(30, 144, 255));
		panel_4.setSize(new Dimension(50, 50));
		panel_4.setMinimumSize(new Dimension(50, 50));
		panel_4.setPreferredSize(new Dimension(50, 50));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(119, 136, 153));
	
		/**
		 * Action lors du clic sur le bouton consulter les semaines
		 */
		JButton btnCreerUneSemaine = new JButton("Consulter\r\n les semaines ");
		btnCreerUneSemaine.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnCreerUneSemaine.setPreferredSize(new Dimension(175, 30));
		panel_6.add(btnCreerUneSemaine);
		
		btnCreerUneSemaine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormSemaines semaines;
				try { 
					semaines = new FormSemaines(main);
					semaines.setVisible(true);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}	      	
			}
		});
		
		JButton btnNewButton = new JButton("Modifier");
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnNewButton.setPreferredSize(new Dimension(175, 30));
		panel_6.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPane.setBackground(SystemColor.inactiveCaption);
	
		JLabel lblAjouterUneSemaine = new JLabel("Ajouter une semaine");
		lblAjouterUneSemaine.setFont(new Font("Verdana", Font.BOLD, 14));
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setMinimumSize(new Dimension(145, 25));
		dateChooser.setFont(new Font("Verdana", Font.PLAIN, 13));
		dateChooser.setPreferredSize(new Dimension(145, 25));
		JButton btnNewButton_4 = new JButton("Ajouter");
		btnNewButton_4.setFont(new Font("Verdana", Font.PLAIN, 10));
		btnNewButton_4.setSize(new Dimension(150, 30));
		btnNewButton_4.setPreferredSize(new Dimension(150, 30));
		
		JButton btnExcel = new JButton("Fichier Excel");
		btnExcel.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnExcel.setPreferredSize(new Dimension(150, 30));
		
		/**
		 * Action lors du clic sur le bouton Fichier Excel
		 */
		btnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				File file = new File("Excel");
				toExcel(table_2, file);
			}
		});
		
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addGap(79)
					.addComponent(lblAjouterUneSemaine)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_4, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(600, Short.MAX_VALUE))
				.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 1076, Short.MAX_VALUE)
				.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 1076, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, gl_panel_5.createSequentialGroup()
					.addContainerGap(42, Short.MAX_VALUE)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 1000, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnExcel, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
					.addGap(34))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnNewButton_4, 0, 0, Short.MAX_VALUE)
						.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE)
							.addComponent(lblAjouterUneSemaine, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnExcel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
					.addGap(24)
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		comboBox.setPreferredSize(new Dimension(145, 25));
		comboBox.setMinimumSize(new Dimension(145, 25));
		comboBox.setFont(new Font("Verdana", Font.BOLD, 13));
		
		/**
		 * Action lors du clic sur le bouton ajouter 
		 */
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(dateChooser.getDate() != null){
					java.util.Date dateFromDateChooser = dateChooser.getDate();
			
					String dateString = String.format("%1$td-%1$tm-%1$tY", dateFromDateChooser);
					System.out.println("La date au format = " + dateString );
					
					int annee = Integer.valueOf(dateString.split("-")[2]);

					DateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
					Date Datetemp = null ;
					try {
						Datetemp = new Date(format2.parse(dateString).getTime());
					} catch (ParseException e1) {
						e1.printStackTrace();
					};
					
					Date date_lundi = new Date (OutilsDate.getDatesFromWeekNumber(annee, OutilsDate.DateSemaine(Datetemp)).get(0).getTime());
					Semaine Semaine1;
					try {
						Semaine1 = new Semaine(2,dateString,null,false);
						Semaine1.InsertionSemaineJour();
						
						String jour_lundi = String.valueOf(OutilsDate.getJour(date_lundi)) ;
						if (OutilsDate.getJour(date_lundi) < 10){
							jour_lundi = "0" + jour_lundi ;
						}
						String mois_lundi = String.valueOf(OutilsDate.getMois(date_lundi)) ;
						if (OutilsDate.getMois(date_lundi) < 10){
							mois_lundi = "0" + mois_lundi ;
						}
						String annee_lundi = String.valueOf(OutilsDate.getAnnee(date_lundi)) ;
						String dateTemp = OutilsDate.DateSemaine(Semaine1.getDate()) + " - " + jour_lundi  + "-" +  mois_lundi  + "-" + annee_lundi ;
						
						int indice = 0 ;
						for(int i = 0; i < comboBox.getItemCount(); i++)
						{
							if(dateTemp.equals(comboBox.getSelectedItem().toString())){
								indice ++;
							}
						}
						if(indice > 0){
							JOptionPane.showMessageDialog(null, "La date sélectionnée existe déjà");
						}
						else {
							comboBox.addItem(dateTemp);
							comboBox.setSelectedItem(dateTemp);
							Con1.ChargementDesSemainesEtJours(AssoProfilId);
							comboBox.getActionListeners()[0].actionPerformed(arg0);
							comboBox.repaint();
						}
					} catch (ClassNotFoundException | SQLException | ParseException e) {
						e.printStackTrace();
					}	
				}	
			}
		});

		
		//Action lors du clic sur le bouton Sauvegarder de l'onglet Planning
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String date_lundi = "0";
			for(int i=0;i<table_2.getRowCount();i++){		
				String dates =  table_2.getValueAt(i, 0).toString();

				int zero =  Integer.valueOf(table_2.getValueAt(i, 1).toString());
				int un =    Integer.valueOf(table_2.getValueAt(i, 2).toString());
				int deux  = Integer.valueOf(table_2.getValueAt(i, 3).toString());
				int trois = Integer.valueOf(table_2.getValueAt(i, 4).toString());
				int quatre= Integer.valueOf(table_2.getValueAt(i, 5).toString());
				int cinq =  Integer.valueOf(table_2.getValueAt(i, 6).toString());
				int six =   Integer.valueOf(table_2.getValueAt(i, 7).toString());
				int sept =  Integer.valueOf(table_2.getValueAt(i, 8).toString());
				int huit =  Integer.valueOf(table_2.getValueAt(i, 9).toString());
				int neuf =  Integer.valueOf(table_2.getValueAt(i, 10).toString());
				int dix =   Integer.valueOf(table_2.getValueAt(i, 11).toString());
				int onze =  Integer.valueOf(table_2.getValueAt(i, 12).toString());
				int douze = Integer.valueOf(table_2.getValueAt(i, 13).toString());
				int treize = Integer.valueOf(table_2.getValueAt(i, 14).toString());
				int quatorze = Integer.valueOf(table_2.getValueAt(i, 15).toString());
				int quinze =   Integer.valueOf(table_2.getValueAt(i, 16).toString());
				int seize =  Integer.valueOf(table_2.getValueAt(i, 17).toString());
				int dixsept =   Integer.valueOf(table_2.getValueAt(i, 18).toString());
				int dixhuit =  Integer.valueOf(table_2.getValueAt(i, 19).toString());
				int dixneuf = Integer.valueOf(table_2.getValueAt(i, 20).toString());
				int vingt =  Integer.valueOf(table_2.getValueAt(i, 21).toString());
				int vingtun =  Integer.valueOf(table_2.getValueAt(i, 22).toString());
				int vingtdeux = Integer.valueOf(table_2.getValueAt(i, 23).toString());
				int vingttrois =   Integer.valueOf(table_2.getValueAt(i, 24).toString());
					
					
				String datestring;
				
					try {
						String dateparse = Semaine.dateLitToIso(dates);
						SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date tempDate2 = new Date(format2.parse(dateparse).getTime());	
						datestring = dateFormat.format(tempDate2); 
									
						String sql = String.format(ReqSQLite.MODIFICATION_JOUR.getRequeteSQL(),zero,un,deux,trois,quatre,cinq,six,sept,huit,neuf,dix,onze,douze,treize,quatorze,quinze,seize,dixsept,dixhuit,dixneuf,vingt,vingtun,vingtdeux,vingttrois,datestring); 
						System.out.println(sql);
						
						SQLiteJDBC.ExecuteRequete(sql);
						System.out.println("requête effectuée");
						
						if (i == 0){
							date_lundi = datestring ;
						}						
					} catch (ParseException | ClassNotFoundException | SQLException e2) {
						e2.printStackTrace();
						System.out.println("problème d'insertion");
					} 		
	            }
			String sql = String.format(ReqSQLite.AFFICHER_CONTENU_JOURTYPE_POUR_UNE_SEMAINE_LUNDI.getRequeteSQL(),date_lundi);
			System.out.println(sql);
			ResultSet rs = null;
			rs = SQLiteJDBC.ExecuteRequeteSelect(sql);
						
			TableModel semaineType = DbUtils.resultSetToTableModel(rs);
			TableModel semaineActuelle = table_2.getModel();
			
			if (!semaineType.equals(semaineActuelle)){
				sql = String.format(ReqSQLite.SEMAINE_PERSONNALISEE.getRequeteSQL(),date_lundi);
				System.out.println(sql);
				try {
					SQLiteJDBC.ExecuteRequete(sql);
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			}
		});

		JLabel lblSemaine = new JLabel("Semaine");
		lblSemaine.setFont(new Font("Verdana", Font.PLAIN, 18));
		
		JLabel label_2 = new JLabel("Formulaire de Planning");
		label_2.setPreferredSize(new Dimension(150, 14));
		label_2.setFont(new Font("Verdana", Font.BOLD, 24));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap(367, Short.MAX_VALUE)
					.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE)
					.addGap(124)
					.addComponent(lblSemaine, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblSemaine)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(16))
		);
		panel_4.setLayout(gl_panel_4);
		panel_5.setLayout(gl_panel_5);
		
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 40));
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		splitPane_2.setLeftComponent(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel panel_2 = new JPanel();
		
		panel_2.setBackground(new Color(119, 136, 153));
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_5 = new JLabel("Groupes");
		lblNewLabel_5.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		lblNewLabel_5.setOpaque(true);
		lblNewLabel_5.setPreferredSize(new Dimension(180, 16));
		lblNewLabel_5.setMinimumSize(new Dimension(140, 14));
		lblNewLabel_5.setBackground(new Color(30, 144, 255));
		lblNewLabel_5.setFont(new Font("Verdana", Font.BOLD, 16));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_5, BorderLayout.CENTER);
		
		
		ArrayList<String> ListGroupe = new ArrayList<String>();
		for (Iterator<Groupe> i = AssoGroupe.values().iterator() ; i.hasNext();){
			Groupe currentGroupe = new Groupe();
			ListGroupe.add(currentGroupe.getNom());
			i.next();
	      }
		 
		JList<String> list_2 = new JList<String>(modelListGroupe);
		list_2.setFont(new Font("Verdana", Font.BOLD, 13));
		list_2.setSelectionForeground(new Color(255, 255, 255));
		list_2.setSelectionBackground(new Color(0, 0, 0));
	
		/**
		 * Evènement lors du clic sur le nom du groupe
		 */
		list_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if(list_2.getSelectedValue()!=null){
					Hashtable<String,Long> listeGroupe = SQLiteJDBC.getlistgroupe(AssoGroupe);
					Long GroupeID = listeGroupe.get(list_2.getSelectedValue());
					GroupeSelected = AssoGroupe.get(GroupeID);
					System.out.println("Identifiant du groupe = " + GroupeSelected.getIdentifiant());
					System.out.println("Identifiant du groupe = " + GroupeSelected.getNom());
				}
			}
		});
		
		list_2.setMinimumSize(new Dimension(180, 460));
		list_2.setBackground(new Color(211, 211, 211));
		list_2.setDragEnabled(true);
		list_2.setVisibleRowCount(1);
		list_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_2.setValueIsAdjusting(true);
		list_2.setPreferredSize(new Dimension(180, 460));
		panel_2.add(list_2, BorderLayout.SOUTH);
		
		JPanel panel_13 = new JPanel();
		panel_2.add(panel_13, BorderLayout.EAST);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane_3 = new JSplitPane();
		
		tabbedPane.addTab("Profil", null, splitPane_3, null);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		splitPane_3.setLeftComponent(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.BLACK);
		panel_7.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JLabel lblProfil = new JLabel("Profils");
		lblProfil.setBorder(new MatteBorder(0, 0, 1, 1, (Color) new Color(0, 0, 0)));
		lblProfil.setBackground(new Color(30, 144, 255));
		lblProfil.setOpaque(true);
		lblProfil.setInheritsPopupMenu(false);
		lblProfil.setMinimumSize(new Dimension(140, 14));
		lblProfil.setFont(new Font("Verdana", Font.BOLD, 16));
		lblProfil.setHorizontalAlignment(SwingConstants.CENTER);
		panel_9.add(lblProfil, BorderLayout.CENTER);
		
		modeListProfil = new DefaultListModel<String>();
		listProfil = new JList<String>(modeListProfil);		
		listProfil.setFont(new Font("Verdana", Font.BOLD, 13));
		listProfil.setSelectionForeground(new Color(245, 255, 250));
		listProfil.setForeground(new Color(0, 0, 0));
		listProfil.setSelectionBackground(new Color(0, 0, 0));
		listProfil.setValueIsAdjusting(true);
		listProfil.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		/**
		 * Ajout des éléments du tableau AssoProfilId dans le modèl listProfil
		 */
		for (String elem : SQLiteJDBC.getListeProfil(AssoProfilId)){
			modeListProfil.addElement(elem);
		}
		
		/**
		 * Evènement lors du clic sur un profil dans l'onglet Profil 
		 */
		listProfil.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0){
				Hashtable<String,Long> lalistProfil = SQLiteJDBC.getlistprofil(AssoProfilId);
				if(listProfil.getSelectedValue() != null){
					Long ProfilSelected = lalistProfil.get(listProfil.getSelectedValue());
					scrollPane_1.setVisible(false);
					
						Profil Profiltempe = AssoProfilId.get(ProfilSelected);
						System.out.println("Identifiant du profil = " + Profiltempe.getIdentifiant());
						System.out.println("Nom du profil = " + Profiltempe.getNom());
						
						textNomProfil.setText(Profiltempe.getNom());
						int couleur = Integer.parseInt(Profiltempe.getCouleur());
						Color c = new Color(couleur);
						textCouleurProfil.setBackground(c);
						
						if(ProfilSelected != null){
							scrollPane_1.setVisible(true);
						}
						try {
							UpdateTable();	
						} catch (ClassNotFoundException | ParseException | SQLException e) {
							e.printStackTrace();
						}
					}
				}

			/**
			 * Mettre à jour la table après avoir cliqué sur un nom de profil
			 * @throws ParseException
			 * @throws ClassNotFoundException
			 * @throws SQLException
			 */
			public void UpdateTable() throws ParseException, ClassNotFoundException, SQLException{
				Hashtable<String,Long> lalistProfil = SQLiteJDBC.getlistprofil(AssoProfilId);
				Long ProfilSelected = lalistProfil.get(listProfil.getSelectedValue());
				
				String sql = String.format(ReqSQLite.AFFICHER_CONTENU_JOURTYPE_POUR_UNE_SEMAINE.getRequeteSQL(),ProfilSelected);
				System.out.println(sql);
				ResultSet rs = null;
				rs = SQLiteJDBC.ExecuteRequeteSelect(sql);
				
				scrollPane_1.setViewportView(table_3);
				table_3.setModel(DbUtils.resultSetToTableModel(rs));
				
				table_3.setRowHeight(0,33);
				table_3.setRowHeight(1,33);
				table_3.setRowHeight(2,33);
				table_3.setRowHeight(3,33);
				table_3.setRowHeight(4,33);
				table_3.setRowHeight(5,33);
				table_3.setRowHeight(6,33);	
				table_3.getColumnModel().getColumn(0).setPreferredWidth(75);
				for(int x = 1; x<=24; x++){
					table_3.getColumnModel().getColumn(x).setPreferredWidth(20);
				}
				
			}	
		});
		
		listProfil.setSize(new Dimension(400, 0));
		listProfil.setBackground(new Color(211, 211, 211));
		listProfil.setPreferredSize(new Dimension(150, 460));
		panel_9.add(listProfil, BorderLayout.SOUTH);
		
		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_9.add(panel_14, BorderLayout.EAST);
		panel_14.setLayout(new BorderLayout(0, 0));
		
		/**
		 * Evènement lors du clic sur le bouton "-" => Suppression d'un ProfilType
		 */
		JButton button_5 = new JButton("-");
		button_5.setPreferredSize(new Dimension(41, 34));
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(listProfil.getSelectedValue()!=null){
					try {
						Hashtable<String,Long> ListProfils= SQLiteJDBC.getlistprofil(FormMain.AssoProfilId);
						Long ProfilSelected = ListProfils.get(listProfil.getSelectedValue()); // recupere l'id du profil
						System.out.println(ProfilSelected + "ID DU PROFIL");
						
						Profil NewProfil = FormMain.AssoProfilId.get(ProfilSelected);
						String NomProfil = NewProfil.getNom(); // recupère le nom du profil
						System.out.println("Nom Profil = " + NomProfil);	
							
						long id_semaine = 0;
					
							while(id_semaine !=0){
								for(int i = 0 ; i < NewProfil.getListeSemaineType().size(); i++){
									id_semaine = NewProfil.getListeSemaineType().get(i).getId_semaine();
								}
							
							}

						String sql1 = String.format(ReqSQLite.SUPPRIMER_JOURTYPE.getRequeteSQL(),id_semaine);
						System.out.println(sql1);
						SQLiteJDBC.ExecuteRequete(sql1);
						
						String sql2 = String.format(ReqSQLite.SUPPRIMER_SEMAINETYPE.getRequeteSQL(),ProfilSelected);
						System.out.println(sql2);
						SQLiteJDBC.ExecuteRequete(sql2);
						
						String sql3 = String.format(ReqSQLite.SUPPRIMER_PROFIL.getRequeteSQL(),NewProfil.getIdentifiant());
						System.out.println(sql3);
						SQLiteJDBC.ExecuteRequete(sql3);
					
						AssoProfilId.remove(ProfilSelected);
						modeListProfil.removeElement(NomProfil);
						
						listProfil.repaint();
						scrollPane_1.setVisible(false);
						
					}catch (ClassNotFoundException | SQLException e1) {	
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(frame, "Profil Supprimé.");
				}
				else{
					JOptionPane.showMessageDialog(frame, "Sélectionner un Profil");
				}
			}
		});
		panel_14.add(button_5, BorderLayout.NORTH);
		button_5.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		
		JButton button_1 = new JButton("+");
		button_1.setPreferredSize(new Dimension(42, 32));
		panel_14.add(button_1, BorderLayout.SOUTH);
		button_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		
		/**
		 * Evènement lors du clic sur le bouton "+" 
		 */
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormProfilType NewProfilType;
				try {
					if(textNomProfil.getText().equals("") || String.valueOf(textCouleurProfil.getBackground().getRGB()).equals("")){
						JOptionPane.showMessageDialog(frame, "Insertion impossible vérifiez vos informations");
				}
					else{
						int vartemp =0;
						for(int i = 0 ; i < getModeListProfil().getSize(); i++){
							if(getModeListProfil().get(i).equals(textNomProfil.getText())){
								vartemp ++;
							}
						}
						
						if(vartemp > 0){
								System.out.println("Le nom de profil existe déjà");
								JOptionPane.showMessageDialog(null, "Le nom de profil existe déjà");
						}
						else{
							NewProfilType = new FormProfilType();
							NewProfilType.setVisible(true);
						}
					}
					
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(SystemColor.inactiveCaption);
		splitPane_3.setRightComponent(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_6 = new JLabel("Formulaire de Profil");
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setBackground(new Color(30, 144, 255));
		lblNewLabel_6.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblNewLabel_6.setPreferredSize(new Dimension(50, 50));
		lblNewLabel_6.setFont(new Font("Verdana", Font.BOLD, 26));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_8.add(lblNewLabel_6, BorderLayout.NORTH);
		
		JPanel Profil_Bouton_save = new JPanel();
		Profil_Bouton_save.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
		Profil_Bouton_save.setBackground(new Color(119, 136, 153));
		panel_8.add(Profil_Bouton_save, BorderLayout.SOUTH);
		
		JButton Sauvegarde = new JButton("Modifier");
		Sauvegarde.setFont(new Font("Verdana", Font.PLAIN, 11));
		Sauvegarde.setPreferredSize(new Dimension(150, 30));
		Sauvegarde.setBackground(SystemColor.text);
		Sauvegarde.addActionListener(new ActionListener() {
			
			/**
			 * Evènement lors du clic sur le textField Couleur
			 */
			public void actionPerformed(ActionEvent e) {
				if(listProfil.getSelectedValue() != null ){	
					if(textNomProfil.getText().equals("") || String.valueOf(textCouleurProfil.getBackground().getRGB()).equals("")){
						JOptionPane.showMessageDialog(frame, "Insertion Impossible vérifiez vos informations");
					}
					else{
						Hashtable<String,Long> TemplistProfil = SQLiteJDBC.getlistprofil(AssoProfilId);
						Long idSelected = TemplistProfil.get(listProfil.getSelectedValue());			
						System.out.println(idSelected);
					
						String sql = String.format(ReqSQLite.MODIFICATION_PROFIL.getRequeteSQL(),textNomProfil.getText(),textCouleurProfil.getBackground().getRGB(),idSelected);
						System.out.println(sql);
						Profil tempProfil = null;
						try {
							
							SQLiteJDBC.ExecuteRequete(sql);
							tempProfil = new Profil(idSelected,textNomProfil.getText(),String.valueOf(textCouleurProfil.getBackground().getRGB()));
							modeListProfil.set(listProfil.getSelectedIndex(), tempProfil.getNom());
							AssoProfilId.put(idSelected,tempProfil);	
							
						} catch (NumberFormatException | ClassNotFoundException | SQLException e2) {
							JOptionPane.showMessageDialog(frame, "Problème de modification vérifiez vos données");
							e2.printStackTrace();
						}
						JOptionPane.showMessageDialog(frame, "Modification réussie");
						}
					}
				}
			});
		Profil_Bouton_save.add(Sauvegarde);
		
		panel_10 = new JPanel();
		panel_10.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(0, 0, 0)));
		panel_10.setFocusCycleRoot(true);
		panel_10.setFocusTraversalPolicyProvider(true);
		panel_10.setAutoscrolls(true);
		panel_10.setIgnoreRepaint(true);
		panel_10.setInheritsPopupMenu(true);
		panel_10.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_10.setBackground(new Color(119, 136, 153));
		panel_8.add(panel_10, BorderLayout.CENTER);
		GridBagLayout gbl_panel_10 = new GridBagLayout();
		gbl_panel_10.columnWidths = new int[]{123, 138, 352, 146, 290, 0};
		gbl_panel_10.rowHeights = new int[]{53, 113, 113, 113, 51, 0};
		gbl_panel_10.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_10.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_10.setLayout(gbl_panel_10);
		
		JLabel lblNom_1 = new JLabel("Nom");
		lblNom_1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblNom_1.setBackground(new Color(30, 144, 255));
		lblNom_1.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblNom_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNom_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNom_1.setAlignmentX(0.5f);
		GridBagConstraints gbc_lblNom_1 = new GridBagConstraints();
		gbc_lblNom_1.anchor = GridBagConstraints.WEST;
		gbc_lblNom_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblNom_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom_1.gridx = 1;
		gbc_lblNom_1.gridy = 1;
		panel_10.add(lblNom_1, gbc_lblNom_1);
		
		textNomProfil = new JTextField();
		textNomProfil.setPreferredSize(new Dimension(145, 25));
		textNomProfil.setFont(new Font("Verdana", Font.PLAIN, 13));
		textNomProfil.setMinimumSize(new Dimension(145, 25));
		textNomProfil.setColumns(12);
		GridBagConstraints gbc_textNomProfil = new GridBagConstraints();
		gbc_textNomProfil.anchor = GridBagConstraints.WEST;
		gbc_textNomProfil.insets = new Insets(0, 0, 5, 5);
		gbc_textNomProfil.gridx = 2;
		gbc_textNomProfil.gridy = 1;
		panel_10.add(textNomProfil, gbc_textNomProfil);
		
		Label label = new Label("Couleur");
		
		label.setFocusable(false);
		label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		label.setBackground(new Color(119, 136, 153));
		label.setFont(new Font("Verdana", Font.PLAIN, 18));
		
		label.setAlignment(Label.CENTER);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.fill = GridBagConstraints.VERTICAL;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 3;
		gbc_label.gridy = 1;
		panel_10.add(label, gbc_label);
		
		textCouleurProfil = new JTextField();
		textCouleurProfil.setFont(new Font("Verdana", Font.PLAIN, 13));
		
		/**
		 * Evènement lors du choix de la couleur
		 */
		textCouleurProfil.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {		
				Color c = JColorChooser.showDialog(null, "hello",Color.getHSBColor(255, 153, 153));
					if(c!=null || String.valueOf(c) != "null"){
						textCouleurProfil.setForeground(c);
						textCouleurProfil.setBackground(c);				
					}	
			}
		});
		
		textCouleurProfil.setMinimumSize(new Dimension(145, 25));
		textCouleurProfil.setPreferredSize(new Dimension(145, 25));
		textCouleurProfil.setColumns(12);
		GridBagConstraints gbc_textCouleurProfil = new GridBagConstraints();
		gbc_textCouleurProfil.anchor = GridBagConstraints.WEST;
		gbc_textCouleurProfil.insets = new Insets(0, 0, 5, 0);
		gbc_textCouleurProfil.gridx = 4;
		gbc_textCouleurProfil.gridy = 1;
		panel_10.add(textCouleurProfil, gbc_textCouleurProfil);
		
		JPanel panel_17 = new JPanel();
		panel_17.setAutoscrolls(true);
		panel_17.setFocusTraversalPolicyProvider(true);
		panel_17.setFocusCycleRoot(true);
		panel_17.setBackground(new Color(119, 136, 153));
		GridBagConstraints gbc_panel_17 = new GridBagConstraints();
		gbc_panel_17.gridheight = 3;
		gbc_panel_17.gridwidth = 5;
		gbc_panel_17.fill = GridBagConstraints.BOTH;
		gbc_panel_17.gridx = 0;
		gbc_panel_17.gridy = 2;
		panel_10.add(panel_17, gbc_panel_17);
	
		UIManager.put("Synthetica.tabbedPane.keepOpacity", true);

		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setFont(new Font("Dialog", Font.PLAIN, 11));
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setForeground(new Color(30, 144, 255));
		scrollPane_1.setPreferredSize(new Dimension(860, 254));
		panel_17.add(scrollPane_1);
		scrollPane_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPane_1.setBackground(new Color(30, 144, 255));
		
		table_3 = new JTable();
		table_3.setEnabled(false);
		table_3.setFont(new Font("Dialog", Font.PLAIN, 11));
		table_3.setSelectionForeground(new Color(30, 144, 255));
		table_3.setSelectionBackground(new Color(30, 144, 255));
		table_3.setGridColor(Color.WHITE);
		
		/**
		 * Evènement ajoute la couleur du groupe dans la table
		 */
		table_3.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			public Component getTableCellRendererComponent(JTable table,
	                 Object value, boolean isSelected, boolean hasFocus,
	                 int row, int column) {
	             super.getTableCellRendererComponent(table, value, isSelected,
	                     hasFocus, row, column);
			             if (column>=2  && GroupeSelected != null) {
			            	 if(table_3.getValueAt(row, column).toString() != null){
			            		 int id = Integer.parseInt(table_3.getValueAt(row, column).toString());
			            		 
				            	 if (id != 0){
										long truc = (long)id ;
										Groupe group = AssoGroupe.get(truc);
										String color = group.getCouleur() ;
										int couleur = Integer.parseInt(color);					
										setBackground(new Color(couleur));
								 }
				            	 else{
				            		 setBackground(Color.WHITE);
				            	 }		
			            	 }
			             }
			        table_3.repaint();
	             	return this ;
	            }
	         });
		table_3.setFocusable(false);
		table_3.setFocusTraversalKeysEnabled(false);
		table_3.setRequestFocusEnabled(false);
		table_3.setRowSelectionAllowed(false);
		scrollPane_1.setViewportView(table_3);
		GridBagLayout gbl_panel_11 = new GridBagLayout();
		gbl_panel_11.columnWidths = new int[] {150, 150};
		gbl_panel_11.rowHeights = new int[] {92, 92, 92, 92, 0};
		gbl_panel_11.columnWeights = new double[]{0.0, 0.0};
		gbl_panel_11.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	
	scrollPane.setViewportView(table_1);
	
	
	
	table_2 = new JTable();
	table_2.setEnabled(false);
	table_2.setFocusTraversalKeysEnabled(false);
	table_2.setFocusable(false);
	table_2.setUpdateSelectionOnSort(false);
	table_2.setRequestFocusEnabled(false);
	table_2.setRowSelectionAllowed(false);
	
	/**
	 * Evènement Ajout l'identifiant du Groupe dans la table
	 */
	table_2.addMouseListener(new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent arg0) {
			super.mouseClicked(arg0);
		
			int column = table_2.columnAtPoint(arg0.getPoint());
			int row = table_2.rowAtPoint(arg0.getPoint());
			if (arg0.getButton() == MouseEvent.BUTTON3  && column >= 1){
				table_2.setValueAt('0', row, column);
			}
			else if (GroupeSelected != null && column >= 1 ){
				table_2.setValueAt(GroupeSelected.getIdentifiant(), row, column);
			}				
		}	
	});
	
	/**
	 * Evènement, ajoute la couleur du groupe dans la table
	 */
	 table_2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
		private static final long serialVersionUID = 1L;
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
             super.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);
             		if (column>=1 && GroupeSelected != null) {
		            	 if(table_2.getValueAt(row, column).toString() != null){
		            		 int id = Integer.parseInt(table_2.getValueAt(row , column).toString());
			            	 if (id != 0){
									long truc = (long)id ;
									Groupe group = AssoGroupe.get(truc);
									String color = group.getCouleur() ;
									int couleur = Integer.parseInt(color);					
									setBackground(new Color(couleur));
								}
								else{
									setBackground(Color.WHITE);
								}	
		            	 }
		             }
				table_2.repaint();
             	return this ;
            }
         });
	 
	 
     
	/**
	 * Action qui met a jour la table
	 */
	comboBox.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
				try {
					UpdateTable();
				} catch (ClassNotFoundException | ParseException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		@SuppressWarnings("deprecation")
		public void UpdateTable() throws ParseException, ClassNotFoundException, SQLException{
			String numSemaine =(String)comboBox.getSelectedItem();
			if(numSemaine != null){
				System.out.println(numSemaine + "ici");
				String tempDate = numSemaine.split(" - ")[1];
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
				Date tempDate2 = new Date(format.parse(tempDate).getTime());
				
				ArrayList<Date> tempJours = OutilsDate.getDatesFromWeekNumber(OutilsDate.getAnnee(tempDate2),OutilsDate.DateSemaine(tempDate2));
				String sql = String.format(ReqSQLite.AFFICHER_CONTENU_JOUR_POUR_UNE_SEMAINE.getRequeteSQL(),tempJours.get(0));
				
				System.out.println("La date sélectionnée est = " + numSemaine );
				System.out.println(sql);
				
				ResultSet rs = null;
				rs = SQLiteJDBC.ExecuteRequeteSelect(sql);
				scrollPane.setViewportView(table_2);
				
				table_2.setModel(DbUtils.resultSetToTableModel(rs));
				
				for(int i = 0; i< 7; i++){
					String date1 = (String) table_2.getValueAt(i, 0);
					Date date2 = new Date(format2.parse(date1).getTime());
					table_2.setValueAt(date2.toLocaleString().split("00:00:00")[0], i, 0);
				}
				
				table_2.setRowHeight(0,42);
				table_2.setRowHeight(1,42);
				table_2.setRowHeight(2,42);
				table_2.setRowHeight(3,42);
				table_2.setRowHeight(4,42);
				table_2.setRowHeight(5,42);
				table_2.setRowHeight(6,42);

				table_2.getColumnModel().getColumn(0).setPreferredWidth(95);
				for(int x = 1; x<=24; x++){
					table_2.getColumnModel().getColumn(x).setPreferredWidth(21);
				}
			}
			
			}
		});
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		scrollPane.setViewportView(table_2);
		frame.getContentPane().add(tabbedPane, "name_436195724291395");
	}
	
	
	public void toExcel(JTable table, File file){
	    try{
	        TableModel model = table.getModel();
	        FileWriter excel = new FileWriter(file);
	        for(int i = 0; i < model.getColumnCount(); i++){
	            excel.write(model.getColumnName(i) + "\t");
	    }
	        excel.write("\n");
	        for(int i=0; i< model.getRowCount(); i++) {
	            for(int j=0; j < model.getColumnCount(); j++) {
	                excel.write(model.getValueAt(i,j).toString()+"\t");
	            }
	            excel.write("\n");
	        }    
	        excel.close();
	    }catch(IOException e){ System.out.println(e); }
	}
	
	//Getter/Setter
	public static Hashtable<Long, Profil> getAssoProfilId() {
		return AssoProfilId;
	}


	public static void setAssoProfilId(Hashtable<Long, Profil> assoProfilId) {
		AssoProfilId = assoProfilId;
	}
	
	public JList<String> getListProfil() {
		return getListProfil();
	}
	
	public void setListProfil(JList<String> listProfil) {
		this.listProfil = listProfil;
	}
	
}

