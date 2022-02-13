import java.awt.Color;
import java.util.Random;

/**
 * KI ist die Hauptklasse der facharbeitlichen Ausarbeitung. Von anderen Klassen wird neben dem Konstruktor auf gibKopie() und ermittleNaechstenZug() aufgerufen. Alle
 * weiteren Methode werden in der Regel nur innerhalb der Klasse verwendet
 * 
 * @author Leon Wehrwein
 * @version 13.02.2022
 */
public class KI extends Spieler
{
    private int suchtiefe;
    /**
     * Konstruktor der Klasse KI
     * 
     * @param color Farbe der KI
     * @param suchtiefe Die Tiefe, bis zuwelcher der MinMax-Algorithmus suchen soll
     * 
     */
    public KI(Color color, int suchtiefe){
        super(color);
        this.suchtiefe = suchtiefe;
    }
    
    /**
     * Erstellt eine Kopie 
     * 
     * @return neue Instanz, mit gleichen Eigenschaften der angesteuerten Instanz von KI
     */
    public Spieler gibKopie(){
        return new KI(color,suchtiefe);
    }
    
    /**
     * Schnittstelle zwischen MinMax-Algorithmus und Spiel. Ruft bildeUndBewerteSpielbaum() auf, und bildet vom rückgegebenen Spielbaum den auszuführenden Zug.
     * 
     * @param s die Spielsituation, zu welcher der Folgezug ermittelt werden soll
     * @return den besten ermittelten Folgezug bei der Spielsituation s
     */
    public Zug ermittleNachestenZug(Spielsituation s){
        Tree<Spielsituation> spielbaum = bildeUndBewerteSpielbaum(suchtiefe, s.gibKopie());
        System.out.println(spielbaum.getContent().gibBewertung());

        return passendenZugZuSpielsituationenErmitteln(spielbaum.getChildTrees(), spielbaum.getContent().gibBewertung());
    }

    /**
     * Die eigentliche MinMax-Methode. Bildet und bewertet einen gesamten Spielbaum zur Situation s bis zur Tiefe t
     *  
     *  @param s die Ausgangssituation
     *  @param t die Tiefe, von der Ausgangssituation aus, bis zu welcher gesucht werden soll
     *  @return einen vollwertigen Spielbaum von Ausgangsituation s bis zur Tiefe t, welcher vollständig bewertet ist
     */
    public Tree<Spielsituation> bildeUndBewerteSpielbaum(int t, Spielsituation s){
        Tree<Spielsituation> tree = new Tree<Spielsituation>();

        System.out.println("Tiefe: " + (suchtiefe - (t-1)));
        System.out.println(s.spielsituationZuString());

        if(t == 0){
            s.setzeBewertung(bewerteSpielsituation(s));
            tree = new Tree<Spielsituation>(s);
        }else{
            List<Zug> zuege = ermittleMoeglicheZuege(s);
            
            //Nicht mehr weiter suchen wenn gewinnsituation
            if(zuege.isEmpty() || s.pruefeGewonnen()){
                s.setzeBewertung(bewerteSpielsituation(s));
                tree = new Tree<Spielsituation>(s);
            }else{
                //Baumerzeugung
                zuege.toFirst();
                while(zuege.hasAccess()){
                    Spielsituation s0 = s.gibKopie();
                    s0.fuehreZugAus(zuege.getContent());
                    tree.addChildTree(bildeUndBewerteSpielbaum(t-1,s0));
                    zuege.next();
                }

                //Bewertungsdurchgabe
                List<Tree<Spielsituation>> nachfolgeSituationen = tree.getChildTrees();
                nachfolgeSituationen.toFirst();

                int min = nachfolgeSituationen.getContent().getContent().gibBewertung();
                int max = nachfolgeSituationen.getContent().getContent().gibBewertung();
                while(nachfolgeSituationen.hasAccess()){
                    if(nachfolgeSituationen.getContent().getContent().gibBewertung() > max){
                        max = nachfolgeSituationen.getContent().getContent().gibBewertung();
                    }else if(nachfolgeSituationen.getContent().getContent().gibBewertung() < min){
                        min = nachfolgeSituationen.getContent().getContent().gibBewertung();
                    }

                    nachfolgeSituationen.next();
                }

                //Spielerermittlung
                if(s.gibAktuellenSpieler() instanceof KI){
                    s.setzeBewertung(max);
                }else{
                    s.setzeBewertung(min);
                }

                tree.setContent(s);
            }
        }
        System.out.println("Tiefe " + (suchtiefe - (t-1)) + " fertig : " + s.gibBewertung() + "\n");

        return tree;
    }
    
    /**
     * Gibt einen Zug zurück, welcher zu einer der gegebenen Folgesituationen mit der gegeben Bewertung führt. 
     * 
     * @param folgeSpielsituationen alle direkten Folgesituationen zur Ausgangssituation
     * @param bewertung die beste Bewertung innerhalb der Folgesituationen
     * @return einen möglichen Zug, um eine Folgesituation zu erhalten, welche die gegebenen Bewertung hat
     */
    public Zug passendenZugZuSpielsituationenErmitteln(List<Tree<Spielsituation>> folgeSpielsituationen, int bewertung){
        List<Zug> zuege = new List<Zug>();
        
        folgeSpielsituationen.toFirst();
        zuege.toFirst();
        while(folgeSpielsituationen.hasAccess()){
            if(folgeSpielsituationen.getContent().getContent().gibBewertung() == bewertung){
                zuege.append(folgeSpielsituationen.getContent().getContent().gibLetztenZug());
            }

            folgeSpielsituationen.next();
        }
        
        return zufaelligenZugWaehlen(zuege);
    }
    
    /**
     * Ermittelt aus mehreren Zügen einen zufälligen.(Zum Beispiel wenn mehrere Züge gleichwertig sind)
     * 
     * @param zuege Liste der auszuwählenden Züge
     * @return eine zufälligen Zug innerhalb der Liste
     */
    public Zug zufaelligenZugWaehlen(List<Zug> zuege){
        zuege.toFirst();
        int laenge = 0;
        while(zuege.hasAccess()){
            laenge++;
            zuege.next();
        }
        
        Random r = new Random();
        int zugNr = r.nextInt(laenge);
        
        zuege.toFirst();
        for(int i = 0; i< zugNr; i++){
            zuege.next();
        }
        
        return zuege.getContent();
    }
    
    /**
     * Ermittel die möglichen Züge bei einer gegebenen Situation s
     * 
     * @param s Ausgangssituation
     * @return Liste der Möglichen Züge
     */
    public List<Zug> ermittleMoeglicheZuege(Spielsituation s){
        List<Zug> zuege = new List<Zug>();
        for(int i = 0; i < 7; i++){
            if(s.gibZeileZurSpalte(i) != -1){
                zuege.append(new Zug(i));
            }
        }

        return zuege;
    }

    /**
     * Bertungsprozedur des MinMax-Algorithmus
     * 
     * @param s Spielsituation, die bewertet werden soll
     * @return Wertung
     */
    public int bewerteSpielsituation(Spielsituation s){
        //Wenn ich die Methode ansprechen sollte, explizit auf Facharbeit verweisen
        //Wenn nicht, dann nur unten in der Quellenangabe
        int b = 0;

        if(s.pruefeGewonnen()){
            if(s.gibAktuellenSpieler() instanceof KI){
                b += 1000000;
            }else{
                b -= 1000000;
            }
        }

        b += s.zaehleDreierreihen(getColor()) * 100;
        b += s.zaehleDreierreihen(Color.RED) *-100;

        return b;
    }

}
