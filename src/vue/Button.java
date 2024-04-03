package vue;

import modele.Jeu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Button extends JButton implements ActionListener {

    protected Jeu jeu;

    public Button(Jeu jeu, String name){
        super(name);
        this.jeu=jeu;

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){}


}
