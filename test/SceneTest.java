import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SceneTest {

    private Scene scene;
    @BeforeEach
    public void init() {
        scene = new Scene(true,true,false);
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
        assertFalse(scene.estLocomotive());
    }

    @Test
    void putPerso(){
        Bandit b=new Bandit(0,0,"toto");
        scene.putPerso(b);
        assertSame(scene.getPersos().get(b.tag),b);

    }
    @Test
    void removePerso(){
        Bandit b=new Bandit(0,0,"toto");
        scene.putPerso(b);
        assertSame(scene.removePerso("toto"),b);
        assertTrue(scene.getPersos().isEmpty());
    }
}