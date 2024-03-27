package modele;

import java.util.ArrayList;
import java.util.Random;

 public class Marshall extends Personnage{
    public int nervosite = 30;
    private Random rnd = new Random();

    public Marshall(int x, String t,Plateau p){
        super(x,0,t,p);

    }

    public void expulse(){
        ArrayList<Bandit> cibleSet = this.plateau.getScene(this.coordX, this.coordY).getArrayBandits();
        if(cibleSet.isEmpty()) return; //Aucun bandit à taper
        System.out.println("Le maréchal " +this.tag + " fait fuir des bandits");
        for(Bandit cible : cibleSet){
            cible.dropButin();
            cible.fuit();
        }
        return;
    }


   /* public void faitAction(){
        int testNerveux = rnd.nextInt() % 100;
        if(testNerveux < nervosite) return; //Test échoué, le maréchal fait rien.
        int closestX = this.plateau.NB_WAGON;
        //Cherche le bandit le plus proche
        for(int i = 0; i <= this.plateau.NB_WAGON - 1 ; i++){
            if(this.plateau.getScene(i, 0).getBandits().isEmpty())  continue;
            //else
            int oper = this.plateau.getScene(i, 0).getBandits().get(0).getCoordX() - this.coordX;
            if(Math.abs(oper) < Math.abs(closestX)) closestX = oper;
        }
        if(closestX < 0) deplace(modele.DIRECTION.GAUCHE);
        //Si il y a aucun bandit en bas, se déplace à droite par défaut
        if(closestX > 0) deplace(modele.DIRECTION.DROITE);
        expulse(); //Expulse le bandit de toute manière
        return;
    }
*/
}
