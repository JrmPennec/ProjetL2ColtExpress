
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
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(coordX, 1).getArrayBandits();
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
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(coordX, 0).getArrayBandits();
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
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(i, coordY).getArrayBandits();
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
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(i, coordY).getArrayBandits();
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




}
