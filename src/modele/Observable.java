package modele;
import vue.Observer;
import java.util.ArrayList;

public abstract class Observable {
    private ArrayList<Observer> observers;
    public Observable(){
        this.observers= new ArrayList<Observer>();
    }
    public void addObserver(Observer o){
        observers.add(o);
    }
    public void notifyObservers(){
        for (Observer o : observers){
            o.update();
        }
    }
}

