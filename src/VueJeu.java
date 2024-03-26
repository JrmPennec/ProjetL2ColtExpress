import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class VueJeu extends JFrame implements Observer {
    JPanel affichageTrain;
    VueInteraction affichageInteraction;
    Jeu jeu;

    public VueJeu(Jeu j) {
        super("Colt Express");
        this.jeu = j;
        jeu.getPlateau().addObserver(this);
        this.setLayout(new BorderLayout());
        affichageInteraction = new VueInteraction(this);
        this.add(affichageInteraction, BorderLayout.NORTH);
        affichageTrain = new JPanel(new GridLayout(2, Jeu.NB_WAGON));
        this.add(affichageTrain, BorderLayout.CENTER);
        afficheTrain();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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

    void setAffichageInteraction() {


    }

    @Override
    public void update() {
        afficheTrain();

    }
    static public void main(String[] args){
        VueJeu test= new VueJeu(new Jeu());
        Personnage p =test.jeu.getPersos().get(0);
        Bandit b=(Bandit)p;
        test.jeu.ajouteAction(b,new Input(DIRECTION.DROITE,ACTION.DEPLACE));
    }

}






