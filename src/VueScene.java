import javax.swing.*;

public class VueScene extends JPanel {
    JLabel PersosList;
    JLabel Objets;


    VueScene(Scene s) {
        super();
        //on doit utiliser html pour les jlabel
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
        String textO = " Objet(s) : ";

    }

    static public void main(String[] args) {
        Jeu test= new Jeu();
        JFrame j = new JFrame("test");
        VueScene v = new VueScene(test.getPlateau().getScene(0, 1));
        VueScene v2 = new VueScene(test.getPlateau().getScene(1, 1));
        j.add(v);
        j.add(v2);
        j.pack();
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setVisible(true);
    }

}

