import java.awt.Color;

/**
 * Beschreiben Sie hier die Klasse Spielsituation.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Spielsituation
{
    private Stein [][] modell; // [zeile][spalte]
    private Spieler aktuellerSpieler;
    private Spieler spieler1;
    private Spieler spieler2;

    private int bewertung;
    private Zug letzterZug;
    
    /**
     * Konstruktor der Spielsituation
     * 
     * @param modell Spielfeld
     * @param aktuellerSpieler Spieler, der in der Spielsituation am Zug ist
     * @param spieler1 Spieler1
     * @param spieler2 Spieler2
     */ 
    public Spielsituation(Stein[][] modell, Spieler aktuellerSpieler, Spieler spieler1, Spieler spieler2){
        this.modell = modell;
        this.aktuellerSpieler = aktuellerSpieler;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
    }
    
    /**
     * Gibt den letzten Zug zurück
     * 
     * @return letzter Zug
     */ 
    public Zug gibLetztenZug(){
        return letzterZug;
    }
    
    /**
     * Setzt die Situationsbewertung auf den übergebenen Wert
     * 
     * @param bewertung Situationsbewertung
     */ 
    public void setzeBewertung(int bewertung){
        this.bewertung = bewertung;
    }
    
    /**
     * Gibt die Situationsbewertung zurück
     * 
     * @return Situationsbewertung
     */ 
    public int gibBewertung(){
        return bewertung;
    }
    
    /**
     * Führt den übergebenen Zug z aus
     * 
     * @param z auszuführender Zug
     */ 
    public void fuehreZugAus(Zug z){
        setzeStein(z.gibZug());
        letzterZug = z;
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
     * Gibt aktuellen Spieler zurück
     * 
     * @return aktueller Spieler
     */
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
    
    /**
     * Gibt neu Instanz der Spielsituation mit selben Werten zurück
     * 
     * @return Kopie 
     */
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
     * Gibt Anzahl der Dreierreihen zurück
     * 
     * @return Anzahl Dreierreihen
     */
    public int zaehleDreierreihen(Color c){
        return zaehleDreiDiagonalLR(c) + zaehleDreiDiagonalRL(c) + zaehleDreiInEinerSpalte(c) + zaehleDreiInEinerZeile(c);
    }
    
    /**
     * Teil der Dreierreihenzahlung
     * 
     * @return Anzahl Dreierreihen Verikal
     */
    private int zaehleDreiInEinerSpalte(Color c){
        int anzahl = 0;

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                if(i == 3){
                    if(modell[i][j] != null && modell[i+1][j] != null && modell[i+2][j] != null){
                        if(modell[i][j].getColor() == c && modell[i+1][j].getColor() == c && modell[i+2][j].getColor() == c){
                            anzahl++;
                        }
                    }
                }else{
                    if(modell[i][j] != null && modell[i+1][j] != null && modell[i+2][j] != null){
                        if(modell[i+3][j] != null){
                            if(modell[i][j].getColor() == c && modell[i+1][j].getColor() == c && modell[i+2][j].getColor() == c && modell[i+3][j].getColor() != c){
                                anzahl++;
                            }
                        }else{
                            if(modell[i][j].getColor() == c && modell[i+1][j].getColor() == c && modell[i+2][j].getColor() == c){
                                anzahl++;
                            }
                        }
                    }
                }
            }
        }

        return anzahl;
    }
    
    /**
     * Teil der Dreierreihenzahlung
     * 
     * @return Anzahl Dreierreihen Horizontal
     */
    private int zaehleDreiInEinerZeile(Color c){
        int anzahl = 0;

        for (int i = 0; i < modell.length; i++){
            for (int j = 0; j < 5; j++){
                if(j == 4){
                    if(modell[i][j] != null && modell[i][j+1] != null && modell[i][j+2] != null){
                        if(modell[i][j].getColor() == c && modell[i][j+1].getColor() == c && modell[i][j+2].getColor() == c){
                            anzahl++;
                        }
                    }
                }else{
                    if(modell[i][j] != null && modell[i][j+1] != null && modell[i][j+2] != null){
                        if(modell[i][j+3] != null){
                            if(modell[i][j].getColor() == c && modell[i][j+1].getColor() == c && modell[i][j+2].getColor() == c && modell[i][j+3].getColor() != c){
                                anzahl++;
                            }
                        }else{
                            if(modell[i][j].getColor() == c && modell[i][j+1].getColor() == c && modell[i][j+2].getColor() == c){
                                anzahl++;
                            }
                        }
                    }
                }
            }
        }

        return anzahl;
    }
    
    /**
     * Teil der Dreierreihenzahlung
     * 
     * @return Anzahl Dreierreihen Diagonal Linksoben Rechtsunten
     */
    private int zaehleDreiDiagonalLR(Color c){
        int anzahl = 0;

        for (int i = 2; i < modell.length; i++){
            for (int j = 0; j < 5; j++){
                if(i == 2 || j == 4){
                    if(modell[i][j] != null && modell[i-1][j+1] != null && modell[i-2][j+2] != null){
                        if(modell[i][j].getColor() == c && modell[i-1][j+1].getColor() == c && modell[i-2][j+2].getColor() == c){
                            anzahl++;
                        }
                    }
                }else{
                    if(modell[i][j] != null && modell[i-1][j+1] != null && modell[i-2][j+2] != null){
                        if(modell[i-3][j+3] != null){
                            if(modell[i][j].getColor() == c && modell[i-1][j+1].getColor() == c && modell[i-2][j+2].getColor() == c && modell[i-3][j+3].getColor() != c){
                                anzahl++;
                            }
                        }else{
                            if(modell[i][j].getColor() == c && modell[i-1][j+1].getColor() == c && modell[i-2][j+2].getColor() == c){
                                anzahl++;
                            }
                        }
                    }
                }
            }
        }

        return anzahl;
    }
    
    /**
     * Teil der Dreierreihenzahlung
     * 
     * @return Anzahl Dreierreihen Diagonal Rechtsoben linksunten
     */
    private int zaehleDreiDiagonalRL(Color c){
        int anzahl = 0;

        for (int i = modell.length-1; i > 1; i--){
            for (int j = modell[0].length-1; j > 1; j--){
                if(i == 2 || j == 2){
                    if(modell[i][j] != null && modell[i-1][j-1] != null && modell[i-2][j-2] != null){
                        if(modell[i][j].getColor() == c && modell[i-1][j-1].getColor() == c && modell[i-2][j-2].getColor() == c){
                            anzahl++;
                        }
                    }
                }else{
                    if(modell[i][j] != null && modell[i-1][j-1] != null && modell[i-2][j-2] != null){
                        if(modell[i-3][j-3] != null){
                            if(modell[i][j].getColor() == c && modell[i-1][j-1].getColor() == c && modell[i-2][j-2].getColor() == c && modell[i-3][j-3].getColor() != c){
                                anzahl++;
                            }
                        }else{
                            if(modell[i][j].getColor() == c && modell[i-1][j-1].getColor() == c && modell[i-2][j-2].getColor() == c){
                                anzahl++;
                            }
                        }
                    }
                }
            }
        }

        return anzahl;
    }
    
    /**
     * Gibt Anzahl der Zweierreihen zurück
     * 
     * @return Anzahl Zweierreihen
     */
    public int zaehleZweierreihen(Color c){
        return zaehleZweiDiagonalLR(c) + zaehleZweiDiagonalRL(c) + zaehleZweiInEinerSpalte(c) + zaehleZweiInEinerZeile(c);
    }
    
    /**
     * Teil der Zweierreihenzahlung
     * 
     * @return Anzahl Zweierreihen Vertikal
     */
    private int zaehleZweiInEinerSpalte(Color c){
        int anzahl = 0;

        for(int i = 0; i < 5; i++){
            for(int j = 0; j< modell[0].length;j++){
                if(i == 4){
                    if(modell[i][j] != null && modell[i+1][j] != null){
                        if(modell [i][j].getColor() == c && modell[i+1][j].getColor() == c){
                            anzahl++;
                        } 
                    }
                }else{
                    if(modell[i][j] != null && modell[i+1][j] != null){
                        if(modell[i+2][j] != null){
                            if(modell [i][j].getColor() == c && modell[i+1][j].getColor() == c && modell[i+2][j].getColor() != c){
                                anzahl++;
                            }
                        }else{
                            if(modell [i][j].getColor() == c && modell[i+1][j].getColor() == c){
                                anzahl++;
                            } 
                        }
                    }
                }
            }
        }

        return anzahl;
    }
    
    /**
     * Teil der Zweierreihenzahlung
     * 
     * @return Anzahl Zweierreihen Horizontal
     */
    private int zaehleZweiInEinerZeile(Color c){
        int anzahl = 0;

        for(int i = 0; i < modell.length; i++){
            for(int j = 0; j < 6; j++){
                if(j == 5){
                    if(modell[i][j] != null && modell[i][j+1] != null){
                        if(modell[i][j].getColor() == c && modell[i][j+1].getColor() == c){
                            anzahl++;
                        }
                    }
                }else{
                    if(modell[i][j] != null && modell[i][j+1] != null){
                        if(modell[i][j+2] != null){
                            if(modell[i][j].getColor() == c && modell[i][j+1].getColor() == c && modell[i][j+2].getColor() != c){
                                anzahl++;
                            }
                        }else{
                            if(modell[i][j].getColor() == c && modell[i][j+1].getColor() == c){
                                anzahl++;
                            }
                        }
                    }
                }
            }
        }
        return anzahl;
    }
    
    /**
     * Teil der Zweierreihenzahlung
     * 
     * @return Anzahl Zweierreihen Digonal linksunten Rechtsoben
     */
    private int zaehleZweiDiagonalLR(Color c){
        int anzahl = 0;

        for(int i = 1; i < modell.length; i++){
            for(int j = 0; j < 6; j++){
                if(i == 1 || j == 5){
                    if(modell[i][j] != null && modell[i-1][j+1] != null){
                        if(modell[i][j].getColor() == c && modell[i-1][j+1].getColor() == c){
                            anzahl++;
                        }
                    }
                }else{
                    if(modell[i][j] != null && modell[i-1][j+1] != null){
                        if(modell[i-2][j+2] != null){
                            if(modell[i][j].getColor() == c && modell[i-1][j+1].getColor() == c && modell[i-2][j+2].getColor() == c){
                                anzahl++;
                            }
                        }else{
                            if(modell[i][j].getColor() == c && modell[i-1][j+1].getColor() == c){
                                anzahl++;
                            }
                        }
                    }
                }
            }
        }

        return anzahl;
    }
    
    /**
     * Teil der Zweierreihenzahlung
     * 
     * @return Anzahl Zweierreihen Diagonal Rechtsoben linksunten
     */
    private int zaehleZweiDiagonalRL(Color c){
        int anzahl = 0;

        for (int i = modell.length-1; i > 0; i--){
            for (int j = modell[0].length-1; j > 0; j--){
                if(i == 1 || j == 1){
                    if(modell[i][j] != null && modell[i-1][j-1] != null){
                        if(modell[i][j].getColor() == c && modell[i-1][j-1].getColor() == c){
                            anzahl++;
                        }
                    }
                }else{
                    if(modell[i][j] != null && modell[i-1][j-1] != null){
                        if(modell[i-2][j-2] != null){
                            if(modell[i][j].getColor() == c && modell[i-1][j-1].getColor() == c && modell[i-2][j-2].getColor() != c){
                                anzahl++;
                            }
                        }else{
                            if(modell[i][j].getColor() == c && modell[i-1][j-1].getColor() == c){
                                anzahl++;
                            }
                        }
                    }
                }
            }
        }

        return anzahl;
    }

    /**
     * Wenn vier Steine einer gleichen Farbe in einer Spalte nebeneinanderliegen gebe true zurück, ansonsten false.
     * @return Wahrheitswert
     */
    private boolean pruefeVierInEinerSpalte ()
    {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j< modell[0].length;j++){
                if(modell[i][j] != null && modell[i+1][j] != null && modell[i+2][j] != null && modell[i+3][j] != null && modell [i][j].getColor() == aktuellerSpieler.getColor()){
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
        for(int i = 0; i < modell.length; i++)
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
     * Wenn vier Steine einer Farbe in einer Diagonalen von links oben nach rechts unten vorhanden sind, gebe true zurück, ansonsten false.
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
     * Wenn vier Steine einer Farbe in einer Diagonalen von rechts oben nach links unten vorhanden sind, gebe true zurück, ansonsten false.
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
