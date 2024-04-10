package vue;

import com.sun.org.apache.xml.internal.security.Init;
import modele.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class VueTrain extends JPanel implements Observer {
    VueJeu vue;
    Jeu jeu;




    public VueTrain(VueJeu vue) {
        super();
        this.vue = vue;
        this.jeu = vue.getJeu();
        jeu.getPlateau().addObserver(this);
        GridLayout gl= new GridLayout();
        gl.setColumns(Jeu.NB_WAGON);
        gl.setRows(2);
        gl.setHgap(4);
        gl.setVgap(4);

        this.setLayout(gl);
        afficheTrain();
    }

    private void afficheTrain() {
        for (int i =1; i>=0;i--) {
            for (int j = 0; j < Jeu.NB_WAGON; j++)
                this.add(new VueScene(jeu.getPlateau().getScene(j, i)));
        }
    }




    public void update() {
        this.removeAll();
        afficheTrain();
        revalidate();
        repaint();
    }


    public static class VueScene extends JPanel {
        Scene scene;


        VueScene(Scene s) {
            super();
            //Initialisation du layout
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.scene = s;
            this.setLayout(new GridBagLayout());
            this.setPreferredSize(new Dimension(200,130));
            //Init contraintes
            GridBagConstraints c = new GridBagConstraints();
            c.fill=GridBagConstraints.BOTH;
            c.anchor=GridBagConstraints.NORTH;
            c.gridx=0;
            c.gridy=0;
            c.weighty=0.75;
            c.weightx=1;

            this.add(affichagePerso(),c);
            //Modification des contraintes pour le panel d'objets
            c.gridy=1;
            c.weighty=0.25;
            c.anchor=GridBagConstraints.SOUTHWEST;
            this.add(affichageObjet(),c);
        }
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
                JLabel label = new JLabel("      "+b.getTag());
                panel.add(label);
            }
            return panel;
        }

        public JPanel affichageObjet(){
            JPanel panel = new JPanel(new GridLayout(2,3));
            panel.add(new JLabel("Objets :"));
            for (Objet o : scene.getTresor())
                panel.add(new JLabel(o.getTag()));
            return panel;
        }
        //Premier affichage
        public JLabel affichagePerso1() {
            //Jlabel au format html pour le formatage
            String textP = "<html>";
            //Marshall
            if (scene.isMarshallHere())
                textP += scene.getMarshall().getTag();
            textP+="<br>";
            //Bandits
            for (int i=0; i<Jeu.NB_JOUEURS;i++){
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
        }


    }
}

