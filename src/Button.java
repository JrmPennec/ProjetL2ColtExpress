import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract class Button extends JButton implements ActionListener {

    protected VueJeu interfaceJeu;
    public Button(VueJeu jeu,String name){
        super(name);
        this.interfaceJeu=jeu;

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){}


}
