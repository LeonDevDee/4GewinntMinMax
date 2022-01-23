
import sas.*;
import java.awt.Color;
/**
 * Die Klasse Anzeige regelt die Grafik des Spiels
 * 
 * @author (Luka und Leon) 
 * 
 * @version (21.09.2021)
 */
public class Anzeige
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private View fenster;
    private Modell modell;
    private Circle [] buttons;

    /**
     * Konstruktor für Objekte der Klasse Anzeige
     * @param modell Modell übergibt das Modell
     */
    public Anzeige(Modell modell)
    {
        fenster = new View(700, 700, "4 Gewinnt");
        this.modell = modell;
        buttons = new Circle[7];
        updateView();
    }

    /**
     * Aktuallisiert die Anzeige. Dabei wird das Spielfeld auf dem Frame der durch die 
     * SAS-Bibliothek erzeugt wurde (fenster) gezeichnet. In der oberen Zeile werden dabei
     * Buttons erzeugt, auf die der Spieler klicken kann um die Spalte für seinen nächsten
     * Wurf zu bestimmen.     * 
     * Bisher geworfene Steine werden
     * über die Verknüpfung zum Modell (modell) geholt und ebenfalls gezeichnet.
     */
    public void updateView()
    {
        for(int i = 0; i < 700; i = i + 100)
        {
            buttons[i/100] = new Circle(i,0, 50, modell.gibAktuelleSpielsituation().gibFarbeAktuellerSpieler());
        }
        for(int i = 100; i < 700; i = i + 100)
        {
            new Rectangle(i,100, 5, 600, Color.black);
        }
        for(int i = 100; i < 700; i = i + 100)
        {
            new Rectangle(0,i, 700, 5, Color.black);
        }
        for(int i  = 0; i < modell.gibAktuelleSpielsituation().gibSteine().length; i++)
        {
            for(int j = 0; j < modell.gibAktuelleSpielsituation().gibSteine()[0].length; j++)
            {
                if(modell.gibAktuelleSpielsituation().gibSteine()[i][j] != null)
                {
                    new Circle(modell.gibAktuelleSpielsituation().gibSteine()[i][j].getX(), modell.gibAktuelleSpielsituation().gibSteine()[i][j].getY(), modell.gibAktuelleSpielsituation().gibSteine()[i][j].getRadius() ,modell.gibAktuelleSpielsituation().gibSteine()[i][j].getColor());
                }
            }
        }

    }

    /**
     * Methode gibt die Buttons zurück
     *
     * @return Button[]  
     */
    public Circle [] getButtons()
    {
        return buttons;
    }

    /**
     * Gibt das Fenster(View) zurück
     * 
     * @return View 
     */
    public View getView()
    {
        return fenster;
    }
}

