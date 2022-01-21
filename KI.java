import java.awt.Color;
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
    
    public void ermittleZuSpielendeSpalte(){
        //Hier kommt der MINMAX hin
        setzeZuSpielendeSpalte(2);
    }
}
