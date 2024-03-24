import javax.swing.*;

public class VueScene extends JPanel {
    JLabel PersosList;
    JLabel Objets;


    VueScene(Scene s) {
        //on doit utiliser html pour les jlabel
        String textP = "<html> Personnages : <br/>";
        for (Personnage p : s.getPersos().values())
            textP +=p.getTag() + "<br/";
            textP+= "<html>";
        PersosList = new JLabel(textP);
        this.add(PersosList);
        String textO = " Objet(s) : ";

    }

    static public void main(String[] args) {
        Jeu test= new Jeu();
        JFrame j = new JFrame("test");
        VueScene v = new VueScene(test.getPlateau().getScene(0, 1));
        j.add(v);
        j.pack();
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setVisible(true);
    }

}

