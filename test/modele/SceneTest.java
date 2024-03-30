package modele;

import modele.Bandit;
import modele.Plateau;
import modele.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SceneTest {

    private Scene scene;
    private Plateau plateau;
    @BeforeEach
    public void init() {
        scene = new Scene(true,true,false);
        plateau = new Plateau();
    }

    @Test
    void estToit() {
        assertTrue(scene.estToit());
    }

    @Test
    void estArriere() {
        assertFalse(scene.estArriere());
    }

    @Test
    void estLocomotive() {
        assertTrue(scene.estLocomotive());
    }

    @Test
    void putPerso(){

        Bandit b=new Bandit(0,0,"toto",plateau);
        scene.putPerso(b);
        assertSame(scene.getBandits().get(b.getTag()),b);

    }
    @Test
    void removePerso(){
        Bandit b=new Bandit(0,0,"toto",plateau);
        scene.putPerso(b);
        assertSame(scene.removePerso("toto"),b);
        assertTrue(scene.getBandits().isEmpty());
    }
    @Test
    void MarshallTtest(){
        assertFalse(scene.isMarshallHere());

    }
}