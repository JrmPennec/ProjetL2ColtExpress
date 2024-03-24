import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class VueJeu extends JFrame implements Observer {
    JPanel affichageTrain;
    JPanel affichageInteraction;
    Jeu jeu;
    public VueJeu(Jeu j){
        super("Colt Express");
        afficheTrain();
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void afficheTrain(){
        affichageTrain= new JPanel(new GridLayout(2,4));
        for (int i=1; i>=0;i--){
            for (int j=0;j<Jeu.NB_WAGON;j++){
                VueScene vs= new VueScene(jeu.getPlateau().getScene(j,i));
                affichageTrain.add(vs);
            }
        }
        add(affichageTrain);
    }
    @Override
    public void update() {
        removeAll();
        afficheTrain();
    }
    public static void main(String[] args){
        Jeu jeuTest=new Jeu();
        VueJeu test= new VueJeu(jeuTest);


    }
}
