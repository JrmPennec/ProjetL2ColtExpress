import javax.swing.*;
import java.awt.*;

public class Vue extends JFrame implements Observer {
    JPanel affichageTrain;
    JPanel affichageInteraction;
    public Vue(){
        super("Colt Express");
        affichageTrain= new JPanel(new GridLayout(2,NB_WAGON));




    }
    @Override
    public void update() {

    }
}
