package test.modele;

import controleur.Partie;
import modele.Bandit;
import modele.Personnage;
import modele.Plateau;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlateauTest {

    Plateau p;

    @BeforeEach
    void init() {
    p =new Plateau(new Partie(4,4,4,4));
    }

    @Test
    void getScene() {
        assertThrows(Exception.class,()->p.getScene(5,1));

    }

    @Test
    void deplacePerso() {
        Bandit b = p.getScene(0,1).getBandits().get("b00");

        p.deplacePerso(b,0,0);
        assertTrue(p.getScene(0,1).getBandits().isEmpty());
        assertEquals(p.getScene(0,0).getBandits().get("b00"),b);
    }
}