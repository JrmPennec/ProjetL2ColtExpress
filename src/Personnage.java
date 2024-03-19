
abstract class Personnage extends Entite{

    enum DIRECTION{
        GAUCHE, DROITE, HAUT, BAS, NEUTRAL
    }

    public Personnage(int x,int y,String tag,Plateau p){super(x,y,tag,p);}

    public void deplace(DIRECTION dir){
        int newX;
        int newY;
        switch(dir){
            case GAUCHE :
                if(this.coordX -1 >= 0) {
                     newX=coordX-1;
                System.out.println(this.tag + " se déplace à gauche");
                plateau.deplacePerso(this,newX,this.coordY);
                    coordX=newX;}
                else
                    System.out.println(this.tag + " est déja au bout du train");
                break;
            case DROITE :
                if(this.coordX +1 <= Jeu.NB_WAGON - 1) {
                    newX = coordX + 1; //C'est une const, pas d'inquiétude de modification.
                    System.out.println(this.tag + " se déplace à droite");
                    plateau.deplacePerso(this, newX, coordY);
                    coordX = newX;
                }
                else
                    System.out.println(this.tag + " ne peut pas avancer plus loin que dans la locomotive");

                break;
            case HAUT :
                if(this.coordY == 0) {
                    newY = coordY + 1;
                    System.out.println(this.tag + " monte d'un étage");
                    plateau.deplacePerso(this, coordX, newY);
                    coordY = newY;
                }
                else
                    System.out.println(this.tag +" est deja sur le toit");
                break;
            case BAS :
                if(this.coordY == 1) {
                    newY = coordY - 1;
                    System.out.println(this.tag + " descend");
                    plateau.deplacePerso(this, coordX, newY);
                    coordY = newY;
                }
                else
                    System.out.println(this.tag +" est deja dans le wagon");

                break;
            case NEUTRAL : default :
                System.out.println("Erreur : deplace() direction=neutral est impossible, skip la phase");
                break;

        }

    }



}
