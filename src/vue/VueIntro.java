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
        this.jeu=jeu;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        play = new JButton("JOUER");
        play.addActionListener(this);
        this.add(play);
        this.add(initComboBoxJoueurs());
        this.add(initComboBoxTours());
        this.add(initComboBoxActions());
        this.add(initComboBoxWagons());
    }
    JPanel initComboBoxJoueurs() {
        JPanel pan = new JPanel();
        Integer[] joueurs = {2, 3, 4, 5, 6};
        comboBoxJoueurs = new JComboBox<>(joueurs);
        comboBoxJoueurs.addItemListener(this);
        pan.add(new JLabel("Nombre de joueurs : "));
        pan.add(comboBoxJoueurs);
        return pan;
    }

     JPanel initComboBoxTours(){
        JPanel pan= new JPanel();
        Integer[] tours={4,6,8,10};
        comboBoxTours=new JComboBox<>(tours);
        comboBoxTours.addItemListener(this);
        pan.add(new JLabel("Nombre de tours : "));
        pan.add(comboBoxTours);
        return pan;

    }
    JPanel initComboBoxActions(){
        JPanel pan= new JPanel();
        Integer[] actions={1,2,3,4};
        comboBoxActions= new JComboBox<>(actions);
        comboBoxActions.addItemListener(this);
        pan.add(new JLabel("Nombre d'actions : "));
        pan.add(comboBoxActions);
        return pan;
    }

    JPanel initComboBoxWagons() {
    JPanel pan = new JPanel();
    Integer[] wagons={2,4,6};
    comboBoxWagons= new JComboBox<>(wagons);
    comboBoxWagons.addItemListener(this);
    pan.add(new JLabel("Nombre de wagons : "));
    pan.add(comboBoxWagons);
    return pan;
}

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        jeu.lancerPartie();

    }

    @Override
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
