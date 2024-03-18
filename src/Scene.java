import java.util.ArrayList;

public class Scene extends Observable {
    private boolean toit;
    private boolean locomotive;
    private  boolean arriere;

    private ArrayList<Personnage> persos;

    private ArrayList<Objet> tresor;

    boolean estToit(){
        return toit;
    }
    Scene(boolean toit,boolean locomotive,boolean arriere){
        if (locomotive==arriere)
            throw new Error("Locomotive ne peut être l'arriere du train ");
        this.toit=toit;
        this.locomotive=locomotive;
        this.arriere=arriere;
    }
    public boolean estArriere(){
        return arriere;
    }

    public boolean estLocomotive(){ return locomotive;}
    public  ArrayList<Objet> getTresor(){ return tresor;}
    public ArrayList<Personnage> getPersos(){return persos;}


    }

}
