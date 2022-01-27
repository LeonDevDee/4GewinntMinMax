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
        return new Benutzer(color);
    }

    public Zug ermittleNachestenZug(Spielsituation s){
        Tree<Spielsituation> spielbaum = bildeSpielbaum(suchtiefe, s.gibKopie());
        System.out.println(spielbaum.getContent().gibBewertung());

        List<Tree<Spielsituation>> folgeSpielsituationen = spielbaum.getChildTrees();

        Zug zug = null;
        folgeSpielsituationen.toFirst();
        while(folgeSpielsituationen.hasAccess() && zug == null){
            if(folgeSpielsituationen.getContent().getContent().gibBewertung() == spielbaum.getContent().gibBewertung()){
                zug = folgeSpielsituationen.getContent().getContent().gibLetztenZug();
            }

            folgeSpielsituationen.next();
        }

        return zug;
    }

    private Tree<Spielsituation> bildeSpielbaum(int tiefe, Spielsituation s){
        Tree<Spielsituation> tree = new Tree<Spielsituation>();

        System.out.println("Tiefe: " + (suchtiefe - (tiefe-1)));
        System.out.println(s.spielsituationZuString());

        if(tiefe > 0){
            List<Zug> zuege = ermittleMoeglicheZuege(s);

            //Baumerzeugung
            zuege.toFirst();
            while(zuege.hasAccess()){
                Spielsituation s0 = s.gibKopie();
                s0.fuehreZugAus(zuege.getContent());
                tree.addChildTree(bildeSpielbaum(tiefe-1,s0));
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
            if(s.gibAktuellenSpieler().getColor() == this.getColor()){
                s.setzeBewertung(max);
            }else{
                s.setzeBewertung(min);
            }

            tree.setContent(s);
        }else{
            s.setzeBewertung(bewerteSpielsituation(s));
            tree = new Tree<Spielsituation>(s);
        }
        System.out.println("Tiefe " + (suchtiefe - (tiefe-1)) + " fertig : " + s.gibBewertung() + "\n");

        return tree;
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

    private int bewerteSpielsituation(Spielsituation s){
        int b = 0;

        if(s.pruefeGewonnen()){
            if(s.gibAktuellenSpieler().getColor() == this.getColor()){
                b += 1000;
            }else{
                b -= 1000;
            }
        }
        
        b += s.zaehleDreierreihen(getColor()) * 100;
        b += s.zaehleDreierreihen(Color.RED) *-100;
        

        return b;
    }

}
