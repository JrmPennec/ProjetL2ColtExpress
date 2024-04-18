package test.controleur;

import controleur.Partie;
import modele.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PartieTest {
    Partie partie;
    Bandit b00;
    ArrayList<Input> inputs;


    @BeforeEach
    void init() {
        partie = new Partie(4,4,4,4);
        b00 = partie.getBandits().get(0);
        inputs= new ArrayList<>();

    }
    //Planning Stage
    @Test
    void derouleTourPlanningStage(){
        for (int i=0;i<3;i++) {
            partie.derouleTourPlanningStage();
            System.out.println(i+ " "+ partie.getActionsRestantes());
            assertEquals( 0, partie.getCompteurJoueur());
            assertEquals( partie.NB_ACTION-(1+i), partie.getActionsRestantes());
        }
        //4eme action
        partie.derouleTourPlanningStage();
        assertEquals(4, partie.getActionsRestantes());
        assertEquals(1, partie.getCompteurJoueur());
        //Passage action phase
        for (int i=0; i<12;i++){
            partie.derouleTourPlanningStage();
        }
        assertTrue(partie.isActionStage());
        assertEquals(partie.getActionsRestantes(),4);
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
            partie.setActionStage(true);
            assertTrue(b00.getBuffer().isEmpty());
            partie.ajouteAction(b00, inputs.get(0));
            assertTrue(b00.getBuffer().isEmpty());
        }
        @Test
        void ajouteActionVide() {
            assertTrue(b00.getBuffer().isEmpty());
            partie.ajouteAction(b00, inputs.get(0));
            assertEquals(b00.getBuffer().get(0),inputs.get(0));

        }
        @Test
        void ajouteActions(){
            partie.ajouteAction(b00,inputs.get(0));
            assertEquals(inputs.get(0),b00.getBuffer().getFirst());
            partie.ajouteAction(b00,inputs.get(1));
            partie.ajouteAction(b00,inputs.get(2));
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
            partie.setActionStage(false);
            partie.ajouteAction(b00, inputs.get(0));
            assertFalse(b00.getBuffer().isEmpty());
            partie.actionPhase();
            assertFalse(b00.getBuffer().isEmpty());
        }

        @Test
        void ActionPhaseEmptyQueue() {
            partie.setActionStage(true);
            assertDoesNotThrow(() -> partie.actionPhase());
            assertTrue(b00.getBuffer().isEmpty());
        }

        @Test
        void ActionPhaseWith1Input() {
            partie.ajouteAction(b00, inputs.get(0));
            partie.setActionStage(true);
            partie.actionPhase();
            assertTrue(b00.getBuffer().isEmpty());
        }

        @Test
        void ActionPhaseWithMultipleInputsOnSameQueue() {
            partie.ajouteAction(b00, inputs.get(0));
            partie.ajouteAction(b00, inputs.get(1));
            partie.setActionStage(true);
            partie.actionPhase();
            assertEquals(b00.getBuffer().pop(), inputs.get(1));
        }
    }
    @Test
    void derouleActionStage(){
        partie.setActionStage(true);
        for (int i=0;i<3;i++) {
            partie.derouleTourActionStage();
            System.out.println(i+ " "+ partie.getActionsRestantes());
            assertEquals( 0, partie.getCompteurJoueur());
            assertEquals( partie.NB_ACTION-(1+i), partie.getActionsRestantes());
        }
        //Passage Planning stage
        partie.derouleTourActionStage();
        assertEquals(4, partie.getActionsRestantes());
        assertFalse(partie.isActionStage());

    }
}



