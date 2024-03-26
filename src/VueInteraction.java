import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueInteraction  extends JPanel {
    VueJeu InterfaceJeu;

    public VueInteraction(VueJeu iJeu){
        super();
        this.InterfaceJeu=iJeu;
        this.setLayout(new FlowLayout());
        this.add(new ActionButton());
    }
    class ActionButton extends Button{

        public ActionButton() {
            super(InterfaceJeu, "Action!");
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            InterfaceJeu.jeu.actionPhase();
        }
    }

}
