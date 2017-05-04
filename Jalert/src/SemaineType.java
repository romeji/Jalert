import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SemaineType{ 

	private long id_semaine; 
	private Profil id_profil;
	private boolean personnalise;
	public ArrayList<JourType> listeJours ;
	
	/**
	 * Constructeur par d�faut
	 * @throws ClassNotFoundException
	 */
	public SemaineType() throws ClassNotFoundException {
		this.id_semaine = RecupereIdSemaineType() ;
		this.id_profil = null;
		this.personnalise = false;
		System.out.println("\n"+"Cr�ation d'une semaineType sans parametres");
	}
	
	/**
	 * Constructeur avec des param�tres
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
		System.out.println("\n"+"Cr�ation d'une semaineType");
	}

	
	/**
	 * Fonction qui stocke dans un Arraylist les dates d'une semaineType
	 * Cr�er une semaineType en base de donn�es
	 * R�cup�rer son id en base de donn�es
	 * Cr�er pour les 7 jours de la semaineType de l'objet correspondant
	 * Ins�re en base de donn�es le joursTypes
	 * @param NewProfil
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void InsertionSemaineJour(Profil NewProfil) throws ClassNotFoundException, SQLException
	{
		System.out.println(this.id_semaine + "ID DE LA SEMAINE");
		String sql = String.format(ReqSQLite.INSERTION_SEMAINETYPE.getRequeteSQL(), NewProfil.getIdentifiant(),false);	
		SQLiteJDBC.ExecuteRequete(sql); // on insert la semaine en base de donn�e
	
		for(int i = 0; i <= 6; i++) { 	
			String date[] = {"LUNDI","MARDI","MERCREDI","JEUDI","VENDREDI","SAMEDI","DIMANCHE"};
			JourType temp = new JourType(this.id_semaine,date[i]);
			listeJours.add(temp); // on l'ajoute a notre tableau
			long ai=temp.RecupereIdJourType()+1;
			sql = String.format(ReqSQLite.INSERTION_JOURTYPE.getRequeteSQL(),ai,SemaineType.RecupereIdSemaineType(),date[i],0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);// on l'ajoute � la base de donn�es
			SQLiteJDBC.ExecuteRequete(sql);	
			System.out.println("Jours numero : " + i+ " ins�r�");
		}
	}
	
	
	/**
	 * M�thode qui r�cup�re le dernier id de la table SemaineType 
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
