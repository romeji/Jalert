import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class FormUserGroupe extends JDialog {
	
	
	private static final long serialVersionUID = 1L;
	public static DefaultComboBoxModel<String> modelComboUser;
	public static  DefaultComboBoxModel<String> modelListeGroupee;
	
	public JList<Object> list ;
	private DefaultListModel<Object> modellistUser;

	public FormUserGroupe() throws ClassNotFoundException, SQLException {
		
		
	
		setSize(new Dimension(440, 313));
		setModal(true);
		setResizable(false);
		setLocationRelativeTo(null);
		
		setPreferredSize(new Dimension(500, 500));
		
		getContentPane().setBackground(new Color(112, 128, 144));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(30, 144, 255));
		
		JLabel lblNewLabel = new JLabel("Groupe et Utilisateur");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(112, 128, 144));
		
		JButton btnNewButton = new JButton("Retour au planning");
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormUserGroupe.this.dispose();
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap(383, Short.MAX_VALUE)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
					.addGap(39))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(btnNewButton)
					.addContainerGap(27, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		modellistUser = new DefaultListModel<Object>();
		list = new JList<Object>(modellistUser);
		list.setFont(new Font("Verdana", Font.PLAIN, 12));
	
		/**
		 * Ajout des groupes au modèle
		 */
		modelListeGroupee = new DefaultComboBoxModel<String>();
		for (String elem : SQLiteJDBC.getListeGroupe(FormMain.AssoGroupe))
			modelListeGroupee.addElement(elem);
		
		JComboBox<String> comboBox = new JComboBox<String>(modelListeGroupee);
		comboBox.setFont(new Font("Verdana", Font.PLAIN, 11));
		
		/**
		 * Evènement lors du clic sur la liste déroulante 
		 */
		comboBox.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent  e1) {
				
				modellistUser.clear();
				Hashtable<String,Long> listgroupe = SQLiteJDBC.getlistgroupe(FormMain.AssoGroupe);
				
				Long GroupeSelected = listgroupe.get(comboBox.getSelectedItem());
				System.out.println(GroupeSelected);
				
				Groupe Groupetemp;
				try {
					Groupetemp = new Groupe();
					Groupetemp = FormMain.AssoGroupe.get(GroupeSelected);
				
					ArrayList<String> ListePersonneDuGroupe = new ArrayList<String>();

					
					for(int i = 0; i<Groupetemp.listePersonnes.size();i++){
					    	ListePersonneDuGroupe.add(Groupetemp.listePersonnes.get(i).getPrenom()+" "+Groupetemp.listePersonnes.get(i).getNom());     
					        System.out.println(Groupetemp.listePersonnes.get(i).getPrenom() +" "+Groupetemp.listePersonnes.get(i).getNom() );
					        modellistUser.addElement(Groupetemp.listePersonnes.get(i).getPrenom() +" "+Groupetemp.listePersonnes.get(i).getNom() );
					        
					  }
					System.out.println(Groupetemp.listePersonnes.toString());
					
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, 200));
		
		
		modelComboUser = new DefaultComboBoxModel<String>();
		JComboBox<String> comboBox_1 = new JComboBox<String>(modelComboUser);
		comboBox_1.setFont(new Font("Verdana", Font.PLAIN, 11));
			
		for (String elem : SQLiteJDBC.getListeUtilisateurs(FormMain.AssoUtilisateur))
		modelComboUser.addElement(elem);
		
		JLabel lblSelectionnerUnUtilisateur = new JLabel("Selectionner un Utilisateur :\r\n\r\n");
		lblSelectionnerUnUtilisateur.setFont(new Font("Verdana", Font.BOLD, 10));
		
		JButton btnNewButton_1 = new JButton("+");
		
		
		/**
		 * Evènement lors du clic sur le bouton "+" => ajout d'un utilisateur dans un groupe
		 */
		btnNewButton_1.addMouseListener(new MouseAdapter() {		
			public void mouseClicked(MouseEvent arg0) {
				if(comboBox_1.getSelectedItem() !=null && comboBox.getSelectedItem()!=null){
					System.out.println(comboBox_1.getSelectedItem());
					System.out.println(comboBox.getSelectedItem());
					Hashtable<String,Long> listuser = SQLiteJDBC.getlistuser(FormMain.AssoUtilisateur);
					Hashtable<String,Long> listgroupe = SQLiteJDBC.getlistgroupe(FormMain.AssoGroupe);
					
					Long UserSelected = listuser.get(comboBox_1.getSelectedItem());
					Long GroupeSelected = listgroupe.get(comboBox.getSelectedItem());
					System.out.println("Identifiant du groupe selectionné = " + GroupeSelected);
					System.out.println("Identifiant de l'utilisateur selectionné = " + GroupeSelected);
					
					String sql = String.format(ReqSQLite.INSERTION_ID_TABLE_APPARTIENT.getRequeteSQL(),UserSelected,GroupeSelected,UserSelected,GroupeSelected);
						try {
							SQLiteJDBC.ExecuteRequete(sql);
							
							int indice = 0;
							for(int i =0; i<modellistUser.getSize(); i++){
								
								if(modellistUser.getElementAt(i).toString().split(" ")[1].equals(modelComboUser.getSelectedItem().toString())){
									indice ++;
								}
							}
						
							if(indice > 0){
								JOptionPane.showMessageDialog(null, "La personne fait déjà partie du groupe");
							}
							else {
								FormMain.AssoGroupe.get(GroupeSelected).addListePersonnes(FormMain.AssoUtilisateur.get(UserSelected));
								modellistUser.addElement(FormMain.AssoUtilisateur.get(UserSelected).getPrenom()+ " " + FormMain.AssoUtilisateur.get(UserSelected).getNom());
								System.out.println(FormMain.AssoUtilisateur.get(UserSelected).getNom() + " " + FormMain.AssoUtilisateur.get(UserSelected).getPrenom());
								JOptionPane.showMessageDialog(null, "Insertion effectuée");
							}
						
						
						
						} catch (ClassNotFoundException | SQLException e) {
							JOptionPane.showMessageDialog(null, "Problème d'ajout de cet utilisateur");
							e.printStackTrace();
						}	
				}
				
			}
		});
		
		/**
		 * Evènement lors du clic sur le bouton "-" => suppression de l'utilisateur du groupe
		 */
		JButton button = new JButton("-");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedValue()!=null)
				{
					try {
						Hashtable<String,Long> listUtilisateur= SQLiteJDBC.getlistuser(FormMain.AssoUtilisateur);
						String GroupeSelected = (String) comboBox.getSelectedItem();
						String UserSelected =(String)list.getSelectedValue();
						String split = UserSelected.split(" ")[1];
						String sql = String.format(ReqSQLite.AFFICHER_ID_GROUPE.getRequeteSQL(),GroupeSelected);
						ResultSet rs = SQLiteJDBC.ExecuteRequeteSelect(sql);
						
						long idgroupe = rs.getInt("id_groupe");
						long iduser = listUtilisateur.get(split);
						System.out.println("Identifiant du groupe selectionné = " + idgroupe);
						System.out.println("Identifiant de l'utilisateur selectionné = " + iduser);
						
						sql = String.format(ReqSQLite.SUPPRIMER_TABLE_APPARTIENT_UTILISATEUR_IDGROUPE.getRequeteSQL(),iduser,idgroupe);
						SQLiteJDBC.ExecuteRequete(sql);
						System.out.println(sql);
						
						FormMain.AssoGroupe.get(idgroupe).removeListePersonnes(FormMain.AssoUtilisateur.get(iduser));
						modellistUser.removeElement(UserSelected);
						JOptionPane.showMessageDialog(null, "Utilisateur détaché du groupe");
						
						} catch (ClassNotFoundException | SQLException e1) {
							JOptionPane.showMessageDialog(null, "Impossible de détacher cet utilisateur de son groupe");
							e1.printStackTrace();
						}	
				}
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(37)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
							.addGap(15)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(button, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
								.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addComponent(lblSelectionnerUnUtilisateur))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
					.addGap(50))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(lblSelectionnerUnUtilisateur)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(1))
		);

		scrollPane.setViewportView(list);
		getContentPane().setLayout(groupLayout);

	}
}
