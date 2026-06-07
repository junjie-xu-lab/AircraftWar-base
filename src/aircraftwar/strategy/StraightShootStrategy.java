package aircraftwar.strategy;

import aircraftwar.aircraft.AbstractAircraft;
import aircraftwar.bullet.BaseBullet;
import aircraftwar.bullet.EnemyBullet;
import aircraftwar.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class StraightShootStrategy implements IShootStrategy {

    @Override
    public List<BaseBullet> shoot(AbstractAircraft host, int power) {
        List<BaseBullet> res = new LinkedList<>();
        int x = host.getLocationX();
        int y = host.getLocationY();

        if (host.getClass().getSimpleName().contains("Hero")) {
            res.add(new HeroBullet(x, y - 2, 0, -5, power));
        } else {
            res.add(new EnemyBullet(x, y + 30, 0, 5, power));
        }
        return res;
    }
}

