package aircraftwar.prop;

import aircraftwar.aircraft.HeroAircraft;

/**
 * Common behavior for collectible props.
 */
public interface IProp {

    void forward();

    void activate(HeroAircraft hero);

    boolean notValid();

    void vanish();

    int getLocationX();

    int getLocationY();
}


