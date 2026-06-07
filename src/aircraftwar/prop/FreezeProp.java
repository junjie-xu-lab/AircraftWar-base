package aircraftwar.prop;

import aircraftwar.aircraft.HeroAircraft;
import aircraftwar.application.Main;

/**
 * Freeze prop that notifies enemies and enemy bullets.
 */
public class FreezeProp extends AbstractProp {

    public FreezeProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public void activate(HeroAircraft hero) {
        System.out.println("Freeze prop activated.");

        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (IPropObserver observer : observers) {
            observer.onFreezeActivated();
        }
        System.out.println("Freeze notified " + observers.size() + " observers.");
    }
}

