import java.util.ArrayList;

 class Plateau extends Observable {
     private Jeu jeu;
     private ArrayList<ArrayList<Scene>> train; //Y, puis X coordonnées
     private ArrayList<Personnage> epicGamers; //Non necessaire a mon avis
     private ArrayList<Objet> tresors; //non necessaire


    /*public Plateau(ArrayList<ArrayList<Scene>> completeMap, ArrayList<Personnage> basedWarcriminal, ArrayList<Objet> initTresors, Marshall m) {
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

    public Plateau() {
        //mise en place des scenes
        train = new ArrayList<ArrayList<Scene>>();
        train.add(new ArrayList<Scene>());
        train.add(new ArrayList<Scene>());
        initScenes(false);
        initScenes(true);

    }

    public Scene getScene(int x, int y) {
        if (x > Jeu.NB_WAGON || x < 0 || y > 1 || y < 0) {
            throw new Error("Mauvais indice pour getScene");
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
        for (int i = 1; i < Jeu.NB_WAGON-1; i++)
            train.get(y).add(new Scene(estToit, false, false));
        //Cas locomotive
        train.get(y).add(new Scene(estToit, true, false));


    }


    void deplacePerso(Personnage p,int xTarget, int yTarget){
        //x et y verifiés dans Personnage
        /*int x = xLast;
        int y = yLast;
        switch(d){
            case HAUT : y++; break;
            case BAS : y--; break;
            case DROITE : x++; break;
            case GAUCHE : x--; break;
            default : throw new Error ("deplacePerso : DIRECTION 'd' mal définit");
        }*/
        getScene(xTarget,yTarget).putPerso(p);
        getScene(p.getCoordX(),p.getCoordY()).removePerso(p.getTag());
        System.out.println("plateau deplace ");
        notifyObservers();

    }
    static public void main(String[] args){
        System.out.println("Test Constructeur :");
        Plateau p = new Plateau();
        for (Scene s : p.train.get(1)) {
            System.out.print("Toit : ");
            s.getPersos().forEach((k,v)-> System.out.print(k +" "));
        }
        System.out.println("");
        for (Scene s : p.train.get(0)) {
            System.out.print("Interieur : ");
            s.getPersos().forEach((k, v) -> System.out.print(k + " "));
        }


    }
}

