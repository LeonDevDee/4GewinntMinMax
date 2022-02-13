import java.awt.Color;

/**
 * Beschreiben Sie hier die Klasse Spieler.
 * 
 * @author Raphaél und Alex
 * @version 21.09.2021
 */
public abstract class Spieler
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    protected Color color;
    protected Stein [] steine;
    protected int aktuellerStein;
    
    /**
     * Konstruktor für Objekte der Klasse Spieler
     * 
     * @param color Farbe des Spielers
     */
    public Spieler(Color color)
    {
        this.color = color;
        aktuellerStein = 20;
        initSteine();
    }
    
    /**
     * Gibt eine neue Instanz mit gleichen Eigenschaften zurück
     */
    public abstract Spieler gibKopie();

    /**
     * Weist dem Array steine neue Steinobjekte zu.
     */
    public void initSteine()
    {
        steine = new Stein[21];
        for(int i = 0; i <= steine.length-1; i++)
        {
            steine[i] = new Stein(40, color);   
        }
    }

    /**
     * Gibt zurück, ob in dem Array steine noch Steinobjekte vorhanden sind.
     * 
     * @return Wahrheitswert, ob der Spieler noch Steine hat
     */
    public boolean steineVorhanden()
    {
        if(aktuellerStein > 0)
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    /**
     * Gibt die Farbe der Objekte zurück.
     * @return Color Farbe des Spielers
     */ 
    public Color getColor()
    {
        return color;
    }

    /**
     * Gibt zurück, ob der aktuelle Stein das Steinobjekt an Position 20 im Array steine ist,
     * da damit der Spieler auch der Startspieler ist.
     * 
     * @return Wahrheitswert, ob der Spieler beginnt
     */
    public boolean istStartspieler()
    {
        boolean rickroll = false;
        if(steine[20] == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Diese Methode nimmt den aktuellen Stein aus dem Array (=> null setzen) und gibt
     * das Objekt an dieser Position zurück. Der Wert aktuellerStein wird um 1 verringert.
     * 
     * @return aktuellen Stein
     */
    public Stein gibAktuellenStein()
    {
        Stein akt = steine[aktuellerStein];
        steine[aktuellerStein] = null;
        aktuellerStein--;
        return akt;
    }
    
}
