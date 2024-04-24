package test.modele;
import controleur.Partie;
import modele.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BanditTest {
    Partie testGame;
    Personnage testSubject;

    @BeforeEach
    void testPersonnage() {
        Partie testGame = new Partie(4,4,4,4);
        Bandit testSubject = testGame.getBandit(0);
    }

    @Test
    void testStack(){
        Partie testGame = new Partie(4,4,4,4);
        Bandit testSubject = testGame.getBandit(0); //Jank code
        System.out.println(" TEST : STACK");
        System.out.println("X : "+ testSubject.getCoordX()+ " Y : "+ testSubject.getCoordY());
        Input testInput1 = new Input(DIRECTION.DROITE, ACTION.DEPLACE);
        Input testInput2 = new Input(DIRECTION.DROITE, ACTION.TIR);
        Input testInput3 = new Input(DIRECTION.HAUT, ACTION.DEPLACE);
        Input testInput4 = new Input(DIRECTION.NEUTRAL, ACTION.BRAQUE);
        Input inputReceiver;

        Bandit testBandit = (Bandit) testSubject;

        //Test de popAction de base
        testBandit.putAction(testInput1);
        inputReceiver = testBandit.popAction();
        assertEquals(inputReceiver, testInput1);

        //Test de popAction après 2 enpile.
        testBandit.putAction(testInput1);
        testBandit.putAction(testInput2);
        inputReceiver = testBandit.popAction();
        assertEquals(inputReceiver, testInput1);
        inputReceiver = testBandit.popAction();
        assertEquals(inputReceiver, testInput2);

        //Test de limite de stack
        testBandit.putAction(testInput4);
        testBandit.putAction(testInput4);
        testBandit.putAction(testInput1);
        testBandit.putAction(testInput3);
        inputReceiver = testBandit.popAction();
        assertEquals(inputReceiver, testInput4);
        inputReceiver = testBandit.popAction();
        assertEquals(inputReceiver, testInput4);
        inputReceiver = testBandit.popAction();
        assertEquals(inputReceiver, testInput1);
        try{
            inputReceiver = testBandit.popAction();
        }catch(Error e){
            assertTrue(true);
        }

    }

    //Présupposé : déplacement qui fonctionne (Personne.java)
    @Test
    void testTir(){
        System.out.println(" TEST : TIR");
        Partie testGame = new Partie(4,4,4,4);
        Bandit testSubject1 = (Bandit)testGame.getBandits().get(0); //x , y = 0 , 1
        Bandit testSubject2 = (Bandit)testGame.getBandits().get(1); //x , y = 0 , 1
        Bandit testSubject3 = (Bandit)testGame.getBandits().get(2); //x , y = 0 , 1
        Bandit testSubject4 = (Bandit)testGame.getBandits().get(3); //x , y = 0 , 1
        boolean receptor; //Existe juste pour attraper les returns des deplacements, dont le résultat de l'opération ne nous intéresse pas.

        //Test tir simple dans la salle
        //Convention : on ne peut tirer sur un bandit dans la même salle. (For now)
        assertFalse(testSubject1.tir(DIRECTION.HAUT));
        assertFalse(testSubject1.tir(DIRECTION.BAS));
        assertFalse(testSubject1.tir(DIRECTION.DROITE));
        assertFalse(testSubject1.tir(DIRECTION.GAUCHE));

        //Test : 1 case plus loin
        receptor = testSubject1.deplace(DIRECTION.BAS); //x , y = 0 , 0
        receptor = testSubject3.deplace(DIRECTION.DROITE); //x , y = 1 , 1
        receptor = testSubject4.deplace(DIRECTION.DROITE); //x , y = 1 , 1
        receptor = testSubject3.deplace(DIRECTION.BAS); //x , y = 1 , 0
        assertTrue(testSubject1.tir(DIRECTION.HAUT));
        assertTrue(testSubject1.tir(DIRECTION.DROITE));

        //Test de fuite dû au dégat, étape 1
        assertFalse(testSubject1.tir(DIRECTION.HAUT));
        assertFalse(testSubject1.tir(DIRECTION.GAUCHE));

        //Test : fuite dû au dégat. étape 2
        receptor = testSubject1.deplace(DIRECTION.HAUT);
        assertTrue(testSubject1.tir(DIRECTION.DROITE)); // x , y = 1 , 1

    }

    @Test
    //Presupposition : putObjet, retireObjet, Constructeur objet marche
    void testButin(){
        System.out.println(" TEST : BUTIN");
        Partie testGame = new Partie(4,4,4,4);
        Bandit testBandit = (Bandit)testGame.getBandits().get(0); //x , y = 0 , 0

        //Insertion de 3 objets.
        Scene scene1 = testGame.getPlateau().getScene(testBandit.getCoordX(), testBandit.getCoordY());
        Scene scene2 = testGame.getPlateau().getScene(testBandit.getCoordX() + 2, 0);

        modele.Objet loot1 = new modele.Objet(testBandit.getCoordX(), testBandit.getCoordY(), "MeShimmers" ,testGame.getPlateau(), LOOTTYPE.BIJOUX);
        modele.Objet loot2 = new modele.Objet(testBandit.getCoordX() + 2, 0, "MeLoot'", testGame.getPlateau(), LOOTTYPE.MAGOT);
        modele.Objet loot3 = new modele.Objet(testBandit.getCoordX() + 2, 0, "MeShiny", testGame.getPlateau(), LOOTTYPE.BIJOUX);

        //Check inventaire
        assertEquals(0, testBandit.getTotalValeur());

        //Braque bijoux
        testBandit.braqueButin();
        assertEquals(500, testBandit.getTotalValeur());

        //Braque : rien trouvé
        testBandit.braqueButin();
        assertEquals(500, testBandit.getTotalValeur());

        //Test dropButin
        testBandit.dropButin();
        assertEquals(0, testBandit.getTotalValeur());

        //Déplacement vers loot2/loot3
        assertTrue(testBandit.deplace(DIRECTION.DROITE));
        assertTrue(testBandit.deplace(DIRECTION.DROITE));
        assertTrue(testBandit.deplace(DIRECTION.BAS));

        //Braque 2 trésors
        testBandit.braqueButin();
        testBandit.braqueButin(); //Magot + bijoux = 1500
        assertEquals(1500, testBandit.getTotalValeur());
        testBandit.dropButin();
        assertTrue(testBandit.getTotalValeur() == 1000 || testBandit.getTotalValeur() == 500);


    }


}