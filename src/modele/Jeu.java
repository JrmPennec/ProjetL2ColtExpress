package modele;//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.util.ArrayList;
import java.util.Random;

public class Jeu extends Observable {

    //CONSTANTES
    public static final int NB_WAGON = 4;
    public static final int NB_JOUEURS = 4;
    public static final int NB_ACTION = 4;

    //OBJETS
    private ArrayList<Bandit> bandits;
    private Marshall marshall;
    private Plateau plateau;

    //DEROULEMENT
    private int actionsRestantes;
    private int compteurJoueur;
    private boolean actionStage;

    //MISC
    public static Random rnd = new Random();

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

    //INIT
    public Jeu() {
        bandits = new ArrayList<>();
        plateau = new Plateau();
        marshall = new Marshall(3,"MARSHAll",plateau); //Après les tests lol
        initBandits();
        setActionStage(false);
        actionsRestantes =NB_ACTION;
        compteurJoueur=0;
    }
    private void initBandits() {
        for (int i = 0; i < NB_JOUEURS; i++) {
            Bandit b = new Bandit(0, 1, "Bandit0" + (i+1), plateau);
            bandits.add(b);

        }
        assert(bandits.size()==NB_JOUEURS);
        assert(plateau.getScene(0,1).getBandits().size()==NB_JOUEURS);
    }
    //INTERACTION OBJETS
    public void ajouteAction(Bandit b,Input p){
        if (!isActionStage()) {
            b.putAction(p);
            derouleTourPlanningStage();
            notifyObservers();
        }
        else
            System.out.println("actionStage");
    }
    //DEROULEMENT PARTIE


    public void actionPhase(){ //Dépile 1 fois chaque joueur.
        if (isActionStage()) {
            notifyObservers();
            if (this.marshall != null) this.marshall.expulse();
            for (Bandit gamer : bandits) {
                try {
                    gamer.executionStack();
                    //1 action dépilé, on quitte la fonction.
                    continue;
                } catch (Error e) {
                    //Le stack est vide, on passe au joueur suivant.
                    System.out.println(gamer.tag + " a un stack vide");
                    continue;
                }


            }
            if (this.marshall != null) this.marshall.faitAction();
            derouleTourActionStage();
            notifyObservers();
        }
    }

    /**Décremente action restantes et gere l'incrementation pour le
     * compteJoueur/Passage action phase
     */
    public void derouleTourPlanningStage(){
        actionsRestantes--;
        if (actionsRestantes ==0) {
            compteurJoueur++;
            actionsRestantes = NB_ACTION;
        }
        if(compteurJoueur==Jeu.NB_JOUEURS){
            compteurJoueur = 0;
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
        }
        notifyObservers();
    }

    public static void main(String[] args) {
        Jeu test = new Jeu();
        test.bandits.get(0).deplace(DIRECTION.GAUCHE);
        test.bandits.get(0).deplace(DIRECTION.HAUT);
        test.bandits.get(0).deplace(DIRECTION.BAS);

    }
}


