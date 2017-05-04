import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Semaine {
	
		private long identifiant ;
		private Date date;
		private Profil id_profil;
		private boolean personalise;
		public ArrayList<Jour> listeJours ;
		public String sql;
		
		/**
		 * Constructeur avec des paramètres
		 * @param pid
		 * @param pdate
		 * @param pid_profil
		 * @param ppersonalise
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 * @throws ParseException
		 */
		public Semaine(long pid,String pdate, Profil pid_profil,boolean ppersonalise) throws ClassNotFoundException, SQLException, ParseException{
			this.identifiant = pid;
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			this.date = new Date(format.parse(pdate).getTime());
			this.id_profil = pid_profil ;
			this.personalise = ppersonalise;
			this.listeJours = new ArrayList<Jour>(); 
			System.out.println("Creation de la semaine" + identifiant);
		}
		
		/**
		 * Fonction qui stocke dans un Arraylist les dates d'une semaine
		 * Créer une semaine en base de données
		 * Récupérer son id en base de données
		 * Créer pour les 7 jours de la semaine l'objet correspondant
		 * Insère en base de données le jour 
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		public void InsertionSemaineJour() throws ClassNotFoundException, SQLException{
			ArrayList<Date> tempJours = OutilsDate.getDatesFromWeekNumber(OutilsDate.getAnnee(this.date),OutilsDate.DateSemaine(this.date)); //on stocke l'année et la date de la semaine	
			
			if(id_profil == null){
				String sql = String.format(ReqSQLite.INSERTION_SEMAINE.getRequeteSQL(), tempJours.get(0),0,personalise); //INSERT OR IGNORE INTO SEMAINE(date_lundi,id_profil,personnalise) VALUES('%s','%s','%s')
				System.out.println(sql);
				SQLiteJDBC.ExecuteRequete(sql);

				sql = String.format(ReqSQLite.RECUPERER_ID_SEMAINE_AVEC_DATELUNDI.getRequeteSQL(), tempJours.get(0)); //("SELECT id_semaine FROM SEMAINE WHERE date_lundi = '%s';" ),
				ResultSet resultsID = SQLiteJDBC.ExecuteRequeteSelect(sql); //récupère l'identifiant de la semaine 
				long id_semaine = resultsID.getLong("id_semaine") ;
				for(int i = 0; i < tempJours.size(); i++) {
					Jour temp = new Jour(1,this,tempJours.get(i)); 
					System.out.println("date = "+ tempJours.get(i));
					listeJours.add(temp); // on l'ajoute a notre tableau
					sql = String.format(ReqSQLite.INSERTION_JOUR.getRequeteSQL(),id_semaine,tempJours.get(i),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
					SQLiteJDBC.ExecuteRequete(sql);	
				}
			}
			
		}
		
		/**
		 * Récupère en base de données l'id du jour grâce à la date du lundi 
		 * Récupère tous les jours d'une semaine
		 * et les supprime de la base de données
		 * @param Date
		 * @throws ClassNotFoundException
		 * @throws SQLException
		 */
		public static void SupressionDeSemaineJour(String Date) throws ClassNotFoundException, SQLException{
			 
			String sql = String.format(ReqSQLite.RECUPERER_ID_JOUR_AVEC_DATELUNDI.getRequeteSQL(), Date); //("SELECT id_semaine FROM SEMAINE WHERE date_lundi = '%s';" ),
			ResultSet resultsID = SQLiteJDBC.ExecuteRequeteSelect(sql);
			
			long id_jour = resultsID.getLong("id_jour") ; 
			long fin = id_jour + 6;
			System.out.println(fin);
			for(;id_jour <= fin; id_jour++ ){
				sql = String.format(ReqSQLite.SUPRESSION_JOUR_DEUX.getRequeteSQL(), id_jour);
				System.out.println(sql);
				SQLiteJDBC.ExecuteRequete(sql);
			}
		}
		
		/**
		 * @param String (litteral)
		 * @return le numéro du mois
		 */
		public static String dateLitToIso(String litteral) {
			String jour = litteral.split(" ")[0];
			String mois = litteral.split(" ")[1];
			String annee = litteral.split(" ")[2];
			
			switch(mois) {
				case "janv.":
					mois = "01"; break ;
				case "févr.":
					mois = "02"; break ;
				case "mars":
					mois = "03"; break ;
				case "avr.":
					mois = "04"; break ;
				case "mai":
					mois = "05"; break ;
				case "juin":
					mois = "06"; break ;
				case "juil.":
					mois = "07"; break ;
				case "août":
					mois = "08"; break ;
				case "sept.":
					mois = "09"; break ;
				case "oct.":
					mois = "10"; break ;
				case "nov.":
					mois = "11"; break ;
				case "déc.":
					mois = "12"; break ;
			}
			return annee + "-" + mois + "-" + jour ;
		}
		
		
		/**
		 * Méthode qui récupère le dernier id de la table Semaine
		 * @return ai (long) 	
		 * @throws ClassNotFoundException
		 */
		public static long RecupereIdSemaine() throws ClassNotFoundException{
			String sql = String.format(ReqSQLite.RECUPERER_LAST_ID_SEMAINE.getRequeteSQL()); //("SELECT last_insert_rowid() as last_id FROM JOUR;")
			long ai = -1 ;
			try{
				ResultSet resultsJours = SQLiteJDBC.ExecuteRequeteSelect(sql);
				ai = resultsJours.getInt(1);
			}catch (SQLException e) {
				ai = 1;
			}
			return ai;
		}
		

		/**
		 *Getter/Setter
		 */
		public long getIdentifiant(){
			return identifiant;
		}

		
		public void setIdentifiant(long id){
			this.identifiant = id;
		}

		public Date getDate(){
			return date;
		}

		public void setDate(Date date){
			this.date = date;
		}
		
		
}
		
		
		
		
		


