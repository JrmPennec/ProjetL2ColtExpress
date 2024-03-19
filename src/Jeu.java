import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.util.ArrayList;

public class Jeu {
    static final int NB_WAGON = 4;
    static final int NB_JOUEURS = 1;
    static ArrayList<Personnage> persos;
    static Plateau plateau;

    public Jeu(){
        persos=new ArrayList<>();
        plateau= new Plateau();
    }


    public static void main (String[] args){
        Jeu test = new Jeu();
    test.persos.get(0).deplace(Personnage.DIRECTION.GAUCHE);
    test.persos.get(0).deplace(Personnage.DIRECTION.HAUT);
    test.persos.get(0).deplace(Personnage.DIRECTION.BAS);

    }

}
