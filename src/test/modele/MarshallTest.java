package test.modele;

import controleur.Partie;
import modele.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class MarshallTest {
    @Test
    //Implication : déplacement, bandits fonctionnent
    void marshallTest(){
        System.out.println("TEST : MARSHALL");
        Partie testGame = new Partie(4,4,4,4);
        Marshall testMarshall = testGame.getMarshall();
        Bandit testBandit = testGame.getBandit(0);
        Bandit testBandit2 = testGame.getBandit(1);

        //Déplacement bandit
        assertEquals(testGame.NB_WAGON - 1, testMarshall.getCoordX());
        assertEquals(0, testMarshall.getCoordY());
        assertTrue(testBandit.deplace(DIRECTION.BAS));
        assertTrue(testBandit.deplace(DIRECTION.DROITE));
        assertTrue(testBandit.deplace(DIRECTION.DROITE)); // x, y = (2, 0)

        //Test déplacement
        while(testMarshall.getCoordX() != testGame.NB_WAGON - 2) {
            testMarshall.faitAction();
        }
        assertEquals(0, testMarshall.getCoordY());
        assertEquals(2, testMarshall.getCoordX());

        //Test expulsion
        assertEquals(1, testBandit.getCoordY());

        //Bandit va à la droite du marshall
        assertTrue(testBandit.deplace(DIRECTION.DROITE));
        assertTrue(testBandit.deplace(DIRECTION.BAS)); //x, y = (3, 0)

        //Un bandit descend
        assertTrue(testBandit2.deplace(DIRECTION.BAS)); //x, y = (0, 0)

        //Test de rapprochement du marshall : il doit aller vers le bandit le plus proche
        while(testMarshall.getCoordX() != testGame.NB_WAGON - 1) {
            testMarshall.faitAction();
            if(testMarshall.getCoordX() <= 1){
                fail("Marshall doit aller vers la droite ! : échec test");
            }
        }
        assertEquals(0, testMarshall.getCoordY());
        assertEquals(3, testMarshall.getCoordX());

        //Test expulsion
        assertEquals(1, testBandit.getCoordY());



    }

}

