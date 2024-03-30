package vue;

import modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;

public class VueInteraction extends JPanel {
    VueJeu InterfaceJeu;
    Jeu jeu;

    private boolean actionStage;

    boolean isActionStage() {
        return actionStage;
    }

    public VueInteraction(VueJeu iJeu) {
        super();
        this.InterfaceJeu = iJeu;
        this.jeu = InterfaceJeu.getJeu();
        this.setPreferredSize(new Dimension(InterfaceJeu.width/3,InterfaceJeu.height/3));
        this.setLayout(new BorderLayout());
        this.add(new PanelDeplacement(),BorderLayout.WEST);
        this.add(new ActionButton(),BorderLayout.CENTER);
        this.add(new PanelTir(),BorderLayout.EAST);

    }

    class ActionButton extends Button {

        public ActionButton() {
            super(InterfaceJeu, "Action!");
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (isActionStage()) {
                jeu.actionPhase();
            }
        }


    }


    public class PanelDeplacement extends JPanel {

        VueJeu vuejeu;

        public PanelDeplacement() {
            super();
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridheight = 3;
            c.gridwidth = 3;
            c.gridx = 1;
            c.gridy = 0;
            this.add(new DeplaceButton(DIRECTION.HAUT), c);
            c.gridy = 1;
            c.gridx = 0;
            this.add(new DeplaceButton(DIRECTION.GAUCHE), c);
            c.gridx = 1;
            this.add(new JLabel("DEPLACEMENT"), c);
            c.gridx = 2;
            this.add(new DeplaceButton(DIRECTION.DROITE), c);
            c.gridx = 1;
            c.gridy = 2;
            this.add(new DeplaceButton(DIRECTION.BAS), c);
        }

        class DeplaceButton extends Button {
            DIRECTION dir;

            public DeplaceButton(DIRECTION dir) {
                super(vuejeu, "");
                this.dir = dir;
                switch (dir) {
                    case GAUCHE:
                        this.setName("<");
                        break;
                    case HAUT:
                        this.setName("^");
                        break;
                    case DROITE:
                        this.setName(">");
                        break;
                    case BAS:
                        this.setName("v");
                        break;
                }
                addActionListener(this);
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Input deplacement = new Input(dir, ACTION.DEPLACE);
                jeu.ajouteAction(jeu.getBandits().get(jeu.getCompteurJoueur()), deplacement);
            }


        }
    }

    public class PanelTir extends JPanel {

        VueJeu vuejeu;

        public PanelTir() {
            super();
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridheight = 3;
            c.gridwidth = 3;
            c.gridx = 1;
            c.gridy = 0;
            this.add(new tirButton(DIRECTION.HAUT), c);
            c.gridy = 1;
            c.gridx = 0;
            this.add(new tirButton(DIRECTION.GAUCHE), c);
            c.gridx = 1;
            this.add(new JLabel("TIR"), c);
            c.gridx = 2;
            this.add(new tirButton(DIRECTION.DROITE), c);
            c.gridx = 1;
            c.gridy = 2;
            this.add(new tirButton(DIRECTION.BAS), c);
        }

        class tirButton extends Button {
            DIRECTION dir;

            public tirButton(DIRECTION dir) {
                super(InterfaceJeu, "");
                this.dir = dir;
                switch (dir) {
                    case GAUCHE:
                        this.setName("<");
                        break;
                    case HAUT:
                        this.setName("^");
                        break;
                    case DROITE:
                        this.setName(">");
                        break;
                    case BAS:
                        this.setName("v");
                        break;
                }
                addActionListener(this);
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Input tir = new Input(dir, ACTION.TIR);
                jeu.ajouteAction(jeu.getBandits().get(jeu.getCompteurJoueur()), tir);
            }

        }


    }
}



