/**
 * <p>
 * Materialien zu den zentralen NRW-Abiturpruefungen im Fach Informatik ab 2017.
 * </p>
 * <p>
 * Generische Klasse BinaryTree<ContentType>
 * </p>
 * <p>
 * Mithilfe der generischen Klasse BinaryTree koennen beliebig viele
 * Inhaltsobjekte vom Typ ContentType in einem Binaerbaum verwaltet werden. Ein
 * Objekt der Klasse stellt entweder einen leeren Baum dar oder verwaltet ein
 * Inhaltsobjekt sowie einen linken und einen rechten Teilbaum, die ebenfalls
 * Objekte der generischen Klasse BinaryTree sind.
 * </p>
 * 
 * @author Qualitaets- und UnterstuetzungsAgentur - Landesinstitut fuer Schule;
 *         Materialien zum schulinternen Lehrplan Informatik SII
 * @version Generisch_04 2020-03-11
 */
public class Tree<ContentType> {

    /* --------- Anfang der privaten inneren Klasse -------------- */

    /**
     * Durch diese innere Klasse kann man dafuer sorgen, dass ein leerer Baum null
     * ist, ein nicht-leerer Baum jedoch immer eine nicht-null-Wurzel sowie
     * nicht-null-Teilbaeume, ggf. leere Teilbaeume hat.
     */
    private class BTNode {

        private ContentType content;
        private List<Tree<ContentType>> childTrees;

        private BTNode(ContentType pContent) {
            // Der Knoten hat einen linken und einen rechten Teilbaum, die
            // beide von null verschieden sind. Also hat ein Blatt immer zwei
            // leere Teilbaeume unter sich.
            this.content = pContent;
            childTrees = new List<Tree<ContentType>>();
        }
        
        private BTNode() {
            //Damit leere Knoten auch childs haben können
            childTrees = new List<Tree<ContentType>>();
        }

    }

    /* ----------- Ende der privaten inneren Klasse -------------- */

    private BTNode node;

    /**
     * Konstruktor für einen Baum ohne Inhalt
     */
    public Tree() {
        this.node = new BTNode();
    }

    /**
     * Konstruktor für einen Baum mit Inhalt
     * 
     * @param pContent Inhalt
     */
    public Tree(ContentType pContent) {
        if (pContent != null) {
            this.node = new BTNode(pContent);
        } else {
            this.node = null;
        }
    }

    /**
     * Gibt zurück ob der Knoten einen Inhalt hat
     * 
     * @return Knoten hat Inhalt
     */
    public boolean isEmpty() {
        return this.node == null;
    }

    /**
     * Setzt den Inhalt des Knoten auf den übergebenen Parameter
     * 
     * @param pContent Inhalt
     */
    public void setContent(ContentType pContent) {
        if (pContent != null) {
            if (this.isEmpty()) {
                node = new BTNode(pContent);
            }
            this.node.content = pContent;
        }
    }

    /**
     * Gibt den gespeicherten Inhalt des Knoten zurück
     * 
     * @return Inhalt
     */
    public ContentType getContent() {
        if (this.isEmpty()) {
            return null;
        } else {
            return this.node.content;
        }
    }

    /**
     * Fügt dem Baum einen Teilbaum hinzu
     * 
     * @param pTree hinzuzufügender Teilbaum
     */
    public void addChildTree(Tree<ContentType> pTree) {
        if (!this.isEmpty() && pTree != null) {
            this.node.childTrees.append(pTree);
        }
    }
    
    /**
     * Ermittelt die Blätter des Baumes
     * 
     * @return Blätter des Baumes als Liste
     */
    public List<Tree<ContentType>> getLeafNodes(){
        if(getAmountOfChildTrees() == 0){
            List<Tree<ContentType>> list = new List<Tree<ContentType>>();
            list.append(this);
            return list;
        }else{
            List<Tree<ContentType>> list = new List<Tree<ContentType>>();
            
            List<Tree<ContentType>> childs = getChildTrees();
            
            childs.toFirst();
            while(childs.hasAccess()){
                list.concat(childs.getContent().getLeafNodes());
                childs.next();
            }
            
            return list;
        }
    }
    
    /**
     * Gibt alle Nachfolgerknoten/Unterbäume zurück.
     * 
     * @return Nachfolgerknoten/Unterbäume als Liste
     */
    public List<Tree<ContentType>> getChildTrees(){
        return this.node.childTrees;
    }
    
    /**
     *  Gibt den Nachfolgerknoten/Teilbaum mit dem übergebenem index zurück.
     *  
     *  @param index Index des Nachfolgerknoten/Teilbaum
     *  @return Nacholgerknoten des Indexes
     */
    public Tree<ContentType> getChildTree(int index) {
        if (!this.isEmpty()) {
            this.node.childTrees.toFirst();
            
            for(int i = 0; i<index; i++){
                this.node.childTrees.next();
            }
            
            if(this.node.childTrees.hasAccess()){
                return this.node.childTrees.getContent();
            }else{
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Ermittelt die Anzahl der Nachfolgerknoten/Unterbäume.
     * 
     * @return Anzahl der Nachfolgerknoten/Unterbäume
     */
    public int getAmountOfChildTrees() {
        int i = 0;
        
        this.node.childTrees.toFirst();
        
        while(this.node.childTrees.hasAccess()){
            this.node.childTrees.next();
            i++;
        }
        
        return i;
    }

}
