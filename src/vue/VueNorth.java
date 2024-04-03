package vue;

import modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VueNorth extends JPanel {
    VueJeu vueParent;
    Jeu jeu;


    public VueNorth(VueJeu vue) {
        super();
        this.vueParent = vue;
        this.jeu = vueParent.getJeu();
        this.setLayout(new GridBagLayout());
        GridBagConstraints c= new GridBagConstraints();
        c.gridy= 0;
        c.gridx=0;
        c.gridwidth=1;
        c.gridheight=2;
        this.add(new PanelDeplacement(),c);
        c.gridy= 0;
        c.gridx=2;
        this.add(new PanelTir(),c);

    }




    public class PanelDeplacement extends JPanel {

        VueJeu vuejeu;

        public PanelDeplacement() {
            super();
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 0;
            this.add(new DeplaceButton(DIRECTION.HAUT), c);
            c.gridy = 1;
            c.gridx = 0;
            this.add(new DeplaceButton(DIRECTION.GAUCHE), c);
            c.gridx = 1;
            this.add(new JLabel("MOVE"), c);
            c.gridx = 2;
            this.add(new DeplaceButton(DIRECTION.DROITE), c);
            c.gridx = 1;
            c.gridy = 2;
            this.add(new DeplaceButton(DIRECTION.BAS), c);
        }

        class DeplaceButton extends Button {
            DIRECTION dir;

            public DeplaceButton(DIRECTION dir) {
                super(vueParent.getJeu(), "");
                this.dir = dir;
                switch (dir) {
                    case GAUCHE:
                        this.setText("<");
                        break;
                    case HAUT:
                        this.setText("^");
                        break;
                    case DROITE:
                        this.setText(">");
                        break;
                    case BAS:
                        this.setText("v");
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


        public PanelTir() {
            super();
            this.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 0;
            this.add(new tirButton(DIRECTION.HAUT), c);
            c.gridy = 1;
            c.gridx = 0;
            this.add(new tirButton(DIRECTION.GAUCHE), c);
            c.gridx = 1;
            this.add(new JLabel("SHOT"), c);
            c.gridx = 2;
            this.add(new tirButton(DIRECTION.DROITE), c);
            c.gridx = 1;
            c.gridy = 2;
            this.add(new tirButton(DIRECTION.BAS), c);
        }

        class tirButton extends Button {
            DIRECTION dir;

            public tirButton(DIRECTION dir) {
                super(vueParent.getJeu(), "");
                this.dir = dir;
                switch (dir) {
                    case GAUCHE:
                        this.setText("<");
                        break;
                    case HAUT:
                        this.setText("^");
                        break;
                    case DROITE:
                        this.setText(">");
                        break;
                    case BAS:
                        this.setText("v");
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

    public class PanelMiddle extends JPanel {
        JLabel ActionsRestantes;
        JLabel TourJoueur;
        BraqueButton braquebutton;


        public class BraqueButton extends Button{
            BraqueButton(){
                super(VueNorth.this.jeu,"Braque?");
            }
        }

    }
}



