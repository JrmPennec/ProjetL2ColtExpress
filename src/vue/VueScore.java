package vue;

import controleur.Jeu;
import controleur.Partie;
import modele.Bandit;

import javax.swing.*;
import java.util.ArrayList;

public class VueScore extends JPanel {
    private Partie partie;
    //Liste qui permet de ranger les joueurs du meilleur score au moins bon (pas d'egalité prévu pour le moment)
    private ArrayList<Bandit> rankList;

    VueScore(Partie partie){
        super();
        this.partie =partie ;
        this.rankList = new ArrayList<>();
        calculRankList();
        afficheScore();

    }
    void afficheScore(){
        //mise en place d'un layout  pour poser les elements en vertical
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        //Affichage du vainqueur
        this.add(new JLabel("VAINQUEUR : "+rankList.get(0).getTag()));
        //Affichage des autres joueurs
        for (int i = 1; i< partie.NB_JOUEURS; i++){
            this.add(new JLabel((i+1)+"EME :"+rankList.get(i).getTag()));
        }
    }
     void calculRankList(){
        for(int i = 0; i< partie.NB_JOUEURS; i++) {
            Bandit target=null;
            for (Bandit b : partie.getBandits()){
                //Cas déja traité
                if (rankList.contains(b))
                    continue;
                //Cas de depart
                else if (target==null)
                    target=b;
                //Cas de comparaison
                else if (b.getTotalValeur()>target.getTotalValeur())
                        target=b;
            }
          rankList.add(target);
        }
    }


}
