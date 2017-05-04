import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OutilsDate{
    
	/**
     * Permet d'obtenir l'année d'une date
     * @param date (Date)
     * @return Retourne l'année de l'objet Date (int)
     */
    public static int getAnnee(Date date){
        if (date == null)
            date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Permet d'obtenir le mois d'une date
     * @param date (Date)
     * @return Retourne le mois de l'objet Date (int)
     */
    public static int getMois(Date date){
        if (date == null)
            date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }

    /**
     * Permet d'obtenir le jour d'une date
     * @param date (Date)
     * @return Retourne le numéro du jour dans le mois de l'objet Date
     */
    public static int getJour(Date date){
        if (date == null)
            date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Permet d'obtenir la date celon un pattern
     * @param date (date)
     * @param datePattern(String)
     * @return dateFormatted
     */
    public static String getStringDepuisDate(Date date, String datePattern){
        if (date == null)
            return "null";

        if (datePattern == null)
            datePattern = "yyyy-MM-dd";

        SimpleDateFormat sDF = new SimpleDateFormat(datePattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        sDF.setCalendar(calendar);
        String dateFormatted = sDF.format(date.getTime());
        return dateFormatted;
    }

    
    /**
     * Méthode retourne un Arraylist des 7 dates de la semaine 
     * @param annee
     * @param semaine
     * @return dateDepartSemaine
     */
    public static Date getDateDepuisNumeroSemaine(int annee, int semaine){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, annee);
        cal.set(Calendar.WEEK_OF_YEAR, semaine);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date dateDepartSemaine = new Date();
        dateDepartSemaine.setTime(cal.getTimeInMillis());

        return dateDepartSemaine;
    }

/**
 * Mettre toutes les heures à 0
 * @param date
 * @return date 
 */
    public static Date resetDateHour(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date2 = new Date();
        date2.setTime(cal.getTimeInMillis());
        return date2;
    }
  

    
    /**
     * Récupère le numéro de la semaine depuis une date
     * @param date
     * @return cal
     */
    public static int DateSemaine(Date date){
    	Calendar cal = Calendar.getInstance();
    	 cal.setTime(date);
    	 return cal.get(Calendar.WEEK_OF_YEAR);
    }


    /**
     * Méthode qui retourne un Arraylist des 7 jours de la semaine
     * @param annee
     * @param semaine
     * @return Arraylist (dates)
     */
	   public static ArrayList<java.sql.Date> getDatesFromWeekNumber(int annee, int semaine)
	    {
	        ArrayList<java.sql.Date> dates = new ArrayList<>();
	
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.YEAR, annee);
	        cal.set(Calendar.WEEK_OF_YEAR, semaine);
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	        // cal.set(Calendar.HOUR_OF_DAY, 0);
	        //cal.set(Calendar.MINUTE, 0);
	        //cal.set(Calendar.SECOND, 0);
	        //cal.set(Calendar.MILLISECOND, 0);
	       
	        java.sql.Date dateDepartSemaine = new java.sql.Date(cal.getTimeInMillis()); 
	        //récupère le timestamp; calcule la difference en milliseconde entre datedepartsemaine et le 1 janvier 1990
	        dates.add(dateDepartSemaine);
	
	        for(int i = 1; i < 7; i++)
	        {
	            cal.add(Calendar.DAY_OF_MONTH, 1);
	            java.sql.Date jourSuivant = new java.sql.Date(cal.getTimeInMillis());
	            dates.add(jourSuivant);
	        }
	
	        return dates;
	    }

}