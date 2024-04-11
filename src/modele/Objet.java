package modele;

import static java.lang.Math.abs;

public class Objet extends Entite{

    private modele.Bandit proprietaire;
    private LootType type;
    private final int valeur;

    public Objet(int x,int y, String tag, Plateau p, LootType type){
        super(x,y,tag,p);
        plateau.getScene(x,y).putObjet(this);
        this.proprietaire = null;
        this.type = type;
        switch(this.type){
            case BIJOUX : this.valeur = 500; break;
            case BOURSE : this.valeur = abs(Jeu.rnd.nextInt()) % 501; break;
            case MAGOT  : this.valeur = 1000; break;
            default : throw new Error("Constructeur Objet : type de trésor mal définit !");
        }

    }

    public int getValeur(){
        return this.valeur;
    }

    public void estPris(modele.Bandit b){
        if(this.proprietaire != null){
            throw new Error("estPris : l'objet a déjà un bandit propriétaire");
        }
        this.proprietaire = b;
        this.plateau.getScene(this.proprietaire.getCoordX(), this.proprietaire.getCoordY()).retireObjet(this);
        this.coordX = -1;
        this.coordY = -1;
    }
    public void estLache(){
        if(this.proprietaire == null){
            throw new Error("estLache : l'objet est déjà laché");
        }
        this.coordX = this.proprietaire.getCoordX();
        this.coordY = this.proprietaire.getCoordY();
        this.plateau.getScene(this.coordX, this.coordY).putObjet(this);
        this.proprietaire = null;


    }



}

