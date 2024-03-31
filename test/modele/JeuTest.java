package modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JeuTest {
    Jeu jeu;
    Bandit b00;
    ArrayList<Input> inputs;


    @BeforeEach
    void init() {
        jeu = new Jeu();
        b00 = jeu.getBandits().get(0);
        inputs= new ArrayList<>();

    }
    //Planning Stage
    @Test
    void derouleTourPlanningStage(){
        for (int i=0;i<3;i++) {
            jeu.derouleTourPlanningStage();
            System.out.println(i+ " "+ jeu.getCompteurAction());
            assertEquals( 0,jeu.getCompteurJoueur());
            assertEquals( Jeu.NB_ACTION-(1+i),jeu.getCompteurAction());
        }
        //4eme action
        jeu.derouleTourPlanningStage();
        assertEquals(4,jeu.getCompteurAction());
        assertEquals(1,jeu.getCompteurJoueur());
        //Passage action phase
        for (int i=0; i<12;i++){
            jeu.derouleTourPlanningStage();
        }
        assertTrue(jeu.isActionStage());
        assertEquals(jeu.getCompteurAction(),4);
    }


    @Nested
    class AjouteActionTest {

        @BeforeEach
        void init() {
            inputs = new ArrayList<>();
            inputs.add( new Input(DIRECTION.DROITE, ACTION.DEPLACE));
            inputs.add( new Input(DIRECTION.GAUCHE, ACTION.DEPLACE));
            inputs.add( new Input(DIRECTION.DROITE, ACTION.TIR));
        }

        @Test
        void ajouteActionWrongStage() {
            jeu.setActionStage(true);
            assertTrue(b00.getBuffer().isEmpty());
            jeu.ajouteAction(b00, inputs.get(0));
            assertTrue(b00.getBuffer().isEmpty());
        }
        @Test
        void ajouteActionVide() {
            assertTrue(b00.getBuffer().isEmpty());
            jeu.ajouteAction(b00, inputs.get(0));
            assertEquals(b00.getBuffer().get(0),inputs.get(0));

        }
        @Test
        void ajouteActions(){
            jeu.ajouteAction(b00,inputs.get(0));
            assertEquals(inputs.get(0),b00.getBuffer().getFirst());
            jeu.ajouteAction(b00,inputs.get(1));
            jeu.ajouteAction(b00,inputs.get(2));
            for (int i=0;i<3;i++){
                    assertEquals(inputs.get(i),b00.getBuffer().get(i));
            }

        }

    }
    //ActionPhase
    @Nested
    class ActionPhaseTest {
        @BeforeEach
        void init(){
            inputs.add(new Input(DIRECTION.DROITE,ACTION.DEPLACE));
            inputs.add( new Input(DIRECTION.GAUCHE, ACTION.DEPLACE));
        }

        @Test
        void ActionPhaseOnWrongStage() {
            jeu.setActionStage(false);
            jeu.ajouteAction(b00, inputs.get(0));
            assertFalse(b00.getBuffer().isEmpty());
            jeu.actionPhase();
            assertFalse(b00.getBuffer().isEmpty());
        }

        @Test
        void ActionPhaseEmptyQueue() {
            jeu.setActionStage(true);
            assertDoesNotThrow(() -> jeu.actionPhase());
            assertTrue(b00.getBuffer().isEmpty());
        }

        @Test
        void ActionPhaseWith1Input() {
            jeu.ajouteAction(b00, inputs.get(0));
            jeu.setActionStage(true);
            jeu.actionPhase();
            assertTrue(b00.getBuffer().isEmpty());
        }

        @Test
        void ActionPhaseWithMultipleInputsOnSameQueue() {
            jeu.ajouteAction(b00, inputs.get(0));
            jeu.ajouteAction(b00, inputs.get(1));
            jeu.setActionStage(true);
            jeu.actionPhase();
            assertEquals(b00.getBuffer().pop(), inputs.get(1));
        }
    }
    @Test
    void derouleActionStage(){
        jeu.setActionStage(true);
        for (int i=0;i<3;i++) {
            jeu.derouleTourActionStage();
            System.out.println(i+ " "+ jeu.getCompteurAction());
            assertEquals( 0,jeu.getCompteurJoueur());
            assertEquals( Jeu.NB_ACTION-(1+i),jeu.getCompteurAction());
        }
        //Passage Planning stage
        jeu.derouleTourActionStage();
        assertEquals(4,jeu.getCompteurAction());
        assertFalse(jeu.isActionStage());

    }
}



