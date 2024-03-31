package modele;//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.util.ArrayList;
import java.util.Observable;
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
    private int compteurAction;
    private int compteurJoueur;
    private boolean actionStage;

    //MISC
    public static Random rnd = new Random();

    //GETTER - SETTER
    public ArrayList<Bandit> getBandits() {
        return bandits;
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
    public int getCompteurAction() {
        return compteurAction;
    }

    //INIT
    public Jeu() {
        bandits = new ArrayList<>();
        plateau = new Plateau();
        marshall = new Marshall(3,"*MARSHAll*",plateau); //Après les tests lol
        initBandits();
        setActionStage(false);
        compteurAction=NB_ACTION;
        compteurJoueur=0;
    }
    private void initBandits() {
        for (int i = 0; i < NB_JOUEURS; i++) {
            Bandit b = new Bandit(0, 1, "b0" + i, plateau);
            bandits.add(b);
            plateau.getScene(0, 1).putPerso(b);

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
    }
    //DEROULEMENT PARTIE


    public void actionPhase(){ //Dépile 1 fois chaque joueur.
        if (isActionStage()) {

            for (Bandit gamer : bandits) {
                try {
                    gamer.executionStack();
                    //1 action dépilé, on quitte la fonction.
                    if (this.marshall != null) this.marshall.faitAction();
                    continue;
                } catch (Error e) {
                    //Le stack est vide, on passe au joueur suivant.
                    System.out.println(gamer.tag + " a un stack vide");
                    continue;
                }


            }
            derouleTourActionStage();
            notifyObservers();
        }
    }


    void derouleTourPlanningStage(){
        compteurAction--;
        if (compteurAction==0) {
            compteurJoueur++;
            compteurAction = NB_ACTION;
        }
        if(compteurJoueur==Jeu.NB_JOUEURS){
            setActionStage(true);
        }

    }
    void derouleTourActionStage(){
        compteurAction--;
        if (compteurAction==0) {
            compteurAction = NB_ACTION;
            setActionStage(false);
        }

    }

    public static void main(String[] args) {
        Jeu test = new Jeu();
        test.bandits.get(0).deplace(DIRECTION.GAUCHE);
        test.bandits.get(0).deplace(DIRECTION.HAUT);
        test.bandits.get(0).deplace(DIRECTION.BAS);

    }
}


