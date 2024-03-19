
public class Personnage extends Entite{

    enum DIRECTION{
        GAUCHE, DROITE, HAUT, BAS, NEUTRAL
    }

    public void deplace(DIRECTION dir){
        int oldX=coordX;
        int oldY=coordY;
        switch(dir){
            case GAUCHE :
                if(this.coordX -1 >= 0) this.coordX--;
                System.out.println(this.tag + " se déplace à gauche");
                plateau.deplacePerso(this,coordY,coordX);
                break;
            case DROITE :
                if(this.coordX +1 <= Jeu.NB_WAGON - 1) this.coordX++; //C'est une const, pas d'inquiétude de modification.
                System.out.println(this.tag + " se déplace à droite");
                plateau.deplacePerso(this,coordY,coordX);
                break;
            case HAUT :
                if(this.coordY == 0) this.coordY++;
                System.out.println(this.tag + " monte d'un étage");
                plateau.deplacePerso(this,coordY,coordX);
                break;
            case BAS :
                if(this.coordY == 1) this.coordY--;
                System.out.println(this.tag + " descend");
                plateau.deplacePerso(this,coordY,coordX);
                break;
            case NEUTRAL : default :
                System.out.println("Erreur : deplace() direction=neutral est impossible, skip la phase");
                break;

        }

    }



}
