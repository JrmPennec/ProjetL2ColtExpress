package modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JeuTest {
    Jeu jeu;
    Bandit b00;
    @BeforeEach
    void init(){
        jeu=new Jeu();
        b00=jeu.getBandits().get(0);

    }


    @Test
    void ajouteAction() {
        Input input0= new Input(DIRECTION.DROITE,ACTION.DEPLACE);
        assertTrue(b00.getBuffer().isEmpty());
        jeu.ajouteAction(b00,input0);
        assertEquals(b00.getBuffer().get(0),(input0));

    }

    @Test
    void actionPhase() {
        System.out.println("Test list vide");
        ArrayList<Input> inputs = new ArrayList<>();
        inputs.add(0,new Input(DIRECTION.DROITE,ACTION.DEPLACE));
        inputs.add(1,new Input(DIRECTION.GAUCHE,ACTION.DEPLACE));
        assertDoesNotThrow(()->jeu.actionPhase());
        assertTrue(b00.getBuffer().isEmpty());
        System.out.println("Test list +1");
        jeu.ajouteAction(b00,inputs.get(0));
        jeu.actionPhase();
        assertTrue(b00.getBuffer().isEmpty());
        System.out.println("Test list +2");
        jeu.ajouteAction(b00,inputs.get(0));
        jeu.ajouteAction(b00,inputs.get(1));
        jeu.actionPhase();
        assertEquals(b00.getBuffer().pop(),inputs.get(1));
    }
}