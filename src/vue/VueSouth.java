package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VueSouth extends JPanel {
    VuePartie vueParent;

    JLabel logLine = new JLabel();


    public VueSouth(VuePartie vue){
        this.vueParent=vue;
        this.setLayout(new BorderLayout());
        //Ajout des éléments avec cardinalité (border layout )
        this.add(new ActionButton(),BorderLayout.WEST);
        this.add(new CancelButton(),BorderLayout.EAST);
        this.add(logLine,BorderLayout.NORTH);
        this.logLine.setText(vueParent.getJeu().getLog());
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
        if(!vueParent.getJeu().isActionStage()) this.logLine.setText("Phase de préparation !");
        this.logLine.setText(vueParent.getJeu().getLog());
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
