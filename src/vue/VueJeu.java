package vue;

import controleur.Jeu;

import javax.swing.*;
import java.awt.*;

public class VueJeu extends JFrame {
    Jeu jeu;

    public VueJeu(Jeu jeu,int width, int height){
        super("Colt Express");
        this.jeu=jeu;
        this.setPreferredSize(new Dimension(width, height));
        lanceIntro();
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void lanceIntro(){
        this.setContentPane(new VueIntro(jeu));

    }
    public void lancePartie(){

        this.setContentPane(new VuePartie(jeu.getPartie()));
        this.revalidate();
        this.repaint();
    }

    public void lanceScore() {
        this.setContentPane(new VueScore(jeu.getPartie()));
        this.revalidate();
        this.repaint();
    }



}
