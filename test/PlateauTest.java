import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlateauTest {

    Plateau p;

    @BeforeEach
    void init() {
    p =new Plateau();
    }

    @Test
    void getScene() {
        assertThrows(Error.class,()->p.getScene(5,1));

    }

    @Test
    void deplacePerso() {
        Personnage b = p.getScene(0,1).getPersos().get("b00");
        p.getScene(b.getCoordX(),b.getCoordY()).removePerso(b.getTag());
        p.deplacePerso(b,0,0);
        assertTrue(p.getScene(0,1).getPersos().isEmpty());
        assertEquals(p.getScene(0,0).getPersos().get("b00"),b);
    }
}