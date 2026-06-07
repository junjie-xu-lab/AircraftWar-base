package aircraftwar.aircraft;

import aircraftwar.application.Main;
import aircraftwar.bullet.BaseBullet;
import aircraftwar.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * Boss enemy with high HP and a radial bullet pattern.
 */
public class BossEnemy extends AbstractAircraft {

    private int power = 25;
    private int shootCycle = 40;
    private int shootCounter = 0;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        if (isFrozen) {
            updateFreezeTimer();
        }

        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT * 0.15) {
            speedY = 0;
            locationY = (int) (Main.WINDOW_HEIGHT * 0.15);
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        shootCounter++;
        if (shootCounter >= shootCycle) {
            shootCounter = 0;
            List<BaseBullet> res = new LinkedList<>();
            int x = this.getLocationX();
            int y = this.getLocationY();

            for (int i = 0; i < 20; i++) {
                double angle = 2 * Math.PI * i / 20;
                int speedX = (int) (Math.cos(angle) * 4);
                int speedY = (int) (Math.sin(angle) * 4);
                res.add(new EnemyBullet(x, y, speedX, speedY, power));
            }
            return res;
        }
        return new LinkedList<>();
    }

    @Override
    public void onBombActivated() {
        System.out.println("Boss ignored bomb effect.");
    }

    @Override
    public void onFreezeActivated() {
        System.out.println("Boss ignored freeze effect.");
    }
}




