package modele;

public class Objet extends Entite{

    private modele.Bandit proprietaire;
    private LootType type;
    private final int valeur;

    Objet(int x,int y, String tag, Plateau p, LootType type){
        super(x,y,tag,p);
        plateau.getScene(x,y).putObjet(this);
        this.proprietaire = null;
        this.type = type;
        switch(this.type){
            case BIJOUX : this.valeur = 500; break;
            case BOURSE : this.valeur = Jeu.rnd.nextInt() % 501; break;
            case MAGOT  : this.valeur = 1000; break;
            default : throw new Error("Constructeur Objet : type de trésor mal définit !");
        }

    }

    int getValeur(){
        return this.valeur;
    }

    public void estPris(modele.Bandit b){
        if(this.proprietaire != null){
            throw new Error("estPris : l'objet a déjà un bandit propriétaire");
        }
        this.proprietaire = b;
        this.coordX = -1;
        this.coordY = -1;
        this.plateau.getScene(this.coordX, this.coordY).retireObjet(this);
    }
    public void estLache(){
        if(this.proprietaire == null){
            throw new Error("estLache : l'objet est déjà laché");
        }
        this.coordX = this.proprietaire.getCoordX();
        this.coordY = this.proprietaire.getCoordY();
        this.proprietaire = null;
        this.plateau.getScene(this.coordX, this.coordY).putObjet(this);

    }



}
