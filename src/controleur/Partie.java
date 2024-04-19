package controleur;

import modele.*;

import java.util.ArrayList;
import java.util.Random;

/** Controleur de la partie .
 * du code aurait pu être enlever si cette classe etait une subclasse de Jeu, mais pour une question de lisibilité,
 * elle est restée indépendante
 */
public class Partie extends Observable {
    //PARENT pour retourner la fin de la partie
    Jeu jeu;
    //CONSTANTES
    
     public final int NB_JOUEURS ;
     public final int NB_TOUR ;
     public final int NB_ACTION ;
     public final int NB_WAGON ;
     public final int NB_AI;
    
    //OBJETS / MODELE
    private ArrayList<Bandit> bandits;
    private Marshall marshall;
    private Plateau plateau;

    //DEROULEMENT
    private int compteurTours;
    private int actionsRestantes;
    private int compteurJoueur;

    private int compteurJoueurAction;

    private boolean actionStage;

    //MISC
    public static Random rnd = new Random();

    private String log = "Début de partie !!";

    //GETTER - SETTER
    public ArrayList<Bandit> getBandits() {
        return bandits;
    }
    public Bandit getBandit(int index){
        return bandits.get(index);
    }
    public Marshall getMarshall() {
        return marshall;
    }
    public Plateau getPlateau() {
        return plateau;
    }
    public boolean isActionStage(){
        return actionStage;
    }
    public void setActionStage(Boolean b){
        actionStage=b;
    }
    public int getCompteurJoueur(){
        return compteurJoueur;
    }
    public int getActionsRestantes() {
        return actionsRestantes;
    }
    public String getLog(){return log;}

    //INIT
    public Partie(int nbJ,int nbT,int nbA, int nbW) {
        //Init des attributs
        this.jeu=null;
        NB_JOUEURS=nbJ;
        NB_TOUR=nbT;
        NB_ACTION=nbA;
        NB_WAGON=nbW;
        NB_AI = 0;
        System.out.println("JoueursPartie :"+NB_JOUEURS+ " Tours = "+NB_TOUR+" Actions = "+NB_ACTION+ "Wagons = "+NB_WAGON);
        compteurTours=0;
        bandits = new ArrayList<>();
        plateau = new Plateau(this);
        marshall = new Marshall(3,"MARSHAll",plateau); //Après les tests lol
        initBandits();
        setActionStage(false);
        actionsRestantes =NB_ACTION;
        compteurJoueur=0;
    }
    public Partie(Jeu jeu,int nbJ,int nbT,int nbA, int nbW, int nbAi) {
        //Init des attributs
        this.jeu=jeu;
        NB_JOUEURS=nbJ;
        NB_TOUR=nbT;
        NB_ACTION=nbA;
        NB_WAGON=nbW;
        NB_AI = nbAi;
        System.out.println("JoueursPartie :"+NB_JOUEURS+ " Tours = "+NB_TOUR+" Actions = "+NB_ACTION+ " Wagons = "+NB_WAGON + " AI = " + nbAi);
        compteurTours=0;
        bandits = new ArrayList<>();
        plateau = new Plateau(this);
        marshall = new Marshall(NB_WAGON-1,"MARSHAll",plateau); //Après les tests lol
        initBandits();
        setActionStage(false);
        actionsRestantes =NB_ACTION;
        compteurJoueur=0;
        System.out.println("finConstruct");
    }
    private void initBandits() {
        //création des bandits un par en fonction de nb de joueurs
        for (int i = 0; i < NB_JOUEURS - NB_AI; i++) {
            Bandit b = new Bandit(0, 1, "Bandit0" + (i+1), plateau);
            bandits.add(b);
        }
        for(int i = 0; i < NB_AI; i++){
            BanditAI b = new BanditAI(0, 1, "Tricheur", plateau);
            bandits.add(b);
        }
        assert(bandits.size()==NB_JOUEURS);
        assert(plateau.getScene(0,1).getBandits().size()==NB_JOUEURS);
    }
    //INTERACTION OBJETS
    public void ajouteAction(Bandit b, Input p){
        //Ajoute une action a un bandit b si nous sommes en planning phase
        if (!isActionStage()) {
            b.putAction(p);
            derouleTourPlanningStage();
            notifyObservers();
        }
        else
            System.out.println("actionStage");
    }

    public void annuleAction(Bandit b){
        if (!isActionStage() && actionsRestantes < NB_ACTION) {
            modele.Input cancel = b.popAction();
            actionsRestantes++;
            notifyObservers();
        }
        else
            System.out.println("actionStage");
    }
    //DEROULEMENT PARTIE

    public void emptyLog(){
        this.log = "...";
        for(Bandit gamer : bandits) gamer.logCharacter = "";
        if (this.marshall != null) this.marshall.logCharacter = "";
    }

    public void updateLog(){
        for(Bandit gamer : bandits){
            if (gamer.logCharacter.isEmpty()) continue;
            this.log = gamer.logCharacter;
        }
        if (this.marshall != null && !this.marshall.logCharacter.isEmpty()) this.log += this.marshall.logCharacter;
    }


    public void actionPhase(){ //Dépile 1 fois chaque joueur.
        if (isActionStage()) {
            notifyObservers();
            if (this.marshall != null) this.marshall.expulse();
            for (Bandit gamer : bandits) {
                if(gamer instanceof BanditAI){ //Si c'est une IA, mets à jour en pleine action son buffer
                    ((BanditAI)gamer).AI_putAction();
                }
                try {
                    emptyLog();
                    gamer.executionStack();
                    updateLog();
                    continue;
                } catch (Error e) {
                    //Le stack est vide, on passe au joueur suivant.
                    System.out.println(gamer.getTag() + " a un stack vide");
                    continue;
                }
            }
            if (this.marshall != null) this.marshall.faitAction();
            updateLog();
            derouleTourActionStage();
            notifyObservers();

        }
    }

    public void actionPhase_slow(){ //Dépile 1 fois chaque joueur.
        if (isActionStage()) {
            notifyObservers();
            if (this.marshall != null) this.marshall.expulse();
            Bandit gamer = this.getBandit(this.compteurJoueurAction);
            if(gamer instanceof BanditAI){ //Si c'est une IA, mets à jour en pleine action son buffer
                ((BanditAI)gamer).AI_putAction();
            }
            try {
                emptyLog();
                gamer.executionStack();
                updateLog();
                this.compteurJoueurAction++;
            } catch (Error e) {
                System.out.println(gamer.getTag() + " a un stack vide");
                return;
            }

            //Une fois tous les joueurs ont dépilés 1 fois;
            //On bouge le marshall et regarde si c'est la fin de la phase d'action
            if(this.compteurJoueurAction != NB_JOUEURS) return;
            this.compteurJoueurAction = 0;
            if (this.marshall != null) this.marshall.faitAction();
            updateLog();
            derouleTourActionStage();
            notifyObservers();
        }
    }



    /**Décremente action restantes et gere l'incrementation pour le
     * compteJoueur/Passage action phase
     */
    public void derouleTourPlanningStage(){
        actionsRestantes--;
        //Cas plus d'actions
        if (actionsRestantes ==0) {
            compteurJoueur++;
            //Saute les joueurs IA
            while(compteurJoueur != NB_JOUEURS && this.getBandit(compteurJoueur) instanceof BanditAI) compteurJoueur++;
            actionsRestantes = NB_ACTION;
        }
        //Cas dernier joueur + plus d'actions
        if(compteurJoueur== NB_JOUEURS){
            compteurJoueur = 0;
            compteurJoueurAction = 0;
            setActionStage(true);
        }
        notifyObservers();
    }
    /**Décremente action restantes et gere passage planning stage
     */
    public void derouleTourActionStage(){
        actionsRestantes--;
        if (actionsRestantes ==0) {
            actionsRestantes = NB_ACTION;
            setActionStage(false);
            compteurTours++;
            if (compteurTours==NB_TOUR)
                declencheFin();


        }
        notifyObservers();
    }

    private void declencheFin(){
        //Cas utilisation de partie sans jeu
        if (jeu==null) {
            System.out.println("Fin de Partie");
            System.exit(0);
        }
        else jeu.finPartie();
        }


    public static void main(String[] args) {


    }
}


