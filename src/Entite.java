import org.junit.jupiter.api.*;

abstract class Entite extends Observable {


    protected String tag;
    protected int coordX;
    protected int coordY;

    protected Plateau plateau;

    public Entite(int x,int y,String tag,Plateau p){
        this.coordX = x;
        this.coordY = y;
        this.tag = tag;
        this.plateau = p;
    }



    public int getCoordX(){
        return coordX;
    }

    public int getCoordY(){
        return coordY;
    }
    public String getTag() {
        return tag;
    }


}
