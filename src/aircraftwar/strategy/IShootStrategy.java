package aircraftwar.strategy;

import aircraftwar.aircraft.AbstractAircraft;
import aircraftwar.bullet.BaseBullet;

import java.util.List;

/**
 * Strategy interface for bullet patterns.
 */
public interface IShootStrategy {
    List<BaseBullet> shoot(AbstractAircraft host, int power);
}

