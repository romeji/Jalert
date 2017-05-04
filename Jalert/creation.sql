 CREATE TABLE PROFIL
          				(id_profil 			INTEGER  PRIMARY KEY     AUTOINCREMENT,
        		  		nom					TEXT				 NOT NULL,
          				couleur				TEXT				 NOT NULL);

CREATE TABLE SEMAINE
	(id_semaine 			INTEGER		 			NOT NULL,
	date_lundi 				INTEGER	PRIMARY KEY 		 NOT NULL,
	id_profil 				INTEGER					 	 NOT NULL,
	personalise 			BOOLEAN					 NOT NULL,
	FOREIGN KEY(id_profil) REFERENCES PROFIL(id_profil));
						
						CREATE TABLE ALARME
                    	(id_alarme 				INTEGER PRIMARY KEY      AUTOINCREMENT,
                    	adresse_alarme			TEXT			     NOT NULL,
                    	message					TEXT				 NOT NULL,
                    	dernier_utilisateur		TEXT				 NOT NULL,
                    	date_dernier_envoi		DATE			     NOT NULL);
          
                    
          CREATE TABLE PROJET
      			(id_projet 						INTEGER PRIMARY KEY    	 AUTOINCREMENT,
      			nom								TEXT				 NOT NULL,
      			version							DOUBLE				 NOT NULL,
      			tempo_Appel						DOUBLE				 NOT NULL,
      			langue       					INTEGER   				 NOT NULL);
						
						CREATE TABLE UTILISATEUR
                  		(id_utilisateur 		INTEGER PRIMARY KEY      AUTOINCREMENT,
                  		nom						TEXT				 NOT NULL,
                  		prenom					TEXT			     NOT NULL,
                  		telephone				INTEGER					 NOT NULL,
                  		mail					TEXT			     NOT NULL,
                  		code					TEXT				 NOT NULL,
                  		couleur       			TEXT  				 NOT NULL);
          
		  
						CREATE TABLE GROUPE
                 		(id_groupe 				INTEGER PRIMARY KEY      AUTOINCREMENT,
                 		nom						TEXT				 NOT NULL,
                 		edroit					INTEGER				     NOT NULL,
                 		couleur					TEXT				 NOT NULL,
          				id_utilisateur			INTEGER					 NOT NULL,
          				FOREIGN KEY(id_utilisateur) REFERENCES UTILISATEUR(id_utilisateur));
						
						CREATE TABLE JOUR
    		  			(id_jour 				INTEGER	PRIMARY KEY      	 AUTOINCREMENT,
    		  			id_semaine          	INTEGER  					 NOT NULL,
    		  			date_lundi 				INTEGER						 ,
    		  			'0'						INTEGER			     	 	 ,
    		  			'1'          			INTEGER  					 ,
    		  			'2'         			INTEGER  				 	 ,
    		  			'3'          			INTEGER  					 ,
    		  			'4'        				INTEGER  					 ,
    		  			'5'         			INTEGER  					 ,
    		  			'6'          			INTEGER  					 ,
    		  			'7'         			INTEGER  					 ,
    		  			'8'         			INTEGER  					 ,
    		  			'9'         			INTEGER  					 ,
    		  			'10'         			INTEGER  					 ,
    		  			'11'         			INTEGER  					 ,
    		  			'12'         			INTEGER  					 ,
    		  			'13'         			INTEGER  					 ,
    		  			'14'         			INTEGER  					 ,
    		  			'15'         			INTEGER  					 ,
    		  			'16'         			INTEGER  					 ,
    		  			'17'         			INTEGER  					 ,
    		  			'18'         			INTEGER  					 ,
    		  			'19'         			INTEGER  					 ,
    		  			'20'         			INTEGER  					 ,
						'21'         			INTEGER  					 ,
    		  			'22'         			INTEGER  					 ,
    		  			'23'         			INTEGER  					 ,
    		  			'24'         			INTEGER  					 ,
    		  			FOREIGN KEY(id_semaine) REFERENCES SEMAINE(id_semaine),
    		  			FOREIGN KEY('0') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('1') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('2') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('3') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('4') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('5') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('6') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('7') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('8') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('9') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('10') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('11') REFERENCES GROUPE(id_groupe),
						FOREIGN KEY('12') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('13') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('14') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('15') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('16') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('17') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('18') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('19') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('20') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('21') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('22') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('23') REFERENCES GROUPE(id_groupe),
    		  			FOREIGN KEY('24') REFERENCES GROUPE(id_groupe),
						FOREIGN KEY(date_lundi) REFERENCES SEMAINE(date_lundi));