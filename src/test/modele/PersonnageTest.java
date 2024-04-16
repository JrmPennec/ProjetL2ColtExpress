package test.modele;

import controleur.Partie;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PersonnageTest {
    @Test
    public void testMoveIsolated(){
        System.out.println("TEST PERSONNAGE : MOVEMENT");
        Partie testGame = new Partie(4,4,4,4);
        modele.Bandit testSubject = testGame.getBandits().get(0);
        boolean receiver;

        //Mouvement à droite
        receiver = testSubject.deplace(modele.DIRECTION.DROITE);
        assertEquals(1, testSubject.getCoordX());

        //Mouvement bloqué
        receiver = testSubject.deplace(modele.DIRECTION.GAUCHE);
        assertFalse(testSubject.deplace(modele.DIRECTION.GAUCHE));
        assertEquals(0, testSubject.getCoordX());

        //Mouvement 2
        assertTrue(testSubject.deplace(modele.DIRECTION.DROITE));
        assertEquals(1, testSubject.getCoordX());
        assertTrue(testSubject.deplace(modele.DIRECTION.DROITE));
        assertEquals(2, testSubject.getCoordX());

        //Mouvement complex
        assertEquals(1, testSubject.getCoordY());
        assertTrue(testSubject.deplace(modele.DIRECTION.BAS));
        assertEquals(0, testSubject.getCoordY());
        assertTrue(testSubject.deplace(modele.DIRECTION.GAUCHE));
        assertFalse(testSubject.deplace(modele.DIRECTION.BAS));
        assertTrue(testSubject.deplace(modele.DIRECTION.DROITE));
        assertEquals(2, testSubject.getCoordX());

    }



}
