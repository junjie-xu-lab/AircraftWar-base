package aircraftwar.prop;

import aircraftwar.aircraft.HeroAircraft;
import aircraftwar.application.AudioManager;
import aircraftwar.application.Main;

/**
 * Bomb prop sound and pickup behavior. The game panel applies the clear-screen effect.
 */
public class BombProp extends AbstractProp {

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
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
        System.out.println("Bomb prop activated.");
        AudioManager.getInstance().playBombExplosion();
    }

    @Override
    public void notifyObservers() {
        for (IPropObserver observer : observers) {
            observer.onBombActivated();
        }
        System.out.println("Bomb notified " + observers.size() + " observers.");
    }
}

