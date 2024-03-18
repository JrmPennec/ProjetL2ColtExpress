import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SceneTest {

    private Scene scene;
    @BeforeEach
    public void init() {
        scene = new Scene(true,true,true);
    }

    @Test
    void estToit() {
        assertTrue(scene.estToit());
    }

    @Test
    void estArriere() {
        assertTrue(scene.estArriere());
    }

    @Test
    void estLocomotive() {
        assertTrue(scene.estLocomotive());
    }

    @Test
    void getTresor() {
    }

    @Test
    void getPersos() {
    }
}