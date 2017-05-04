import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;




public class SQLiteJDBC
{
	static Connection cnx;
	static Statement requete;
	static ResultSet rst;
	

	
	/**
	 * Constructeur de la classe Database
     * Charge le driver SQlite JDBC en utilisant la classe loader actuelle
     * @execute une connexion à la base de données
	 * @throws ClassNotFoundException
	 */
	public SQLiteJDBC() throws ClassNotFoundException
	  {	
		Class.forName("org.sqlite.JDBC"); 												
		cnx = connecterDB();
	  }
	
	
	
	/**
	 * Fonction qui créé l'objet Connexion permettant d'établir la connexion
	 * Déclare l'objet qui permet de faire les requêtes
	 * @return la connexion
	 * @throws ClassNotFoundException
	 */
	public static Connection connecterDB() throws ClassNotFoundException{
		try {
			 Connection cnx = DriverManager.getConnection("jdbc:sqlite:BDD.db");
			 requete = cnx.createStatement();										
			 requete.executeUpdate("PRAGMA synchronous = OFF;");
	         requete.setQueryTimeout(30);
	         System.out.println("Connexion bien établie");
	         
			 return cnx;
		}catch(SQLException e){
			System.out.println("requête non executée car la table n'est pas conforme " + e.toString());
			e.printStackTrace();
    		return null;	   	
     	}
	}	
	
	/**
	 * Déconnexion de la base de données
	 * @param newConnexion
	 */
	public static void disconnect(SQLiteJDBC newConnexion){
	   try{
	     if(cnx != null)
	      cnx.close();  
	   }catch(SQLException e){
	        e.printStackTrace();
	   }
	 }
	
	/**
	 * Exécute une requête qui ne retourne rien
	 * @param String requettesql
	 */
	public static void ExecuteRequete(String requettesql) throws ClassNotFoundException, SQLException{
		try{ 
		  requete.executeUpdate(requettesql);
		}catch (SQLException e){
		  e.printStackTrace();
		}
	}   
	/**
	 * Exécute une requête qui retourne quelque chose
	 * @param requête
	 * @return ResultSet (rst)
	 */
	public static ResultSet ExecuteRequeteSelect(String requette) {		
		try{ 
			ResultSet rst =  requete.executeQuery(requette);
			return rst;
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		 return null;
	}
	  

  
  /**
   * Créer les tables si elles n'existent pas 
   */
  public static void CreationDesTables() throws ClassNotFoundException, SQLException{
		ArrayList<String> sql = new ArrayList<String>();
		sql.add(ReqSQLite.CREATE_TABLE_SEMAINE.getRequeteSQL());
		
		sql.add(ReqSQLite.CREATE_TABLE_UTILISATEUR.getRequeteSQL());
		sql.add(ReqSQLite.CREATE_TABLE_GROUPE.getRequeteSQL());	
		sql.add(ReqSQLite.CREATE_TABLE_JOUR.getRequeteSQL());
		sql.add(ReqSQLite.CREATE_TABLE_PROFIL.getRequeteSQL()); 	
		sql.add(ReqSQLite.CREATE_TABLE_APPARTIENT.getRequeteSQL()); 
		sql.add(ReqSQLite.CREATE_TABLE_SEMAINETYPE.getRequeteSQL()); 
		sql.add(ReqSQLite.CREATE_TABLE_JOURTYPE.getRequeteSQL());
		
		for(int i = 0 ; i< sql.size(); i++){
			ExecuteRequete((String) sql.get(i));
			System.out.println("Creation de la table "+ i);
		}
		System.out.println("toute les tables on été créées");	
	}

  /**
   * Charge les Utilisateurs et les Groupes de la base de données et créer les objets et les ajoute à un tableau associatif
   * @param AssoUtilisateur
   * @param AssoGroupe
   */
  public void ChargementAssociations(Hashtable<Long,Utilisateur> AssoUtilisateur,Hashtable<Long,Groupe> AssoGroupe) throws SQLException, ClassNotFoundException, ParseException{
	  String  sql = String.format(ReqSQLite.AFFICHER_TABLE_UTILISATEUR.getRequeteSQL()); // ("SELECT * FROM UTILISATEUR"),
	  ResultSet resultsUtilisateur = SQLiteJDBC.ExecuteRequeteSelect(sql); 

		while(resultsUtilisateur.next()){
			Utilisateur NewUtilisateur = new Utilisateur(resultsUtilisateur.getLong("id_utilisateur"),resultsUtilisateur.getString("nom"),resultsUtilisateur.getString("prenom"),resultsUtilisateur.getInt("telephone"),resultsUtilisateur.getString("mail"),resultsUtilisateur.getString("code"),resultsUtilisateur.getString("couleur"));
			AssoUtilisateur.put(NewUtilisateur.getIdentifiant(), NewUtilisateur);
		}
		
		sql = String.format(ReqSQLite.AFFICHER_TABLE_GROUPE.getRequeteSQL()); // ("SELECT * FROM GROUPE"),
		ResultSet resultsGroupe = SQLiteJDBC.ExecuteRequeteSelect(sql); 
		
		while(resultsGroupe.next()){
			Groupe NewGroupe = new Groupe(resultsGroupe.getLong("id_groupe"),resultsGroupe.getString("nom"),resultsGroupe.getInt("edroit"),resultsGroupe.getString("couleur"));
			AssoGroupe.put(NewGroupe.getIdentifiant(), NewGroupe);
		}
  }
  
  /**
   * Charge les Utilisateurs qui font partie de la table Appartient (Utilisateur qui appartient à un groupe)
   * Ajoute l'Utilisateur à l'Arraylist Utilisateur de la table Groupe
   * @param Hashtable (AssoUtilisateur)
   * @param Hashtable (AssoGroupe)
   */
  public void ChargementUtilisateur(Hashtable<Long,Utilisateur> AssoUtilisateur,Hashtable<Long,Groupe> AssoGroupe) throws SQLException, ClassNotFoundException, ParseException{
		String sql = String.format(ReqSQLite.RECUPERER_LES_ID.getRequeteSQL()); //("SELECT * FROM APPARTIENT ;"),
		ResultSet resultsIdGroupeUtilisateurs = SQLiteJDBC.ExecuteRequeteSelect(sql);
		
		while(resultsIdGroupeUtilisateurs.next()){
			Long tempidGroupe = resultsIdGroupeUtilisateurs.getLong("id_groupe") ;
			Groupe tempGroupe = AssoGroupe.get(tempidGroupe); 
			Long tempidUtilisateur = resultsIdGroupeUtilisateurs.getLong("id_utilisateur") ; 
			Utilisateur tempUtilisateur = AssoUtilisateur.get(tempidUtilisateur); // 
			
			tempUtilisateur.addListeGroupe(tempGroupe);	
		}	
  }

  /**
   * Charge les Groupes qui font partie de la table Appartient (Groupe qui appartient à un Utilisateur)
   * Ajoute le Groupe à l'Arraylist Groupe de la table Utilisateur
   * @param Hashtable (AssoUtilisateur)
   * @param Hashtable (AssoGroupe)
   */
	public void ChargementGroupe(Hashtable<Long,Groupe> AssoGroupe,Hashtable<Long,Utilisateur> AssoUtilisateur) throws SQLException, ClassNotFoundException, ParseException{	
		String sql = String.format(ReqSQLite.RECUPERER_LES_ID.getRequeteSQL()); //("SELECT * FROM APPARTIENT ;"),
		ResultSet resultsIdAppartient = SQLiteJDBC.ExecuteRequeteSelect(sql);

		while(resultsIdAppartient.next()){	
			Long tempidGroupe = resultsIdAppartient.getLong("id_groupe") ;
			Groupe tempGroupe = AssoGroupe.get(tempidGroupe);
			Long tempidUtilisateur = resultsIdAppartient.getLong("id_utilisateur") ;
			Utilisateur tempUtilisateur = AssoUtilisateur.get(tempidUtilisateur);
			
			tempGroupe.addListePersonnes(tempUtilisateur);
		}
	}
			
	 /**
	   * Charge les Profils
	   * Et les ajoute au tableau associatif AssoProfilId
	   * @param Hashtable (AssoUtilisateur)
	   * @param Hashtable (AssoGroupe)
	   */
	public void ChargementDesProfils(Hashtable<Long,Profil> AssoProfilId) throws SQLException, ClassNotFoundException, ParseException{	
			String sql = String.format(ReqSQLite.AFFICHER_PROFIL.getRequeteSQL()); 
			ResultSet resultsProfil = SQLiteJDBC.ExecuteRequeteSelect(sql);		
			Profil tempProfil= null ;
	
			while(resultsProfil.next()){
				tempProfil = new Profil(resultsProfil.getInt("id_profil"), resultsProfil.getString("nom"),resultsProfil.getString("couleur"));
				AssoProfilId.put(tempProfil.getIdentifiant(),tempProfil);
			}
	 }
	
	
	/**
	   * Récupère les semaines de la base de données
	   * Créer les objets semaine
	   * Récupère les jours d'une semaine
	   * Créer les objets jours
	   * pour chaque jour on créé les Objets heures 
	   * @param Hashtable (AssoUtilisateur)
	   * @param Hashtable (AssoGroupe)
	  */
	public void ChargementDesSemainesEtJours(Hashtable<Long,Profil> AssoProfilId) throws SQLException, ClassNotFoundException, ParseException{	
		
		String sql = String.format(ReqSQLite.AFFICHER_SEMAINE.getRequeteSQL()); //"SELECT * FROM SEMAINE "
		ResultSet resultsSemaine = SQLiteJDBC.ExecuteRequeteSelect(sql);
	
			while(resultsSemaine.next()){
				Semaine Semainetemp = null;
				Groupe TempGroupe= null;
				try {
					 Semainetemp = new Semaine(resultsSemaine.getLong("id_semaine"), resultsSemaine.getString("date_lundi"),AssoProfilId.get(resultsSemaine.getInt("id_profil")), resultsSemaine.getBoolean("Personnalise"));
					System.out.println("Nouvelle semaine");						
				}catch (ParseException e){
					e.printStackTrace();
				}
		
				sql = String.format(ReqSQLite.AFFICHER_JOURS_SEMAINE.getRequeteSQL(),String.valueOf(Semainetemp.getIdentifiant())); //("SELECT * FROM JOUR WHERE id_semaine = '%s' "),
				ResultSet resultsJour = SQLiteJDBC.ExecuteRequeteSelect(sql);	
	
			while(resultsJour.next()){
				String jourS = resultsJour.getString("date_lundi");
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date = formatter.parse(jourS);
				ArrayList<Date> tempJours = OutilsDate.getDatesFromWeekNumber(OutilsDate.getAnnee(date),OutilsDate.DateSemaine(date));	
				System.out.println(jourS);	
				Jour NewJour = new Jour(resultsJour.getLong("id_jour"), Semainetemp, tempJours.get(0)); 
				Semainetemp.listeJours.add(NewJour);
					
				
				for(int x = 0; x <= 6; x++){
					for(int i = 0; i < 24; i++){
						sql = String.format(ReqSQLite.AFFICHER_GROUPE_DUNE_HEURE.getRequeteSQL(),i,tempJours.get(x));//("SELECT GROUPE.* FROM JOUR,GROUPE WHERE JOUR.'%s'= GROUPE.id_groupe AND date_lundi ='%s' ")
						ResultSet resultsIdGroupe = SQLiteJDBC.ExecuteRequeteSelect(sql);
						System.out.println(sql);
						
						if (resultsIdGroupe.next()==true){
							TempGroupe = new Groupe(resultsIdGroupe.getLong("id_groupe"),resultsIdGroupe.getString("nom"),resultsIdGroupe.getInt("edroit"),resultsIdGroupe.getString("couleur")); 
							// on créé 24 jours
							Heure NewHeure = new Heure(i,TempGroupe,NewJour);
							NewJour.ListeHeures.add(NewHeure); 
						}	
					}
				}	
			}			
		}		
	 }
	
	
	/**
	   * Récupère les semaineType de la base de données
	   * Créer les objets semaineType
	   * Récupère les jourTypes d'une semaineType
	   * Créer les objets jourTypes
	   * pour chaque jourTypes on créé 24 Objets heures
	   * @param Hashtable (AssoUtilisateur)
	   * @param Hashtable (AssoGroupe)
	  */
	public void ChargementDesSemainesTypeEtJoursType(Hashtable<Long,Profil> AssoProfilId) throws SQLException, ClassNotFoundException, ParseException{	
		
		String sql = String.format(ReqSQLite.AFFICHER_SEMAINETYPE.getRequeteSQL()); //SELECT * FROM SEMAINETYPE
		ResultSet resultsSemaineType = SQLiteJDBC.ExecuteRequeteSelect(sql);
		ResultSet resultsJourTypes;
		
		
		while(resultsSemaineType.next()){
			SemaineType SemaineTypetemp = new SemaineType(resultsSemaineType.getLong("id_semaine"),AssoProfilId.get(resultsSemaineType.getInt(2)), resultsSemaineType.getBoolean("personnalise"));
			
			long tempidProfil = (int)resultsSemaineType.getInt(2);
			Profil temProfil = AssoProfilId.get(tempidProfil); 
			temProfil.addListeSemainesType(SemaineTypetemp);
			
			sql = String.format(ReqSQLite.AFFICHER_JOURS_SEMAINETYPE.getRequeteSQL(),String.valueOf(SemaineTypetemp.getId_semaine())); //("SELECT * FROM JOURTYPE WHERE id_semaine = '%s' "),
			System.out.println(sql);
			resultsJourTypes = SQLiteJDBC.ExecuteRequeteSelect(sql);	
	
			while(resultsJourTypes.next()){
				
				JourType NewJourType = new JourType(resultsJourTypes.getLong("id_jour"),resultsJourTypes.getString("jour"));
				SemaineTypetemp.listeJours.add(NewJourType);
					
				for(int x = 0; x < 6; x++){	
					for(int i = 0; i < 23; i++){ 
						
						String date[] = {"LUNDI","MARDI","MERCREDI","JEUDI","VENDREDI","SAMEDI","DIMANCHE"};
						String sql1 = String.format(ReqSQLite.AFFICHER_GROUPE_DUNE_HEURETYPE.getRequeteSQL(), i , date[x] );
						System.out.println(sql1);
						ResultSet resultsIdGroupe = SQLiteJDBC.ExecuteRequeteSelect(sql1);
								
								
						if (resultsIdGroupe.next()==true){
		
							Groupe TempGroupe = new Groupe(resultsIdGroupe.getLong("id_groupe"),resultsIdGroupe.getString("nom"),resultsIdGroupe.getInt("edroit"),resultsIdGroupe.getString("couleur")); // on creer 24 jour
							Heure NewHeure = new Heure(i,TempGroupe,NewJourType);
							NewJourType.ListeHeures.add(NewHeure); // on ajoute la liste des heures de tempjour dans l'arraylist des heures 	
								
							}
					}		
				}
			}			
		}		
	 }
	
	/**
	 * Fonction qui retourne tous les noms des groupes
	 * @param AssoGroupe
	 * @return Groupe(Array)
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	  public static String[] getListeGroupe(Hashtable<Long,Groupe> AssoGroupe) throws ClassNotFoundException, SQLException {
			
		  	ArrayList<String> ListGroupe = new ArrayList<String>();
			for (Iterator<Groupe> i = AssoGroupe.values().iterator() ; i.hasNext();){
				Groupe currentGroupe = i.next();
				ListGroupe.add(currentGroupe.getNom());
		      }
			
			String[] Groupe = new String[ListGroupe.size()];
			for(int i = 0; i < ListGroupe.size() ; i++){
				Groupe[i] = ListGroupe.get(i);
			}
			return Groupe;
	  }
	  
	  /**
	   * Fonction qui retourne tous les noms des Utilisateurs
	   * @param AssoUtilisateur
	   * @return Utilisateur (array)
	   * @throws ClassNotFoundException
	   * @throws SQLException
	   */
	  public static String[] getListeUtilisateurs(Hashtable<Long,Utilisateur> AssoUtilisateur) throws ClassNotFoundException, SQLException {
		  	
		    ArrayList<String> ListUtilisateur = new ArrayList<String>();
		    for (Iterator<Utilisateur> i = AssoUtilisateur.values().iterator() ; i.hasNext();){
				Utilisateur currentUtilisateur = i.next();
				ListUtilisateur.add(currentUtilisateur.getNom());
		      }
		    
			String[] Utilisateur = new String[ListUtilisateur.size()];
			for(int i = 0; i < ListUtilisateur.size() ; i++){
				Utilisateur[i] = ListUtilisateur.get(i);
			}
			return Utilisateur;
	  }
	  
	  /**
	   * Fonction qui retourne tous les noms des profils
	   * @param AssoProfilId
	   * @return Profil(array)
	   * @throws ClassNotFoundException
	   * @throws SQLException
	   */
	  public static String[] getListeProfil(Hashtable<Long,Profil> AssoProfilId) throws ClassNotFoundException, SQLException {
		    ArrayList<String> ListProfil = new ArrayList<String>();   
		    for (Iterator<Profil> i = AssoProfilId.values().iterator() ; i.hasNext();){
				Profil currentProfil = i.next();
				ListProfil.add(currentProfil.getNom());
		      }
		    
			String[] Profil = new String[ListProfil.size()];
			for(int i = 0; i < ListProfil.size() ; i++){
				Profil[i] = ListProfil.get(i);
			}
			return Profil;
	}
	  
	  /**
	   * Fonction qui retourne tous les id de la table jours
	   * @return Utilisateur(Array)
	   * @throws ClassNotFoundException
	   * @throws SQLException
	   */
	  public static String[] getListeJour() throws ClassNotFoundException, SQLException {
		  	
		  	String sql = String.format(ReqSQLite.AFFICHER_JOUR.getRequeteSQL());
		  	ResultSet resultsJour = SQLiteJDBC.ExecuteRequeteSelect(sql);
		    
		  	ArrayList<String> ListJour = new ArrayList<String>();
			while (resultsJour.next()){
				ListJour.add(resultsJour.getString("id_jour"));
				
			}
			String[] Utilisateur = new String[ListJour.size()];
			ListJour.toArray(Utilisateur);
					
			return Utilisateur;
	  }
	
	  public static String[] getListeSemaine() throws ClassNotFoundException, SQLException, ParseException {
		  	
		  	String sql = String.format(ReqSQLite.AFFICHER_LISTE_SEMAINE.getRequeteSQL());//SELECT date_lundi FROM SEMAINE
		  	ResultSet resultsSemaine = SQLiteJDBC.ExecuteRequeteSelect(sql);
		    ArrayList<String> ListSemaine = new ArrayList<String>();
		    
			while (resultsSemaine.next()){
				String temp = "";
				String date_lundi = resultsSemaine.getString("date_lundi");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
				Date tempDate = new Date(format.parse(date_lundi).getTime());
				temp += OutilsDate.DateSemaine(tempDate) + " - " + format2.format(tempDate);
				System.out.println(temp);
				ListSemaine.add(temp);
			}
			String[] Semaines = new String[ListSemaine.size()];
			ListSemaine.toArray(Semaines);
			
			return Semaines;
	  }
	  
	  
	  
	  public static Hashtable<String, Long> getlistuser(Hashtable<Long, Utilisateur> assoUtilisateur){
		 
		  Hashtable<String,Long> miniHashmap = new Hashtable<String,Long>(); 
		 
		  for (Iterator<Utilisateur> i = assoUtilisateur.values().iterator() ; i.hasNext();){
			  Utilisateur currentUtilisateur = i.next();
			  miniHashmap.put(currentUtilisateur.getNom(),currentUtilisateur.getIdentifiant());
	     }
		 return miniHashmap ; 
	  }
	  
	  public static Hashtable<String, Long> getlistgroupe(Hashtable<Long, Groupe> AssoGroupe){
			
		  Hashtable<String,Long> miniHashmap = new Hashtable<String,Long>(); 
			 
		  for (Iterator<Groupe> i = AssoGroupe.values().iterator() ; i.hasNext();){
				Groupe currentGroupe = i.next();
				miniHashmap.put(currentGroupe.getNom(),currentGroupe.getIdentifiant());
		  }
		  return miniHashmap ;				  
		  }
	  
	  
	  public static Hashtable<String, Long> getlistprofil (Hashtable<Long, Profil> AssoProfilId) {
			 Hashtable<String,Long> miniHashmap = new Hashtable<String,Long>();  
			
			 for (Iterator<Profil> i = AssoProfilId.values().iterator() ; i.hasNext();){
				Profil currentProfil = i.next();
				miniHashmap.put(currentProfil.getNom(),currentProfil.getIdentifiant());
		     }
			 return miniHashmap ; 
		  }
}
  
  

   
