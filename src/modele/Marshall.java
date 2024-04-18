package modele;

import controleur.Partie;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Marshall extends Personnage{
    public int nervosite = 15;

    public Marshall(int x, String t,Plateau p){
        super(x,0,t,p);

    }

    public void expulse(){
        ArrayList<Bandit> cibleSet = this.plateau.getScene(this.coordX, this.coordY).getArrayBandits();
        if(cibleSet.isEmpty()) return; //Aucun bandit à taper
        System.out.println("Le marshall " +this.tag + " fait fuir des bandits");
        logCharacter = " Le marshall " +this.tag + " fait fuir ";
        for(Bandit cible : cibleSet){
            cible.dropButin();
            cible.fuit();
            logCharacter += cible.getTag() + ", ";
        }
        logCharacter += " !";
        return;
    }


    public void faitAction(){
        System.out.println(coordX + " " + coordY);
        int testNerveux = abs(Partie.rnd.nextInt()) % 100;
        expulse(); //Expulse le bandit de toute manière
        if(testNerveux < nervosite) {
            return; //Test échoué, le maréchal fait rien.
        }
        int closestX = partie.NB_WAGON;
        //Cherche le bandit le plus proche
        for(int i = 0; i <= partie.NB_WAGON - 1 ; i++){
            if(this.plateau.getScene(i, 0).getBandits().isEmpty())  continue;
            //else
            int oper = this.plateau.getScene(i, 0).getArrayBandits().get(0).getCoordX() - this.coordX;
            if(abs(oper) < abs(closestX)) closestX = oper;
        }
        if(closestX < 0) deplace(modele.DIRECTION.GAUCHE);
        //Si il y a aucun bandit en bas, se déplace à droite par défaut
        if(closestX > 0) deplace(modele.DIRECTION.DROITE);
        logCharacter = "";
        expulse(); //Expulse post-déplacement
        System.out.println(coordX + " " + coordY);
        return;
    }

}
