package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scene extends Observable {
    private boolean toit;
    private boolean locomotive;
    private  boolean arriere;

    private HashMap<String,Bandit> bandits;

    private Marshall marshall;

    private ArrayList<Objet> tresor;

    public boolean estToit(){
        return toit;
    }
    public Scene(boolean toit,boolean locomotive,boolean arriere){
        if (locomotive && arriere)
            throw new Error("Locomotive ne peut être l'arriere du train ");
        this.toit = toit;
        this.locomotive = locomotive;
        this.arriere = arriere;
        this.bandits = new HashMap<>();
        this.marshall = null;
        this.tresor = new ArrayList<>();
    }
    public boolean estArriere(){
        return arriere;
    }

    public boolean estLocomotive(){ return locomotive;}
    public boolean isMarshallHere(){
        return(marshall!=null);
    }
    public Marshall getMarshall(){
        return marshall;
    }
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

    public HashMap<String,Bandit> getBandits(){return bandits;}

    public ArrayList<Bandit> getArrayBandits(){
        ArrayList<Bandit> banditList = new ArrayList<>(0);
        for(Map.Entry<String, Bandit> subject : this.bandits.entrySet()){
             banditList.add((Bandit)subject.getValue());
        }
        return banditList;
    }

    public void putPerso(Personnage p){
        if (p instanceof Marshall)
            marshall=(Marshall)p;
        else if (p instanceof Bandit)
                bandits.put(p.getTag(),(Bandit)p);
        else
            throw  new IllegalArgumentException("Argument invalide");
    }
    public Bandit removeBandit(String tag){
        return bandits.remove(tag);
    }
    public Marshall removeMarshall(){
        Marshall m =marshall;
        marshall = null;
        return m;
    }
    public Personnage removePerso(String tag){
        //cas particulier
        if (isMarshallHere()) {
            if (tag.equals(marshall.getTag()))
                return removeMarshall();
        }

        return removeBandit(tag);
    }




public static void main(String[] args){
}
}


