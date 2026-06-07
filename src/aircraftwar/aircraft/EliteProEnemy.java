package aircraftwar.aircraft;

import aircraftwar.application.Main;
import aircraftwar.bullet.BaseBullet;
import aircraftwar.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * Ace enemy that slows instead of stopping when frozen.
 */
public class EliteProEnemy extends AbstractAircraft {

    private int power = 20;

    private boolean isSlowed = false;
    private int slowTimer = 0;
    private int originalSpeedX;
    private int originalSpeedY;

    public EliteProEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        if (isSlowed && slowTimer > 0) {
            slowTimer--;
            if (slowTimer <= 0) {
                isSlowed = false;
                speedX = originalSpeedX;
                speedY = originalSpeedY;
                System.out.println("Ace enemy slow effect ended.");
            }
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
        int y = this.getLocationY() + 30;

        for (int i = 0; i < 3; i++) {
            BaseBullet bullet = new EnemyBullet(x, y, (i - 1) * 3, 5, power);
            res.add(bullet);
        }
        return res;
    }

    @Override
    public void onBombActivated() {
        System.out.println("Ace enemy took 50 bomb damage.");
        decreaseHp(50);
    }

    @Override
    public void onFreezeActivated() {
        System.out.println("Ace enemy slowed for 5 seconds.");

        if (!isSlowed) {
            originalSpeedX = speedX;
            originalSpeedY = speedY;

            if (speedX > 0) {
                speedX = Math.max(1, speedX / 2);
            } else if (speedX < 0) {
                speedX = Math.min(-1, speedX / 2);
            }

            if (speedY > 0) {
                speedY = Math.max(1, speedY / 2);
            } else if (speedY < 0) {
                speedY = Math.min(-1, speedY / 2);
            }
        }

        isSlowed = true;
        slowTimer = 125;
    }
}





