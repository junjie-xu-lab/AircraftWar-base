package aircraftwar.strategy;

import aircraftwar.aircraft.AbstractAircraft;
import aircraftwar.bullet.BaseBullet;
import aircraftwar.bullet.EnemyBullet;
import aircraftwar.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class SpreadShootStrategy implements IShootStrategy {

    private int bulletCount = 3;

    @Override
    public List<BaseBullet> shoot(AbstractAircraft host, int power) {
        List<BaseBullet> res = new LinkedList<>();
        int x = host.getLocationX();
        int y = host.getLocationY();

        if (host.getClass().getSimpleName().contains("Hero")) {
            for (int i = 0; i < bulletCount; i++) {
                int speedX = (i - 1) * 3;
                res.add(new HeroBullet(x, y - 2, speedX, -5, power));
            }
        } else {
            for (int i = 0; i < bulletCount; i++) {
                int speedX = (i - 1) * 3;
                res.add(new EnemyBullet(x, y + 30, speedX, 5, power));
            }
        }
        return res;
    }
}

