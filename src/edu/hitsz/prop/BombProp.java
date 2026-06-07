package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.AudioManager;
import edu.hitsz.application.Main;

/**
 * Bomb prop that notifies enemies and enemy bullets.
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

        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (IPropObserver observer : observers) {
            observer.onBombActivated();
        }
        System.out.println("Bomb notified " + observers.size() + " observers.");
    }
}
