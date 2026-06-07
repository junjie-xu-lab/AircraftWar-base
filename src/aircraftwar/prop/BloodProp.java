package aircraftwar.prop;

import aircraftwar.aircraft.HeroAircraft;
import aircraftwar.application.Main;

/**
 * Restores part of the hero aircraft HP.
 */
public class BloodProp extends BaseProp {

    private int healAmount = 30;

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
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
        hero.increaseHp(healAmount);
    }
}

