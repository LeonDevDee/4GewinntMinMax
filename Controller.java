

import sas.*;
import sasio.*;
import java.awt.Color;
import java.util.Date;
/**
 * Beschreiben Sie hier die Klasse VierGewinnt. => Modell
 * 
 * @author (Tom, Joshua) 
 * @version (21.09.2021)
 */
public class Controller
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen

    private Anzeige anzeige;
    private Modell modell;

    /**
     * Konstruktor für Objekte der Klasse VierGewinnt
     */
    public Controller(int kiTiefensuche, int suchmodus)
    {
        modell = new Modell(kiTiefensuche, suchmodus);
        anzeige = new Anzeige(modell);
        spielen();
    }

    /**
     * Diese Methode ermöglicht es zu Spielen (Spielschleife). Über die Buttons der Anzeige wird
     * die Spalte ermittlet in die der Spieler den Stein werfen möchte.
     * Zudem wird bei einem Zug auf die KI zugegriffen um den Gegenzug zu ermitteln. Außerdem wird die Rechnzeit der KI gestoppt
     */
    public void spielen()
    {
        anzeige.updateView(Color.BLACK);
        while(modell.gibAktuelleSpielsituation().pruefeGewonnen() == false)
        {
            for(int i = 0; i < anzeige.getButtons().length; i++)
            {
                if(anzeige.getButtons()[i].mouseClicked() 
                && modell.gibAktuelleSpielsituation().gibZeileZurSpalte(i) != -1)
                {
                    modell.benutzereingabe(i);
                    anzeige.updateView(Color.MAGENTA);
                    long zeitpunkt1 = aktuellerZeitpunkt();
                    modell.kiEingabe();
                    anzeige.updateView(Color.BLACK);
                    long zeitpunkt2 = aktuellerZeitpunkt();
                    //System.out.println("Rechenzeit: " + (zeitpunkt2 - zeitpunkt1) + "ms");
                    System.out.println((zeitpunkt2 - zeitpunkt1));
                }
            }
            anzeige.getView().wait(1);
        }
    }
    
    private long aktuellerZeitpunkt(){
        return new Date().getTime();
    }
}
