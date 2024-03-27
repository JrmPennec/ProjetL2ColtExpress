import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public  abstract class Personnage extends Entite{

    public Personnage(int x,int y,String tag,Plateau p){
        super( x, y, tag, p);
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
                    reussite=true;
                }
                else
                    System.out.println(this.tag + " est déja au bout du train");
                break;
            case DROITE :
                if(this.coordX +1 <= Jeu.NB_WAGON - 1) {
                    targetX++;
                    System.out.println(this.tag + " se déplace à droite");
                    reussite=true;
                }else
                    System.out.println(this.tag + " est déja dans/sur la locomotive");
                break;
            case HAUT :
                if(this.coordY == 0) {
                    targetY++;
                    System.out.println(this.tag + " monte d'un étage");
                    reussite=true;
                }
                else
                    System.out.println(this.tag + " est déja sur le toit");
                break;
            case BAS :
                if(this.coordY == 1) {
                    targetY--;
                    System.out.println(this.tag + " descend");
                    reussite=true;
                }
                else
                    System.out.println(("Et déja a l'interieur du train"));
                break;
            case NEUTRAL : default :
                System.out.println("Erreur : deplace() direction=neutral est impossible, skip la phase");
                break;

        }
        if (reussite)
            plateau.deplacePerso(this,targetX,targetY);
        this.coordX=targetX;
        this.coordY=targetY;
        System.out.println("Bandit :"+this.tag+" X: "+coordX+ " Y: "+coordY);
        return reussite;


    }


    @BeforeEach
    void testPersonnage(){
        System.out.println("TEST PERSONNAGE : MOVEMENT");
    }

    @Test
    public void testMoveIsolated(){
        Jeu testGame = new Jeu();
        Personnage testSubject = testGame.getPersos().get(0);
        System.out.println("Mouvement à droite");
        assertTrue(testSubject.deplace(DIRECTION.DROITE));
        assertEquals(1, testSubject.coordX);

        System.out.println("Mouvement bloqué");
        assertTrue(testSubject.deplace(DIRECTION.DROITE));
        assertTrue(testSubject.deplace(DIRECTION.DROITE));
        assertFalse(testSubject.deplace(DIRECTION.DROITE));
        assertEquals(3, testSubject.coordX);

        System.out.println("Mouvement aller-retour");
        assertTrue(testSubject.deplace(DIRECTION.GAUCHE));
        assertEquals(2, testSubject.coordX);
        assertTrue(testSubject.deplace(DIRECTION.DROITE));
        assertEquals(3, testSubject.coordX);

        System.out.println("Mouvement complex");
        assertEquals(1, testSubject.coordY);
        assertFalse(testSubject.deplace(DIRECTION.HAUT));
        assertEquals(1, testSubject.coordY);
        assertTrue(testSubject.deplace(DIRECTION.GAUCHE));
        assertTrue(testSubject.deplace(DIRECTION.BAS));
        assertTrue(testSubject.deplace(DIRECTION.DROITE));
        assertEquals(3, testSubject.coordX);

    }



}
