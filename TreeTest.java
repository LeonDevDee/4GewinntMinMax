import java.awt.Color;

/**
 * Beschreiben Sie hier die Klasse TreeTest.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class TreeTest
{
    public TreeTest(){
        Tree<Stein> tree = new Tree<Stein>(new Stein(5, Color.BLUE));
        
        tree.addChildTree(new Tree<Stein>(new Stein(5, Color.YELLOW)));
        tree.addChildTree(new Tree<Stein>(new Stein(5, Color.GREEN)));
        tree.addChildTree(new Tree<Stein>(new Stein(5, Color.ORANGE)));
        
        System.out.println(tree.getAmountOfChildTrees());
        
        for(int i = 0; i < tree.getAmountOfChildTrees(); i++){
            System.out.println(tree.getChildTree(i).getContent().getColor().toString());
        }
    }
}
