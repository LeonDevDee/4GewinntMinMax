import java.awt.Color;
import java.util.Random;

/**
 * Beschreiben Sie hier die Klasse KI.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class KI extends Spieler
{
    private int suchtiefe;

    public KI(Color color, int suchtiefe){
        super(color);
        this.suchtiefe = suchtiefe;
    }

    public Spieler gibKopie(){
        return new KI(color,suchtiefe);
    }

    public Zug ermittleNachestenZug(Spielsituation s){
        Tree<Spielsituation> spielbaum = bildeUndBewerteSpielbaum(suchtiefe, s.gibKopie());
        System.out.println(spielbaum.getContent().gibBewertung());

        return passendenZugZuSpielsituationenErmitteln(spielbaum.getChildTrees(), spielbaum.getContent().gibBewertung());
    }

    private Tree<Spielsituation> bildeUndBewerteSpielbaum(int tiefe, Spielsituation s){
        Tree<Spielsituation> tree = new Tree<Spielsituation>();

        System.out.println("Tiefe: " + (suchtiefe - (tiefe-1)));
        System.out.println(s.spielsituationZuString());

        if(tiefe == 0){
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
                    tree.addChildTree(bildeUndBewerteSpielbaum(tiefe-1,s0));
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
        System.out.println("Tiefe " + (suchtiefe - (tiefe-1)) + " fertig : " + s.gibBewertung() + "\n");

        return tree;
    }
    
    private Zug passendenZugZuSpielsituationenErmitteln(List<Tree<Spielsituation>> folgeSpielsituationen, int bewertung){
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
    
    private Zug zufaelligenZugWaehlen(List<Zug> zuege){
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

    private List<Zug> ermittleMoeglicheZuege(Spielsituation s){
        List<Zug> zuege = new List<Zug>();
        for(int i = 0; i < 7; i++){
            if(s.gibZeileZurSpalte(i) != -1){
                zuege.append(new Zug(i));
            }
        }

        return zuege;
    }

    //Wenn ich die Methode ansprechen sollte, explizit auf Facharbeit verweisen
    //Wenn nicht, dann nur unten in der Quellenangabe
    private int bewerteSpielsituation(Spielsituation s){
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
