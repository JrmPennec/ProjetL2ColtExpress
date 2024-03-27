package modele;//import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.util.ArrayList;

public class Jeu {
    public static final int NB_WAGON = 4;
    public static final int NB_JOUEURS = 1;
    private ArrayList<Bandit> bandits;


    private Marshall marshall;
    private Plateau plateau;


    public ArrayList<Bandit> getBandits() {
        return bandits;
    }
    public Marshall getMarshall() {
        return marshall;
    }
    public Plateau getPlateau() {
        return plateau;
    }

    public Jeu() {
        bandits = new ArrayList<>();
        plateau = new Plateau();
        marshall = new Marshall(3,"*MARSHAll*",plateau);
        initBandits();
    }

    private void initBandits() {
        for (int i = 0; i < NB_JOUEURS; i++) {
            Bandit b = new Bandit(0, 1, "b0" + i, plateau);
            bandits.add(b);
            plateau.getScene(0, 1).putPerso(b);

        }
    }

    public void ajouteAction(Bandit b,Input p){
        b.putAction(p);
    }

    public void actionPhase(){ //Dépile 1 fois chaque joueur.

        for(Bandit gamer : bandits){
            try{
                gamer.executionStack();
                //1 action dépilé, on quitte la fonction.
                continue;
            }catch (Error e){
                //Le stack est vide, on passe au joueur suivant.
                System.out.println(gamer.tag + " a un stack vide");
                continue;
            }


        }
        return;
    }


    public static void main(String[] args) {
        Jeu test = new Jeu();
        test.bandits.get(0).deplace(DIRECTION.GAUCHE);
        test.bandits.get(0).deplace(DIRECTION.HAUT);
        test.bandits.get(0).deplace(DIRECTION.BAS);

    }
}


