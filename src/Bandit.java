import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


public class Bandit extends Personnage{
    private Random rnd ;
    private Stack<Input> buffer ;
    private ArrayList<Objet> loot ;

   /* public int getTotalValeur(){
        int result = 0;
        for(Objet i : loot){
            result += i.getValeur();
        }
        return result;
    }*/

    public Bandit(int x, int y, String t, Plateau p){
        super( x,  y,  t, p);
        this.rnd= new Random();
        this.buffer = new Stack<>();
        this.loot = new ArrayList<>();
    }

   /*public void braqueButin(){
        ArrayList<Objet> lootTrouve = new ArrayList<>();
        lootTrouve = this.plateau.getScene(coordX, coordY).getTresor();
        if(!lootTrouve.isEmpty()){
            Objet premierLoot = lootTrouve.get(rnd.nextInt() % lootTrouve.size());
            premierLoot.estPris(this);
            this.plateau.getScene(coordX,coordY).retireObjet(premierLoot);
            loot.add(premierLoot);
            System.out.println(this.tag + " braque et trouve " + premierLoot.tag);
            return;
        }
    }*/

/*
    public void dropButin(){
        loot.get(loot.size()-1).estLache();
        loot.remove(loot.size()-1);
    }*/

    public void fuit(){
        System.out.println( this.tag + " a eu peur et s'en va");
        if(this.coordY == 0) {this.coordY = 1; return;}
        this.coordY = 0; return;
    }

    public boolean tir(DIRECTION dir){
        switch(dir){
            case HAUT :
                if(this.coordY == 0) {
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(coordX, 1).getBandits();
                    if(cibleSet.isEmpty()) break; //Aucune cible, on quitte.
                    Bandit cible = cibleSet.get(rnd.nextInt() % cibleSet.size());
                    cible.dropButin();
                    cible.fuit();
                    System.out.println(this.tag + " pew pew en haut !");
                    return true;
                }
                System.out.println( this.tag + " tire, mais il est schizophrénique et rate sa cible ...");
                break;
            case BAS :
                if(this.coordY == 1) {
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(coordX, 0).getBandits();
                    if(cibleSet.isEmpty()) break; //Aucune cible, on quitte.
                    Bandit cible = cibleSet.get(rnd.nextInt() % cibleSet.size());
                    cible.dropButin();
                    cible.fuit();
                    System.out.println(this.tag + " pew pew en bas !");
                    return true;
                }
                System.out.println( this.tag + " tire, mais il est schizophrénique et rate sa cible ...");
                break;
            case GAUCHE :
                for(int i = coordX; i >= 0; i--){
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(i, coordY).getBandits();
                    if(cibleSet.isEmpty())  continue;//Rien, la balle traverse le wagon
                    Bandit cible = cibleSet.get(rnd.nextInt() % cibleSet.size()); //Cible frappé
                    cible.dropButin();
                    cible.fuit();
                    System.out.println("Bandit " + this.tag + "  pew pew à gauche !");
                    return true;
                }
                System.out.println( this.tag + " tire ! Mais il est schizophrénique et rate sa cible ...");
                break;
            case DROITE :
                for(int i = coordX; i <= Jeu.NB_WAGON - 1; i++){
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(i, coordY).getBandits();
                    if(cibleSet.isEmpty())  continue;//Rien, la balle traverse le wagon
                    Bandit cible = cibleSet.get(rnd.nextInt() % cibleSet.size()); //Cible frappé
                    cible.dropButin();
                    cible.fuit();
                    System.out.println(this.tag + "  pew pew à droite !");
                    return true;
                }
                System.out.println( this.tag + " tire, mais il est schizophrénique et rate sa cible ...");
                break;
            case NEUTRAL : default :
                System.out.println("Erreur : Tir() direction=neutral est impossible, skip la phase");
                break;
        }
        return false;
    }

    public void putAction(Input action){
        if(this.buffer.size() > 3){
            System.out.println("Erreur : putAction(), buffer déjà taille maximale (3), skip phase");
            return;
        }
        this.buffer.push(action);
    }

    public Input popAction() {
        if(this.buffer.isEmpty()){
            throw new Error("Erreur fatale: popAction(), buffer vide, corriger");
        }
        Input result = buffer.pop();
        return result;
    }

    public void executionStack(){
        Input input = this.popAction();
        switch(input.action){
            case DEPLACE :
               this.deplace(input.direction);
                break;


            //case ACTION.BRAQUE : this.braqueButin(); break;
            //case ACTION.TIR : this.tir(input.direction); break;
            default : return;

        }

        System.out.println(this.getTag() + " exécution");
    }
    public void dropButin(){}

    @BeforeEach
    void testPersonnage(){
        System.out.println("TEST BANDIT");
    }

    @Test
    void testStack(){
        System.out.println(" TEST : STACK");
        Input testInput1 = new Input(DIRECTION.DROITE, ACTION.DEPLACE);
        Input testInput2 = new Input(DIRECTION.DROITE, ACTION.TIR);
        Input testInput3 = new Input(DIRECTION.HAUT, ACTION.DEPLACE);
        Input testInput4 = new Input(DIRECTION.NEUTRAL, ACTION.BRAQUE);
        Input inputReceiver;
        Jeu testGame = new Jeu();
        Personnage testSubject = testGame.getPersos().get(0);
        if(!(testSubject instanceof Bandit)) {
            fail("initBandit mal fait");
        }

        Bandit testBandit = (Bandit) testSubject;

        //Test de popAction de base
        testBandit.putAction(testInput1);
        inputReceiver = testBandit.popAction();
        assertEquals(inputReceiver, testInput1);

        //Test de popAction après 2 enpile.
        testBandit.putAction(testInput1);
        testBandit.putAction(testInput2);
        inputReceiver = testBandit.popAction();
        assertEquals(inputReceiver, testInput2);
        inputReceiver = testBandit.popAction();
        assertEquals(inputReceiver, testInput1);

        //Test de limite de stack
        testBandit.putAction(testInput4);
        testBandit.putAction(testInput4);
        testBandit.putAction(testInput1);
        testBandit.putAction(testInput3);
        inputReceiver = testBandit.popAction();
        assertEquals(inputReceiver, testInput1);

        //Test final
        inputReceiver = testBandit.popAction();
        assertEquals(inputReceiver, testInput4);

        //Test empty
        inputReceiver = testBandit.popAction();
        try{
            inputReceiver = testBandit.popAction();
            fail("Erreur pas attrapé");
        }catch(Error a){
            assertTrue(true);
        }


    }

    //Présupposé : déplacement qui fonctionne (Personne.java)
    @Test
    void testTir(){
        System.out.println(" TEST : TIR");
        Jeu testGame = new Jeu();
        Bandit testSubject1 = (Bandit)testGame.getPersos().get(0); //x , y = 0 , 0
        Bandit testSubject2 = (Bandit)testGame.getPersos().get(1); //x , y = 0 , 0
        Bandit testSubject3 = (Bandit)testGame.getPersos().get(2); //x , y = 0 , 0
        Bandit testSubject4 = (Bandit)testGame.getPersos().get(3); //x , y = 0 , 0
        boolean receptor; //Existe juste pour attraper les returns des deplacements, dont le résultat de l'opération ne nous intéresse pas.

        //Test tir simple dans la salle
        //Convention : on ne peut tirer sur un bandit dans la même salle. (For now)
        assertFalse(testSubject1.tir(DIRECTION.HAUT));
        assertFalse(testSubject1.tir(DIRECTION.BAS));
        assertFalse(testSubject1.tir(DIRECTION.DROITE));
        assertFalse(testSubject1.tir(DIRECTION.GAUCHE));

        //Test : 1 case plus loin
        receptor = testSubject2.deplace(DIRECTION.HAUT); //x , y = 0 , 1
        receptor = testSubject3.deplace(DIRECTION.GAUCHE); //x , y = 1 , 0
        assertTrue(testSubject1.tir(DIRECTION.HAUT));
        assertTrue(testSubject1.tir(DIRECTION.GAUCHE));

        //Test de fuite dû au dégat, étape 1
        assertFalse(testSubject1.tir(DIRECTION.HAUT));
        assertFalse(testSubject1.tir(DIRECTION.GAUCHE));

        //Test : fuite dû au dégat. étape 2
        receptor = testSubject1.deplace(DIRECTION.HAUT);
        assertTrue(testSubject1.tir(DIRECTION.GAUCHE)); // x , y = 1 , 1

        //Test : Tir après déplacement abscisse
        receptor = testSubject1.deplace(DIRECTION.GAUCHE);
        receptor = testSubject1.deplace(DIRECTION.BAS);
        receptor = testSubject2.deplace(DIRECTION.DROITE);
        assertEquals(testSubject2.getCoordX(), 0);
        assertEquals(testSubject2.getCoordY(), 0);
        assertEquals(testSubject3.getCoordX(), 0);
        assertEquals(testSubject3.getCoordY(), 0);
        receptor = testSubject1.deplace(DIRECTION.DROITE);
        assertEquals(testSubject1.getCoordX(), 2);
        assertEquals(testSubject2.getCoordY(), 0);
        assertTrue(testSubject1.tir(DIRECTION.GAUCHE)); // vers 0, 0
        assertTrue(testSubject1.tir(DIRECTION.GAUCHE));
        assertTrue(testSubject1.tir(DIRECTION.GAUCHE));
        assertFalse(testSubject1.tir(DIRECTION.GAUCHE)); //Les 3 bandits ont fuit en haut.
    }

    void testButin(){
        System.out.println(" TEST : BUTIN");
        Jeu testGame = new Jeu();
        Bandit testSubject1 = (Bandit)testGame.getPersos().get(0); //x , y = 0 , 0
        Bandit testSubject2 = (Bandit)testGame.getPersos().get(1); //x , y = 0 , 0


    }



}
