import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Profil {
			
		private long identifiant;
		private String nom ;
		private String couleur ;
		private ArrayList<Semaine> listeSemaines ;
		private ArrayList<SemaineType> listeSemainesType ;
		 
		/**
		 * Constructeur par défaut
		 */
		public Profil(){		
			System.out.println("\n"+"Création d'un planning vide");
			this.listeSemaines = new ArrayList<Semaine>();
			this.identifiant = 0 ; 
			this.nom = "Aucun";	
		}
		
		/**
		 * Constructeur avec des paramètres
		 * @param Pid
		 * @param Pnom
		 * @param pcouleur
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		public Profil(long Pid,  String Pnom, String pcouleur) throws ClassNotFoundException, SQLException {	
			this.identifiant = Pid ;
			this.nom = Pnom;
			this.couleur = pcouleur ;
			this.listeSemaines = new ArrayList<Semaine>();
			this.listeSemainesType = new ArrayList<SemaineType>();
			System.out.println("Création profil  Nom = "+nom+" ; couleur = "+couleur);
		}
		
		
		/**
		 * Méthode qui récupère le dernier id de la table Groupe 
		 * @return ai
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		public static long RecupereIdProfil() throws ClassNotFoundException, SQLException{
			String sql = String.format(ReqSQLite.RECUPERER_LAST_ID_PROFIL.getRequeteSQL());
			long ai = -1 ;
			try {
				ResultSet resultsProfil = SQLiteJDBC.ExecuteRequeteSelect(sql);
				ai = resultsProfil.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
				ai = 1;
			}
			return ai;
		}
		
		/**
		 * Getter/Setter
		 */
		public ArrayList<Semaine> getListeSemaine(){
			return listeSemaines;
		}
	
		public ArrayList<SemaineType> getListeSemaineType(){
			return listeSemainesType;
		}
		
		public void addListeSemaines(Semaine AddSemaines){
			 this.listeSemaines.add(AddSemaines);
		}
		
		public void addListeSemainesType(SemaineType AddSemaines){
			 this.listeSemainesType.add(AddSemaines);
		}
		
	
		public String getCouleur() {
			return couleur;
		}
	
	
		public void setCouleur(String couleur){
			this.couleur = couleur;
		}
		
		public long getIdentifiant(){
			return identifiant;
		}
		
		public void setIdentifiant(long id){
			this.identifiant = id; 
		}
		
		
		public String getNom(){
			return nom;
		}
		
		public void setNom(String nom){
			this.nom = nom; 
		}

}



