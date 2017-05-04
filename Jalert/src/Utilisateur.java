import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Utilisateur {

	private long identifiant ;
	private String nom ; 
	private String prenom ;
	private int telephone ; 
	private String mail;
	private String code;
	private String couleur;
	public ArrayList<Groupe> listeGroupe ;
	
	/**
	 * Constructeur par défaut
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Utilisateur() throws ClassNotFoundException, SQLException{
		this.identifiant = RecupereIdUtilisateur() +1 ;
		this.nom = " " ; 
		this.prenom = "";
		this.telephone = 0667066661 ; 
		this.mail = "lopes.jerome21@sap2i.fr";
		this.code = "007";
		this.couleur = "#FFFFFF";
		this.listeGroupe = null ;
		System.out.println("Création d'une équipe");
	}
	
	/**
	 * Constructeur avec des paramètres
	 * @param Pidentifiant
	 * @param Pnom
	 * @param Pprenom
	 * @param Ptelephone
	 * @param Pmail
	 * @param Pcode
	 * @param Pcouleur
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Utilisateur(long Pidentifiant,String Pnom,String Pprenom,int Ptelephone,String Pmail,String Pcode, String Pcouleur) throws ClassNotFoundException, SQLException
	{
		this.identifiant = Pidentifiant ;
		this.nom	= Pnom ;
		this.prenom = Pprenom ;
		this.telephone = Ptelephone ;
		this.mail = Pmail;
		this.code = Pcode;
		this.couleur = Pcouleur;
		this.listeGroupe = new ArrayList<Groupe>();
		System.out.println("Création d'une personne");
	}
	
	/**
	 * Méthode qui récupère le dernier id de la table Utilisateur
	 * @return ai (long)
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static long RecupereIdUtilisateur() throws ClassNotFoundException, SQLException{
		String sql = String.format(ReqSQLite.RECUPERER_LAST_ID_UTILISATEUR.getRequeteSQL()); //("SELECT last_insert_rowid() as last_id FROM JOUR;")
		
		long ai = -1 ;
		try {
			ResultSet resultsJours = SQLiteJDBC.ExecuteRequeteSelect(sql);
			ai = resultsJours.getLong(1);
		} catch (SQLException e) {
			
			e.printStackTrace();
			ai = 1;
		}
		return ai;
	}
	
	public void addListeGroupe(Groupe AddGroupe) {
		this.listeGroupe.add(AddGroupe);
		System.out.println("Ajout du groupe dans l'arrayList utilisateur");
	}
	
	
	//Getter/Setter
	public ArrayList<Groupe> getListeGroupe() {
		return listeGroupe;
	}
	
	public long getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public  int getTelephone() {
		return telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCode() {
		return code ;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCouleur() {
		return couleur;
	}

	public String getNom() {
		return nom;
	}
		
	public String getPrenom() {
		return prenom;
	}
	
	
}
