public abstract class Entite extends Observable {
    protected String tag;
    protected int coordX;
    protected int etage;

    protected Plateau plateau;

    public int getCoordX(){
        return this.coordX;
    }

    public int getEtage(){
        return this.etage;
    }

}
