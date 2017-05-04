import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Jour {
	
	private long identifiant;
	private Date date ;
	private Semaine id_semaine;
	ArrayList<Heure> ListeHeures;
	
	/**
	 * Constructeur par défaut
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Jour() throws ClassNotFoundException, SQLException{
		System.out.println("Création d'un Jour");
		this.identifiant =  RecupereIdJour() + 1 ;
		this.ListeHeures = new ArrayList<Heure>();
	}
	
	/**
	 * Constructeur avec des paramètres
	 * @param pid
	 * @param pid_semaine
	 * @param pdate
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Jour(long pid,Semaine pid_semaine,Date pdate) throws ClassNotFoundException, SQLException{
		System.out.println("Création d'un Jour");
		this.identifiant = pid;
		this.date = pdate ; 
		this.setId_semaine(pid_semaine);
		this.ListeHeures = new ArrayList<Heure>();
	}
	
	
	/**
	 * Méthode qui récupère le dernier id de la table Jour et retourne l'id qu'il a récupéré en base de données
	 * @return ai
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public long RecupereIdJour() throws ClassNotFoundException, SQLException{
		String sql = String.format(ReqSQLite.RECUPERER_LAST_ID_JOUR.getRequeteSQL()); //("SELECT last_insert_rowid() as last_id FROM JOUR;")
		long ai = -1 ;
		try {
			ResultSet resultsJours = SQLiteJDBC.ExecuteRequeteSelect(sql);
			ai = resultsJours.getInt(1);
			
		}catch (SQLException e) {
			
			e.printStackTrace();
			ai = 1;
		}
		return ai;
	}


	/**
	 * Getter/Setter
	 */
	public long getIdentifiant(){
		return identifiant;
	}

	public Date getJour(){
		return date;
	}

	public void setJour(Date pdate){
		this.date = pdate;
	}

	public Semaine getId_semaine() {
		return id_semaine;
	}

	public void setId_semaine(Semaine id_semaine) {
		this.id_semaine = id_semaine;
	}
}
