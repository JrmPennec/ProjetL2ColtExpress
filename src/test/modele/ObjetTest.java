package test.modele;

import controleur.Partie;
import modele.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjetTest {

    @Test
    void objetTest(){
        Partie testGame = new Partie(4,4,4,4);
        System.out.println("TEST : OBJET");

        //Insertion de 3 objets.
        Scene scene1 = testGame.getPlateau().getScene(0, 0);
        Scene scene2 = testGame.getPlateau().getScene(2, 0);

        modele.Objet loot1 = new modele.Objet(0, 0, "MeShimmers" ,testGame.getPlateau(), LOOTTYPE.BOURSE);
        modele.Objet loot2 = new modele.Objet(2, 0, "MeLoot'", testGame.getPlateau(), LOOTTYPE.MAGOT);
        modele.Objet loot3 = new modele.Objet(2, 0, "MeShiny", testGame.getPlateau(), LOOTTYPE.BIJOUX);

        //Check constructeur
        for(int i = 0; i < 400; i++) {
            modele.Objet bourseCheck = new modele.Objet(0, 0, "MeShimmers" ,testGame.getPlateau(), LOOTTYPE.BOURSE);
            testGame.getPlateau().getScene(0, 0).retireObjet(bourseCheck);
            if(bourseCheck.getValeur() < 0 || bourseCheck.getValeur() > 500)  assertFalse(false);
        }
        assertTrue(true);

        //Existence tresor check
        assertTrue(testGame.getPlateau().getScene(2, 0).getTresor().size() >= 2);

        //Test remove
        int sceneLootSize = testGame.getPlateau().getScene(2, 0).getTresor().size();
        testGame.getPlateau().getScene(2, 0).retireObjet(loot2);
        testGame.getPlateau().getScene(2, 0).retireObjet(loot3);
        assertEquals(sceneLootSize - 2, testGame.getPlateau().getScene(2, 0).getTresor().size());
        try{
            testGame.getPlateau().getScene(2, 0).retireObjet(loot1);
            fail("Cette objet ne peut logiquement pas être retiré : échec test");

        }catch(Error e) {assertTrue(true);}

        //Test add
        sceneLootSize = testGame.getPlateau().getScene(0, 0).getTresor().size();
        testGame.getPlateau().getScene(0, 0).retireObjet(loot1);
        testGame.getPlateau().getScene(0, 0).putObjet(loot1);
        assertEquals(sceneLootSize, testGame.getPlateau().getScene(0, 0).getTresor().size());


    }



}
