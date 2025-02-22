package vue;

import controleur.Partie;
import modele.*;

import javax.swing.*;
import java.awt.*;

public class VueTrain extends JPanel implements Observer {
    VuePartie vue;
    Partie partie;




    public VueTrain(VuePartie vue) {
        super();
        this.vue = vue;
        this.partie = vue.getJeu();
        partie.getPlateau().addObserver(this);
        GridLayout gl= new GridLayout();
        //Calibration du layout
        gl.setColumns(partie.NB_WAGON);
        gl.setRows(2);
        gl.setHgap(4);
        gl.setVgap(4);

        this.setLayout(gl);
        afficheTrain();
    }

    private void afficheTrain() {
        // pour le toit puis pour l'interieur
        for (int i =1; i>=0;i--) {
            for (int j = 0; j < partie.NB_WAGON; j++)
                this.add(new VueScene(partie.getPlateau().getScene(j, i)));
        }
    }




    public void update() {
        //Suppression des éléments
        this.removeAll();
        //raffichage
        afficheTrain();
        revalidate();
        repaint();
    }


    public static class VueScene extends JPanel implements Observer {
        Scene scene;


        VueScene(Scene s) {
            super();
            //Initialisation du layout
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.scene = s;
            s.addObserver(this);
            this.setLayout(new GridBagLayout());
            this.setPreferredSize(new Dimension(200, 130));
            //Init contraintes
            addPanels();
        }
        void addPanels() {
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.NORTHWEST;
            c.gridx = 0;
            c.gridy = 0;
            c.weighty = 1;
            c.weightx = 1;

            this.add(affichagePerso(), c);
            //Modification des contraintes pour le panel d'objets
            c.gridy = 1;
            c.weighty = 0.25;
            c.anchor = GridBagConstraints.SOUTHWEST;
            this.add(affichageObjet(), c);
        }

        /** affichage des personnages en colonnes, le Marshall est le premier affiché si il est dans la scene**/
        public JPanel affichagePerso(){
            JPanel panel= new JPanel();
            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
            //panel.setMinimumSize(new Dimension(200,80));
            //" Titre " du panel
            panel.add(new JLabel("Personnages :       ",SwingConstants.LEFT));
            //insertion ou non du sheriff
           if (scene.isMarshallHere()) {
                JLabel label = new JLabel("      "+scene.getMarshall().getTag());
                panel.add(label);
            }
            //Insertion de bandits
            for (Bandit b : scene.getArrayBandits()){
                //ImageIcon icon = new ImageIcon("Bandito_texture/bandit" + b.getTag().charAt(b.getTag().length()- 1) + ".png");
                JLabel label = new JLabel("      "+b.getTag());
                //JLabel label = new JLabel(icon);
                panel.add(label);
            }
            return panel;
        }
        /**Affichage des objets sur 2 lignes (Max 6 objets) **/
        public JPanel affichageObjet(){
            JPanel panel = new JPanel(new GridLayout(2,3));
            panel.add(new JLabel("Objets :"));

            for (Objet o : scene.getTresor())
                panel.add(new JLabel(o.getTag()));
            return panel;
        }

        @Override
        public void update() {
            removeAll();
            addPanels();
            revalidate();
            repaint();

        }
        /*//Premier affichage
        public JLabel affichagePerso1() {
            //Jlabel au format html pour le formatage
            String textP = "<html>";
            //Marshall
            if (scene.isMarshallHere())
                textP += scene.getMarshall().getTag();
            textP+="<br>";
            //Bandits
            for (int i = 0; i<; i++){
                if(i<scene.getArrayBandits().size())
                    textP+=scene.getArrayBandits().get(i).getTag();
                textP+="<br>";
            }
            textP+="<html>";
            return new JLabel(textP);
        }
        //Premier affichage
        JLabel affichageObjet1(){
            String textP = "<html>";
            for ( Objet o : scene.getTresor())
                textP+= o.getTag() + " ";
            textP+="<html>";
            return new JLabel(textP);
        }*/


    }
}

