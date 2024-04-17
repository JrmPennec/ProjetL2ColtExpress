package vue;

import controleur.Partie;
import modele.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
/** Affichage du panel de planification **/
public class VueNorth extends JPanel  {
    VuePartie vueParent;
    Partie partie;


    public VueNorth(VuePartie vue) {
        //Init attributs
        super();
        this.vueParent = vue;
        this.partie = vueParent.getJeu();
        //Init du layout du panel
        this.setLayout(new GridBagLayout());
        GridBagConstraints c= new GridBagConstraints();
        //Placement du premier panel
        c.gridy= 0;
        c.gridx=0;
        c.gridwidth=1;
        c.gridheight=2;
        c.insets=new Insets(10,10,10,10);
        this.add(new PanelDeplacement(),c);
        //Placement du deuxieme panel
        c.gridy= 0;
        c.gridx=2;
        this.add(new PanelTir(),c);
        //Placement du 3eme panel
        c.gridx=1;
        c.gridy=0;
        c.gridheight=3;
        this.add(new MiddlePanel(),c);

    }

    public class PanelDeplacement extends JPanel {

        VuePartie vuejeu;

        public PanelDeplacement() {
            super();
            //Init layout puis placement de chaque bouttons grave au GridBagConstraint c
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
                partie.ajouteAction(partie.getBandits().get(partie.getCompteurJoueur()), deplacement);
            }


        }
    }

    public class PanelTir extends JPanel {


        public PanelTir() {
            super();
            //Init layout puis placement de chaque bouttons grave au GridBagConstraint c
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
        //Creation du bouton et de son interaction
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
                //Création d'une input et envoi au bon joueur
                Input tir = new Input(dir, ACTION.TIR);
                partie.ajouteAction(partie.getBandits().get(partie.getCompteurJoueur()), tir);
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
            //Récupération de l'information des tours
            partie.addObserver(this);
            // Compteur d'action + phase
            phase = new JLabel("Planning Phase");
            actionsRestantes = new JLabel("Action(s) Restante(s) : " + partie.getActionsRestantes());
            // Affichage du joueur en cours de planification
            tourJoueur = new JLabel(("Joueur n ° " + (partie.getCompteurJoueur() + 1) ));

            braqueButton= new BraqueButton();
            //Placement dans le panel
            this.setLayout(new GridLayout(4,1));
            //Ajout des éléments au panel
            this.add(phase);
            this.add(actionsRestantes);
            this.add(tourJoueur);
            this.add(braqueButton);
        }

        @Override
        public void update() {
            //Mise a jour du text
            actionsRestantes.setText("Action(s) Restante(s) : " + partie.getActionsRestantes());
            if (partie.isActionStage()) {
                phase.setText("Action Phase");
                tourJoueur.setText("Tous les joueurs");
            } else {
                phase.setText("Planning Phase");
                tourJoueur.setText("Joueur n °" + (partie.getCompteurJoueur() + 1));
            }
        }

        public class BraqueButton extends Button{
            BraqueButton(){
                super(VueNorth.this.partie,"Braque");
                addActionListener(this);
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Input braque = new Input(DIRECTION.NEUTRAL,ACTION.BRAQUE);
                partie.ajouteAction(partie.getBandits().get(partie.getCompteurJoueur()),braque);

            }
        }



    }
}



