package edu.hitsz.bullet;

import edu.hitsz.prop.IPropObserver;

/**
 * Bullet fired by enemy aircraft.
 */
public class EnemyBullet extends BaseBullet implements IPropObserver {

    private boolean isFrozen = false;
    private int freezeTimer = 0;

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void forward() {
        if (isFrozen && freezeTimer > 0) {
            freezeTimer--;
            if (freezeTimer <= 0) {
                isFrozen = false;
                System.out.println("Enemy bullet freeze effect ended.");
            }
            return;
        }

        super.forward();
    }

    @Override
    public void onBombActivated() {
        System.out.println("Enemy bullet cleared by bomb.");
        vanish();
    }

    @Override
    public void onFreezeActivated() {
        System.out.println("Enemy bullet frozen for 5 seconds.");
        this.isFrozen = true;
        this.freezeTimer = 125;
    }
}
