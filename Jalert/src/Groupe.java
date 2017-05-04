import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Groupe {

		private long identifiant ;
		private String nom;
		private int edroit;
		private String couleur;
		ArrayList<Utilisateur> listePersonnes ;
		
		/**
		 * Constructeur par défaut
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		public Groupe() throws ClassNotFoundException, SQLException {	
			this.identifiant = RecupereIdGroupe() + 1 ; ;
			this.nom = "Aucune"; 
			this.edroit = 0;
			this.couleur = "#00000" ;
			this.listePersonnes = new ArrayList<Utilisateur>();
			System.out.println("\n"+ "Création d'une équipe sans parametre");
		}
	
		/**
		 * Constructeur avec des paramètres
		 * @param Pid
		 * @param PnomGroupe
		 * @param PdroitAlerte
		 * @param Pcouleur
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		public Groupe(long Pid, String PnomGroupe,int PdroitAlerte, String Pcouleur) throws ClassNotFoundException, SQLException {	
			this.identifiant = Pid ;
			this.nom = PnomGroupe;
			this.edroit = PdroitAlerte ;
			this.couleur = Pcouleur;
			this.listePersonnes = new ArrayList<Utilisateur>();
			System.out.println("\n"+ "Création d'un groupe");
		}	
		
		
		/**
		 * Méthode qui récupère le dernier id de la table Groupe 
		 * Et retourne l'id qu'il a récupéré en base de données
		 * @return ai
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		public static long RecupereIdGroupe() throws ClassNotFoundException, SQLException{
			String sql = String.format(ReqSQLite.RECUPERER_LAST_ID_GROUPE.getRequeteSQL()); //("SELECT last_insert_rowid() as last_id FROM JOUR;")
			
			long ai = -1 ;
			try {
				ResultSet resultsJours = SQLiteJDBC.ExecuteRequeteSelect(sql);
				ai = resultsJours.getLong(1);
			} catch (SQLException e) {
				
				e.printStackTrace();
				ai =1;
			}
			System.out.println(ai);
			return ai;
			
		}
	
	
		/**
		 * Fonction qui permet de supprimer un utilisateur de l'arraylist des utilisateurs.
		 * @param Ppersonne
		 * @throws SQLException
		 */
		public void SuppUtilisateurGroupe(Utilisateur Ppersonne) throws SQLException
		{
			this.listePersonnes.remove(Ppersonne);
			System.out.println(Ppersonne.getPrenom() + Ppersonne.getNom() + " Supprimé de l'arrayList");
			
			String sql = String.format(ReqSQLite.SUPPRESSION_UTILISATEUR.getRequeteSQL(),Ppersonne.getNom(),Ppersonne.getPrenom());
			try{
				SQLiteJDBC.ExecuteRequete(sql);
				System.out.println("Suppresion de " + Ppersonne.getPrenom() + Ppersonne.getNom()+ " de la base de donnée");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * Getter/Setter
		 */
		public void addListePersonnes(Utilisateur AddPersonnes) {
			this.listePersonnes.add(AddPersonnes);
		}
		
		public void removeListePersonnes(Utilisateur AddPersonnes) {
			this.listePersonnes.remove(AddPersonnes);
		}
		
		public ArrayList<Utilisateur> getListePersonnes() {
			return listePersonnes;
		}
		
		public long getIdentifiant() {
			return identifiant;
		}
		
		public String getNom() {
			return nom ;
		}
		
		public int getEdroit() {
			return edroit ;
		}
		
		public String getCouleur() {
			return couleur ;
		}
		
		public void setNom(String nom){
			this.nom = nom; 
		}
		public void setIdentifiant(long id) {
			this.identifiant = id;
	}
}



	

