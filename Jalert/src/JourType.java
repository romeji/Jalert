import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JourType {
	private long identifiant;
	private String jour;
	ArrayList<Heure> ListeHeures;
	
	/**
	 * Constructeur avec des paramètres
	 * @param Pid
	 * @param Pjour
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public JourType(long Pid,String Pjour) throws ClassNotFoundException, SQLException {
		this.identifiant = RecupereIdJourType() +1 ;
		this.jour = Pjour;
		this.ListeHeures = new ArrayList<Heure>();
	}

	
	/**
	 * Méthode qui récupère le dernier id de la table JourType et retourne l'id qu'il a récupéré en base de données	
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public long RecupereIdJourType() throws ClassNotFoundException, SQLException{
		String sql = String.format(ReqSQLite.RECUPERER_LAST_ID_JOURTYPE.getRequeteSQL()); //("SELECT last_insert_rowid() as last_id FROM JOUR;")
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
	
	//Getter/Setter 
	public long getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(long id) {
		this.identifiant = id;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}
}
