package vue;

import controleur.Jeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class VueIntro extends JPanel implements ActionListener, ItemListener {
    Jeu jeu;
    JComboBox<Integer> comboBoxJoueurs;
    JComboBox<Integer> comboBoxTours;
    JComboBox<Integer> comboBoxActions;
    JComboBox<Integer> comboBoxWagons;
    JButton play;

    VueIntro(Jeu jeu){
        //Init
        this.jeu=jeu;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        //Création du bouton
        play = new JButton("JOUER");
        play.addActionListener(this);
        //Ajout des éléments du panel
        this.add(play);
        this.add(initComboBoxJoueurs());
        this.add(initComboBoxTours());
        this.add(initComboBoxActions());
        this.add(initComboBoxWagons());
    }
    JPanel initComboBoxJoueurs() {
        JPanel pan = new JPanel();
        //Création du tableau des valeurs autorisées
        Integer[] joueurs = {2, 3, 4, 5, 6};
        comboBoxJoueurs = new JComboBox<>(joueurs);
        //Liaison au item listener de VueIntro
        comboBoxJoueurs.addItemListener(this);
        pan.add(new JLabel("Nombre de joueurs : "));
        pan.add(comboBoxJoueurs);
        return pan;
    }

     JPanel initComboBoxTours(){
        JPanel pan= new JPanel();
         //Création du tableau des valeurs autorisées
        Integer[] tours={4,6,8,10};
        comboBoxTours=new JComboBox<>(tours);
         //Liaison au item listener de VueIntro
        comboBoxTours.addItemListener(this);
        pan.add(new JLabel("Nombre de tours : "));
        pan.add(comboBoxTours);
        return pan;

    }
    JPanel initComboBoxActions(){
        JPanel pan= new JPanel();
        //Création du tableau des valeurs autorisées
        Integer[] actions={1,2,3,4};
        comboBoxActions= new JComboBox<>(actions);
        //Liaison au item listener de VueIntro
        comboBoxActions.addItemListener(this);
        pan.add(new JLabel("Nombre d'actions : "));
        pan.add(comboBoxActions);
        return pan;
    }

    JPanel initComboBoxWagons() {
    JPanel pan = new JPanel();
        //Création du tableau des valeurs autorisées
    Integer[] wagons={2,4,6};
    comboBoxWagons= new JComboBox<>(wagons);
        //Liaison au item listener de VueIntro
    comboBoxWagons.addItemListener(this);
    pan.add(new JLabel("Nombre de wagons : "));
    pan.add(comboBoxWagons);
    return pan;
}

    @Override
    /** Lance la partie quand on appuie sur un bouton **/
    public void actionPerformed(ActionEvent actionEvent) {
        jeu.lancerPartie();

    }

    @Override
    /** change les valeur pour la construction de partie en fonction de la combobox utilisée **/
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource()==comboBoxJoueurs) {
            jeu.setNbJoueurs((Integer) comboBoxJoueurs.getSelectedItem());
        }
        if (e.getSource()==comboBoxTours)
            jeu.setNbTours((Integer)comboBoxTours.getSelectedItem());
        if (e.getSource()==comboBoxActions)
            jeu.setNbActions((Integer)comboBoxActions.getSelectedItem());
        if (e.getSource()==comboBoxWagons)
            jeu.setNbWagons((Integer)comboBoxWagons.getSelectedItem());

        System.out.println("Joueurs :"+jeu.getNbJoueurs()+ " Tours = "+jeu.getNbTours()+" Actions = "+jeu.getNbActions()+ "Wagons = "+jeu.getNbWagons());
    }
}
