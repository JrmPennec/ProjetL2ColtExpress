package controleur;

import vue.VueJeu;

public class Jeu {
    private VueJeu vue;
    private Partie partie;
    private int nbJoueurs;
    private int nbTours;
    private int nbActions;
    private int nbWagons;


    //GETTER /SETTER
    public Partie getPartie() {
        return partie;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }
    public void setNbJoueurs(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }
    public int getNbTours() {
        return nbTours;
    }
    public void setNbTours(int nbTours) {
        this.nbTours = nbTours;
    }
    public int getNbActions() {
        return nbActions;
    }
    public void setNbActions(int nbActions) {
        this.nbActions = nbActions;
    }
    public int getNbWagons() {
        return nbWagons;
    }
    public void setNbWagons(int nbWagons) {
        this.nbWagons = nbWagons;
    }


    public Jeu(int width,int height){
        vue=new VueJeu(this,width,height);
        nbJoueurs=2;
        nbTours=4;
        nbActions=1;
        nbWagons=2;


    }
    public void lancerPartie(){
        System.out.println("Joueurs :"+nbJoueurs+ " Tours = "+nbTours+" Actions = "+nbActions+ "Wagons = "+nbWagons);
        partie = new Partie(this,nbJoueurs,nbTours,nbActions,nbWagons);
        vue.lancePartie();
    }
    public void finPartie(){
        vue.lanceScore();

    }
    static public void main(String[] args){
        Jeu jeu= new Jeu(1280,720);

}
}
