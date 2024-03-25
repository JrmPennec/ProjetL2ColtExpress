import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public  abstract class Personnage extends Entite{

    enum DIRECTION{
        GAUCHE, DROITE, HAUT, BAS, NEUTRAL
    }

    public Personnage(int x,int y,String tag,Plateau p){
        super( x, y, tag, p);
    }

    public boolean deplace(DIRECTION dir){ //renvoie un booléan confirmant le succès ou non de l'opération.
        switch(dir){
            case GAUCHE :
                if(this.coordX -1 >= 0) this.coordX--;
                else return false;
                System.out.println(this.tag + " se déplace à gauche");
                return true;
            case DROITE :
                if(this.coordX +1 <= Jeu.NB_WAGON - 1) this.coordX++;
                else return false;
                System.out.println(this.tag + " se déplace à droite");
                return true;
            case HAUT :
                if(this.coordY == 0) this.coordY++;
                else return false;
                System.out.println(this.tag + " monte d'un étage");
                return true;
            case BAS :
                if(this.coordY == 1) this.coordY--;
                else return false;
                System.out.println(this.tag + " descend");
                return true;
            case NEUTRAL : default :
                System.out.println("Erreur : deplace() direction=neutral est impossible, skip la phase");
                return false;

        }

    }

    @BeforeEach
    void testPersonnage(){
        System.out.println("TEST PERSONNAGE : MOVEMENT");
    }

    @Test
    public void testMoveIsolated(){
        Jeu testGame = new Jeu();
        Personnage testSubject = testGame.getPersos().get(0);
        //Mouvement à droite
        assertTrue(testSubject.deplace(DIRECTION.DROITE));
        assertEquals(1, testSubject.coordX);

        //Mouvement bloqué
        assertFalse(testSubject.deplace(DIRECTION.DROITE));
        assertEquals(1, testSubject.coordX);

        //Mouvement aller-retour
        assertTrue(testSubject.deplace(DIRECTION.GAUCHE));
        assertEquals(0, testSubject.coordX);
        assertTrue(testSubject.deplace(DIRECTION.DROITE));
        assertEquals(1, testSubject.coordX);

        //Mouvement complex
        assertEquals(0, testSubject.coordY);
        assertTrue(testSubject.deplace(DIRECTION.HAUT));
        assertEquals(1, testSubject.coordY);
        assertTrue(testSubject.deplace(DIRECTION.GAUCHE));
        assertTrue(testSubject.deplace(DIRECTION.BAS));
        assertTrue(testSubject.deplace(DIRECTION.DROITE));
        assertEquals(1, testSubject.coordX);

    }



}
