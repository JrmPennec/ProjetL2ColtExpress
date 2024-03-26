import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

enum ACTION{
    TIR, DEPLACE, BRAQUE,

}
class Input{
    public Personnage.DIRECTION direction;
    public ACTION action;

    public Input(Personnage.DIRECTION d, ACTION a){
        this.direction = d;
        this.action = a;
    }

}


public class Bandit extends Personnage{
    private Random rnd ;
    private Stack<Input> buffer ;
    private ArrayList<Objet> loot ;

    /*public int getTotalValeur(){
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

   public void braqueButin(){
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
    }


    public void dropButin(){
        loot.get(loot.size()-1).estLache();
        loot.remove(loot.size()-1);
    }

    public void fuit(){
        System.out.println( this.tag + " a eu peur et s'en va");
        if(this.coordY == 0) {this.coordY = 1; return;}
        this.coordY = 0; return;
    }

    public void tir(DIRECTION dir){
        switch(dir){
            case HAUT :
                if(this.coordY == 0) {
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(coordX, 1).getBandits();
                    if(cibleSet.isEmpty()) break; //Aucune cible, on quitte.
                    Bandit cible = cibleSet.get(rnd.nextInt() % cibleSet.size());
                    cible.dropButin();
                    cible.fuit();
                    System.out.println(this.tag + " pew pew en haut !");
                    return;
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
                    return;
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
                    return;
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
                    return;
                }
                System.out.println( this.tag + " tire, mais il est schizophrénique et rate sa cible ...");
                break;
            case NEUTRAL : default :
                System.out.println("Erreur : Tir() direction=neutral est impossible, skip la phase");
                break;
        }
        return;
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
            case ACTION.DEPLACE :{
                int lastX= this.coordX;
                int lastY = this.coordY;
                if(this.deplace(input.direction)){
                    //Si déplacement autorisé
                    this.plateau.deplacePerso(this, lastX, lastY, input.direction);
                    break;
                }
                else {
                    System.out.println("Erreur de déplacement");
                    break;
                }
            }

            case ACTION.BRAQUE : this.braqueButin(); break;
            case ACTION.TIR : this.tir(input.direction); break;
            default : return;

        }

        System.out.println(this.getTag() + " exécution");



    }

    @BeforeEach
    void testPersonnage(){
        System.out.println("TEST BANDIT");
    }

    @Test
    void testPopAction_PutAction(){
        Input testInput1 = new Input(DIRECTION.DROITE, ACTION.DEPLACE);
        Input testInput2 = new Input(DIRECTION.DROITE, ACTION.TIR);
        Input testInput3 = new Input(DIRECTION.HAUT, ACTION.DEPLACE);
        Input testInput4 = new Input(DIRECTION.NEUTRAL, ACTION.BRAQUE);
        Input inputReceiver;
        Jeu testGame = new Jeu();
        Personnage testSubject = testGame.getPersos().get(0);
        if(testSubject instanceof Bandit) {
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
        else fail("initBandit mal fait");
    }




}
