import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scene extends Observable {
    private boolean toit;
    private boolean locomotive;
    private  boolean arriere;

    private HashMap<String,Personnage> persos;

    private ArrayList<Objet> tresor;

    boolean estToit(){
        return toit;
    }
    Scene(boolean toit,boolean locomotive,boolean arriere){
        if (locomotive && arriere)
            throw new Error("Locomotive ne peut être l'arriere du train ");
        this.toit = toit;
        this.locomotive = locomotive;
        this.arriere = arriere;
        this.persos = new HashMap<>();
        this.tresor = new ArrayList<>();
    }
    public boolean estArriere(){
        return arriere;
    }

    public boolean estLocomotive(){ return locomotive;}
    public  ArrayList<Objet> getTresor(){ return tresor;}

    public void retireObjet(Objet obj){
        for(Objet i : this.tresor){
            if(i.getTag().equals(obj.getTag())){
                tresor.remove(i);
                return;
            }
        }
        throw new Error("retireObjet : L'objet n'existe pas dans la scène !");
    }

    public HashMap<String,Personnage> getPersos(){return persos;}

    public ArrayList<Bandit> getBandits(){
        ArrayList<Bandit> banditList = new ArrayList<>(0);
        for(Map.Entry<String, Personnage> subject : this.persos.entrySet()){
            if(subject.getValue() instanceof Bandit) banditList.add((Bandit)subject.getValue());
        }
        return banditList;
    }

    public void putPerso(Personnage p){
        persos.put(p.getTag(),p);
    }
    public Personnage removePerso(String tag){
        return persos.remove(tag);
    }



public static void main(String[] args){
}
}


