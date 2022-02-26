import java.awt.Color;
import java.util.Arrays;

/**
 * Beschreiben Sie hier die Klasse Modell.
 * 
 * @author (Anna, Antonia, Florian) 
 * @version (22.09.21, 16:20)
 */
public class Modell
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private Spielsituation spielsituation;
    
    /**
     * Konstruktor für Objekte der Klasse Modell
     */
    public Modell(int KITiefensuche, int suchmodus)
    {
        Stein [][] modell = new Stein[6][7];
        Spieler spieler1 = new Benutzer(Color.red);
        Spieler spieler2 = new KI(Color.blue, KITiefensuche, suchmodus);
        Spieler aktuellerSpieler = spieler1;
        
        spielsituation = new Spielsituation(modell, aktuellerSpieler, spieler1, spieler2);
    }

    public Spielsituation gibAktuelleSpielsituation(){
        return spielsituation;
    }
    
    public void benutzereingabe(int spalte){
        if(gibAktuelleSpielsituation().gibAktuellenSpieler() instanceof Benutzer){
            gibAktuelleSpielsituation().fuehreZugAus(new Zug(spalte));
        }
    }
    
    public void kiEingabe(){
        if(!gibAktuelleSpielsituation().pruefeGewonnen()
            && gibAktuelleSpielsituation().gibAktuellenSpieler() instanceof KI){
            KI ki = (KI)gibAktuelleSpielsituation().gibAktuellenSpieler();
            gibAktuelleSpielsituation().fuehreZugAus(ki.ermittleNaechstenZug(gibAktuelleSpielsituation()));
        }
    }
}
