package aircraftwar.strategy;

import aircraftwar.aircraft.AbstractAircraft;
import aircraftwar.bullet.BaseBullet;
import aircraftwar.bullet.EnemyBullet;
import aircraftwar.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class CircleShootStrategy implements IShootStrategy {

    private int bulletCount = 20;

    @Override
    public List<BaseBullet> shoot(AbstractAircraft host, int power) {
        List<BaseBullet> res = new LinkedList<>();
        int x = host.getLocationX();
        int y = host.getLocationY();

        for (int i = 0; i < bulletCount; i++) {
            double angle = 2 * Math.PI * i / bulletCount;
            int speedX = (int) (Math.cos(angle) * 4);
            int speedY = (int) (Math.sin(angle) * 4);
            if (host.getClass().getSimpleName().contains("Hero")) {
                res.add(new HeroBullet(x, y, speedX, speedY, power));
            } else {
                res.add(new EnemyBullet(x, y, speedX, speedY, power));
            }
        }
        return res;
    }
}

