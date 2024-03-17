import java.util.ArrayList;

public class Plateau extends Observable{
    public final int NB_WAGON;
    private ArrayList<ArrayList<Scene>> train; //Y, puis X coordonnées
    private ArrayList<Personnage> epicGamers;
    private ArrayList<Objet> tresors;

    public Plateau(ArrayList<ArrayList<Scene>> completeMap, ArrayList<Personnage> basedWarcriminal, ArrayList<Objet> initTresors, Marshall m){
        if(completeMap.size() > 2){
            throw new Error("Train mal définit : il y a plus de 2 étages en argument");
        }
        if(!completeMap.get(0).get(completeMap.get(0).size() - 1).estLocomotive()){
            throw new Error("Train mal définit : pas de locomotive définit à l'extrémité");
        }
        this.NB_WAGON = completeMap.size();
        this.epicGamers = basedWarcriminal;
        this.epicGamers.add(m);
        this.tresors = initTresors;
        this.train = completeMap;
    }

    public Scene getScene(int x, int y){
        if(x > NB_WAGON || x < 0 || y > 1 || y < 0){
            throw new Error("Mauvais indice pour getScene");
        }
        return train.get(y).get(x);
    }

    public void updateScene(int x, int y){
        train.get(y).get(x).update();
    }
}
