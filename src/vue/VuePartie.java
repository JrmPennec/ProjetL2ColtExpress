package vue;

import controleur.Jeu;
import controleur.Partie;

import javax.swing.*;
import java.awt.*;

public class VuePartie extends JPanel implements Observer {
    private JPanel affichageTrain;
    private JPanel affichageNorth;
    private JPanel affichageSouth;
    private JPanel affichageEast;
    private Partie partie;





    public VuePartie(Partie j) {
        //init jframe

        this.partie = j;
        //Init des attributs
        this.affichageNorth=new VueNorth(this);
        this.affichageTrain = new VueTrain(this);
        this.affichageSouth=new VueSouth(this);
        this.affichageEast=new VueEast(this);
        //Init Observer
        partie.getPlateau().addObserver(this);
        //Ajout des panel dans le frame
        this.setLayout(new GridBagLayout());
        this.add(affichageNorth,getContraintesNorth());
        this.add(affichageTrain, getContraintesTrain());
        this.add(affichageSouth,getContraintesSouth());
        this.add(affichageEast,getContraintesEast());


    }

    //Affichage Train
    private GridBagConstraints getContraintesTrain(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridy=1;
        c.gridx=0;
        c.gridwidth=3;
        c.gridheight=2;
        c.insets=new Insets(10,30,10,20);
        return c;
    }

    //Affichage South

    private GridBagConstraints getContraintesSouth(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx=0;
        c.gridy=4;
        c.gridheight=1;
        c.gridwidth=3;
        c.insets=new Insets(10,30,0,20);
        return c;
    }

    //Affichage North
    private GridBagConstraints getContraintesNorth(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx=0;
        c.gridy=0;
        c.gridheight=1;
        c.gridwidth=3;
        c.insets=new Insets(0,30,10,20);
        return c;
    }

    //Affichage East
    private GridBagConstraints getContraintesEast(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx=4;
        c.gridy=1;
        c.gridheight=2;
        c.gridwidth=1;
        c.insets=new Insets(10,10,0,0);
        return c;
    }

    public Partie getJeu() {
        return partie;
    }
    @Override
    public void update() {

    }
    static public void main(String[] args){

        VuePartie test= new VuePartie(new Partie(new Jeu(1280,720),4,4,4,4));
        //Bandit b =test.jeu.getBandits().get(0);

        //est.jeu.ajouteAction(b,new Input(DIRECTION.DROITE, ACTION.DEPLACE));
    }

}






