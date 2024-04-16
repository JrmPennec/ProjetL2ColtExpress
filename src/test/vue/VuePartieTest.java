package test.vue;

import controleur.Partie;
import modele.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vue.VuePartie;

import javax.swing.*;
import java.awt.*;


class VuePartieTest {
    JFrame frame;
    VuePartie test;
    @BeforeEach
    void init(){
        frame= new JFrame("testVueJeuTest");
        frame.setPreferredSize(new Dimension(500,500));
        test= new VuePartie(new Partie(4,4,4,4));
        frame.add(test);
    }
    @Test
    void ViewTest()throws InterruptedException{
        test.getJeu().getBandits().get(0).deplace(DIRECTION.GAUCHE);
        Thread.sleep(2000);
        test.getJeu().getBandits().get(0).deplace(DIRECTION.HAUT);
        Thread.sleep(2000);
        test.getJeu().getBandits().get(0).deplace(DIRECTION.BAS);
        Thread.sleep(2000);
        test.getJeu().getBandits().get(0).deplace(DIRECTION.BAS);
        Thread.sleep(2000);
        test.getJeu().getBandits().get(0).deplace(DIRECTION.DROITE);
        Thread.sleep(2000);
        test.getJeu().getBandits().get(0).deplace(DIRECTION.GAUCHE);

    }
    @Test
    void ActionTest(){
        Bandit b =test.getJeu().getBandits().get(0);
        test.getJeu().ajouteAction(b,new Input(DIRECTION.DROITE, ACTION.DEPLACE));
    }


}