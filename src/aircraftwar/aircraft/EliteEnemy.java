package aircraftwar.aircraft;

import aircraftwar.application.Main;
import aircraftwar.bullet.BaseBullet;
import aircraftwar.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * Elite enemy that can fire a single bullet.
 */
public class EliteEnemy extends AbstractAircraft {

    private int shootNum = 1;
    private int power = 10;
    private int direction = 1;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        if (isFrozen) {
            updateFreezeTimer();
            return;
        }

        super.forward();
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        if (isFrozen) {
            return new LinkedList<>();
        }

        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 30;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 5;

        for (int i = 0; i < shootNum; i++) {
            BaseBullet bullet = new EnemyBullet(
                    x + (i * 2 - shootNum + 1) * 10,
                    y,
                    speedX,
                    speedY,
                    power
            );
            res.add(bullet);
        }
        return res;
    }

    @Override
    public void onBombActivated() {
        System.out.println("Elite enemy destroyed by bomb.");
        vanish();
    }

    @Override
    public void onFreezeActivated() {
        System.out.println("Elite enemy frozen for 4 seconds.");
        activateFreezeEffect(100);
    }

}



