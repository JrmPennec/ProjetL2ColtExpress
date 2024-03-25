import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.util.ArrayList;

public class Jeu {
    static final int NB_WAGON = 4;
    static final int NB_JOUEURS = 1;
    private ArrayList<Personnage> persos;
    private Plateau plateau;

    public ArrayList<Personnage> getPersos() {return persos;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Jeu() {
        persos = new ArrayList<>();
        plateau = new Plateau();
        initBandits();
    }

    private void initBandits() {
        for (int i = 0; i < NB_JOUEURS; i++) {
            Bandit b = new Bandit(0, 1, "b0" + i, plateau);
            persos.add(b);
            plateau.getScene(0, 1).putPerso(b);

        }
    }

    public void executionStack(Bandit id){
        Input input = id.popAction();
        switch(input.action){
            case ACTION.DEPLACE :{
                int lastX= id.getCoordX();
                int lastY = id.getCoordY();
                if(id.deplace(input.direction)){
                    //Si déplacement autorisé
                    this.plateau.deplacePerso(id, lastX, lastY, input.direction);
                    break;
                }
                else {
                    System.out.println("Erreur de déplacement");
                    break;
                }
            }

            case ACTION.BRAQUE : id.braqueButin(); break;
            case ACTION.TIR : id.tir(input.direction); break;
            case default : return;

        }

        System.out.println(id.getTag() + " exécution");
    }


    public static void main(String[] args) {
        Jeu test = new Jeu();
        test.persos.get(0).deplace(Personnage.DIRECTION.GAUCHE);
        test.persos.get(0).deplace(Personnage.DIRECTION.HAUT);
        test.persos.get(0).deplace(Personnage.DIRECTION.BAS);

    }
}


