public enum ReqSQLite {
	
	RECUPERER_ID_PROFIL("SELECT * FROM PROFIL WHERE id_profil = '%s';" ),
	AFFICHER_PROFIL("SELECT * FROM PROFIL"),
	AFFICHER_UN_PROFIL_ID("SELECT* FROM PROFIL WHERE id = '%s';"),
	CREATE_TABLE_PROFIL("CREATE TABLE IF NOT EXISTS PROFIL" +
		"(id_profil 				INTEGER  		NOT NULL	PRIMARY KEY     	AUTOINCREMENT	UNIQUE		,"+
		"nom						TEXT			NOT NULL										UNIQUE		,"+
		"couleur					TEXT			NOT NULL										UNIQUE		);"),
	
	SUPPRIMER_PROFIL("DELETE FROM `Profil` WHERE id_profil = '%s'; "),
	INSERTION_PROFIL("INSERT OR IGNORE INTO PROFIL(nom,couleur) VALUES('%s','%s');"),
	MODIFICATION_PROFIL("UPDATE PROFIL SET nom = '%s' ,couleur = '%s' where id_profil = '%s';"),
	AFFICHER_CONTENU_JOURTYPE_POUR_UNE_SEMAINE("SELECT jour,`0`,`1`,`2`,`3`,`4`,`5`,`6`,`7`,`8`,`9`,`10`,`11`,`12`,`13`,`14`,`15`,`16`,`17`,`18`,`19`,`20`,`21`,`22`,`23` FROM JOURTYPE WHERE id_semaine IN ( SELECT id_semaine FROM SEMAINETYPE WHERE id_profil = '%s');"),
	AFFICHER_CONTENU_JOURTYPE_POUR_UNE_SEMAINE_LUNDI("SELECT jour,`0`,`1`,`2`,`3`,`4`,`5`,`6`,`7`,`8`,`9`,`10`,`11`,`12`,`13`,`14`,`15`,`16`,`17`,`18`,`19`,`20`,`21`,`22`,`23` FROM JOURTYPE WHERE id_semaine IN ( SELECT id_semaine FROM SEMAINE WHERE date_lundi = '%s');"),
	
	SEMAINE_PERSONNALISEE("UPDATE SEMAINE SET personnalise = 'true' WHERE id_semaine IN ( SELECT id_semaine FROM SEMAINE WHERE date_lundi = '%s');" ),
	
	RECUPERER_ID_SEMAINE("SELECT * FROM SEMAINE WHERE id_semaine = '%s';" ),
	RECUPERER_ID_SEMAINE_AVEC_DATELUNDI("SELECT id_semaine FROM SEMAINE WHERE date_lundi = '%s';" ),
	AFFICHER_SEMAINE("SELECT * FROM SEMAINE "),
	
	SUPRIMER_SEMAINE("DELETE FROM `SEMAINE` WHERE id_profil = '%s';"),
	SUPRIMER_SEMAINE_DATE("DELETE FROM `SEMAINE` WHERE date_lundi = '%s';"),
	INSERTION_SEMAINE("INSERT OR IGNORE INTO SEMAINE(date_lundi,id_profil,personnalise) VALUES('%s','%s','%s');"),
	CREATE_TABLE_SEMAINE("CREATE TABLE IF NOT EXISTS SEMAINE"+
		"(id_semaine 				INTEGER			NOT NULL 		PRIMARY KEY 		AUTOINCREMENT 	UNIQUE	,"+
		"date_lundi 				DATE			NOT NULL											UNIQUE	,"+			
		"id_profil 					INTEGER					 	 												,"+
		"personnalise 				BOOLEAN			NOT NULL													,"+
		"FOREIGN KEY(id_profil) REFERENCES PROFIL(id_profil)													);"),		 											
	
	
	RECUPERER_LES_ID("SELECT * FROM APPARTIENT ;"),
	RECUPERER_LES_ID_USER("SELECT id_utilisateur FROM APPARTIENT  ;"),
	INSERTION_ID_TABLE_APPARTIENT("INSERT INTO APPARTIENT(id_utilisateur,id_groupe) SELECT '%s','%s' WHERE NOT EXISTS (SELECT * FROM APPARTIENT WHERE id_utilisateur = '%s' AND id_groupe = '%s');"),
	SUPPRIMER_TABLE_APPARTIENT_GROUPE("DELETE FROM `APPARTIENT` WHERE id_groupe = '%s'"),
	SUPPRIMER_TABLE_APPARTIENT_UTILISATEUR("DELETE FROM `APPARTIENT` WHERE id_utilisateur = '%s'"),
	SUPPRIMER_TABLE_APPARTIENT_UTILISATEUR_IDGROUPE("DELETE FROM `APPARTIENT` WHERE id_utilisateur = '%s' AND id_groupe = '%s' ;"),
	
	CREATE_TABLE_APPARTIENT ("CREATE TABLE IF NOT EXISTS APPARTIENT"+
		"(id_utilisateur			INTEGER										,"+
		"id_groupe					INTEGER 										,"+
		"FOREIGN KEY(id_utilisateur) REFERENCES UTILISATEUR(id_utilisateur)					,"+
		"FOREIGN KEY(id_groupe) REFERENCES GROUPE(id_groupe))								;"),
	
	
	AFFICHER_TABLE_GROUPE ("SELECT * FROM GROUPE"),
	AFFICHER_INFO_DUN_GROUPE ("SELECT * FROM GROUPE WHERE nom = '%s'"),
	CREATE_TABLE_GROUPE ("CREATE TABLE IF NOT EXISTS GROUPE"+
		"(id_groupe 		INTEGER 			NOT NULL		PRIMARY KEY      	AUTOINCREMENT 		UNIQUE  ,"+
		"nom				TEXT				NOT NULL										 				,"+
		"edroit				INTEGER				NOT NULL										 				,"+
		"couleur			TEXT				NOT NULL												UNIQUE  );"),
	
	SUPRESSION_GROUPE ("DELETE FROM GROUPE WHERE `nom`='%s';"),
	INSERTION_GROUPE ("INSERT OR IGNORE INTO GROUPE(nom,edroit,couleur)VALUES('%s', '%s', '%s');"),
	MODIFICATION_GROUPE("UPDATE GROUPE SET nom = '%s', edroit = '%s' ,couleur = '%s' where id_groupe = '%s';"),
	AFFICHER_ID_GROUPE("SELECT id_groupe FROM GROUPE WHERE nom = '%s';"),
	
	AFFICHER_TOUS_LES_UTILISATEUR("SELECT * FROM UTILISATEUR WHERE Utilisateur.nom_groupe = Groupe.nom ;"),
	AFFICHER_TABLE_UTILISATEUR("SELECT * FROM UTILISATEUR"),
	AFFICHER_INFO_DUN_UTILISATEUR("SELECT * FROM UTILISATEUR where nom = '%s'"),
	AFFICHER_ID_UTILISATEUR("SELECT id_utilisateur FROM UTILISATEUR Where nom = '%s'"),
	MODIFICATION_UTILISATEUR("UPDATE UTILISATEUR SET nom = '%s' ,prenom = '%s',telephone = '%s',mail = '%s',code = '%s',couleur = '%s' where id_utilisateur = '%s';"),
	INSERTION_UTILISATEUR("INSERT OR IGNORE INTO UTILISATEUR(nom,prenom,telephone,mail,code,couleur) VALUES('%s','%s','%s','%s','%s','%s');"),
	AFFICHER_UTILISATEUR_DUN_GROUPE("SELECT utilisateur.nom, prenom FROM GROUPE,APPARTIENT,UTILISATEUR Where Groupe.id_groupe=APPARTIENT.id_groupe AND APPARTIENT.id_utilisateur=UTILISATEUR.id_utilisateur AND Groupe.nom = '%s' ;"),
	SUPPRESSION_UTILISATEUR ("DELETE FROM UTILISATEUR WHERE nom = '%s' AND prenom = '%s';"),
	
	REMETRE_A_ZERO_UTILISATEUR("ALTER TABLE UTILISATEUR AUTO_INCREMENT=0"),

	CREATE_TABLE_UTILISATEUR ("CREATE TABLE IF NOT EXISTS UTILISATEUR"+
		"(id_utilisateur 		INTEGER 		NOT NULL	PRIMARY KEY     AUTOINCREMENT 	UNIQUE	 ,"+
		"nom					TEXT			NOT NULL							 				 ,"+
		"prenom					TEXT			NOT NULL							 				 ,"+
		"telephone				INTEGER					 											 ,"+
		"mail					TEXT		  	 									  				 ,"+
		"code					TEXT			NOT NULL											 ,"+
		"couleur       			TEXT  			NOT NULL							 			);"),
	
	
	RECUPERER_LAST_ID_SEMAINE("SELECT Max(`id_semaine`) FROM SEMAINE;"),
	RECUPERER_LAST_ID_JOUR("SELECT Max(`id_jour`) FROM JOUR;"),
	RECUPERER_LAST_ID_GROUPE("SELECT Max(`id_groupe`) FROM GROUPE;"),
	RECUPERER_LAST_ID_UTILISATEUR("SELECT Max(`id_utilisateur`) FROM UTILISATEUR;"),
	RECUPERER_LAST_ID_PROFIL("SELECT Max(`id_profil`) FROM PROFIL;"),
	
	AFFICHER_JOUR("SELECT * FROM JOUR "),
									
	AFFICHER_GROUPE_DUNE_HEURE_TYPE("SELECT GROUPE.* FROM JOURTYPE,GROUPE WHERE JOURTYPE.'%s'= GROUPE.id_groupe AND jour ='%s' "),
	AFFICHER_GROUPE_DUNE_HEURE("SELECT GROUPE.* FROM JOUR,GROUPE WHERE JOUR.'%s'= GROUPE.id_groupe AND date_lundi ='%s' "),
	AFFICHER_JOURS_SEMAINE("SELECT * FROM JOUR WHERE id_semaine = '%s' "),
	AFFICHER_LISTE_SEMAINE("SELECT date_lundi FROM SEMAINE "),
	COMPTE_LIGNE_SEMAINE("SELECT COUNT (*) FROM JOUR WHERE id_semaine = '%s';"),
	AFFICHER_CONTENU_JOUR_POUR_UNE_SEMAINE("SELECT date_lundi,`0`,`1`,`2`,`3`,`4`,`5`,`6`,`7`,`8`,`9`,`10`,`11`,`12`,`13`,`14`,`15`,`16`,`17`,`18`,`19`,`20`,`21`,`22`,`23` FROM JOUR WHERE id_semaine IN ( SELECT id_semaine FROM SEMAINE WHERE date_lundi = '%s');"),
	RECUPERER_ID_JOUR_AVEC_DATELUNDI("SELECT id_jour FROM JOUR WHERE date_lundi = '%s';" ),
	INSERTION_PROFIL_DANS_SEMAINE("UPDATE SEMAINE SET id_profil = '%s' WHERE id_semaine = '%s' "),
	MODIFICATION_JOUR ("UPDATE JOUR																																																		"+
						"SET '0' = '%s', '1' = '%s', '2' = '%s', '3' = '%s', '4' = '%s', '5' = '%s', '6' = '%s', '7' = '%s', '8' = '%s', '9' = '%s', '10' = '%s', '11' = '%s', '12' = '%s', '13' = '%s', '14' = '%s', '15' = '%s'		,"+
							"'16' = '%s', '17' = '%s','18' = '%s' ,'19' = '%s', '20' = '%s', '21' = '%s', '22' = '%s', '23' = '%s'																										"+
						"WHERE date_lundi = '%s'																																														;"),
	
	INSERTION_JOUR ("INSERT OR IGNORE INTO JOUR(id_semaine,date_lundi,'0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23') VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');"),
	SUPRESSION_JOUR ("DELETE FROM `JOUR` WHERE `date` = '%s' OR `id_semaine` = '%s';"),
	SUPRESSION_JOUR_DEUX ("DELETE FROM `JOUR` WHERE  `id_jour` = '%s';"),
	CREATE_TABLE_JOUR ("CREATE TABLE IF NOT EXISTS JOUR"+
		"(id_jour 				INTEGER	 		NOT NULL 	PRIMARY KEY       AUTOINCREMENT				,"+
		"id_semaine          	INTEGER	 		NOT NULL							 					,"+
		"date_lundi				DATE	 		NOT NULL							 			UNIQUE 	,"+
		"'0'          			INTEGER  					 											,"+
		"'1'         			INTEGER  				 	 											,"+
		"'2'          			INTEGER  																,"+
		"'3'        			INTEGER  																,"+
		"'4'         			INTEGER  					 											,"+
		"'5'          			INTEGER  					 											,"+
		"'6'         			INTEGER  					 											,"+
		"'7'         			INTEGER  					 											,"+
		"'8'         			INTEGER  					 											,"+
		"'9'         			INTEGER  					 											,"+
		"'10'         			INTEGER  					 											,"+
		"'11'         			INTEGER  					 											,"+
		"'12'         			INTEGER  																,"+
		"'13'         			INTEGER  					 											,"+
		"'14'         			INTEGER  					 											,"+
		"'15'         			INTEGER  					 											,"+
		"'16'         			INTEGER  					 											,"+
		"'17'         			INTEGER  					 											,"+
		"'18'         			INTEGER  					 											,"+
		"'19'         			INTEGER  					 											,"+
		"'20'         			INTEGER  					 											,"+
		"'21'         			INTEGER  					 											,"+
		"'22'         			INTEGER  					 											,"+
		"'23'         			INTEGER  					 											,"+
		"FOREIGN KEY(id_semaine) REFERENCES SEMAINE(id_semaine)											,"+
		"FOREIGN KEY('0') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('1') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('2') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('3') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('4') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('5') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('6') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('7') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('8') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('9') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('10') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('11') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('12') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('13') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('14') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('15') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('16') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('17') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('18') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('19') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('20') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('21') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('22') REFERENCES GROUPE(id_groupe)													,"+
		"FOREIGN KEY('23') REFERENCES GROUPE(id_groupe))												;"),
	
	
	CREATE_TABLE_SEMAINETYPE("CREATE TABLE IF NOT EXISTS SEMAINETYPE"+
	"('id_semaine' 				INTEGER 				NOT NULL 		PRIMARY KEY 	AUTOINCREMENT	,"+
	"'id_profil'				INTEGER 				NOT NULL										,"+
	"'personnalise' 			BOOLEAN																,"+
	"FOREIGN KEY ('id_profil') references PROFIL(id_profil))														;"),
	
	AFFICHER_GROUPE_DUNE_HEURETYPE("SELECT GROUPE.* FROM JOURTYPE,GROUPE WHERE JOURTYPE.'%s'= GROUPE.id_groupe AND jour ='%s' "),
	AFFICHER_JOURS_SEMAINETYPE("SELECT * FROM JOURTYPE WHERE id_semaine = '%s' "),
	INSERTION_SEMAINETYPE("INSERT OR IGNORE INTO SEMAINETYPE(id_profil,personnalise) VALUES('%s','%s');"),
	RECUPERER_LAST_ID_SEMAINETYPE("SELECT Max(`id_semaine`) FROM SEMAINETYPE;"),
	RECUPERER_LAST_ID_JOURTYPE("SELECT Max(`id_jour`) FROM JOURTYPE;"),
	AFFICHER_SEMAINETYPE("SELECT * FROM SEMAINETYPE "),
	INSERTION_JOURTYPE ("INSERT INTO JOURTYPE(id_jour,id_semaine,jour,'0','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23') VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');"),
	
	//TROIS REQUETTE POUR SUPPRIMER UN PROFIL ET SES CLES ETRANGERE
	RECUPERE_IDSEMAINETYPE("SELECT id_semaine FROM SEMAINETYPE WHERE id_profil = '%s'"),
	SUPPRIMER_SEMAINETYPE("DELETE FROM SEMAINETYPE WHERE id_profil = '%s'"),
	SUPPRIMER_JOURTYPE("DELETE FROM JOURTYPE WHERE id_semaine = '%s'"),
	
	
	MODIFICATION_JOURTYPE ("UPDATE JOURTYPE SET "
			+ "	'0' = '%s'	,"
			+ "	'1' = '%s'	,"
			+ " '2' = '%s'	,"
			+ " '3' = '%s'	,"
			+ " '4' = '%s'	,"
			+ " '5' = '%s'	,"
			+ " '6' = '%s'	,"
			+ " '7' = '%s'	,"
			+ " '8' = '%s'	,"
			+ " '9' = '%s'	,"
			+ " '10' = '%s'	,"
			+ " '11' = '%s'	,"
			+ " '12' = '%s'	,"
			+ " '13' = '%s'	,"
			+ " '14' = '%s'	,"
			+ " '15' = '%s'	,"
			+ " '16' = '%s'	,"
			+ " '17' = '%s'	,"
			+ "	'18' = '%s'	,"
			+ "	'19' = '%s'	,"
			+ " '20' = '%s'	,"
			+ " '21' = '%s'	,"
			+ " '22' = '%s'	,"
			+ " '23' = '%s'	"
			+ " WHERE id_semaine = '%s' AND jour = '%s' ;"),
	
	MAJ_TABLE_JOUR("UPDATE JOUR "
			+"SET" 
			+"'0' = (SELECT JOURTYPE.'0' FROM JOURTYPE WHERE id_semaine = '%s' AND jour = '%s'),"
			+"'1' = (SELECT JOURTYPE.'1' FROM JOURTYPE WHERE id_semaine = '%s' AND jour = '%s'),"
			+"'2' = (SELECT JOURTYPE.'2' FROM JOURTYPE WHERE id_semaine = '%s' AND jour = '%s'),"
			+"'3' = (SELECT JOURTYPE.'3' FROM JOURTYPE WHERE id_semaine = '%s' AND jour = '%s'),"
			+"'4' = (SELECT JOURTYPE.'4' FROM JOURTYPE WHERE id_semaine = '%s' AND jour = '%s'),"
			+"'5' = (SELECT JOURTYPE.'5' FROM JOURTYPE WHERE id_semaine = '%s' AND jour = '%s'),"
			+"'6' = (SELECT JOURTYPE.'6' FROM JOURTYPE WHERE id_semaine = '%s' AND jour = '%s'),"
			+"'7' = (SELECT JOURTYPE.'7' FROM JOURTYPE WHERE id_semaine = '%s' AND jour = '%s'),"
			+"'8' = (SELECT JOURTYPE.'8' FROM JOURTYPE WHERE id_semaine = '%s' AND jour = '%s'),"
			+"'9' = (SELECT JOURTYPE.'9' FROM JOURTYPE WHERE id_semaine = '%s' AND jour = '%s'),"
			+"'10' = (SELECT JOURTYPE.'10' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'11' = (SELECT JOURTYPE.'11' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'12' = (SELECT JOURTYPE.'12' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'13' = (SELECT JOURTYPE.'13' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'14' = (SELECT JOURTYPE.'14' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'15' = (SELECT JOURTYPE.'15' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'16' = (SELECT JOURTYPE.'16' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'17' = (SELECT JOURTYPE.'17' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'18' = (SELECT JOURTYPE.'18' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'19' = (SELECT JOURTYPE.'19' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'20' = (SELECT JOURTYPE.'20' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'21' = (SELECT JOURTYPE.'21' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'22' = (SELECT JOURTYPE.'22' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s'),"
			+"'23' = (SELECT JOURTYPE.'23' FROM JOURTYPE WHERE id_semaine = '%s'AND jour = '%s')"
			+"WHERE id_semaine = '%s' AND date_lundi = '%s' ;										"),
	
	DEFAULT_TABLE_JOUR("UPDATE JOUR "
			+"SET" 
			+"'0' = 0,"
			+"'1' = 0,"
			+"'2' = 0,"
			+"'3' = 0,"
			+"'4' = 0,"
			+"'5' = 0,"
			+"'6' = 0,"
			+"'7' = 0,"
			+"'8' = 0,"
			+"'9' = 0,"
			+"'10' = 0,"
			+"'11' = 0,"
			+"'12' = 0,"
			+"'13' = 0,"
			+"'14' = 0,"
			+"'15' = 0,"
			+"'16' = 0,"
			+"'17' = 0,"
			+"'18' = 0,"
			+"'19' = 0,"
			+"'20' = 0,"
			+"'21' = 0,"
			+"'22' = 0,"
			+"'23' = 0"
			+" WHERE id_semaine = '%s';										"),
	
	
	CREATE_TABLE_JOURTYPE("CREATE TABLE IF NOT EXISTS JOURTYPE"+
	"('id_jour' 			INTEGER 					NOT NULL		PRIMARY KEY 	AUTOINCREMENT	,"+
	"'id_semaine' 			INTEGER 					NOT NULL										,"+
	"'jour' 				TEXT 						NOT NULL										,"+
	"'0' INTEGER																						,"+
	"'1' INTEGER																						,"+
	"'2' INTEGER																						,"+
	"'3' INTEGER																						,"+
	"'4' INTEGER																						,"+	
	"'5' INTEGER																						,"+
	"'6' INTEGER																						,"+
	"'7' INTEGER																						,"+
	"'8' INTEGER																						,"+
	"'9' INTEGER																						,"+
	"'10' INTEGER																						,"+
	"'11' INTEGER																						,"+
	"'12' INTEGER																						,"+
	"'13' INTEGER																						,"+
	"'14' INTEGER																						,"+
	"'15' INTEGER																						,"+
	"'16' INTEGER																						,"+
	"'17' INTEGER																						,"+
	"'18' INTEGER																						,"+
	"'19' INTEGER																						,"+
	"'20' INTEGER																						,"+
	"'21' INTEGER																						,"+
	"'22' INTEGER																						,"+
	"'23' INTEGER																						,"+
	"FOREIGN KEY ('id_semaine') REFERENCES SEMAINE_TYPE);												");
	
	String requeteSQL = new String();
	
	private ReqSQLite(String requeteSQL) { this.requeteSQL = requeteSQL;	}
	
	public String getRequeteSQL() { return this.requeteSQL;	}
}
