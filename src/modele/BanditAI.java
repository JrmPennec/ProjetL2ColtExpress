package modele;

import java.util.ArrayList;

import static java.lang.Math.pow;


//Remarque : J1 ne peut jamais être une IA
public class BanditAI extends modele.Bandit{
    private ArrayList<int[]> marshall_tracked;
    private ArrayList<int[]> loot_tracked;
    private ArrayList<int[]> player_tracked;


    public BanditAI(int x, int y, String t, Plateau p){
        super( x,  y,  t, p);
    }

    public int getClosestTo(int x, int y, ArrayList<int[]> subjects){
        int closestFound = 0;
        for(int i = 1; i < subjects.size(); i ++){
            if(Math.sqrt( Math.pow(x - subjects.get(i)[0], 2 ) + Math.pow(y - subjects.get(i)[1], 2 )) >
                Math.sqrt( Math.pow(x - subjects.get(closestFound)[0], 2 ) + Math.pow(y - subjects.get(closestFound)[1], 2 ))
            ) continue;
            closestFound = i;

        }
        return closestFound;
    }

    public void AI_putAction(){ //Note : l'IA triche, elle empile en phase d'action et s'adapte
        for(modele.Bandit target : this.partie.getBandits()){
            int[] coord = {target.getCoordX(), target.getCoordY()};
            if(coord[0] == this.coordX && coord[1] == this.coordY) continue; //Ignore lui-même et les bandits dans la même scène
            player_tracked.add(coord);
        }
        for(int x = 0; x < this.partie.NB_WAGON; x++) {
            for (int y = 0; y < 2; y++) {
                ArrayList<Objet> targetArray = this.partie.getPlateau().getScene(x, y).getTresor();
                for (Objet target : targetArray) {
                    int[] coord = {target.getCoordX(), target.getCoordY()};
                    loot_tracked.add(coord);
                }
            }
        }
        if(this.partie.getMarshall() != null){
            int[] coord = {this.partie.getMarshall().getCoordX(), this.partie.getMarshall().getCoordY()};
            marshall_tracked.add(coord);
        }

        int index_closestObjet = getClosestTo(this.coordX, this.coordY, loot_tracked);
        int index_closestBandit = getClosestTo(this.coordX, this.coordY, player_tracked);
        int index_closestMarshall = getClosestTo(this.coordX, this.coordY, marshall_tracked);

            //Sabotage (Tentative)
            if(player_tracked.get(index_closestBandit)[1] == 1 && marshall_tracked.get(index_closestMarshall)[0] ==  player_tracked.get(index_closestBandit)[0]
                && this.coordY == 1){
                DIRECTION dir = (player_tracked.get(index_closestBandit)[0] > this.coordX) ? DIRECTION.DROITE : DIRECTION.GAUCHE;
                this.putAction(new Input(dir, ACTION.TIR));
                return;
            }
            //Fuit le marshall
            if(Math.abs(marshall_tracked.get(index_closestMarshall)[0] - this.coordX) == 1 && this.coordY == 0){
                this.putAction(new Input(DIRECTION.HAUT, ACTION.DEPLACE));
                return;
            }

            // THIS IS MINE
            if(Math.abs(player_tracked.get(index_closestBandit)[0] - loot_tracked.get(index_closestObjet)[0]  ) <= 1
                    && player_tracked.get(index_closestBandit)[1] == this.coordY){
                DIRECTION dir = (player_tracked.get(index_closestBandit)[0] > this.coordX) ? DIRECTION.DROITE : DIRECTION.GAUCHE;
                this.putAction(new Input(dir, ACTION.TIR));
                return;
            }

            //Se déplace vers le trésor le plus proche tout en évitant le marshall
            if(loot_tracked.get(index_closestObjet)[0] != this.coordX &&  loot_tracked.get(index_closestObjet)[1] != this.coordY ){
                DIRECTION dir;
                ACTION action;
                if(this.coordY != loot_tracked.get(index_closestObjet)[1] && //Aucune Marshall qui peut embêter et si on est pas au même niveau.
                        (this.coordY == 0 || Math.sqrt(Math.pow( this.coordX - marshall_tracked.get(index_closestMarshall)[0],2) +
                        Math.pow( this.coordY - marshall_tracked.get(index_closestMarshall)[1],2)) > 1.5)){
                    dir = (this.coordY == 1) ? DIRECTION.BAS : DIRECTION.HAUT;
                    action = ACTION.DEPLACE;
                    this.putAction(new Input(dir, action));
                    return;
                }
                if(this.coordX == loot_tracked.get(index_closestObjet)[0]){ //Si on a juste besoin, et qu'on arrive pas à descendre : PEW PEW PEW
                    if(controleur.Partie.rnd.nextInt() % 10 < 3) dir = (this.coordY == 0 ) ? DIRECTION.HAUT : DIRECTION.BAS;
                    else dir = (player_tracked.get(index_closestBandit)[0] > this.coordX) ? DIRECTION.DROITE : DIRECTION.GAUCHE;
                    action = ACTION.TIR;
                    this.putAction(new Input(dir, action));
                    return;
                }

                dir = (loot_tracked.get(index_closestBandit)[0] > this.coordX) ? DIRECTION.DROITE : DIRECTION.GAUCHE;
                action = ACTION.DEPLACE;
                this.putAction(new Input(dir, action));
                return;
            }

            //Braque si un trésor dans la scène, et qu'il est dans une position "sécurisé".
            this.putAction(new Input(DIRECTION.NEUTRAL, ACTION.BRAQUE));






    }


}
