
public class Personnage extends Entite{

    enum DIRECTION{
        GAUCHE, DROITE, HAUT, BAS, NEUTRAL
    }

    public void deplace(DIRECTION dir){
        switch(dir){
            case GAUCHE :
                if(this.coordX -1 >= 0) this.coordX--;
                System.out.println(this.tag + " se déplace à gauche");
                break;
            case DROITE :
                if(this.coordX +1 <= plateau.NB_WAGON - 1) this.coordX++; //C'est une const, pas d'inquiétude de modification.
                System.out.println(this.tag + " se déplace à droite");
                break;
            case HAUT :
                if(this.etage == 0) this.etage++;
                System.out.println(this.tag + " monte d'un étage");
                break;
            case BAS :
                if(this.etage == 1) this.etage--;
                System.out.println(this.tag + " descend");
                break;
            case NEUTRAL : default :
                System.out.println("Erreur : deplace() direction=neutral est impossible, skip la phase");
                break;

        }

    }



}
