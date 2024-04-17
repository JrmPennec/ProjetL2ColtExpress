package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VueSouth extends JPanel {
    VuePartie vueParent;


    public VueSouth(VuePartie vue){
        this.vueParent=vue;
        this.setLayout(new BorderLayout());
        //Ajout des éléments avec cardinalité (border layout )
        this.add(new ActionButton(),BorderLayout.WEST);
        this.add(new CancelButton(),BorderLayout.EAST);
        //this.add(new JLabel("LOGS A FAIRE"),BorderLayout.CENTER);
        this.logUpdate();
    }
    class ActionButton extends Button {

        public ActionButton() {
            super(VueSouth.this.vueParent.getJeu(), "Action!");
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (partie.isActionStage()) {
                partie.actionPhase();
            }
            logUpdate();
        }


    }

    public void logUpdate(){
        this.add(new JLabel(vueParent.getJeu().getLog()),BorderLayout.NORTH);
    }

    class CancelButton extends Button {

        public CancelButton() {
            super(VueSouth.this.vueParent.getJeu(), "Annuler ...");
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (partie.isActionStage()) return;
            partie.annuleAction(partie.getBandits().get(partie.getCompteurJoueur()));
            logUpdate();
        }


    }


}
