package vue;

import modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VueNorth extends JPanel  {
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
        c.insets=new Insets(10,10,10,10);
        this.add(new PanelDeplacement(),c);
        c.gridy= 0;
        c.gridx=2;
        this.add(new PanelTir(),c);
        c.gridx=1;
        c.gridy=0;
        c.gridheight=3;
        this.add(new MiddlePanel(),c);

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
        //Création du bouton et de son interaction
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
        //Creation du button et de son interaction
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

    public class MiddlePanel extends JPanel implements Observer {
        JLabel phase;
        JLabel actionsRestantes;
        JLabel tourJoueur;
        BraqueButton braqueButton;
        public MiddlePanel() {
            super();
            jeu.addObserver(this);
            phase = new JLabel("Planning Phase");
            actionsRestantes = new JLabel("Action(s) Restante(s) : " + jeu.getActionsRestantes());
            tourJoueur = new JLabel(("Joueur n ° " + (jeu.getCompteurJoueur() + 1) ));
            braqueButton= new BraqueButton();
            this.setLayout(new GridLayout(4,1));
            this.add(phase);
            this.add(actionsRestantes);
            this.add(tourJoueur);
            this.add(braqueButton);
        }

        @Override
        public void update() {

            actionsRestantes.setText("Action(s) Restante(s) : " + jeu.getActionsRestantes());
            if (jeu.isActionStage()) {
                phase.setText("Action Phase");
                tourJoueur.setText("Tous les joueurs");
            } else {
                phase.setText("Planning Phase");
                tourJoueur.setText("Joueur n °" + (jeu.getCompteurJoueur() + 1));
            }
        }

        public class BraqueButton extends Button{
            BraqueButton(){
                super(VueNorth.this.jeu,"Braque?");
            }
        }

    }
}



