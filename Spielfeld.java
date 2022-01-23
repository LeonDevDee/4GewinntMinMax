
/**
 * Tragen Sie hier eine Beschreibung des Interface Spielfeld ein.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */

public interface Spielfeld
{
    public void fuehreZugAus(Zug zug);
    public List<Zug> gibMoeglicheZuege();
}
