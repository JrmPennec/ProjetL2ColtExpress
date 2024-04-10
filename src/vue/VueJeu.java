package vue;

import modele.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class VueJeu extends JFrame implements Observer {
    private JPanel affichageTrain;
    private JPanel affichageNorth;
    private JPanel affichageSouth;
    private JPanel affichageEast;
    private Jeu jeu;





    public VueJeu(Jeu j,int width,int height) {
        //init jframe
        super("Colt Express");
        this.jeu = j;
        //Init des attributs
        this.affichageNorth=new VueNorth(this);
        this.affichageTrain = new VueTrain(this);
        this.affichageSouth=new VueSouth(this);
        this.affichageEast=new VueEast(this);
        //Init Observer
        jeu.getPlateau().addObserver(this);
        //Ajout des panel dans le frame
        this.setPreferredSize(new Dimension(width, height));
        this.getContentPane().setLayout(new GridBagLayout());
        this.add(affichageNorth,getContraintesNorth());
        this.add(affichageTrain, getContraintesTrain());
        this.add(affichageSouth,getContraintesSouth());
        this.add(affichageEast,getContraintesEast());

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
    private GridBagConstraints getContraintesNorth(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx=0;
        c.gridy=0;
        c.gridheight=1;
        c.gridwidth=3;
        c.insets=new Insets(0,30,10,20);
        return c;
    }
    private GridBagConstraints getContraintesEast(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx=4;
        c.gridy=1;
        c.gridheight=2;
        c.gridwidth=1;
        c.insets=new Insets(10,10,0,0);
        return c;
    }

    public Jeu getJeu() {
        return jeu;
    }
    @Override
    public void update() {

    }
    static public void main(String[] args){
        VueJeu test= new VueJeu(new Jeu(),1280,720);
        //Bandit b =test.jeu.getBandits().get(0);

        //est.jeu.ajouteAction(b,new Input(DIRECTION.DROITE, ACTION.DEPLACE));
    }

}






