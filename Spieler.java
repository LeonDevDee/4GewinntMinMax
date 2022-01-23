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
     */
    public Spieler(Color color)
    {
        this.color = color;
        aktuellerStein = 20;
        initSteine();
    }

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
     * @return Wahrheitswert
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
     * @return Color 
     */ 
    public Color getColor()
    {
        return color;
    }

    /**
     * Gibt zurück, ob der aktuelle Stein das Steinobjekt an Position 20 im Array steine ist,
     * da damit der Spieler auch der Startspieler ist.
     * 
     * @return Wahrheitswert
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
     * @return Stein 
     */
    public Stein gibAktuellenStein()
    {
        Stein akt = steine[aktuellerStein];
        steine[aktuellerStein] = null;
        aktuellerStein--;
        return akt;
    }
}
