package test.vue;

import modele.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vue.VueJeu;


class VueJeuTest {

    VueJeu test;
    @BeforeEach
    void init(){
        test= new VueJeu(new Jeu(),500,500);
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