package test.vue;

import controleur.Partie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vue.VuePartie;
import vue.VueSouth;

import javax.swing.*;
import java.awt.*;

class VueSouthTest {
    JFrame frame;
    Partie partie;
    VuePartie vue;
    VueSouth vueSouth;

    @BeforeEach
    void init(){
         frame= new JFrame("VueSouthTest");
        frame= new JFrame("testVueJeuTest");
        frame.setPreferredSize(new Dimension(1280,720));
        partie = new Partie(4,4,4,4);
        vue = new VuePartie(partie);
    }
    @Test
    void AffichageTest(){
        vueSouth= new VueSouth(vue);
    }

}