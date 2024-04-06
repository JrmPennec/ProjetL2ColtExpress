package vue;

import modele.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class VueTrain extends JPanel implements Observer {
    VueJeu vue;
    Jeu jeu;
    Border border;


    public VueTrain(VueJeu vue){
        super();
        this.vue=vue;
        this.jeu = vue.getJeu();
        this.border = BorderFactory.createLineBorder(Color.BLACK);
        GridLayout gl = new GridLayout();
        gl.setRows(2);
        gl.setColumns(Jeu.NB_WAGON);
        gl.setVgap(2);
        gl.setHgap(2);

        this.setLayout(gl);
        afficheTrain();

    }

    private void afficheTrain() {
        for (int i = 1; i >= 0; i--) {
            for (int j = 0; j < Jeu.NB_WAGON; j++) {
                VueScene vs = new VueTrain.VueScene(jeu.getPlateau().getScene(j, i));
                vs.setBorder(border);
                this.add(vs);
            }
        }
    }

    public void update(){
        this.removeAll();
        afficheTrain();
        revalidate();
        repaint();
    }


    public static class VueScene extends JPanel {
        VueScene(Scene s) {
            super();
            GridBagLayout layout = new GridBagLayout();
            //possibilité de modifier ici les caractéristiques du layout
            this.setLayout(layout);
            GridBagConstraints c = new GridBagConstraints();
            c.gridx=0;
            c.gridy=0;
            c.gridheight=1;
            c.gridwidth=2;
            if (s.isMarshallHere())
                this.add(new JLabel(s.getMarshall().getTag()),c);
            for (int i=0;i<s.getBandits().size();i++){
                c.gridy=1+i;
                this.add(new JLabel("Bandit "+s.getArrayBandits().get(i).getTag()+"      "),c);
            }
            c.gridy=Jeu.NB_JOUEURS+2;
            c.gridwidth=2;
            GridLayout gl= new GridLayout(1,s.getTresor().size());
                for (Objet o : s.getTresor()) {
                    this.add(new JLabel(o.getTag()));
                }
            }







            /*//on doit utiliser html pour les jlabel
            String textP = "<html> Personnages : <br/>";
            if (!s.getBandits().isEmpty()) {
                for (Personnage p : s.getBandits().values())
                    textP += p.getTag() + "<br/";
                if(s.isMarshallHere())
                    textP+=s.getMarshall().getTag() + "<br/>";
                textP += "<html>";

            }
            PersosList = new JLabel(textP);
            this.add(PersosList);
            //Pas encore implementée la  suite
            String textO = " modele.Objet(s) : ";*/

        }

}

