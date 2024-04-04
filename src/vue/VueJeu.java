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
        this.affichageNorth=new VueNorth(this);
        affichageTrain = new JPanel(new GridLayout(2, Jeu.NB_WAGON));
        this.affichageSouth=new VueSouth(this);
        this.affichageEast=new VueEast(this);

        this.setPreferredSize(new Dimension(width, height));
        jeu.getPlateau().addObserver(this);
        this.getContentPane().setLayout(new GridBagLayout());
        this.add(affichageNorth,getContraintesNorth());
        this.add(affichageTrain, getContraintesTrain());
        this.add(affichageSouth,getContraintesSouth());
        this.add(affichageEast,getContraintesEast());
        afficheTrain();

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
    private void afficheTrain() {
        //On clear dans affichage Train
        affichageTrain.removeAll();
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        //Boucle sur le toit puis l'interieur
        for (int i = 1; i >= 0; i--) {
            for (int j = 0; j < Jeu.NB_WAGON; j++) {
                VueScene vs = new VueScene(jeu.getPlateau().getScene(j, i));
                vs.setBorder(border);
                affichageTrain.add(vs);
            }
        }
        //Necessaire pour mettre a jour
        affichageTrain.revalidate();
        affichageTrain.repaint();

    }
    //Affichage South

    private GridBagConstraints getContraintesSouth(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx=0;
        c.gridy=3;
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
        afficheTrain();

    }
    static public void main(String[] args){
        VueJeu test= new VueJeu(new Jeu(),1280,720);
        //Bandit b =test.jeu.getBandits().get(0);

        //est.jeu.ajouteAction(b,new Input(DIRECTION.DROITE, ACTION.DEPLACE));
    }

}






