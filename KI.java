import java.awt.Color;
import java.util.Random;

/**
 * Beschreiben Sie hier die Klasse KI.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class KI extends Spieler
{
    private int suchtiefe;
    
    public KI(Color color, int suchtiefe){
        super(color);
        this.suchtiefe = suchtiefe;
    }
    
    public Zug ermittleNachestenZug(Spielsituation s){
        List<Zug> zuege = s.gibMoeglicheZuege();
        
        Random random = new Random();
        
        int i = 0;
        zuege.toFirst();
        while(zuege.hasAccess()){
            i++;
            zuege.next();
        }
        
        
        
        return new Zug(random.nextInt(i));
    }
}
