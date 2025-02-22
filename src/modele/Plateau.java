package modele;

import controleur.Partie;
import java.util.ArrayList;
import static java.lang.Math.abs;

public class Plateau extends Observable {
     private Partie partie;
     private ArrayList<ArrayList<Scene>> train; //Y, puis X coordonnées


    /*public modele.Plateau(ArrayList<ArrayList<modele.Scene>> completeMap, ArrayList<modele.Personnage> basedWarcriminal, ArrayList<modele.Objet> initTresors, modele.Marshall m) {
        if (completeMap.size() > 2) {
            throw new Error("Train mal défini : il y a plus de 2 étages en argument");
        }
        if (!completeMap.get(0).get(completeMap.get(0).size() - 1).estLocomotive()) {
            throw new Error("Train mal défini : pas de locomotive définit à l'extrémité");
        }
        this.NB_WAGON = completeMap.size();
        this.epicGamers = basedWarcriminal;
        this.epicGamers.add(m);
        this.tresors = initTresors;
        this.train = completeMap;
    }*/

    public Plateau(Partie partie) {
        this.partie=partie;
        //mise en place des scenes
        train = new ArrayList<ArrayList<Scene>>();
        train.add(new ArrayList<Scene>());
        train.add(new ArrayList<Scene>());
        initScenes(false);
        initScenes(true);

    }
    public Partie getPartie(){
        return partie;
    }

    public Scene getScene(int x, int y) {
        if (x > partie.NB_WAGON || x < 0 || y > 1 || y < 0) {
            throw new ArrayIndexOutOfBoundsException("Mauvais indice pour getScene " + x+ " Nb.Wagons"+ partie.NB_WAGON);
        }
        return train.get(y).get(x);
    }

    private void initScenes(boolean estToit) {
        int y;
        if (estToit)
            y=1;
        else
            y=0;
        //Cas Dernier Wagon
        train.get(y).add(new Scene(estToit, false, true));
        for (int i = 1; i < partie.NB_WAGON-1; i++) {
            train.get(y).add(new Scene(estToit, false, false));
            for(int chance = 0; chance < (abs(Partie.rnd.nextInt()) % 101 > 75 ? 2 + (abs(Partie.rnd.nextInt()) % 101 < 30 ? 1 : 0) : 1); chance++) {
                if (abs(Partie.rnd.nextInt()) % 101 > 70) {
                    modele.Objet newTreasure = new modele.Objet(i, y, "bijoux-" + abs(Partie.rnd.nextInt()) % 3000, this, LOOTTYPE.BIJOUX);
                    continue;
                } else {
                    modele.Objet newTreasure = new modele.Objet(i, y, "bourse-"  +abs(Partie.rnd.nextInt()) % 3000, this, LOOTTYPE.BOURSE);
                }
            }
        }
        //Cas locomotive
        train.get(y).add(new Scene(estToit, true, false));
        if(y == 1) return;
        modele.Objet newTreasure = new modele.Objet(train.get(y).size() - 1, 0, "Maggot", this, LOOTTYPE.MAGOT);


    }


    public void deplacePerso(Personnage p, int xTarget, int yTarget){
        getScene(xTarget,yTarget).putPerso(p);
        getScene(p.getCoordX(),p.getCoordY()).removePerso(p.getTag());
        notifyObservers();

    }
    static public void main(String[] args){
        System.out.println("Test Constructeur :");
        Plateau p = new Plateau(new Partie(4,4,4,4));
        for (Scene s : p.train.get(1)) {
            System.out.print("Toit : ");
            s.getBandits().forEach((k,v)-> System.out.print(k +" "));
        }
        System.out.println("");
        for (Scene s : p.train.get(0)) {
            System.out.print("Interieur : ");
            s.getBandits().forEach((k, v) -> System.out.print(k + " "));
        }


    }
}

