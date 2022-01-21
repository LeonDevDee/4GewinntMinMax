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
    private Stein [][] modell; //6/7 erste Stelle Zeilen, zweite Stelle die Spalten
    private Spieler aktuellerSpieler;
    private Spieler spieler1;
    private Spieler spieler2;

    /**
     * Konstruktor für Objekte der Klasse Modell
     */
    public Modell()
    {
        modell = new Stein[6][7];
        spieler1 = new Benutzer(Color.red);
        spieler2 = new KI(Color.yellow,3);
        aktuellerSpieler = spieler1;
    }

    /**
     * Gibt ein 2D-Array mit allen im Attribut modell gespeicherten Steinen zurück
     * 
     * @return Stein[][]
     */    
    public Stein [][] gibSteine()
    {
        return modell;
    }

    /**
     * Wenn der Spieler noch Steine vorhanden hat, holt sich das Modell den Stein vom aktuellen Spieler.
     * Die aktuell mögliche Zeile wird ermittelt. Sollte diese != -1 sein, wird das Modell mit dem
     * Stein an aktueller Position erweitert. Sollte noch kein Spieler gewionnen haben, wird der 
     * Spieler gewechselt.
     * 
     * Die Spalte wird von dem Spieler als zuSpielendeSpalte genommen
     */
    public void setzeStein()
    {
        if(aktuellerSpieler.steineVorhanden())
        {
            int spalte = aktuellerSpieler.gibZuSpielendeSpalte();
            
            Stein aktuell = aktuellerSpieler.gibAktuellenStein();
            aktuell.setX(spalte*100+10);
            int zeile = gibZeile(spalte);
            if(zeile != -1)
            {
                aktuell.setY(zeile * 100+110);
                modell[zeile][spalte] = aktuell;
                aktuellerSpieler.setzeZuSpielendeSpalte(-1);
                if(pruefeGewonnen() == false)
                {
                    spielerWechseln();
                }
            }
        }
    }

    /**
     * Hier wird die aktuelle Zeile zurückgegeben. Sollte auch die oberste Zeile belegt sein, wird - 1
     * zurück gegeben.
     * 
     * @param spalte - gibt die Spalte an, die vom Spieler gewählt wurde (Spaltenzählung ab 0)
     * @return zeilenwert - gibt die Zeile an, die von oben gesehen noch frei ist
     */
    public int gibZeile(int spalte)
    {
        if(modell[0][spalte] == null)
        {
            int i = modell.length-1;
            while(modell[i][spalte] != null)
            {
                i = i - 1;
            }
            System.out.println(i);
            return i;
        }
        return -1;
    }

    /**
     * Gib die Farbe des Attributes aktuellerSpieler zurück
     * 
     * @return Color
     */
    public Color gibFarbeAktuellerSpieler()
    {
        return aktuellerSpieler.getColor();
    }

    /**
     * Überprüft, welcher Spieler im Attribut AktuellerSpieler gespeichert ist und setzt den anderen 
     * Spieler als AktuellerSpieler. 
     */
    private void spielerWechseln()
    {
        if(aktuellerSpieler == spieler1)
        {
            aktuellerSpieler = spieler2;
        }
        else
        {
            aktuellerSpieler = spieler1;
        }
    }

    /**
     * Hier werden alle Pruefemethoden aufgerufen.
     * @return Wahrheitswert
     */
    public boolean pruefeGewonnen ()
    {
        if(pruefeVierInEinerSpalte() == true || pruefeVierInEinerZeile() == true)
        {
            return true;
        }
        else if(pruefeVierDiagonalLR() == true || pruefeVierDiagonalRL() == true)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    
    public void benutzereingabe(int spalte){
        if(aktuellerSpieler instanceof Benutzer){
            aktuellerSpieler.setzeZuSpielendeSpalte(spalte);
        }
    }
    
    public void modellUpdate(){
        if(aktuellerSpieler.gibZuSpielendeSpalte() != -1){
            setzeStein();
        }else if(aktuellerSpieler instanceof KI){
            KI ki = (KI)aktuellerSpieler;
            ki.ermittleZuSpielendeSpalte();
            setzeStein();
        }
    }

    /**
     * Wenn vier Steine einer gleichen Farbe in einer Spalte nebeneinanderliegen gebe true zurück, ansonsten false.
     * @return Wahrheitswert
     */
    private boolean pruefeVierInEinerSpalte ()
    {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j< modell[0].length;j++){
                if(modell[i][j] != null && modell [i][j].getColor() == aktuellerSpieler.getColor()){
                    if(modell[i+1][j].getColor() == aktuellerSpieler.getColor() &&
                    modell[i+2][j].getColor() == aktuellerSpieler.getColor() &&
                    modell[i+3][j].getColor() == aktuellerSpieler.getColor()){
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * Wenn vier Steine einer gleichen Farbe in einer Zeile nebeneinanderliegen gebe true zurück, ansonsten false.
     * @return Wahrheitswert
     */
    private boolean pruefeVierInEinerZeile ()
    {
        Color col = gibFarbeAktuellerSpieler();
        for(int i = 0; i < 6; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                if(modell[i][j] != null && modell[i][j+1] != null && modell[i][j+2] != null && modell[i][j+3] != null)
                {
                    if(modell[i][j].getColor() == col && modell[i][j+1].getColor() == col && modell[i][j+2].getColor() == col && modell[i][j+3].getColor() == col)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Wenn vier Steine einer Farbe in einer Diagonalen von links nach rechts vorhanden sind, gebe true zurück, ansonsten false.
     * @return Wahrheitswert
     */
    private boolean pruefeVierDiagonalLR()
    {

        for(int i = 3; i < 6; i++){
            for(int j = 0; j < 4; j++){
                if(modell[i][j] != null)
                {
                    Color steinFarbe = modell[i][j].getColor();
                    if(steinFarbe == aktuellerSpieler.getColor()){
                        if(modell[i-1][j+1] != null && steinFarbe == modell[i-1][j+1].getColor() && 
                        modell[i-2][j+2] != null && steinFarbe == modell[i-2][j+2].getColor() &&
                        modell[i-3][j+3] != null && steinFarbe == modell[i-3][j+3].getColor()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    /**
     * Wenn vier Steine einer Farbe in einer Diagonalen von rechts nach links vorhanden sind, gebe true zurück, ansonsten false.
     * @return Wahrheitswert
     */
    private boolean pruefeVierDiagonalRL ()
    {
        for (int i = modell.length-1; i >= 0; i--)
        {
            for (int j = modell[0].length-1; j > 0; j--)
            {
                if(modell[i][j] != null)
                {
                    if (modell[i][j].getColor().equals(aktuellerSpieler.getColor()) == true)
                    {
                        if (j > 2 && i > 2)
                        {
                            if(modell[i-1][j-1] != null && modell[i-2][j-2] != null && modell[i-3][j-3] != null)
                            {
                                if ((modell[i-1][j-1].getColor().equals(aktuellerSpieler.getColor()) == true) && (modell[i-2][j-2].getColor().equals(aktuellerSpieler.getColor()) == true) && (modell[i-3][j-3]).getColor().equals(aktuellerSpieler.getColor()) == true)
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
}
