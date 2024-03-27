import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VueJeuTest {

    VueJeu test;
    @BeforeEach
    void init(){
        test= new VueJeu(new Jeu());
    }
    @Test
    void ViewTest()throws InterruptedException{
        test.jeu.getBandits().get(0).deplace(DIRECTION.GAUCHE);
        Thread.sleep(2000);
        test.jeu.getBandits().get(0).deplace(DIRECTION.HAUT);
        Thread.sleep(2000);
        test.jeu.getBandits().get(0).deplace(DIRECTION.BAS);
        Thread.sleep(2000);
        test.jeu.getBandits().get(0).deplace(DIRECTION.BAS);
        Thread.sleep(2000);
        test.jeu.getBandits().get(0).deplace(DIRECTION.DROITE);
        Thread.sleep(2000);
        test.jeu.getBandits().get(0).deplace(DIRECTION.GAUCHE);

    }
    @Test
    void ActionTest(){
        Bandit b =test.jeu.getBandits().get(0);
        test.jeu.ajouteAction(b,new Input(DIRECTION.DROITE,ACTION.DEPLACE));
    }


}