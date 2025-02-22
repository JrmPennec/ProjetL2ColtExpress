package modele;

import java.util.*;
import controleur.Partie;

import static java.lang.Math.abs;


public class Bandit extends Personnage{



    private LinkedList<Input> buffer ;
    private ArrayList<Objet> loot ;



    public Bandit(int x, int y, String t, Plateau p){
        super( x,  y,  t, p);
        this.buffer = new LinkedList<>();
        this.loot = new ArrayList<>();
    }

    //GETTER & SETTER
    public LinkedList<Input> getBuffer() {
        return buffer;
    }
    public ArrayList<Objet> getLoot() {
        return loot;
    }
    public int getTotalValeur(){
        int result = 0;
        for(modele.Objet i : loot){
            result += i.getValeur();
        }
        return result;
    }


    //BUFFER
    public void putAction(Input action){
        if(this.buffer.size() > 3){
            System.out.println("Erreur : putAction(), buffer déjà taille maximale (3), skip phase");
            return;
        }
        this.buffer.addLast(action);
    }

    public Input popAction() {
        if(this.buffer.isEmpty()){
            throw new Error("Erreur fatale: popAction(), buffer vide, corriger");
        }

        return  buffer.pop();
    }

    //ACTIONS
    public void executionStack(){
        Input input = this.popAction();
        switch(input.action){
            case DEPLACE :
                this.deplace(input.direction);
                break;
            case BRAQUE : {
                this.braqueButin();
                break;
            }
            case TIR : this.tir(input.direction); break;
            default : return;

        }
    }

    public void braqueButin(){
        ArrayList<modele.Objet> lootTrouve = new ArrayList<>();
        lootTrouve = this.plateau.getScene(coordX, coordY).getTresor();
        if(!lootTrouve.isEmpty()){
            modele.Objet premierLoot = lootTrouve.get(abs(Partie.rnd.nextInt()) % lootTrouve.size());
            premierLoot.estPris(this);
            loot.add(premierLoot);
            System.out.println(this.tag + " braque et trouve " + premierLoot.tag);
            logCharacter = this.tag + " braque ! "+ premierLoot.getTag() + " est trouvé avec une valeur de " + premierLoot.getValeur() + " !";;
            return;
        }
    }

    public void dropButin(){
        if(loot.isEmpty()) return;
        loot.get(loot.size()-1).estLache();
        loot.remove(loot.size()-1);
    }

    public void fuit(){
        System.out.println( this.tag + " a eu peur et s'en va");
        if(this.coordY == 0) {deplace(DIRECTION.HAUT); return;}
        if(this.coordY == 1) {deplace(DIRECTION.BAS); return;}
        this.logCharacter = ""; //Annule le log créer par les déplacements
    }

    public boolean tir(DIRECTION dir){
        switch(dir){
            case HAUT :
                if(this.coordY == 0) {
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(coordX, 1).getArrayBandits();
                    if(cibleSet.isEmpty()) break; //Aucune cible, on quitte.
                    Bandit cible = cibleSet.get(abs(Partie.rnd.nextInt()) % cibleSet.size());
                    cible.dropButin();
                    cible.fuit();
                    System.out.println(this.tag + " pew pew en haut !");
                    logCharacter = this.tag + " pew pew en haut ! "+ cible.getTag() + " s'est pris chère !";;
                    return true;
                }
                System.out.println( this.tag + " tire, mais il est schizophrénique et rate sa cible ...");
                logCharacter = this.tag + " tire, mais il est schizophrénique et rate sa cible ...";
                break;
            case BAS :
                if(this.coordY == 1) {
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(coordX, 0).getArrayBandits();
                    if(cibleSet.isEmpty()) break; //Aucune cible, on quitte.
                    Bandit cible = cibleSet.get(abs(Partie.rnd.nextInt()) % cibleSet.size());
                    cible.dropButin();
                    cible.fuit();
                    System.out.println(this.tag + " pew pew en bas !");
                    logCharacter = this.tag + " pew pew en bas ! "+ cible.getTag() + " s'est pris chère !"; ;
                    return true;
                }
                System.out.println( this.tag + " tire, mais il est schizophrénique et rate sa cible ...");
                logCharacter = this.tag + " tire, mais il est schizophrénique et rate sa cible ...";
                break;
            case GAUCHE :
                for(int i = coordX - 1; i >= 0; i--){
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(i, coordY).getArrayBandits();
                    if(cibleSet.isEmpty()) {
                        //Si dans le wagon, la balle traverse qu'une seule salle.
                        if(coordY == 0) break;
                        //Rien, la balle traverse le wagon
                        continue;
                    }
                    Bandit cible = cibleSet.get(abs(Partie.rnd.nextInt()) % cibleSet.size()); //Cible frappé
                    cible.dropButin();
                    cible.fuit();
                    System.out.println("modele.Bandit " + this.tag + "  pew pew à gauche !");
                    logCharacter = this.tag + " pew pew à gauche ! " + cible.getTag() + " s'est pris chère !";
                    return true;
                }
                System.out.println( this.tag + " tire ! Mais il est schizophrénique et rate sa cible ...");
                logCharacter = this.tag + " tire ! Mais il est schizophrénique et rate sa cible ...";
                break;
            case DROITE :
                for(int i = coordX + 1; i <= partie.NB_WAGON - 1; i++){
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(i, coordY).getArrayBandits();
                    if(cibleSet.isEmpty()) {
                        //Si dans le wagon, la balle traverse qu'une seule salle.
                        if(coordY == 0) break;
                        //Rien, la balle traverse le wagon
                        continue;
                    }
                    Bandit cible = cibleSet.get(abs(Partie.rnd.nextInt()) % cibleSet.size()); //Cible frappé
                    cible.dropButin();
                    cible.fuit();
                    System.out.println(this.tag + "  pew pew à droite !");
                    logCharacter = this.tag + " pew pew à droite ! " + cible.getTag() + " s'est pris chère !";;
                    return true;
                }
                System.out.println( this.tag + " tire, mais il est schizophrénique et rate sa cible ...");
                logCharacter = this.tag + " tire ! Mais il est schizophrénique et rate sa cible ...";
                break;
            case NEUTRAL : default :
                System.out.println("Erreur : Tir() direction=neutral est impossible, skip la phase");
                break;
        }
        return false;
    }


}
