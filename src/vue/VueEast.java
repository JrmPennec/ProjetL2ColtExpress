package vue;
import controleur.Partie;
import modele.Bandit;
import javax.swing.*;
import java.awt.*;
/** Affichage des scores  en colonne**/
public class VueEast extends JPanel implements Observer {
    VuePartie vueParent;
    Partie partie;

    public VueEast(VuePartie vue){
        super();
        vueParent = vue;
        partie =vueParent.getJeu();
        //Calibration
        GridLayout gl = new GridLayout(4,1);
        gl.setVgap(5);
        this.setLayout(gl);
        vueParent.getJeu().addObserver(this);


        affichageEast();
        }

    public void update(){
        removeAll();
        affichageEast();
        revalidate();
        repaint();
    }
    /** affichage du score de chaque bandit avec un JLabel**/
     private void affichageEast(){
         for(Bandit b : partie.getBandits()){
             JLabel label= new JLabel(b.getTag()+" CASH : "+b.getTotalValeur());
             this.add(label);
         }
    }

}


