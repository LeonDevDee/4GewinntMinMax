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
    private int suchmodus; //0: Zufall, 1:MinMax, 2:AlphaBeta
    /**
     * Konstruktor der Klasse KI
     * 
     * @param color Farbe der KI
     * @param suchtiefe Die Tiefe, bis zuwelcher der MinMax-Algorithmus suchen soll
     * 
     */
    public KI(Color color, int suchtiefe, int suchmodus){
        super(color);
        this.suchtiefe = suchtiefe;
        this.suchmodus = suchmodus;
    }

    /**
     * Erstellt eine Kopie 
     * 
     * @return neue Instanz, mit gleichen Eigenschaften der angesteuerten Instanz von KI
     */
    public Spieler gibKopie(){
        return new KI(color,suchtiefe,suchmodus);
    }

    /**
     * Schnittstelle zwischen MinMax-Algorithmus und Spiel. Ruft bildeUndBewerteSpielbaum() auf, und bildet vom rückgegebenen Spielbaum den auszuführenden Zug.
     * 
     * @param s die Spielsituation, zu welcher der Folgezug ermittelt werden soll
     * @return den besten ermittelten Folgezug bei der Spielsituation s
     */
    public Zug ermittleNaechstenZug(Spielsituation s){
        Zug z = new Zug(0);
        if(suchmodus == 0){
            z = zufaelligenZugWaehlen(ermittleMoeglicheZuege(s));
        }else if(suchmodus == 1){
            Tree<Spielsituation> spielbaum = minMax(suchtiefe, s.gibKopie());
            z = passendenZugZuSpielsituationenErmitteln(spielbaum.getChildTrees(), spielbaum.getContent().gibBewertung());
        }else {
            List<Spielsituation> folgesituationenList = gibNachfolgesituationen(s.gibKopie());
            List<Tree<Spielsituation>> folgesituationenTree = new List<Tree<Spielsituation>>();
            
            int max = -1000000;
            
            folgesituationenList.toFirst();
            while(folgesituationenList.hasAccess()){
                Spielsituation g = folgesituationenList.getContent();
                int wertung = alphaBeta(suchtiefe-1, -1000000, 1000000, g);
                g.setzeBewertung(wertung);
                folgesituationenTree.append(new Tree<Spielsituation>(g));
                
                if(wertung > max){
                    max = wertung;
                }
                
                folgesituationenList.next();
            }
            
            z= passendenZugZuSpielsituationenErmitteln(folgesituationenTree, max);
            
        }

        return z;
    }

    public int alphaBeta(int t, int alpha, int beta, Spielsituation s){
        if(t == 0){
            return bewerteSpielsituation(s);
        }else{
            List<Spielsituation> folgesituationen = gibNachfolgesituationen(s.gibKopie());
            folgesituationen.toFirst();

            while(folgesituationen.hasAccess()){
                Spielsituation r = folgesituationen.getContent();
                if(s.gibAktuellenSpieler() instanceof KI){
                    alpha = Integer.max(alpha, alphaBeta(t-1, alpha, beta, r));
                    if(alpha >= beta){
                        return alpha;
                    }
                }else{
                    beta = Integer.min(beta, alphaBeta(t-1, alpha, beta, r));
                    if(alpha >= beta){
                        return beta;
                    }
                }
                folgesituationen.next();
            }

            if(s.gibAktuellenSpieler() instanceof KI){
                return alpha;     
            }else{
                return beta;
            }
        }
    }

    public List<Spielsituation> gibNachfolgesituationen(Spielsituation s){
        List<Zug> zuege = ermittleMoeglicheZuege(s);
        List<Spielsituation> folgeSituationen = new List<Spielsituation>();

        zuege.toFirst();
        folgeSituationen.toFirst();
        while(zuege.hasAccess()){
            Spielsituation s0 = s.gibKopie();
            s0.fuehreZugAus(zuege.getContent());
            folgeSituationen.append(s0);
            zuege.next();
        }

        return folgeSituationen;
    }

    /**
     * Die eigentliche MinMax-Methode. Bildet und bewertet einen gesamten Spielbaum zur Situation s bis zur Tiefe t
     *  
     *  @param s die Ausgangssituation
     *  @param t die Tiefe, von der Ausgangssituation aus, bis zu welcher gesucht werden soll
     *  @return einen vollwertigen Spielbaum von Ausgangsituation s bis zur Tiefe t, welcher vollständig bewertet ist
     */
    public Tree<Spielsituation> minMax(int t, Spielsituation s){
        Tree<Spielsituation> tree;

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
                tree = new Tree<Spielsituation>();
                zuege.toFirst();
                while(zuege.hasAccess()){
                    Spielsituation s0 = s.gibKopie();
                    s0.fuehreZugAus(zuege.getContent());
                    tree.addChildTree(minMax(t-1,s0));
                    zuege.next();
                }

                //Bewertungsdurchgabe
                List<Tree<Spielsituation>> childSituationen = tree.getChildTrees();
                childSituationen.toFirst();

                int min = 1000000;
                int max = -1000000;
                while(childSituationen.hasAccess()){
                    int wertung = childSituationen.getContent().getContent().gibBewertung();
                    
                    max = Integer.max(wertung, max);
                    min = Integer.min(wertung, min);

                    childSituationen.next();
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
        /* irgendein Bug
        if(zuege.isEmpty()){
            folgeSpielsituationen.toFirst();
            while(folgeSpielsituationen.hasAccess()){
            zuege.append(folgeSpielsituationen.getContent().getContent().gibLetztenZug());

            folgeSpielsituationen.next();
        }
        }*/
        
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

        b += s.zaehleDreierreihen(getColor()) * 10;
        b += s.zaehleDreierreihen(Color.RED) *-10;

        b += s.zaehleZweierreihen(getColor()) * 1;
        b += s.zaehleZweierreihen(Color.RED) *-1;

        return b;
    }

}
