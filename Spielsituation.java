import java.awt.Color;

/**
 * Beschreiben Sie hier die Klasse Spielsituation.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Spielsituation
{
    private Stein [][] modell;
    private Spieler aktuellerSpieler;
    private Spieler spieler1;
    private Spieler spieler2;
    
    private int bewertung;
    private Zug letzterZug;
    
    public Spielsituation(Stein[][] modell, Spieler aktuellerSpieler, Spieler spieler1, Spieler spieler2){
        this.modell = modell;
        this.aktuellerSpieler = aktuellerSpieler;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
    }
    
    public Zug gibLetztenZug(){
        return letzterZug;
    }
    
    public void setzeBewertung(int bewertung){
        this.bewertung = bewertung;
    }
    
    public int gibBewertung(){
        return bewertung;
    }
    
    public void fuehreZugAus(Zug zug){
        setzeStein(zug.gibZug());
        letzterZug = zug;
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
    
    public Spieler gibAktuellenSpieler(){
        return aktuellerSpieler;
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
        if(aktuellerSpieler.getColor() == spieler1.getColor())
        {
            aktuellerSpieler = spieler2;
        }
        else
        {
            aktuellerSpieler = spieler1;
        }
    }
    
    /**
     * Wenn der Spieler noch Steine vorhanden hat, holt sich das Modell den Stein vom aktuellen Spieler.
     * Die aktuell mögliche Zeile wird ermittelt. Sollte diese != -1 sein, wird das Modell mit dem
     * Stein an aktueller Position erweitert. Sollte noch kein Spieler gewionnen haben, wird der 
     * Spieler gewechselt.
     * 
     * Die Spalte wird von dem Spieler als zuSpielendeSpalte genommen
     */
    public void setzeStein(int spalte)
    {
        if(aktuellerSpieler.steineVorhanden())
        {
            Stein aktuell = aktuellerSpieler.gibAktuellenStein();
            aktuell.setX(spalte*100+10);
            int zeile = gibZeileZurSpalte(spalte);
            
            if(zeile != -1)
            {
                aktuell.setY(zeile * 100+110);
                modell[zeile][spalte] = aktuell;
                if(!pruefeGewonnen()){
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
    public int gibZeileZurSpalte(int spalte)
    {
        if(modell[0][spalte] == null)
        {
            int i = modell.length-1;
            while(modell[i][spalte] != null)
            {
                i = i - 1;
            }
            return i;
        }
        return -1;
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
    
    public String spielsituationZuString(){
        String s = " \n ";
        
        for(int i= 0; i < modell.length; i++){
            for(int j = 0; j< modell[i].length; j++){
                if(modell[i][j] != null){
                    s = s + modell[i][j].steinZuChar();
                }else{
                    s = s + "o";
                }
            }
            
            s = s +" \n ";
        }
        
        return s;
    }
    
    public Spielsituation gibKopie(){
        Stein[][] modellKopie = new Stein[6][7];
        
        for(int i = 0; i < modell.length; i++){
            for(int j = 0; j < modell[i].length; j++){
                modellKopie[i][j] = modell[i][j];
            }
        }
        
        return new Spielsituation(modellKopie, aktuellerSpieler.gibKopie(), spieler1.gibKopie(), spieler2.gibKopie());
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
