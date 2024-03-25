import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class VueJeu extends JFrame implements Observer {
    JPanel affichageTrain;
    JPanel affichageInteraction;
    Jeu jeu;
    public VueJeu(Jeu j){
        super("Colt Express");
        this.jeu=j;
        jeu.getPlateau().addObserver(this);
        affichageTrain= new JPanel(new GridLayout(2,Jeu.NB_WAGON));
        this.add(affichageTrain);
        afficheTrain();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void afficheTrain(){
        //On clear dans affichage Train
        affichageTrain.removeAll();
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        //Boucle sur le toit puis l'interieur
        for (int i=1; i>=0;i--){
            for (int j=0;j<Jeu.NB_WAGON;j++){
                VueScene vs= new VueScene(jeu.getPlateau().getScene(j,i));
                vs.setBorder(border);
                affichageTrain.add(vs);
            }
        }
        //Necessaire pour mettre a jour
        affichageTrain.revalidate();
        affichageTrain.repaint();

    }
    @Override
    public void update() {
        System.out.println("coucou");
        afficheTrain();

    }

    public static void main(String[] args )throws InterruptedException{
        Jeu test = new Jeu();
        VueJeu testVue=new VueJeu(test);
        test.getPersos().get(0).deplace(Personnage.DIRECTION.GAUCHE);
        Thread.sleep(2000);
        test.getPersos().get(0).deplace(Personnage.DIRECTION.HAUT);
        Thread.sleep(2000);
        test.getPersos().get(0).deplace(Personnage.DIRECTION.BAS);
        Thread.sleep(2000);
        test.getPersos().get(0).deplace(Personnage.DIRECTION.BAS);
        Thread.sleep(2000);
        test.getPersos().get(0).deplace(Personnage.DIRECTION.DROITE);
        Thread.sleep(2000);
        test.getPersos().get(0).deplace(Personnage.DIRECTION.GAUCHE);



    }
}
