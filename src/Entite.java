public abstract class Entite extends Observable {


    protected String tag;
    protected int coordX;
    protected int coordY;

    protected Plateau plateau;



    public int getCoordX(){
        return this.coordX;
    }

    public int getCoordY(){
        return this.coordY;
    }
    public String getTag() {
        return tag;
    }
}
