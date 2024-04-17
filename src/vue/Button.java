package vue;

import controleur.Partie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**Classe de base qui relie le bouton a la partie**/
public abstract class Button extends JButton implements ActionListener {

    protected Partie partie;

    public Button(Partie partie, String name){
        super(name);
        this.partie = partie;

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){}


}
