package test.vue;

import modele.Jeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vue.VueJeu;
import vue.VueSouth;

import static org.junit.jupiter.api.Assertions.*;

class VueSouthTest {
    Jeu jeu;
    VueJeu vue;
    VueSouth vueSouth;

    @BeforeEach
    void init(){
        jeu = new Jeu();
        vue = new VueJeu(jeu,1280,720);
    }
    @Test
    void AffichageTest(){
        vueSouth= new VueSouth(vue);
    }

}