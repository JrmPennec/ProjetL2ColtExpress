package vue;

import modele.Jeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VueSouth extends JPanel {
    VueJeu vueParent;


    public VueSouth(VueJeu vue){
        this.vueParent=vue;
        this.setLayout(new BorderLayout());
        this.add(new ActionButton(),BorderLayout.NORTH);
        this.add(new JLabel("LOGS A FAIRE"),BorderLayout.CENTER);




    }
    class ActionButton extends Button {

        public ActionButton() {
            super(VueSouth.this.vueParent.getJeu(), "Action!");
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (jeu.isActionStage()) {
                jeu.actionPhase();
            }
        }


    }


}
