package aircraftwar.strategy;

import aircraftwar.aircraft.AbstractAircraft;
import aircraftwar.bullet.BaseBullet;

import java.util.List;

public class ShootContext {

    private IShootStrategy strategy;

    public ShootContext(IShootStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(IShootStrategy strategy) {
        this.strategy = strategy;
    }

    public List<BaseBullet> execute(AbstractAircraft host, int power) {
        return strategy.shoot(host, power);
    }
}

