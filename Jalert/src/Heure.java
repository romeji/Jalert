public class Heure {
	
		private long heure ;
		private Jour jour ;
		private JourType jours ;
		private Groupe groupe;
		
		/**
		 * Constructeur par défaut
		 * @param pheure
		 * @param pgroupe
		 * @param pjour
		 */
		public Heure(long pheure, Groupe pgroupe, Jour pjour)
		{
			System.out.println("Création d'une Heure");
			this.setHeure(pheure) ;
			this.groupe = pgroupe;
			this.setJour(pjour);
		}
		
		/**
		 * Constructeur avec des paramètres
		 * @param pheure
		 * @param pgroupe
		 * @param pjour
		 */
		public Heure(long pheure, Groupe pgroupe, JourType pjour)
		{
			System.out.println("Création d'une Heure");
			this.setHeure(pheure) ;
			this.groupe = pgroupe;
			this.setJours(pjour);
		}
		
		/**
		 * Getter/Setter
		 */
		public Groupe getGroupe(){
			return groupe;
		}
	
		public void setGroupe(Groupe groupe){
			this.groupe = groupe;
		}
	
		public long getHeure() {
			return heure;
		}
	
		public void setHeure(long heure) {
			this.heure = heure;
		}
	
		public JourType getJours() {
			return jours;
		}
	
		public void setJours(JourType jours) {
			this.jours = jours;
		}
	
		public Jour getJour() {
			return jour;
		}
	
		public void setJour(Jour jour) {
			this.jour = jour;
		}
	
}




