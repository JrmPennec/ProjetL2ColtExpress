package modele;

import controleur.Jeu;
import controleur.Partie;

public abstract class Personnage extends Entite{
    Partie partie;

    public String logCharacter = "";

    public Personnage(int x,int y,String tag,Plateau p){
        super( x, y, tag, p);
        partie=p.getPartie();
        plateau.getScene(x,y).putPerso(this);
    }


    public boolean deplace(DIRECTION dir){ //renvoie un booléan confirmant le succès ou non de l'opération.

        int targetX = this.coordX;
        int targetY = this.coordY;
        boolean reussite=false;
        switch(dir){
            case GAUCHE :
                if(this.coordX -1 >= 0) {
                    targetX--;
                    System.out.println(this.tag + " se déplace à gauche");
                    logCharacter = this.tag + " se déplace à gauche";
                    reussite=true;
                }
                else{
                    System.out.println(this.tag + " est déja au bout du train");
                    logCharacter = this.tag + " est déjà au bout du train";
                }
                break;
            case DROITE :
                if(this.coordX +1 <= partie.NB_WAGON - 1) {
                    targetX++;
                    System.out.println(this.tag + " se déplace à droite");
                    logCharacter = this.tag + " se déplace à droite";
                    reussite=true;
                }else {
                    System.out.println(this.tag + " est déja dans/sur la locomotive");
                    logCharacter = this.tag + " est déjà dans/sur la locomotive";
                }
                break;
            case HAUT :
                if(this.coordY == 0) {
                    targetY++;
                    System.out.println(this.tag + " monte d'un étage");
                    logCharacter = this.tag + " se déplace en haut";
                    reussite=true;
                }
                else {
                    System.out.println(this.tag + " est déja sur le toit");
                    logCharacter = this.tag + " est déjà sur le toit";
                }
                break;
            case BAS :
                if(this.coordY == 1) {
                    targetY--;
                    System.out.println(this.tag + " descend");
                    logCharacter = this.tag + " se déplace en bas";
                    reussite=true;
                }
                else {
                    System.out.println(("Et déja a l'interieur du train"));
                    logCharacter = this.tag + " est déjà dans le train";
                }
                break;
            case NEUTRAL : default :
                System.out.println("Erreur : deplace() direction=neutral est impossible, skip la phase");
                break;

        }
        if (reussite)
            plateau.deplacePerso(this,targetX,targetY);
        this.coordX=targetX;
        this.coordY=targetY;
        System.out.println("modele.Bandit :"+this.tag+" X: "+coordX+ " Y: "+coordY);
        return reussite;


    }


/*


*/


}