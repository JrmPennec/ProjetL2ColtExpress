package vue;

import modele.Bandit;
import modele.Jeu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class VueEast extends JPanel implements Observer {
    VueJeu vueParent;
    Jeu jeu;

    public VueEast(VueJeu vue){
        super();
        vueParent = vue;
        jeu=vueParent.getJeu();
        GridLayout gl = new GridLayout(4,1);
        gl.setVgap(5);
        this.setLayout(gl);


        affichageEast();
        }

    public void update(){
        removeAll();
        affichageEast();
        revalidate();
        repaint();
    }
     private void affichageEast(){
         for(Bandit b : jeu.getBandits()){
             JLabel label= new JLabel(b.getTag()+" CASH : "+b.getTotalValeur());
             this.add(label);
         }
    }

}


