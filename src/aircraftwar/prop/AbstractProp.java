package aircraftwar.prop;

import aircraftwar.aircraft.HeroAircraft;

import java.util.ArrayList;
import java.util.List;

/**
 * Base prop type that acts as the subject in the observer pattern.
 */
public abstract class AbstractProp extends BaseProp {

    protected List<IPropObserver> observers = new ArrayList<>();

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void registerObserver(IPropObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(IPropObserver observer) {
        observers.remove(observer);
    }

    public abstract void notifyObservers();
}


