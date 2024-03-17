import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

enum ACTION{
    TIR, DEPLACE, BRAQUE,

}
class Input{
    public Personnage.DIRECTION direction;
    public ACTION action;
}


public class Bandit extends Personnage{
    Random rnd = new Random();
    private Stack<Input> buffer = new Stack<>();
    private ArrayList<Objet> loot = new ArrayList<>(0);

    public int getTotalValeur(){
        int result = 0;
        for(Objet i : loot){
            result += i.getValeur();
        }
        return result;
    }

    public void braqueButin(){
        ArrayList<Objet> lootTrouve = new ArrayList<>();
        lootTrouve = this.plateau.getScene(coordX, etage).getButin();
        if(!lootTrouve.isEmpty()){
            Objet premierLoot = lootTrouve.get(rnd.nextInt() % lootTrouve.size());
            premierLoot.estPris(this);
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
        if(this.etage == 0) {etage = 1; return;}
        this.etage = 0; return;
    }

    public void tir(DIRECTION dir){
        switch(dir){
            case HAUT :
                if(this.etage == 0) {
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(coordX, 1).getBandits();
                    if(cible.isEmpty()) break; //Aucune cible, on quitte.
                    Bandit cible = cibleSet.get(rnd.nextInt() % cibleSet.size());
                    cible.dropButin();
                    cible.fuit();
                    System.out.println(this.tag + " pew pew en haut !");
                    return;
                }
                System.out.println( this.tag + " tire, mais il est schizophrénique et rate sa cible ...");
                break;
            case BAS :
                if(this.etage == 1) {
                    ArrayList<Bandit> cibleSet = this.plateau.getScene(coordX, 0).getBandits();
                    if(cible.isEmpty()) break; //Aucune cible, on quitte.
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
                    ArrayList<Bandit> ciblePotentiel = this.plateau.getScene(i, etage).getBandits();
                    if(ciblePotentiel.isEmpty())  continue;//Rien, la balle traverse le wagon
                    Bandit cible = ciblePotentiel.get(rnd.nextInt() % cibleSet.size()); //Cible frappé
                    cible.dropButin();
                    cible.fuit();
                    System.out.println("Bandit " + this.tag + "  pew pew à gauche !");
                    return;
                }
                System.out.println( this.tag + " tire ! Mais il est schizophrénique et rate sa cible ...");
                break;
            case DROITE :
                for(int i = coordX; i <= plateau.NB_WAGON - 1; i++){
                    ArrayList<Bandit> ciblePotentiel = this.plateau.getScene(i, etage).getBandits();
                    if(ciblePotentiel.isEmpty())  continue;//Rien, la balle traverse le wagon
                    Bandit cible = ciblePotentiel.get(rnd.nextInt() % cibleSet.size()); //Cible frappé
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
        if(action.size() > 2){
            System.out.println("Erreur : putAction(), action ne doit pas dépasser la taille de 2, skip phase");
            return;
        }
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

    public void executeAction(){
        Input nextAction = this.popAction();
        switch(nextAction.action){
            case TIR :
                tir(nextAction.direction);
                break;
            case DEPLACE :
                deplace(nextAction.direction);
                break;
            case BRAQUE :
                braqueButin();
                break;
            default :
                System.out.println("Erreur : executeAction(), action de buffer invalide");
                break;

        }
        return;
    }





}
