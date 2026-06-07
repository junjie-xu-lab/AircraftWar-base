package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * Advanced elite enemy that moves sideways and fires two bullets.
 */
public class ElitePlusEnemy extends AbstractAircraft {

    private int power = 15;

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
        int y = this.getLocationY() + 30;

        for (int i = 0; i < 2; i++) {
            BaseBullet bullet = new EnemyBullet(x + (i * 2 - 1) * 20, y, 0, 5, power);
            res.add(bullet);
        }
        return res;
    }

    @Override
    public void onBombActivated() {
        System.out.println("Advanced elite enemy destroyed by bomb.");
        vanish();
    }

    @Override
    public void onFreezeActivated() {
        System.out.println("Advanced elite enemy frozen for 3 seconds.");
        activateFreezeEffect(75);
    }
}




