import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SemaineType{ 

	private long id_semaine; 
	private Profil id_profil;
	private boolean personnalise;
	public ArrayList<JourType> listeJours ;
	
	/**
	 * Constructeur par défaut
	 * @throws ClassNotFoundException
	 */
	public SemaineType() throws ClassNotFoundException {
		this.id_semaine = RecupereIdSemaineType() ;
		this.id_profil = null;
		this.personnalise = false;
		System.out.println("\n"+"Création d'une semaineType sans parametres");
	}
	
	/**
	 * Constructeur avec des paramètres
	 * @param Pid_semaine
	 * @param Pid_profil
	 * @param Ppersonalise
	 * @throws ClassNotFoundException
	 */
	public SemaineType(long Pid_semaine,Profil Pid_profil,boolean Ppersonalise) throws ClassNotFoundException {
		this.id_semaine = Pid_semaine;
		this.id_profil = Pid_profil ;
		this.personnalise = Ppersonalise;
		this.listeJours = new ArrayList<JourType>(); 
		System.out.println("\n"+"Création d'une semaineType");
	}

	
	/**
	 * Fonction qui stocke dans un Arraylist les dates d'une semaineType
	 * Créer une semaineType en base de données
	 * Récupérer son id en base de données
	 * Créer pour les 7 jours de la semaineType de l'objet correspondant
	 * Insère en base de données le joursTypes
	 * @param NewProfil
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void InsertionSemaineJour(Profil NewProfil) throws ClassNotFoundException, SQLException
	{
		System.out.println(this.id_semaine + "ID DE LA SEMAINE");
		String sql = String.format(ReqSQLite.INSERTION_SEMAINETYPE.getRequeteSQL(), NewProfil.getIdentifiant(),false);	
		SQLiteJDBC.ExecuteRequete(sql); // on insert la semaine en base de donnée
	
		for(int i = 0; i <= 6; i++) { 	
			String date[] = {"LUNDI","MARDI","MERCREDI","JEUDI","VENDREDI","SAMEDI","DIMANCHE"};
			JourType temp = new JourType(this.id_semaine,date[i]);
			listeJours.add(temp); // on l'ajoute a notre tableau
			long ai=temp.RecupereIdJourType()+1;
			sql = String.format(ReqSQLite.INSERTION_JOURTYPE.getRequeteSQL(),ai,SemaineType.RecupereIdSemaineType(),date[i],0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);// on l'ajoute à la base de données
			SQLiteJDBC.ExecuteRequete(sql);	
			System.out.println("Jours numero : " + i+ " inséré");
		}
	}
	
	
	/**
	 * Méthode qui récupère le dernier id de la table SemaineType 
	 * @return ai (long)
	 * @throws ClassNotFoundException
	 */
	public static long RecupereIdSemaineType() throws ClassNotFoundException{
		String sql = String.format(ReqSQLite.RECUPERER_LAST_ID_SEMAINETYPE.getRequeteSQL()); 
		long ai = -1 ;	
		try {
			ResultSet resultsJours = SQLiteJDBC.ExecuteRequeteSelect(sql);
			ai = resultsJours.getInt(1);
		}catch (SQLException e) {
			ai = 0;
		}
			return ai;
	}
	
	/**
	 *Getter/Setter
	 */
	public long getId_semaine() {
		return id_semaine;
	}

	public void setId_semaine(long id_semaine) {
		this.id_semaine = id_semaine;
	}

	public Profil getId_profil() {
		return id_profil;
	}

	public void setId_profil(Profil id_profil) {
		this.id_profil = id_profil;
	}

	public boolean isPersonnalise() {
		return personnalise;
	}

	public void setPersonnalise(boolean personalise) {
		this.personnalise = personalise;
	}
	
}
