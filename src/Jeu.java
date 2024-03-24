import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.util.ArrayList;

public class Jeu {
    static final int NB_WAGON = 4;
    static final int NB_JOUEURS = 1;
    private ArrayList<Personnage> persos;
    private Plateau plateau;

    public ArrayList<Personnage> getPersos() {
        return persos;
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


    public static void main(String[] args) {
        Jeu test = new Jeu();
        test.persos.get(0).deplace(Personnage.DIRECTION.GAUCHE);
        test.persos.get(0).deplace(Personnage.DIRECTION.HAUT);
        test.persos.get(0).deplace(Personnage.DIRECTION.BAS);

    }
}


