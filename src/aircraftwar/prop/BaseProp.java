package aircraftwar.prop;

import aircraftwar.aircraft.HeroAircraft;
import aircraftwar.basic.AbstractFlyingObject;

/**
 * Shared base class for collectible props.
 */
public abstract class BaseProp extends AbstractFlyingObject implements IProp {

    public BaseProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public abstract void activate(HeroAircraft hero);
}



